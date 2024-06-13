package com.adel.coursecatalogservice.service

import com.adel.coursecatalogservice.dto.CourseDTO
import com.adel.coursecatalogservice.entity.CourseEntity
import com.adel.coursecatalogservice.exception.CourseNotFoundException
import com.adel.coursecatalogservice.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService(val courseRepository: CourseRepository) {
    companion object : KLogging()

    fun addCourse(courseDTO: CourseDTO): CourseDTO {
        return courseRepository.save(courseDTO.let {
            CourseEntity(null, it.name, it.category)
        }).let {
            CourseDTO(it.id, it.name, it.category)
        }.also {
            logger().info { "Saved course is $it" }
        }
    }

    fun retrieveAllCourses(courseName: String?): List<CourseDTO> {
        val courses = courseName?.let { courseRepository.findByNameContaining(courseName) } ?: courseRepository.findAll()
        return courses.map { CourseDTO(it.id, it.name, it.category) }
    }


    fun updateCourse(courseId: Int, courseDTO: CourseDTO): CourseDTO {
        val existingCourse = courseRepository.findById(courseId)
        return if (existingCourse.isPresent) {
            existingCourse.get().let {
                it.name = courseDTO.name
                it.category = courseDTO.category
                val courseEntity = courseRepository.save(it)
                CourseDTO(courseEntity.id, courseEntity.name, courseEntity.category)
            }
        } else {
            throw CourseNotFoundException("No course found for this ID: $courseId")
        }
    }

    fun deleteCourse(courseId: Int) {
        val existingCourse = courseRepository.findById(courseId)
        return if (existingCourse.isPresent) {
            courseRepository.delete(existingCourse.get())
        } else {
            throw CourseNotFoundException("No course found for this ID: $courseId")
        }
    }

//    fun retrieveCourseByName(name: String) = courseRepository.findByNameContaining(name).let {
//        CourseDTO(it.id, it.name, it.category)
//    }
}