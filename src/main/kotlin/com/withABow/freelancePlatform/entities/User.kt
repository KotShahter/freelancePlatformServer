package com.withABow.freelancePlatform.entities

import jakarta.persistence.*

enum class Role {ADMIN, USER}

@Entity
@Table(name = "Users")
data class User(

    @Id @GeneratedValue
    val id: Int = 0,

    val username: String,

    @Enumerated(EnumType.STRING)
    var role: Role,
)