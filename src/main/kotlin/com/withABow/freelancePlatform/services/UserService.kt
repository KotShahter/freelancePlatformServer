package com.withABow.freelancePlatform.services

import com.withABow.freelancePlatform.repos.UserRepository
import com.withABow.freelancePlatform.entities.Role
import com.withABow.freelancePlatform.entities.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService (
        private val userRepository : UserRepository,
        private val passwordEncoder : PasswordEncoder
)

{
    fun createUser(name: String = "noname",
                   contact: String? = "",
                   role: Role = Role.USER,
                   password: String = "VeryHardPasswordNoOneCaresAboutUserAccountsLol"): User {
        val user = User(
            contact = contact,
            username = name,
            role = role,
            password = passwordEncoder.encode(password)
        )

        return userRepository.save(user)
    }

    fun getUsers() : List<User> = userRepository.findAll()

    fun deleteUser(id: Long) {
        val user = getUserById(id) ?: throw IllegalArgumentException("User not found")
        if (user.username == "admin") throw IllegalArgumentException("Cannot delete admin")

        userRepository.delete(user)
    }

    fun getUserById(id: Long): User? =
        userRepository.findByIdOrNull(id)

    fun getUserByName(username: String): User? =
        userRepository.findByUsername(username)

    fun authenticate(username: String, password: String): User? {
        val user = userRepository.findByUsername(username) ?: return null
        return if (passwordEncoder.matches(password, user.password)) user else null
    }
}