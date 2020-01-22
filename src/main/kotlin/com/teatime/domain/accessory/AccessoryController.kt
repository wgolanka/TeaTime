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
                     isNecessary: String,
                     imageLink: String,
                     response: HttpServletResponse) {

        val isNecessaryBoolean = isNecessary != ""
        val priceRange = "$priceFrom - $priceTo"
        val accessory = Accessory(name, priceRange, description, imageLink, isNecessaryBoolean)
        accessoryService.add(accessory)
    }

    @GetMapping("/all")
    fun getAllAccessories(): ResponseEntity<List<Accessory>> {
        return status(HttpStatus.OK).body(accessoryService.getAll())
    }

    @PutMapping(value = ["/edit"])
    @ResponseStatus(HttpStatus.OK)
    fun editAccessory(@RequestBody(required = true) accessory: Accessory,
                      response: HttpServletResponse) {

        accessoryService.edit(accessory)
    }

    @DeleteMapping(value = ["/delete"])
    @ResponseStatus(HttpStatus.OK)
    fun removeAccessory(@RequestParam(required = true) accessoryId: UUID,
                        response: HttpServletResponse) {

        accessoryService.remove(accessoryId) //TODO does it work ?
    }
}

