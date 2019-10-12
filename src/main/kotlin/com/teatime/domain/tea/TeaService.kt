package com.teatime.domain.tea

import com.teatime.domain.tea.config.BrewingConfigRepository
import com.teatime.domain.tea.config.BrewingConfiguration
import com.teatime.domain.user.UserService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TeaService(private val userService: UserService,
                 private val teaRepository: TeaRepository,
                 private val brewingConfigRepository: BrewingConfigRepository) {

    fun add(tea: Tea, brewingConfig: BrewingConfiguration) {
        brewingConfigRepository.saveAndFlush(brewingConfig)

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
                        brewingConfig
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
        tea.brewingConfig = updateTeaBrewingConfigFields(tea.brewingConfig, updated.brewingConfig)
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
}