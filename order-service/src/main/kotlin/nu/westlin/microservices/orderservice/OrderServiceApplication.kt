package nu.westlin.microservices.orderservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
@EnableEurekaClient
class OrderServiceApplication

fun main(args: Array<String>) {
    runApplication<OrderServiceApplication>(*args)
}

class Order(val id: Int, val customerId: Int, val name: String)

@RestController
class OrderController {

    private val allOrders: List<Order> = listOf(
        Order(1, 1, "Lipstick"),
        Order(2, 1, "Skirt"),
        Order(3, 2, "Pants"),
        Order(4, 1, "Shoes"),
        Order(5, 2, "Shoes")
    )

    @GetMapping("/")
    fun allOrders(): List<Order> {
        return allOrders
    }

    @GetMapping("/{id}")
    fun getOrderById(@PathVariable id: Int): Order {
        return allOrders
            .first { order: Order -> order.id == id }
    }

    @GetMapping("/customer/{customerId}")
    fun getOrderByCustomerId(@PathVariable customerId: Int): List<Order> {
        return allOrders
            .filter { order: Order -> order.customerId == customerId }
    }
}