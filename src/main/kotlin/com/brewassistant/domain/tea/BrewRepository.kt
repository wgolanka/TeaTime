package com.brewassistant.domain.tea

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BrewRepository : JpaRepository<Tea, Long> {
    fun getAllByIdIsNotNull(): List<Tea>
}