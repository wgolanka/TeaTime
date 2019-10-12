package com.teatime.domain.tea

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TeaRepository : JpaRepository<Tea, Long> {
    fun getAllByIdIsNotNull(): List<Tea>
}