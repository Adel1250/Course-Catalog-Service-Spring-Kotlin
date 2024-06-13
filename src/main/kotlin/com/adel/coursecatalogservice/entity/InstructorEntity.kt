package com.adel.coursecatalogservice.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "Instructors")
data class InstructorEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    @get:NotBlank(message = "Instructor Name must not be blank")
    val name: String,
    @OneToMany(mappedBy = "instructor", cascade = [CascadeType.ALL], orphanRemoval = true)
    var courses: List<CourseEntity> = mutableListOf()
)