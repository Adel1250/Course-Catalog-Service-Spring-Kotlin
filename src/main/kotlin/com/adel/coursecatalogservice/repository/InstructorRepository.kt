package com.adel.coursecatalogservice.repository

import com.adel.coursecatalogservice.entity.InstructorEntity
import org.springframework.data.repository.CrudRepository

interface InstructorRepository : CrudRepository<InstructorEntity, Int>