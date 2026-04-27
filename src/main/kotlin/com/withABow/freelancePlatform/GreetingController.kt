package com.withABow.freelancePlatform

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicInteger

private const val template = "Hello, %s!"
private val users = mutableListOf<User>()
private val orders = mutableListOf<Order>()

@RestController
class GreetingController {

    private val counter = AtomicInteger()

    @GetMapping("/balls")
    fun greeting() = "oo"

    @GetMapping("/getUsers")
    fun showUsers() = users

    @GetMapping("/getOrders")
    fun showOrders() = orders

    @PostMapping("/createUser")
    fun createUser(@RequestParam name: String = "noname"): String {
        users.add(User(counter.incrementAndGet(), name, Role.USER))
        return "Success!, User $name created"
    }

    @PostMapping("/createOrder")
    fun createOrder(
        @RequestParam title: String,
        @RequestParam uni: String,
//        @RequestParam createdBy: String,
//        @RequestParam takenBy: String,
        ) : String {
            orders.add(Order(
                counter.incrementAndGet(),
                title,
                uni,
                Status.Open,
                users.last(),
                users.first()))

            return "Success!, Order $title created. Selected uni is $uni"
    }



    @GetMapping("/greeting")
    fun greeting(@RequestParam name: String = "World") =
        Greeting(1,template.format(name))
}