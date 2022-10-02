# Site Integration Management REST API

## Reference Documentation
The goal of this exercise is to build a small application that persists, deletes, and lists site integration
properties as keys and values in a SQL database


# Solution
  ## 1. Requirements:
  - Docker https://www.docker.com/
  - Postman (https://www.postman.com/) or any other Rest client tools
  - Postgres (https://www.postgresql.org/)
  - Java 17 (https://www.oracle.com/java/technologies/downloads/#java17)
  - Maven 3 (https://maven.apache.org/)

  ## 2. How to build and run
  - Checkout the repo at (https://github.com/supngo/thrive.git)
  - Run locally:
  ```
  mvn clean package
  java -jar target/properties-1.0.0.jar
  ```
  - Run in Docker Compose:
  ```bash
  mvn install
  docker-compose up --build
  ```

  ## 3. Test Rest Endpoints
  User Postmand or Swagger to test the 3 endpoints below:

  POST http://localhost:8080/thrive/properties/integration/site/{id}

  Get http://localhost:8080/thrive/properties/integration/site/{id}

  DELETE http://localhost:8080/thrive/properties/integration/site/{id}/index/{index}

  ## 4. Heath Check
  http://localhost:8080/thrive/actuator/health

  ## 5. Swagger
  http://localhost:8080/thrive/swagger-ui/index.html

Question? thongo5430@gmail.com
