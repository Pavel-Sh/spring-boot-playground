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

## Modify RAM limits
By default the application is not limited on both docker and java heap.
#### 1.  Heap limits
Modify microservice java command in docker-compose file to add Xmx:
`java -Xmx100m`
#### 2. Docker limits
To set limits for microservice app container add the following to docker compose:
```
deploy:
  resources:
    limits:
      memory: 300M
```

## Examine
microservice application is the one that should be examined for memory consumption.
#### 1. Examine via VisualVM
You can check memory consumption by connecting to port 3333.
#### 2. Examine via docker stats
Simply execute `docker stats`. spring-boot-playground_microservice_1 - is the container to be tracked. 
#### 3. Examine via java jcmd
Firstly you need to identify java app PID:
`docker exec spring-boot-playground_microservice_1 ps`

and then execute for required PID:
`docker exec spring-boot-playground_microservice_1 jcmd 9 VM.native_memory summary`