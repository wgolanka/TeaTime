package com.brewassistant.domain.user

import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {

    fun getCurrentUser(): User {
//        val jack = UUID.fromString("")
        val anne = UUID.fromString("a7a78aba-0bdd-4fcc-b973-4aa1ab0c3ee4") //default set user
        if (currentUser == null) {
            setCurrentUser(anne)
            return userRepository.findByIdIs(anne)
        }
        return currentUser as User
    }

    fun setCurrentUser(id: UUID) {
        currentUser = userRepository.findByIdIs(id) //TODO handle null
    }

    companion object {
        var currentUser: User? = null
    }
}