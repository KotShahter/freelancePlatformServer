package com.withABow.freelancePlatform.entities

import jakarta.persistence.*

enum class Role {ADMIN, USER, TUTOR}

@Entity
@Table(name = "users")
data class User(

    @Id @GeneratedValue
    val id: Int = 0,


    @Column(unique = true)
    val username: String,
    val contact: String?,

    val password: String?,

    @Enumerated(EnumType.STRING)
    var role: Role
)