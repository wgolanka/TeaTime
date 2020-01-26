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
            val newUser = BaseUser("Arthur", "https://a.wattpad.com/cover/182129071-352-k178784.jpg",
                    LocalDate.now(), "This is arthur",
                    "Arthur@rdr2.com")
            userRepository.save(newUser)
            return userRepository.findByIdIs(newUser.getId()!!)
        }

        return allUsers[0]
    }

    fun setCurrentUser(id: UUID) {
        currentDefaultUser = userRepository.findByIdIs(id) //TODO handle null
    }

    fun update(user: BaseUser, nickname: String, avatar: String?, description: String) {
        user.nickname = nickname
        if (avatar != null) {
            user.avatar = avatar
        }
        user.description = description
        userRepository.save(user)
    }

    companion object {
        var currentDefaultUser: BaseUser? = null
    }
}