package nu.westlin.microservices.orderservice

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
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
    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Bean
    fun restTemplate(@Value("\${api.rooUri}") apiRootUri: String): RestTemplate {
        logger.info("apiRootUri = $apiRootUri")
        return RestTemplateBuilder().rootUri(apiRootUri).build()
    }
}

@Controller
class WebController(private val restTemplate: RestTemplate) {

    @GetMapping(path = ["/", "/index.html"])
    fun allOrders(model: HashMap<String, Any>): ModelAndView {
        runBlocking(Dispatchers.IO) {
            launch {
                model["orders"] = restTemplate.getForObject<List<Order>>("/orders")
            }
            launch {
                model["customers"] = restTemplate.getForObject<List<Customer>>("/customers")
            }
        }

        return ModelAndView("ordersandcustomers", model)
    }
}