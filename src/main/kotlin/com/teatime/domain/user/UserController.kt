package com.teatime.domain.user

import com.teatime.domain.tea.Tea
import com.teatime.domain.tea.TeaService
import org.springframework.http.HttpStatus
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
class UserController(val userService: UserService, val userRepository: UserRepository,
                     val teaService: TeaService) {

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
                ?: throw IllegalStateException() //TODO throw smth different
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
        UserService.currentDefaultUser = all[0]
        return ok(all[0])
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = ["/current"])
    fun deleteUser() {
        if (UserService.currentDefaultUser == null) return
        val currentUserId = UserService.currentDefaultUser!!.getId()
        val user = userRepository.findByIdIs(currentUserId!!) ?: return

        user.createdTeas.forEach { tea -> teaService.delete(tea.getId()!!) }
        userRepository.deleteByIdIs(user.getId()!!)
        UserService.currentDefaultUser = null
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

    @PutMapping(value = ["/setCurrent"])
    @ResponseBody
    fun setCurrentUser(@RequestParam(required = true) uuid: UUID, response: HttpServletResponse) {
        val exist = getAll().stream().anyMatch { user -> user.getId() == uuid }
        if (exist) {
            userService.setCurrentUser(uuid)
        }
    }
}