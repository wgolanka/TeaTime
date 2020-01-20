package com.teatime.domain.tea

import com.teatime.domain.accessory.AccessoryRepository
import com.teatime.domain.tea.config.BrewingConfigRepository
import com.teatime.domain.tea.config.BrewingConfiguration
import com.teatime.domain.user.UserService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class TeaService(private val userService: UserService,
                 private val teaRepository: TeaRepository,
                 private val brewingConfigRepository: BrewingConfigRepository,
                 private val accessoryRepository: AccessoryRepository) {

    fun add(tea: Tea) {
//        brewingConfigRepository.saveAndFlush(brewingConfig)

        if (teaRepository.existsByNameIs(tea.name)) {
            throw TeaAlreadyExistException()
        }

        val user = userService.getCurrentUser()
        teaRepository.saveAndFlush(
                Tea(
                        tea.name,
                        LocalDate.now(),
                        tea.imageLink,
                        tea.originCountry,
                        tea.caffeineContent,
                        tea.harvestSeasons,
                        user,
                        null
                )
        )
    }

    fun getAll(): List<Tea> {
        return teaRepository.getAllByIdIsNotNull()
    }

    fun update(tea: Tea) {
        if (tea.getId() == null) {
            return
        }
        val existingTea = teaRepository.getTeaByIdEquals(tea.getId())
        if (existingTea != null) {
            teaRepository.save(updateTeaFields(existingTea, tea))
        }
    }

    fun updateTeaFields(tea: Tea, updated: Tea): Tea {
        tea.name = updated.name
        tea.accessories = if (updated.accessories == null) mutableSetOf() else updated.accessories
        tea.brewingConfig = updateTeaBrewingConfigFields(tea.brewingConfig!!, updated.brewingConfig!!) //todo null?
        tea.caffeineContent = updated.caffeineContent
        tea.harvestSeasons = updated.harvestSeasons
        tea.imageLink = updated.imageLink
        tea.originCountry = updated.originCountry
        return tea
    }

    fun updateTeaBrewingConfigFields(configuration: BrewingConfiguration, updated: BrewingConfiguration): BrewingConfiguration {
        configuration.brewingTime = updated.brewingTime
        configuration.description = updated.description
        configuration.drinkingTime = updated.drinkingTime
        configuration.ingredients = updated.ingredients
        configuration.isDifficultToMake = updated.isDifficultToMake
        return configuration
    }

    fun addAccessory(teaId: UUID, accessoryId: UUID) {
        val accessory = accessoryRepository.getByIdEquals(accessoryId)
        val tea = teaRepository.getTeaByIdEquals(teaId)

        if (accessory == null || tea == null) {
            return
        }

        tea.addAccessory(accessory)
        teaRepository.save(tea)
    }

    fun delete(teaId: UUID) {
        val tea = teaRepository.getTeaByIdEquals(teaId) ?: return
        tea.accessories.removeAll(tea.accessories)
        teaRepository.delete(tea)
//        val accessoriesIterator = teaAccessories.iterator()
//        var next: Accessory
//        while (accessoriesIterator.hasNext()){
//            next = accessoriesIterator.next()
//            next.removeTea(tea)
//        }
//        if(tea.accessories.isEmpty()){
//            accessoryRepository.saveAll(teaAccessories)
//            teaRepository.deleteByIdEquals(teaId)
//        }
    }
}