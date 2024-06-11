package com.adel.coursecatalogservice.controller

import com.adel.coursecatalogservice.service.GreetingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/greetings")
class GreetingController(
    val greetingService: GreetingService
) {
    @GetMapping("/{name}")
    fun retrieveGreeting(@PathVariable("name") name: String) = greetingService.retrieveGreeting(name)
}