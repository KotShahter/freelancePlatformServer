package com.withABow.freelancePlatform

import com.withABow.freelancePlatform.entities.Role
import com.withABow.freelancePlatform.repos.UserRepository
import com.withABow.freelancePlatform.services.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application (){

    @Bean
    fun commandLineRunner(userService: UserService, userRepository: UserRepository) = CommandLineRunner {
        if (userRepository.findByUsername("admin") == null) {
            userService.createUser(name = "admin", contact = "admin@tutor.com", role = Role.ADMIN)
            println("✅ Default admin created: username=admin | password=123")
        }
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}