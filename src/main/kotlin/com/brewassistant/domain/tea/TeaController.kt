package com.brewassistant.domain.tea

import com.brewassistant.domain.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate
import javax.servlet.http.HttpServletResponse
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@CrossOrigin(origins = ["http://localhost:3000", "http://localhost:3000/#"], maxAge = 3600)
@Controller
@Validated
class TeaController(private val userService: UserService,
                    private val teaRepository: TeaRepository) {


    @PostMapping(value = ["/tea/add"])
    fun addBrew(@RequestParam(required = true) @NotBlank name: String,
                @RequestParam(required = true) @NotBlank originCountry: String,
                @RequestParam(required = true) @NotNull caffeineContent: String,
                @RequestParam(required = true) @NotBlank @Size(max = 100) description: String,
                @RequestParam(required = true) @NotBlank imageLink: String,
                @RequestParam(required = true) @NotBlank harvestSeasons: ArrayList<String>,
                @RequestParam(required = true) @NotBlank brewingConfiguration: BrewingConfiguration,
                response: HttpServletResponse) {

        val person = userService.getCurrentUser()
        val tea = Tea(name, LocalDate.now(), imageLink, originCountry, caffeineContent.toDouble(), harvestSeasons,
                person, brewingConfiguration)
        teaRepository.saveAndFlush(tea)
    }


    @GetMapping("/tea/all") //TODO
    fun getTeaExtent(): ResponseEntity<List<Tea>> {
        val all = teaRepository.getAllByIdIsNotNull()
        return ok(all)
    }
}

