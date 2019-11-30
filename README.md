# Customer/Order microservice system

## Implementation
The system is implemented with the excellent [Kotlin language](https://kotlinlang.org/).

It is composed by the following components:
---|---
discovery-service | used for by the other components for service discovery
customer-service | handles customers 
order-service | handles orders
gateway-service | acts as a client gateway to the others services

### discovery-service
[Eureka Server](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-eureka-server.html)

### customer-service
Customer API.

### order-service
Order API.

### gateway-service
[Zuul](https://cloud.spring.io/spring-cloud-netflix/multi/multi__router_and_filter_zuul.html).
 

## TODO
* OpenFeign?
* Ribbon?
* Hystrix?
* Kotlin 1.3.61 - define in root project
* define common stuff in root project
