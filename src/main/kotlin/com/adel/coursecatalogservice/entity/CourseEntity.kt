package com.adel.coursecatalogservice.entity

import jakarta.persistence.*

@Entity
@Table(name = "Courses")
data class CourseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    var name: String,
    var category: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSTRUCTOR_ID", nullable = false)
    val instructor: InstructorEntity? = null
) {
    override fun toString(): String {
        return "CourseEntity(id=$id, name='$name', category='$category', instructor=${instructor?.id})"
    }
}
