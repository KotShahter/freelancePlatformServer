package com.withABow.freelancePlatform

enum class Role {ADMIN, USER}

data class User(
    val id : Int,
    val username : String,
    val role : Role)
