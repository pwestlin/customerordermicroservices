# Customer/Order microservice system

## Implementation
The system is implemented with the excellent [Kotlin language](https://kotlinlang.org/).

It is composed by the following components:
---|---
discovery-service | used for by the other components for service discovery
customer-service | handles customers 
order-service | handles orders
gateway-service | acts as a client gateway to the others services

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

### Docker (preferred)
1. Build all docker images: `gradle docker`
2. Start system: `docker-compose up`

In a production system the only exposed port from Docker Compose would be the port for the gateway-service (8080).  
I've chosen to export 3000 which is the port for Eureka.

### Gradle
Start each services with `gradle bootrun` in their respective directory in the following order:
1. discovery-service
2. customer-service 
3. order-service
4. gateway-service | acts as a client gateway to the others services

## Using
You can view all registered services in Eureka server (discovery-service) at http://localhost:3000.  

Get customer with id 1:  
http://localhost:8080/customers/1

Get order with id 1:  
http://localhost:8080/orders/1

Get all orders for customer with id 1:  
http://localhost:8080/orders/customer/1


## TODO
* Another service that combines info from the order-service and customer-service (using coroutines?)
* OpenFeign?
* Ribbon?
* Hystrix?
* define common stuff in root project
* Tests... :D