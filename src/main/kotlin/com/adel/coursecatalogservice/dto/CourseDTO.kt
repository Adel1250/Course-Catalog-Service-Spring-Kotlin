package com.adel.coursecatalogservice.dto

import jakarta.validation.constraints.NotBlank

data class CourseDTO(
    val id: Int?,
    @get:NotBlank(message = "Course Name must not be blank")
    val name: String,
    @get:NotBlank(message = "Course Category must not be blank")
    val category: String
)
