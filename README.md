# Spring Boot Playground App

This is a basic Spring Boot app which can be used to demonstrate memory consumptions.
As any regular microservice app this one would contain:
* rest endpoints 
* db repository 
* kafka publisher/subscriber
* http client

## Install
Before running the app please ensure to compile the following subprojects:
* eureka
* gateway
* microservice

You can compile them with gradle wrapper:

`gradlew build`

## Run
Once all required jars are compiled you can launch the entire stack:

`docker-compose up --build`

This would start and prepare all necessary components including db, kafka, eureka, gateway, etc.

## Use
After the app is launched you can use the following endpoints:
GET `http://localhost:8080/microservice`
GET `http://localhost:8080/microservice/{id}`
POST `http://localhost:8080/microservice` application/json {"name":"serviceA"}

## Examine
microservice application is the one that should be examined for memory consumption.
You can check this with VisualVm (port 3333 is already exposed).

You can also track memory consumption with `docker stats`. spring-boot-playground_microservice_1 - is the container to be tracked. 