# Countries API 
Simple API to get information about countries. Made for educational purposes for my blog on "Creating a REST API with Spring."

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites
- Java 21
- Maven 3.6.3 (wrapper included)

### Installing
1. Clone the repository
2. Run `mvn clean install` in the root directory
3. Run `mvn spring-boot:run` in the root directory
4. The API will be available at `http://localhost:8080/api`

### Running the tests
Run `mvn test` in the root directory

### How to use
The API has 3 endpoints:
- `/v1/all` - Returns all countries
- `/v1/country/{capital name}` - Returns the country with the given capital name
- `/v1/capital/{country name}` - Returns the capital of the given country name

You can use Postman or any other tool to make requests to the API.

The API Specification can be found [here](API_Specification.json).

### Built With
- [Spring Boot](https://spring.io/projects/spring-boot) - The web framework used
- [Maven](https://maven.apache.org/) - Dependency Management

## Authors
- **Brayan Roman**