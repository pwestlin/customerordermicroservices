# Customer/Order microservice system

## Implementation
The system is implemented with the excellent [Kotlin language](https://kotlinlang.org/).

It is composed by the following components:
---|---
discovery-service | used for by the other components for service discovery
customer-service | handles customers 
order-service | handles orders
gateway-service | acts as a client API gateway to the other services
web | An **extremely** simple web frontend implemented with Spring Boot and Mustache.

## How it works
order-service, customer-service, and gateway-services registers themselves with discovery-service.  
gateway-service is the entry point for clients and therefore it is responsible for the external API.  

### discovery-service
[Eureka Server](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-eureka-server.html)

### customer-service
Customer API.

### order-service
Order API.

### gateway-service
[Zuul](https://cloud.spring.io/spring-cloud-netflix/multi/multi__router_and_filter_zuul.html).
 
## Building and running

1. Build all docker images: `gradle docker`
2. Start system: `docker-compose up`

In a production system the only exposed port from Docker Compose would be the port for the frontend (8080).  
I've also chosen to export 3000 which is the port for Eureka and 8090 which is the API gateway, for testing purposes.

## Using
You can view all registered services in Eureka server (discovery-service) at http://localhost:3000.  

Get customer with id 1:  
http://localhost:8090/customers/1

Get order with id 1:  
http://localhost:8090/orders/1

Get all orders for customer with id 1:  
http://localhost:8090/orders/customer/1

See all customers and orders in the web frontend:  
http://localhost:8080/

FÖRKLARA FLÖDET OCH ATT DET FINNS FLERA AV (NÄSTAN) VARJE KOMPONENT
SKA JAG HA TVÅ GATEWAYS, EN FÖR WEB OCH EN FÖR API?

## TODO
* (Open)Feign?
* Hystrix?
* define common stuff in Gradle root project
* Tests... :D