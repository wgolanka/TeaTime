package com.brewassistant.domain.user

import com.brewassistant.domain.tea.Tea
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*
import javax.servlet.http.HttpServletResponse

@CrossOrigin(origins = ["http://localhost:3000", "http://localhost:3000/#"], maxAge = 3600)
@Controller
@RequestMapping("/user")
class UserController(val userService: UserService, val userRepository: UserRepository) {

    @PutMapping(value = ["/add"])
    fun addNewUser(@RequestParam(required = true) nickname: String,
                   @RequestParam(required = true) avatar: ByteArray,
                   @RequestParam(required = true) description: String,
                   @RequestParam(required = true) emailAddress: String,
                   response: HttpServletResponse) {

        if (userRepository.findAll().stream().noneMatch { brewer -> brewer.emailAddress == emailAddress }) {
            val newUser = User(nickname, avatar, LocalDate.now(), description, emailAddress)
            userRepository.save(newUser)
        }
    }

    @GetMapping(value = ["/all"])
    fun getUsers(): ResponseEntity<MutableList<User>> {
        val all = userRepository.findAll()
        return ok(all)
    }

    @GetMapping("/teas")
    fun getUserTeas(@RequestParam(required = true) id: String): ResponseEntity<MutableSet<Tea>> { //TODO
        val person = userRepository.findByIdIs(UUID.fromString(id))
        return ok(person.createdTeas)
    }

    fun getAll(): MutableList<User> {
        return userRepository.findAll()
    }

    @PutMapping(value = ["/setCurrent"])
    @ResponseBody
    fun setCurrentUser(@RequestParam(required = true) uuid: UUID, response: HttpServletResponse) {
        val exist = getAll().stream().anyMatch { user -> user.getId() == uuid }
        if (exist) {
            userService.setCurrentUser(uuid)
        }
    }
}