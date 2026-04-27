package com.withABow.freelancePlatform

enum class Status {Open, Closed}

data class Order(
    val id: Int,
    val title: String,
    val uni: String,
    val status: Status,
    val createdBy: User,
    var takenBy: User
)