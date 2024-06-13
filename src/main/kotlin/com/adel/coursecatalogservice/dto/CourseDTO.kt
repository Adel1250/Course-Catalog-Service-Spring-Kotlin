package com.adel.coursecatalogservice.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CourseDTO(
    val id: Int?,
    @get:NotBlank(message = "Course Name must not be blank")
    val name: String,
    @get:NotBlank(message = "Course Category must not be blank")
    val category: String,
    @get:NotNull(message = "Instructor ID must not be empty")
    val instructorId: Int? = null
)
