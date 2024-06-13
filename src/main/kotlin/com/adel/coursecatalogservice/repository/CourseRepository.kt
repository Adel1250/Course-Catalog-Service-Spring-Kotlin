package com.adel.coursecatalogservice.repository

import com.adel.coursecatalogservice.entity.CourseEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<CourseEntity, Int> {
    fun findByNameContaining(name: String): List<CourseEntity>

    @Query(value = "SELECT * FROM courses WHERE name LIKE %?1%", nativeQuery = true)
    fun findCoursesByName(name: String): List<CourseEntity>
}