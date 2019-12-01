package nu.westlin.microservices.orderservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView


@SpringBootApplication
//@EnableEurekaClient
class OrderServiceApplication

fun main(args: Array<String>) {
    runApplication<OrderServiceApplication>(*args)
}

class Order(val id: Int, val customerId: Int, val name: String)
class Customer(val id: Int, val name: String)


@Controller
class WebController {

    private val orders: List<Order> = listOf(
        Order(1, 1, "Lipstick"),
        Order(2, 1, "Skirt"),
        Order(3, 2, "Pants"),
        Order(4, 1, "Shoes"),
        Order(5, 2, "Shoes")
    )

    private val customers = listOf(
        Customer(1, "Daisy Duck"),
        Customer(2, "Mickey Mouse")
    )

    @GetMapping(path = ["/", "/index.html"])
    fun allOrders(model: HashMap<String, Any>): ModelAndView {
        model["orders"] = orders
        model["customers"] = customers

        return ModelAndView("ordersandcustomers", model)
    }

/*
    @GetMapping("/customer/{customerId}")
    fun getOrderByCustomerId(@PathVariable customerId: Int): List<Order> {
        return allOrders
            .filter { order: Order -> order.customerId == customerId }
    }
*/
}