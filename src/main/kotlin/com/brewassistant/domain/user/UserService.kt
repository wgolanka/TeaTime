package com.brewassistant.domain.user

import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val personRepository: PersonRepository) {

    fun getCurrentUser(): Person {
//        val jack = UUID.fromString("")
        val anne = UUID.fromString("a7a78aba-0bdd-4fcc-b973-4aa1ab0c3ee4") //default set user
        if (currentUser == null) {
            setCurrentUser(anne)
            return personRepository.findByIdIs(anne)
        }
        return currentUser as Person
    }

    fun setCurrentUser(id: UUID) {
        currentUser = personRepository.findByIdIs(id) //TODO handle null
    }

    companion object {
        var currentUser: Person? = null
    }
}