package com.teatime.domain.user

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
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
            return userRepository.findByIdIs(newUser.getId()!!)!!
        }
        currentDefaultUser = allUsers[0]
        return allUsers[0]
    }

    fun setCurrentUser(id: UUID) {
        currentDefaultUser = userRepository.findByIdIs(id) //TODO handle null
    }

    fun update(user: BaseUser, nickname: String, avatar: String?, description: String) {
        user.username = nickname
        if (avatar != null) {
            user.avatar = avatar
        }
        user.description = description
        userRepository.save(user)
    }

    fun delete() {
        val currentUserId = currentDefaultUser?.getId()
        val user = userRepository.findByIdIs(currentUserId!!) ?: return
        userRepository.deleteByIdIs(user.getId()!!) //TODO fix, not working
        currentDefaultUser = null
    }

    companion object {
        var currentDefaultUser: BaseUser? = null
    }
}