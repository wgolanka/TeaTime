package com.brewassistant.domain.tea

import com.brewassistant.domain.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@CrossOrigin(origins = ["http://localhost:3000", "http://localhost:3000/#"], maxAge = 3600)
@Controller
@Validated
class BrewController(private val userService: UserService,
                     private val brewRepository: BrewRepository) {


    @PostMapping(value = ["/tea/add"])
    fun addBrew(@RequestParam(required = true) @NotBlank name: String,
                @RequestParam(required = true) @NotBlank originCountry: String,
                @RequestParam(required = true) @NotNull caffeineContent: String,
                @RequestParam(required = true) @NotBlank @Size(max = 100) description: String,
                @RequestParam(required = true) @NotBlank imageLink: String,
                response: HttpServletResponse) {

        val person = userService.getCurrentUser()
        val tea = brewMakerService.make(person, name, originCountry, caffeineContent.toDouble(), description, imageLink)
        brewRepository.saveAndFlush(tea)
    }


    @GetMapping("/tea/all") //TODO
    fun getTeaExtent(): ResponseEntity<List<Tea>> {
        val all = brewRepository.getAllByIdIsNotNull()
        return ok(all)
    }
}

