package com.teatime.domain.user

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {

    fun getCurrentUser(): BaseUser {
        if (currentDefaultUser != null) {
            return currentDefaultUser as BaseUser
        }

        val allUsers = userRepository.findAll()
        if (allUsers.isEmpty()) {
            val newUser = BaseUser("Arthur", byteArrayOf(), LocalDate.now(), "This is arthur",
                    "Arthur@rdr2.com")
            userRepository.save(newUser)
            return userRepository.findByIdIs(newUser.getId()!!)
        }

        return allUsers[0]
    }

    fun setCurrentUser(id: UUID) {
        currentDefaultUser = userRepository.findByIdIs(id) //TODO handle null
    }

    companion object {
        var currentDefaultUser: BaseUser? = null
    }
}