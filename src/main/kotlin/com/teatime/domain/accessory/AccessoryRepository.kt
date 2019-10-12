package com.teatime.domain.accessory

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccessoryRepository : JpaRepository<Accessory, Long> {
    fun getAllByIdIsNotNull(): List<Accessory>
}