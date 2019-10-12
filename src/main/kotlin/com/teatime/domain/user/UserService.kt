package com.teatime.domain.user

import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {

    fun getCurrentUser(): BaseUser {
//        val jack = UUID.fromString("")
        val sadie = UUID.fromString("e1405043-c77d-4612-a7d0-f2de256697d9") //default set user
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