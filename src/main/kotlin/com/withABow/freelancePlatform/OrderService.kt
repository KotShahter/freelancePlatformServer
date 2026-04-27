package com.withABow.freelancePlatform

import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicInteger

@Service
class OrderService (private val userService: UserService)
{
    private val orders = mutableListOf<Order>()
    private val counter = AtomicInteger()

    fun createOrder(title : String, uni : String, userId : Int): Order {

        val user = userService.getUserById(userId)

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

    fun acceptOrder(orderId: Int, userId: Int)
    {
        getOrderById(orderId).takenBy = userService.getUserById(userId)
    }

    fun getOrders() = orders

    fun getOrderById(id: Int): Order? = orders.find { order -> order.id == id }

}