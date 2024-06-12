package com.adel.coursecatalogservice.repository

import com.adel.coursecatalogservice.entity.CourseEntity
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<CourseEntity, Int>