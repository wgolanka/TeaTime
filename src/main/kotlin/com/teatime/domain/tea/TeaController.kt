package com.teatime.domain.tea

import com.teatime.domain.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*
import javax.transaction.Transactional

@CrossOrigin("*", maxAge = 3600)
@Controller
@Validated
@RequestMapping("/tea")
@Transactional
class TeaController(private val teaService: TeaService, private val userService: UserService) {


    @PostMapping(value = ["/add"])
    @ResponseStatus(HttpStatus.OK)
    fun addTea(@RequestParam(required = true) name: String,
               @RequestParam(required = true) originCountry: String,
               @RequestParam(required = true) harvestSeason: String,
               @RequestParam caffeineContent: Double,
               @RequestParam(required = true) description: String,
               @RequestParam imageLink: String) {

        val baseUser = userService.getCurrentUser()
        teaService.add(Tea(name, LocalDate.now(), imageLink, originCountry, caffeineContent,
                harvestSeason, baseUser, null))
        //TODO walidacja czy herbata o danej nazwie istnieje ? potem
    }

    @GetMapping("/all")
    fun getAllTeas(): ResponseEntity<List<Tea>> {

        val all = teaService.getAll()
        return status(HttpStatus.OK).body(all)
    }

    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    fun getEditTea(@RequestBody(required = false) teaObject: Tea) {

        teaService.update(teaObject)
    }

    @PutMapping("/accessory/add")
    @ResponseStatus(HttpStatus.OK)
    fun addAccessory(@RequestParam(required = true) teaId: UUID,
                     @RequestParam(required = true) accessoryId: UUID) {

        teaService.addAccessory(teaId, accessoryId)
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    fun deleteTea(@RequestParam(required = true) teaId: UUID) {

        teaService.delete(teaId)
        //TODO removes accesories relation correctly, but doesnt remove itself, add
        // Tea onetoone to BrewConfiguration
    }
}

