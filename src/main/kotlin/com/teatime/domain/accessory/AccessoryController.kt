package com.teatime.domain.accessory

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletResponse

@CrossOrigin(origins = ["http://localhost:3000", "http://localhost:3000/#"], maxAge = 3600)
@Controller
@Validated
@RequestMapping("/accessory")
class AccessoryController(val accessoryService: AccessoryService) {


    @PostMapping(value = ["/add"])
    fun addAccessory(@RequestBody(required = true) accessory: Accessory,
                     response: HttpServletResponse) {

        accessoryService.add(Accessory(accessory.name, accessory.priceRange,
                accessory.description, accessory.imageLink, accessory.isNecessary))
    }

    @GetMapping("/all")
    fun getAllAccessories(): ResponseEntity<List<Accessory>> {
        return ok(accessoryService.getAll())
    }

    @PutMapping(value = ["/edit"])
    fun editAccessory(@RequestBody(required = true) accessory: Accessory,
                      response: HttpServletResponse) {

        accessoryService.edit(accessory)
    }

    @DeleteMapping(value = ["/delete"])
    fun removeAccessory(@RequestParam(required = true) accessoryId: UUID,
                        response: HttpServletResponse) {

        accessoryService.remove(accessoryId)
    }
}

