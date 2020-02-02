package com.teatime.domain.user

import com.teatime.domain.tea.Tea
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*
import javax.servlet.http.HttpServletResponse
import javax.transaction.Transactional

@CrossOrigin("*", maxAge = 3600)
@Controller
@RequestMapping("/user")
@Transactional
class UserController(val userService: UserService, val userRepository: UserRepository) {

    @PostMapping(value = ["/add"])
    fun addNewUser(@RequestParam(required = true) username: String,
                   @RequestParam(required = false) avatar: String?,
                   @RequestParam(required = true) description: String,
                   @RequestParam(required = true) emailAddress: String,
                   response: HttpServletResponse) {

        if (userRepository.findAll().stream().noneMatch { brewer -> brewer.emailAddress == emailAddress }) {
            val newUser = BaseUser(username, avatar, LocalDate.now(), description, emailAddress, "")
            userRepository.save(newUser)
        }
    }


    @PostMapping(value = ["/register"])
    fun register(@RequestParam(required = true) username: String,
                 @RequestParam(required = false) avatar: String?,
                 @RequestParam(required = true) description: String,
                 @RequestParam(required = true) email: String,
                 @RequestParam(required = true) password: String): ResponseEntity<BaseUser> {

        if (!userRepository.existsByEmailAddressIs(email)) {
            return ok(userService.registerUser(username, avatar, LocalDate.now(), description, email, password))
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
    }

    @ResponseStatus(OK)
    @PostMapping(value = ["/signout"])
    fun signout() {
        UserService.currentDefaultUser = null
    }

    @GetMapping("/login")
    fun logIn(@RequestParam(required = true) email: String,
              @RequestParam(required = true) password: String): ResponseEntity<BaseUser> {

        if (userRepository.existsByEmailAddressIs(email)) {
            val user = userRepository.findByEmailAddressIs(email)
            if (user != null && Password.check(password, user.password)) {
                setCurrentUser(user.getId()!!)
                return ok(user)
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
    }

    @PutMapping(value = ["/update"])
    fun updateUser(
            @RequestParam(required = true) id: String,
            @RequestParam(required = true) username: String,
            @RequestParam(required = false) avatar: String?,
            @RequestParam(required = true) description: String,
            response: HttpServletResponse) {

        val user = userRepository.findByIdIs(UUID.fromString(id))
                ?: throw IllegalStateException() //TODO throw smth different
        userService.update(user, username, avatar, description)
    }

    @GetMapping(value = ["/all"])
    fun getUsers(): ResponseEntity<MutableList<BaseUser>> {
        val all = userRepository.findAll()
        return ok(all)
    }

    @GetMapping(value = ["/current"])
    fun getUser(): ResponseEntity<BaseUser> {
        if (UserService.currentDefaultUser != null) {
            return ok(userService.getCurrentUser())
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
    }

    @ResponseStatus(OK)
    @DeleteMapping(value = ["/current"])
    fun deleteUser() {
        if (UserService.currentDefaultUser == null) return
        userService.delete()
    }

    @GetMapping(value = ["/{id}"])
    fun getUserById(@PathVariable("id") id: String): ResponseEntity<BaseUser> {
        val user = userRepository.findByIdIs(UUID.fromString(id)) ?: throw IllegalStateException()
        return ok(user)
    }

    @GetMapping("/teas")
    fun getUserTeas(@RequestParam(required = true) id: String): ResponseEntity<MutableSet<Tea>> { //TODO
        val person = userRepository.findByIdIs(UUID.fromString(id))
        if (person != null) {
            return ok(person.createdTeas)
        }
        return ok(mutableSetOf())
    }

    fun getAll(): MutableList<BaseUser> {
        return userRepository.findAll()
    }

    @ResponseStatus(OK)
    @PutMapping(value = ["/setCurrent"])
    @ResponseBody
    fun setCurrentUser(@RequestParam(required = true) uuid: UUID) {
        val exist = getAll().stream().anyMatch { user -> user.getId() == uuid }
        if (exist) {
            userService.setCurrentUser(uuid)
        }
    }
}