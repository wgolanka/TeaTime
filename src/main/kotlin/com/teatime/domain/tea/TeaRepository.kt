package com.teatime.domain.tea

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TeaRepository : JpaRepository<Tea, Long> {
    fun getAllByIdIsNotNull(): List<Tea>
    fun getTeaByIdEquals(id: UUID?): Tea?
}