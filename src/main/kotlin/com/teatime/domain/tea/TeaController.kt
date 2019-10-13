package com.teatime.domain.tea

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletResponse
import javax.transaction.Transactional

@CrossOrigin(origins = ["http://localhost:3000", "http://localhost:3000/#"], maxAge = 3600)
@Controller
@Validated
@RequestMapping("/tea")
@Transactional
class TeaController(private val teaService: TeaService) {


    @PostMapping(value = ["/add"])
    fun addTea(@RequestBody(required = false) teaObject: Tea, response: HttpServletResponse): ResponseEntity.BodyBuilder {

        teaService.add(teaObject, teaObject.brewingConfig!!) //todo null?
        return ok()
    }

    @GetMapping("/all")
    fun getAllTeas(): ResponseEntity<List<Tea>> {

        val all = teaService.getAll()
        return ok(all)
    }

    @PutMapping("/edit")
    fun getEditTea(@RequestBody(required = false) teaObject: Tea): ResponseEntity.BodyBuilder {

        teaService.update(teaObject)
        return ok()
    }

    @PutMapping("/accessory/add")
    fun addAccessory(@RequestParam(required = true) teaId: UUID,
                     @RequestParam(required = true) accessoryId: UUID): ResponseEntity.BodyBuilder {

        teaService.addAccessory(teaId, accessoryId)
        return ok()
    }

    @DeleteMapping("/delete")
    fun deleteTea(@RequestParam(required = true) teaId: UUID): ResponseEntity.BodyBuilder {

        teaService.delete(teaId)
        return ok() //TODO removes accesories relation correctly, but doesnt remove itself, add
        // Tea onetoone to BrewConfiguration
    }
}

