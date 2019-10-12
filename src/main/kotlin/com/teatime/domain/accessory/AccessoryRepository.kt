package com.teatime.domain.accessory

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccessoryRepository : JpaRepository<Accessory, Long> {
    fun getAllByIdIsNotNull(): List<Accessory>
    fun getByIdEquals(id: UUID?): Accessory?
    fun deleteByIdEquals(id: UUID)
}