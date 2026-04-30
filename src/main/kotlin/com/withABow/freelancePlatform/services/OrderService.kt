package com.withABow.freelancePlatform.services

import com.withABow.freelancePlatform.entities.Order
import com.withABow.freelancePlatform.entities.Status
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicInteger

@Service
class OrderService (private val userService: UserService)
{
    private val orders = mutableListOf<Order>()
    private val counter = AtomicInteger()

    fun createOrder(title : String, uni : String, userId : Int): Order {

        val user = userService.getUserById(userId) ?: throw RuntimeException ("shits fucked")

        val order = Order(
            counter.incrementAndGet(),
            title,
            uni,
            Status.Open,
            user,
            null
        )
        orders.add(order)
        return order
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
        return order
    }

    fun deleteOrder (orderId: Int): Boolean {
        return orders.removeAll { value -> value.id == orderId }
    }

    fun getOrders() = orders

    fun getOrderById(id: Int): Order? = orders.find { order -> order.id == id }

}