package com.adel.coursecatalogservice.service

import com.adel.coursecatalogservice.dto.InstructorDTO
import com.adel.coursecatalogservice.entity.InstructorEntity
import com.adel.coursecatalogservice.repository.InstructorRepository
import com.adel.coursecatalogservice.service.CourseService.Companion.logger
import org.springframework.stereotype.Service
import java.util.*

@Service
class InstructorService(
    val instructorRepository: InstructorRepository
) {
    fun addInstructor(instructorDTO: InstructorDTO): InstructorDTO {
        return instructorRepository.save(InstructorEntity(null, instructorDTO.name)).let {
            InstructorDTO(it.id, it.name)
        }.also {
            logger().info { "Saved instructor is $it" }
        }
    }

    fun retrieveInstructorById(instructorId: Int): Optional<InstructorEntity> {
        return instructorRepository.findById(instructorId)
    }
}