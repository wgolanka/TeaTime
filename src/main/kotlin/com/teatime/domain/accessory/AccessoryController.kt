package com.teatime.domain.accessory

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletResponse

@CrossOrigin("*", maxAge = 3600)
@Controller
@Validated
@RequestMapping("/accessory")
class AccessoryController(val accessoryService: AccessoryService) {


    @PostMapping(value = ["/add"])
    @ResponseStatus(HttpStatus.OK)
    fun addAccessory(@RequestParam(required = true) name: String,
                     priceFrom: Double,
                     priceTo: Double,
                     description: String,
                     @RequestParam(required = false) isNecessary: String?,
                     imageLink: String,
                     response: HttpServletResponse) {

        val isNecessaryBoolean = isNecessary != null
        val priceRange = "$priceFrom - $priceTo"
        //TODO check if same name brewer exist
        val accessory = Accessory(name, priceFrom, priceTo, priceRange, description, imageLink, isNecessaryBoolean)
        accessoryService.add(accessory)
    }

    @GetMapping("/all")
    fun getAllAccessories(): ResponseEntity<List<Accessory>> {
        return status(HttpStatus.OK).body(accessoryService.getAll())
    }

    @GetMapping("/allExceptContainingTea/{teaId}")
    fun getFilteredAccessories(@PathVariable("teaId") id: String): ResponseEntity<List<Accessory>> {
        return status(HttpStatus.OK).body(accessoryService.getAllExceptFrom(UUID.fromString(id)))
    }

    @GetMapping("/{id}")
    fun getAccessory(@PathVariable("id") id: String): ResponseEntity<Accessory> {
        return status(HttpStatus.OK).body(accessoryService.get(id))
    }

    @PutMapping(value = ["/update"])
    @ResponseStatus(HttpStatus.OK)
    fun editAccessory(@RequestParam(required = true) id: String,
                      @RequestParam(required = true) name: String,
                      priceFrom: Double,
                      priceTo: Double,
                      description: String,
                      @RequestParam(required = false) isNecessary: String?,
                      @RequestParam(required = false) imageLink: String?,
                      response: HttpServletResponse) {

//        val baseUser = userService.getCurrentUser() //TODO add user
        val accessory = Accessory(name, priceFrom, priceTo, "$priceFrom - $priceTo",
                description, imageLink, isNecessary != null)
        accessoryService.edit(UUID.fromString(id), accessory)
    }

    @DeleteMapping(value = ["/delete"])
    @ResponseStatus(HttpStatus.OK)
    fun removeAccessory(@RequestParam(required = true) id: String,
                        response: HttpServletResponse) {
        //TODO exception when doesn't exist or wrong uuid
        accessoryService.remove(UUID.fromString(id)) //TODO does it work ?
    }
}

