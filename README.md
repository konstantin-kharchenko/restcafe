# restcafe
The client makes an Order for lunch (chooses from the menu) and indicates the time
when he would like to receive an order. The system shows the price of the Order and
offers to pay from a client account or in cash upon receipt
order. Points are awarded to the client for pre-orders
loyalty. If the Client places an order and does not pick it up, then the points
loyalty is removed up to its blocking. The client can evaluate
every order and leave feedback. The administrator manages the menu,
sets/removes bans/bonuses/points for Clients.

### The client can:
+ create an order,
+ change profile.

### Administrator can:
In this implementation of the project, the admin logic was not developed

Also, the client and the administrator can register and log in

This application has been developed to demonstrate how a REST application works, and Spring Security will be implemented in this application.
What is REST for?
You need to remember the previous SpringBootCafe application and think about what the problem might be.
The monolithic nature of View and the server part can cause quite a few problems in the future. It is much better to develop independent parts from each other. For what? To make it easier to replace those who want to use the server. It's about microservices. That's what REST is for. How is REST different from regular Spring MVC? The fact that the controller passes JSON. JSON is a standard textual data exchange format. Our data (for example, Entity) is placed in a JSON file and sent to whoever requests data from the server.
The microservice sends a request to our server.
After that, the controller method calls the methods of the service, for which the injection into this same controller was previously made using Spring. The Service contains the main application logic. The service uses the repository to retrieve data from the database. In this application, the work of obtaining data is made much easier than in the SpringCafe application. Here it is not gentle to receive a Hibernate session, everything is done for us. We only need to create an interface for one or another repository and inherit from the repositories already offered to us, which are located in Sprint Data JPA. These repositories contain almost all the necessary methods: findAll(), deleteById() and many others. Also, if you are missing a method, then you can easily create a method for you. Also, if you follow the rules for the name of the method and its parameters, you do not need to use @Query at all. Spring will understand what we want. 
After that, the Entity will return to the service, where some manipulation will be performed on it, and then it will return to the controller.
After that, the controller returns the Entity. The conversion of the Entity to a JSON object happens automatically thanks to @RestController.

### Below is the schema of the tables in my database

![image](https://user-images.githubusercontent.com/51529773/190674084-5b537bbf-0408-4c50-baaf-e439689c399a.png)

### Applied technologies:
+ Spring Core
+ REST
+ Spring Data JPA
+ Spring Boot
+ Spring Security
+ Lombok
+ Mapstruct
+ Mail Sender
+ PostgreSQL
+ Hibernate Validator
+ Password encoding
