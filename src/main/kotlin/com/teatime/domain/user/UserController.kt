package com.teatime.domain.user

import com.teatime.domain.tea.Tea
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*
import javax.servlet.http.HttpServletResponse

@CrossOrigin("*", maxAge = 3600)
@Controller
@RequestMapping("/user")
class UserController(val userService: UserService, val userRepository: UserRepository) {

    @PostMapping(value = ["/add"])
    fun addNewUser(@RequestParam(required = true) nickname: String,
                   @RequestParam(required = false) avatar: String?,
                   @RequestParam(required = true) description: String,
                   @RequestParam(required = true) emailAddress: String,
                   response: HttpServletResponse) {

        if (userRepository.findAll().stream().noneMatch { brewer -> brewer.emailAddress == emailAddress }) {
            val newUser = BaseUser(nickname, avatar, LocalDate.now(), description, emailAddress)
            userRepository.save(newUser)
        }
    }

    @PutMapping(value = ["/update"])
    fun updateUser(
            @RequestParam(required = true) id: String,
            @RequestParam(required = true) nickname: String,
            @RequestParam(required = false) avatar: String?,
            @RequestParam(required = true) description: String,
            response: HttpServletResponse) {

        val user = userRepository.findByIdIs(UUID.fromString(id))
        userService.update(user, nickname, avatar, description)
    }

    @GetMapping(value = ["/all"])
    fun getUsers(): ResponseEntity<MutableList<BaseUser>> {
        val all = userRepository.findAll()
        return ok(all)
    }

    @GetMapping(value = ["/current"])
    fun getUser(): ResponseEntity<BaseUser> {
        val all = userRepository.findAll()
        return ok(all[0])
    }

    @GetMapping(value = ["/{id}"])
    fun getUserById(@PathVariable("id") id: String): ResponseEntity<BaseUser> {
        val user = userRepository.findByIdIs(UUID.fromString(id))
        return ok(user)
    }

    @GetMapping("/teas")
    fun getUserTeas(@RequestParam(required = true) id: String): ResponseEntity<MutableSet<Tea>> { //TODO
        val person = userRepository.findByIdIs(UUID.fromString(id))
        return ok(person.createdTeas)
    }

    fun getAll(): MutableList<BaseUser> {
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