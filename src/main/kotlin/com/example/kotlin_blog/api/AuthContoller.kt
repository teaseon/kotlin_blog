package com.example.kotlin_blog.api

import jakarta.servlet.http.HttpSession
import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthContoller(

) {
    var log = KotlinLogging.logger {  }


    @GetMapping("/login")
    fun login(session : HttpSession) {
        session.setAttribute("principal", "pass")
    }

}