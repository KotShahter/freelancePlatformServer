package com.withABow.freelancePlatform.Repos

import com.withABow.freelancePlatform.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findByUsername(username: String) : User?
}