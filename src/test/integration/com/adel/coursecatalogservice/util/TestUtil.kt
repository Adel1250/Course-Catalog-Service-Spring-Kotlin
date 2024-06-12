package com.adel.coursecatalogservice.util

import com.adel.coursecatalogservice.entity.CourseEntity

fun courseEntityList() = listOf(
    CourseEntity(null,
        "Build RestFul APis using SpringBoot and Kotlin", "Development"),
    CourseEntity(null,
        "Build Reactive Microservices using Spring WebFlux/SpringBoot", "Development"
        ,
    ),
    CourseEntity(null,
        "Wiremock for Java Developers", "Development" ,
    )
)