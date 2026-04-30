package com.withABow.freelancePlatform.entities

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

enum class Status {Open, Closed}

@Entity
@Table(name = "Orders")
data class Order(
    @Id @GeneratedValue
    val id: Int = 0,

    val title: String,
    val uni: String,

    @Enumerated(EnumType.STRING)
    var status: Status,

    @ManyToOne
    val createdBy: User,

    @ManyToOne
    var takenBy: User?
)