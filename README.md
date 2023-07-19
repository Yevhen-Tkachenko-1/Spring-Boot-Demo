# Spring-Boot-Demo-Application
Learn and play with Spring Framework using Spring Boot

Topics covered:
- Spring Data
- Spring App Listener
- Service Layer
- Web Controllers
- Web Pages with Thymeleaf
- Rest Controllers

Implemented based on LinkedIn learning course:
[Learning Spring with Spring Boot](https://www.linkedin.com/learning/learning-spring-with-spring-boot-13886371)

### Tech stack

- Java 17
- Gradle
- Spring Boot 3.1.0 (data-jpa, web, thymeleaf, test)
- H2 database
- Lombok

### Set up your Local Project
In case you use IntelliJ IDEA, make sure you have such settings:
- [Project Structure 1](decumentation/IntelliJ-IDEA-settings-1.PNG)
- [Project Structure 2](decumentation/IntelliJ-IDEA-settings-2.PNG)
- [Settings](decumentation/IntelliJ-IDEA-settings-3.PNG)

### Static UI review
- Open static [home page](src/main/resources/static/index.html) in browser to see view and data placeholders
- Open static [reservations page](src/main/resources/templates/room_reservations.html) in browser to see view and data placeholders
- Open static [guest base page](src/main/resources/templates/guest_base.html) in browser to see view and data placeholders

### Local run
1. Run `main()` method in [application](src/main/java/com/yevhent/springbootdemo/SpringBootDemoApplication.java) class
1. Check output in console
1. Start exploring UI with [home page](http://localhost:8081) by opening it in browser
1. Continue exploring UI with other pages:
   - Go to [reservations page](http://localhost:8081/reservations) to see all reservations
   - Go to [reservations page](http://localhost:8081/reservations?targetDate=2022-01-01) with `Target Date` request param
   - Go to [reservations page](http://localhost:8081/reservations?targetDate=2022-01-01&roomNumber=C1) with `Target Date` and `Room Number` request params
   - Go to [guest base page](http://localhost:8081/guestbase) to see all guests
   - Go to [guest base page](http://localhost:8081/guestbase/radams1v@xinhuanet.com) with `Guest Email` path variable
   - Go to [guest base page](http://localhost:8081/guestbase/name?firstName=Roy&lastName=Adams) with  `name` path and `First Name` and `Last Name` request params
1. Make REST API calls using command line:
   - Add Guest:<br> 
   ```
   curl -X POST http://localhost:8081/api/guestbase -H 'Content-Type: application/json' -d '{"firstName":"Yevhen","lastName":"T","email":"Yevhen.T@gmail.com","address":"House","country":"UA","state":"L","phoneNumber":"123456789"}'
   ```
   - Create Room Reservation:<br>
   ```
   curl -X POST http://localhost:8081/api/reservations -H 'Content-Type: application/json' -d '{"targetDate":"2023-01-01", "roomNumber":"W1", "guestEmail":"Yevhen.T@gmail.com"}`
   ```
1. Make REST API calls using [Postman collection](postman/API-requests.json).