package com.adel.coursecatalogservice.util

import com.adel.coursecatalogservice.dto.CourseDTO
import com.adel.coursecatalogservice.entity.CourseEntity
import com.adel.coursecatalogservice.entity.InstructorEntity

fun courseEntityList(instructor: InstructorEntity? = null) = listOf(
    CourseEntity(null,
        "Build RestFul APis using SpringBoot and Kotlin", "Development",
        instructor),
    CourseEntity(null,
        "Build Reactive Microservices using Spring WebFlux/SpringBoot", "Development"
        ,instructor
    ),
    CourseEntity(null,
        "Wiremock for Java Developers", "Development" ,
        instructor)
)

fun instructorEntity(name : String = "Adel")
        = InstructorEntity(null, name)

fun courseDTO(
    id: Int? = null,
    name: String = "Build RestFul APis using Spring Boot and Kotlin",
    category: String = "Adel",
) = CourseDTO(
    id,
    name,
    category
)