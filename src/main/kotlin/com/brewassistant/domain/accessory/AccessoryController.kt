package com.brewassistant.domain.accessory

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
@RequestMapping("/accessory")
class AccessoryController(val accessoryRepository: AccessoryRepository) {


    @PostMapping(value = ["/add"])
    fun addAccessory(@RequestParam(required = true) @NotBlank name: String,
                     @RequestParam(required = true) @NotNull priceRange: String,
                     @RequestParam(required = true) @NotBlank @Size(max = 100) description: String,
                     @RequestParam(required = true) @NotBlank imageLink: String,
                     @RequestParam(required = true) @NotBlank isNecessary: Boolean,
                     response: HttpServletResponse) {

        accessoryRepository.save(Accessory(name, priceRange, description, imageLink, isNecessary))
    }


    @GetMapping("/all")
    fun getAllAccessories(): ResponseEntity<List<Accessory>> {
        return ok(accessoryRepository.getAllByIdIsNotNull())
    }
}

