package com.teatime.domain.tea.config

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BrewingConfigRepository : JpaRepository<BrewingConfiguration, Long> {
    fun getAllByIdIsNotNull(): List<BrewingConfiguration>
    fun getByIdEquals(id: UUID?): BrewingConfiguration
    fun removeByIdEquals(id: UUID)
}