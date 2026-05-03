package com.withABow.freelancePlatform.services

import com.withABow.freelancePlatform.Repos.OrderRepository
import com.withABow.freelancePlatform.entities.Order
import com.withABow.freelancePlatform.entities.Status
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class OrderService (private val userService: UserService, private val orderRepository: OrderRepository )
{

    fun createOrder(title : String, uni : String, userId : Int): Order {

        val user = userService.getUserById(userId) ?: throw RuntimeException ("shits fucked")

        val order = Order(
            title = title,
            uni = uni,
            status = Status.Open,
            createdBy = user,
            takenBy = null
        )

        return orderRepository.save(order)
    }

    fun acceptOrder(orderId: Int, userId: Int): Order {

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