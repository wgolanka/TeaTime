package com.teatime.domain.accessory

import com.teatime.domain.tea.TeaRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class AccessoryService(val accessoryRepository: AccessoryRepository, val teaRepository: TeaRepository) {

    fun add(accessory: Accessory) {
        accessoryRepository.save(accessory)
    }

    fun getAll(): List<Accessory> {
        return accessoryRepository.findAll()
    }

    fun edit(id: UUID, accessory: Accessory) {
        val existingAccessory = accessoryRepository.getByIdEquals(id)
        if (existingAccessory != null) {
            accessoryRepository.save(updateAccessoryFields(existingAccessory, accessory))
        }
    }

    fun updateAccessoryFields(accessory: Accessory, updated: Accessory): Accessory {
        accessory.name = updated.name
        accessory.description = updated.description
        accessory.imageLink = updated.imageLink
        accessory.necessary = updated.necessary
        accessory.priceRange = updated.priceRange
        return accessory
    }

    fun remove(accessoryId: UUID) {
        val accessory = accessoryRepository.getByIdEquals(accessoryId) ?: return
        accessory.author.accessories.remove(accessory)
        val teas = accessory.teas
        teas.stream().forEach { tea -> tea.accessories.remove(accessory) }
        accessoryRepository.deleteByIdEquals(accessoryId)
    }

    fun get(id: String): Accessory? {
        //TODO throw exception if uuid is wrong
        return accessoryRepository.getByIdEquals(UUID.fromString(id))
    }

    fun getAllExceptFrom(teaId: UUID?): List<Accessory>? {
        val allAccessories = accessoryRepository.getAllByIdIsNotNull()
        val tea = teaRepository.getTeaByIdEquals(teaId)
        return allAccessories.filter { accessory -> !accessory.teas.contains(tea) }
    }

    fun getByTea(teaId: String): MutableSet<Accessory> {
        val tea = teaRepository.getTeaByIdEquals(UUID.fromString(teaId))
        return tea?.accessories ?: mutableSetOf()
    }
}