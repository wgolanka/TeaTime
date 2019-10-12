package com.teatime.domain.accessory

import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class AccessoryService(val accessoryRepository: AccessoryRepository) {

    fun add(accessory: Accessory) {
        accessoryRepository.save(accessory)
    }

    fun getAll(): List<Accessory> {
        return accessoryRepository.findAll()
    }

    fun edit(accessory: Accessory) {
        val existingAccessory = accessoryRepository.getByIdEquals(accessory.getId())
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
}