package com.teatime.domain.user

import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {

    fun getCurrentUser(): BaseUser {
//        val jack = UUID.fromString("")
        val sadie = UUID.fromString("c4697741-e4fb-4ce7-8701-2f135e69c8cb") //default set user
        if (currentBaseUser == null) {
            setCurrentUser(sadie)
            return userRepository.findByIdIs(sadie)
        }
        return currentBaseUser as BaseUser
    }

    fun setCurrentUser(id: UUID) {
        currentBaseUser = userRepository.findByIdIs(id) //TODO handle null
    }

    companion object {
        var currentBaseUser: BaseUser? = null
    }
}