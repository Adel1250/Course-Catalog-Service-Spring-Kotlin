package com.adel.coursecatalogservice.controller

import com.adel.coursecatalogservice.dto.InstructorDTO
import com.adel.coursecatalogservice.service.InstructorService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertEquals

@WebMvcTest(controllers = [InstructorController::class])
@AutoConfigureWebTestClient
class InstructorControllerUnitTest {
    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var instructorServiceMock: InstructorService

    @Test
    fun addInstructor() {
        val instructorDTO = InstructorDTO(null, "Adel")

        every {
            instructorServiceMock.addInstructor(any())
        } returns InstructorDTO(id = 1, name = "Adel")

        val savedInstructorDTO = webTestClient
            .post()
            .uri("/v1/instructors")
            .bodyValue(instructorDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue {
            savedInstructorDTO!!.id != null
        }
    }

    @Test
    fun addInstructorValidation() {
        val instructorDTO = InstructorDTO(null, "")

        every {
            instructorServiceMock.addInstructor(any())
        } returns InstructorDTO(id = 1, name = "Adel")

        val response = webTestClient
            .post()
            .uri("/v1/instructors")
            .bodyValue(instructorDTO)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals("Instructor Name must not be blank", response)
    }
}