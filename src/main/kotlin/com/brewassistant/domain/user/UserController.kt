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
class UserController(val userService: UserService, val personRepository: PersonRepository) {

    /**
     *
     * @param name String
     * @param surname String
     * @param emailAddress String
     * @param phoneNumber String
     * @param response HttpServletResponse
     */
    @PutMapping(value = ["/add/user"])
    fun addNewUser(@RequestParam(required = true) name: String,
                   @RequestParam(required = true) surname: String,
                   @RequestParam(required = true) emailAddress: String,
                   @RequestParam(required = true) phoneNumber: String, response: HttpServletResponse) {

        if (personRepository.findAll().stream().noneMatch { brewer -> brewer.emailAddress == emailAddress }) {
            val newBrewer = User(name, surname, null, LocalDate.now(),
                    emailAddress, phoneNumber, mutableSetOf())
            personRepository.save(newBrewer)
        }
    }

    /**
     *
     * @return ResponseEntity<Set<Person>>
     */
    @GetMapping(value = ["/users"])
    fun getUsers(): ResponseEntity<MutableList<Person>> {
        val all = personRepository.findAll()
        return ok(all)
    }

    /**
     *
     * @param id String
     * @return ResponseEntity<MutableSet<Brew>>
     */
    @GetMapping("/person/teas")
    fun getTeaExtent(@RequestParam(required = true) id: String): ResponseEntity<MutableSet<Tea>> { //TODO
        val person = personRepository.findByIdIs(UUID.fromString(id))
        return ok(person.createdBrews)
    }

    fun getAll(): MutableList<Person> {
        return personRepository.findAll()
    }

    /**
     *
     * @param uuid UUID
     * @param response HttpServletResponse
     */
    @PutMapping(value = ["/setCurrentUser"])
    @ResponseBody
    fun setCurrentUser(@RequestParam(required = true) uuid: UUID, response: HttpServletResponse) {
        val exist = getAll().stream().anyMatch { user -> user.getId() == uuid }
        if (exist) {
            userService.setCurrentUser(uuid)
        }
    }
}