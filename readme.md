# Spring Boot - Grade APIs 
This is a simple Spring Boot Rest APIs provides CRUD operation to a mySQL database regarding student's grades, courses and students identities.

The APIs designed to demo various aspects of Spring Boot
- APIs are protected by `Spring Security` via `JWT token`
- Field Vaildations, Exception handling
- use `JPA` as the Object Relational Mapper(ORM)
- Integrate `OpenAPI and Swagger UI` to document and demonstrate the APIs
- API testing with `MockMvc`

Application Docker image available

# Get Started
### Pre-reqs
- [Java 17 distro](https://aws.amazon.com/corretto/) - if you want to run the application locally
- [ Docker-for-mac or Docker-for-Windows 18.03+](https://docs.docker.com/get-docker/) - for running as a container, or image build and push

### Prepare mySQL container
mySQL container will use port 3302 in the host machine
```bash
cd ./
docker compose up  # the mySQL service
```

## Run Application
### Build the Application Image
```bash
docker build -t grade-api .
```

- or pull and use the one I built 
```bash
docker pull mlpppp/grade-api:latest
```

### Run the Container
grade-api-container will use port 8088 in the host machine
```bash
docker run --name grade-api-container --restart=on-failure --detach \
    --publish 8088:8080 \
    grade-api
```

### Run APIs Locally
ALternatively, if you have installed Java 17(corretto), you can run the Spring Boot applicaiton locally 
- You need to change the database connect string
```bash
# /src/main/resources/application.properties
# change line: previous
spring.datasource.url=jdbc:mysql://host.docker.internal:3306/db
# to
spring.datasource.url=jdbc:mysql://localhost:3306/db
```
But running Spring boot locally you can run

```bash
./mvnw spring-boot:run

# Testing APIs
./mvnw test
```

## Use APIs
- make sure the port 8088 is not in use
- API description will be available in [SwaggerUI](http://localhost:8088/swagger-ui/index.html#/)

### Authentiation
You can use `User APIs` described in [SwaggerUI](http://localhost:8088/swagger-ui/index.html#/) to register/login as user and then use bearer Token to access the rest of the APIs.

