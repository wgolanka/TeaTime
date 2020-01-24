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
               @RequestParam imageLink: String) {

        val baseUser = userService.getCurrentUser()
        teaService.add(Tea(name, LocalDate.now(), imageLink, originCountry, caffeineContent,
                harvestSeason, baseUser, null))
    }

    @GetMapping("/{id}") //TODO id required
    fun getOne(@PathVariable("id") id: String): ResponseEntity<Tea> {

        val tea = teaService.getOne(id) //TODO exception when ID is not uuuid
        return status(HttpStatus.OK).body(tea)
    }

    @GetMapping("/all")
    fun getAllTeas(): ResponseEntity<List<Tea>> {

        val all = teaService.getAll()
        return status(HttpStatus.OK).body(all)
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    fun updateTea(@RequestParam(required = true) id: String,
                  @RequestParam(required = true) name: String,
                  @RequestParam(required = true) originCountry: String,
                  @RequestParam(required = true) harvestSeason: String,
                  @RequestParam caffeineContent: Double,
                  @RequestParam imageLink: String) {

        val baseUser = userService.getCurrentUser()

        val tea = Tea(name, LocalDate.now(), imageLink, originCountry, caffeineContent,
                harvestSeason, baseUser, null)
        teaService.update(UUID.fromString(id), tea)
    }

    @GetMapping("/byAccessory/{accessoryId}")
    fun getAccessoryByTea(@PathVariable("accessoryId") accessoryId: String): ResponseEntity<Set<Tea>> {
        return status(HttpStatus.OK).body(teaService.getByAccessory(accessoryId))
    }

    @PutMapping("/accessory/add/{teaId}/{accessoryId}")
    @ResponseStatus(HttpStatus.OK)
    fun addAccessory(@PathVariable("teaId") teaId: String,
                     @PathVariable("accessoryId") accessoryId: String) {

        teaService.addAccessory(UUID.fromString(teaId), UUID.fromString(accessoryId))
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    fun deleteTea(@RequestParam(required = true) id: String) {

        teaService.delete(UUID.fromString(id))
        //TODO removes accessories relation correctly, but doesnt remove itself, add
        // Tea onetoone to BrewConfiguration
    }
}

