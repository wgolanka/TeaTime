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
        accessory.isNecessary = updated.isNecessary
        accessory.priceRange = updated.priceRange
        accessory.teas = if (updated.teas == null) mutableSetOf() else updated.teas
        return accessory
    }

    fun remove(accessoryId: UUID) {
        accessoryRepository.deleteByIdEquals(accessoryId) //TODO handle remove accessory from tea  in db?
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