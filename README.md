# carsale
CarSale

1) Open API 3.0 Doc

http://localhost:8080/v1/swagger-ui/#/
   
2) This project was created with Spring Framework 2.1.16 to simulate a car sale, where the Customer, Car and Order information have endpoints to be    invoked, other additional information like Brand and Options were not created in this example but the information is populated with a migration to the upload the project.
A business rule has been added where the maximum limit of options is two.
An embedded database was used (h2 https://www.h2database.com/) where it is built when uploading the project as well as the relationships between tables and primary keys and foreign keys
To access the DB frontend, access the address http://localhost:8080/v1/h2-console/login.jsp , after project start

  User: sa

  Pwd: password

3) The http unit tests were created in java and are in the test \ java \ com \ mvc directory
   To run the test, clone this project and use the maven in the terminal: 
   
   mvn test
   
   or  
   
   mvn clean install

4) To run this project into containner use:

   Docker container:   
   
   docker pull masrcosrfreitas/carsale:version1

   docker run -p 8080:8080 --name carsale masrcosrfreitas/carsale:version1

   https://hub.docker.com/layers/139391986/masrcosrfreitas/carsale/version1/images/sha256-14376733235b666120b2ce1b3aaa2670af531a8045e859daa3b54ca620a28153?context=explore		   
 
   
   (it is possible to run the application from the jar file, if you prefer)
   Jar File Executable location: carsale / target / carsale-spring-1.0.0.jar
   
   Run with command:
   java -jar carsale-spring-1.0.0.jar
   
 5) TODO
 
 6) DONE
   
   
   
   
