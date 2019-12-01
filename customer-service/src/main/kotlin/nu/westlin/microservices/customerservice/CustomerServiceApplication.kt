package nu.westlin.microservices.customerservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse


@SpringBootApplication
@EnableEurekaClient
class CustomerServiceApplication

fun main(args: Array<String>) {
    runApplication<CustomerServiceApplication>(*args)
}

class Customer(val id: Int, val name: String)

@RestController
class CustomerController {

    private val allCustomers: List<Customer> = listOf(
        Customer(1, "Daisy Duck"),
        Customer(2, "Mickey Mouse")
    )

    @GetMapping("/")
    fun allCustomers(): List<Customer> {
        return allCustomers
    }

    @GetMapping("/{id}")
    fun getCustomerById(@PathVariable id: Int, response: HttpServletResponse): Customer? {
        val customer = allCustomers
            .firstOrNull { customer: Customer -> customer.id == id }
        if (customer == null) response.status = HttpStatus.NOT_FOUND.value()
        return customer
    }
}