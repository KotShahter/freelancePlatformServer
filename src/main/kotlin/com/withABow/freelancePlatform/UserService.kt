package com.withABow.freelancePlatform

import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicInteger

@Service
class UserService {
    private val users = mutableListOf<User>()
    private val counter = AtomicInteger()

    fun createUser(name: String = "noname"): User {
        val user = User(counter.incrementAndGet(), name, Role.USER)
        users.add(user)
        return user
    }

    fun getUsers() = users

    fun getUserById(id: Int): User? = users.find { user -> user.id == id }
}