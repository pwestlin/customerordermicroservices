package nu.westlin.microservices.orderservice

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.net.InetAddress


@SpringBootApplication
@EnableEurekaClient
class OrderServiceApplication

fun main(args: Array<String>) {
    runApplication<OrderServiceApplication>(*args)
}

class Order(val id: Int, val customerId: Int, val name: String)

@RestController
class OrderController(private val environment: Environment) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    private val allOrders: List<Order> = listOf(
        Order(1, 1, "Lipstick"),
        Order(2, 1, "Skirt"),
        Order(3, 2, "Pants"),
        Order(4, 1, "Shoes"),
        Order(5, 2, "Shoes")
    )

    @GetMapping("/")
    fun allOrders(): List<Order> {
        logger.info("service:port: ${environment["spring.application.name"]}(${InetAddress.getLocalHost().hostName}):${environment["server.port"]}")
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