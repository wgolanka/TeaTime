package com.teatime.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<BaseUser, Long> {
    fun findByIdIs(id: UUID): BaseUser?
    fun findByEmailAddressIs(email: String): BaseUser?
    fun deleteByIdIs(id: UUID)
    fun existsByEmailAddressIs(email: String): Boolean
}