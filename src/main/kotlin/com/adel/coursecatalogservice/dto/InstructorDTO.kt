package com.adel.coursecatalogservice.dto

import jakarta.validation.constraints.NotBlank

data class InstructorDTO(
    val id: Int?,
    @get:NotBlank(message = "Instructor Name must not be blank")
    val name: String
)