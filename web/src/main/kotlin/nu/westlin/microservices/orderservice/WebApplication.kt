package nu.westlin.microservices.orderservice

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.servlet.ModelAndView


@SpringBootApplication
@EnableEurekaClient
class WebApplication

fun main(args: Array<String>) {
    runApplication<WebApplication>(*args)
}

class Order(val id: Int, val customerId: Int, val name: String)
class Customer(val id: Int, val name: String)

@Configuration
class WebConfiguration {

    @Bean
    fun restTemplate(): RestTemplate = RestTemplateBuilder().build()
}

@Controller
class WebController(private val restTemplate: RestTemplate, private val loadBalancerClient: LoadBalancerClient) {
    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping(path = ["/", "/index.html"])
    fun allOrders(model: HashMap<String, Any>): ModelAndView {
        val url = apiUrl()
        runBlocking(Dispatchers.IO) {
            launch {
                model["orders"] = restTemplate.getForObject<List<Order>>("$url/orders")
            }
            launch {
                model["customers"] = restTemplate.getForObject<List<Customer>>("$url/customers")
            }
        }

        return ModelAndView("ordersandcustomers", model)
    }

    private fun apiUrl(): String {
        return with(loadBalancerClient.choose("gateway-service")) {
            "http://${host}:${port}/api"
        }.also { logger.info("url = $it") }
    }
}