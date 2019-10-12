package com.teatime.domain.tea.config

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BrewingConfigRepository : JpaRepository<BrewingConfiguration, Long> {
    fun getAllByIdIsNotNull(): List<BrewingConfiguration>
}