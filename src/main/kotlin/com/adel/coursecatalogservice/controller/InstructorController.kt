package com.adel.coursecatalogservice.controller

import com.adel.coursecatalogservice.dto.InstructorDTO
import com.adel.coursecatalogservice.service.InstructorService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/instructors")
@Validated
class InstructorController(
    val instructorService: InstructorService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addInstructor(@RequestBody @Valid instructorDTO: InstructorDTO) = instructorService.addInstructor(instructorDTO)
}