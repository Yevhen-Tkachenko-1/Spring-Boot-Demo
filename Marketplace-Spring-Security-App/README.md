# Marketplace Spring-Security app

Implemented based on LinkedIn learning course:
[Spring: Spring Security](https://www.linkedin.com/learning/spring-spring-security-15832928)

### Tech stack

- Java 17
- Gradle
- Spring Security
- H2 database
- Lombok

There is Marketplace web app where we use next Spring Security features:

* [In-Memory Authentication (non-production)](#in-memory-authentication-non-production-)

#### In-Memory Authentication (non-production) 

We start from basic Web Spring Boot app.
Let's check project structure and start service:

![](picture/1.PNG)

As we can see, in `build.gradle` file we have standard dependencies for Spring Boot web app.

As soon as app is up and running we can easily access all the endpoints.

Home:

![](picture/2.PNG)

Customers:

![](picture/3.PNG)

Orders:

![](picture/4.PNG)

Now let's introduce Spring Security by doing small change in `build.gradle` file.
We are adding just one line dependency `implementation("org.springframework.boot:spring-boot-starter-security")`
and run our app again:

![](picture/5.PNG)

At that time when we try to access any endpoint, we are getting redirected to `/login` page:

![](picture/6.PNG)

So, app is accessible only with `username` and `password`.
By default, in Spring Security `username` is `user` and password generated each time we run service.
In our case it's `56997...` as we can see on screenshot above (IntelliJ logs output).

Let's customize default Spring Security implementation (still it should NOT be in production):

![](picture/7.PNG)

We've added custom HTTP filtration, in order to make HOME page open, but other endpoints remain secured.
As soon as app is up and running we can easily access endpoints:

`localhost:8082/`:

![](picture/8.PNG)

`localhost:8082/home`:

![](picture/9.PNG)

But credentials are required for others:

![](picture/10.PNG)

Now, we have to type `user` as `username` and `password` as `password` (as specified in our configuration class)

#### JDBC User Authentication (non-production)

Previously we had credentials in runtime memory of app.
Now, let's store sensitive data as corresponding entities in DB 
and configure Spring to use JDBC User Details as suggested in 
[official docs](https://docs.spring.io/spring-security/reference/servlet/appendix/database-schema.html#_user_schema): 

![](picture/11.PNG)

As shown on the picture:
- updated schema to have `User` and `Authority` Entities
- inserted some data as plain text (which is not production way)
- updated Spring Configuration to use  `JdbcUserDetailsManager` implementation
- added Spring Configuration to encode passwords from DB - no encoding in our case (which is not production way)

As soon as app is up and running we can easily access endpoints:

`localhost:8082/`, `localhost:8082/home`:

![](picture/12.PNG)

But still, credentials are required for others.

For example, we can use user `admin` for Orders page:

![](picture/13.PNG)

And, for example, we can use user `Yevhen` for Customers page:

![](picture/14.PNG)

with passwords hardcoded in `data.sql`.

For now, no authority matters (`ROLE_ADMIN` or `ROLE_USER`).