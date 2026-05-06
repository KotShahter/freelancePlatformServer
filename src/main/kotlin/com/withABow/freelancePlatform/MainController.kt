package com.withABow.freelancePlatform

import com.withABow.freelancePlatform.services.OrderService
import com.withABow.freelancePlatform.services.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class MainController (
    private val userService: UserService,
    private val orderService: OrderService
){

    @GetMapping("/users")
    fun showUsers() = userService.getUsers()

    @GetMapping("/orders")
    fun showOrders() = orderService.getOrders()

    @PostMapping("/users")
    fun createUser(@RequestParam name: String = "Noname") = userService.createUser(name)

//    @PostMapping("/orders")
//    fun createOrder(
//        @RequestParam title: String,
//        @RequestParam uni: String,
//        @RequestParam userId: Int) = orderService.createOrde  r(11111, uni, userId)


    @DeleteMapping("/orders")
    fun deleteOrder(
        @RequestParam id: Int
    ) = orderService.deleteOrder(id)

    @PatchMapping("/orders")
    fun acceptOrder(@RequestParam orderId: Int, @RequestParam userId: Long) =
        orderService.acceptOrder(orderId, userId)
}
