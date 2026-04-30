package com.withABow.freelancePlatform.services

import com.withABow.freelancePlatform.Repository
import com.withABow.freelancePlatform.entities.Role
import com.withABow.freelancePlatform.entities.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicInteger

@Service
class UserService (private val userRepository : Repository)

{
    fun createUser(name: String = "noname"): User {
        val user = User(
            username = name,
            role = Role.USER
        )

        return userRepository.save(user)
    }

    fun getUsers() : List<User> = userRepository.findAll()


    fun getUserById(id: Int): User? =
        userRepository.findByIdOrNull(id)
}