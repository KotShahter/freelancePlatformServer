package com.withABow.freelancePlatform.services

import com.withABow.freelancePlatform.repos.OrderRepository
import com.withABow.freelancePlatform.entities.Order
import com.withABow.freelancePlatform.entities.Status
import com.withABow.freelancePlatform.entities.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class OrderService (private val userService: UserService, private val orderRepository: OrderRepository )
{

    fun createOrder(
        createdBy: User,
        title: String,
        uni: String,
        professor: String,
        notes: String
    ): Order {

        val order = Order(
            title = title,
            uni = uni,
            status = Status.Open,
            createdBy = createdBy,
            professor = professor,
            notes = notes,
            takenBy = null
        )

        return orderRepository.save(order)
    }

    fun acceptOrder(orderId: Int, userId: Long): Order {

        val order = getOrderById(orderId) ?: throw RuntimeException ("shit is fucked")
        val user = userService.getUserById(userId) ?: throw RuntimeException ("shits fucked")

        if (order.status == Status.Closed)
        {
            throw RuntimeException ("nah already taken")
        }

        order.takenBy = user
        order.status = Status.Closed

        return orderRepository.save(order)
    }

    fun deleteOrder (orderId: Int) {
        return orderRepository.deleteById( orderId )
    }

    fun getOrders() = orderRepository.findAll()

    fun getOrderById(id: Int): Order? = orderRepository.findByIdOrNull(id)
}