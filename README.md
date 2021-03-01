# carsale
CarSale

1) Open API 3.0 Doc

	Doc: carsale / openApiDoc

	https://github.com/sbcmfreitas/carsale/blob/master/openApiDoc/openapi.yaml


   
2) This project was created with Spring Framework 2.1.16 to simulate a car sale, where the Customer, Car and Order information have endpoints to be invoked, other additional information like Brand and Options were not created in this example but the information is populated with a migration to the upload the project.
 
   A business rule has been added where the maximum limit of options is two.

   An embedded database was used (h2) where it is built when uploading the project as well as the relationships between tables and primary keys and foreign keys

	To access the DB frontend, access the address http://localhost:8080/v1/h2-console/login.jsp , after project start.

 	 User: sa

  	 Pwd: password
  
 	 H2 More info: https://www.h2database.com/
    

3) The 50 http unit tests were created in java and are in the test \ java \ com \ mvc directory
   To run the test, clone this project and use the maven in the terminal: 
   
   mvn test   or    mvn clean install
   
   Show source: https://github.com/sbcmfreitas/carsale/tree/master/src/test/java/com/mvc
   


4) To run this project into containner use:

   Docker container:   
   
   docker pull masrcosrfreitas/carsale:version1

   docker run -p 8080:8080 --name carsale masrcosrfreitas/carsale:version1

   https://hub.docker.com/layers/139391986/masrcosrfreitas/carsale/version1/images/sha256-14376733235b666120b2ce1b3aaa2670af531a8045e859daa3b54ca620a28153?context=explore		   
 
   (it is possible to run the application from the jar file, if you prefer)
   Jar File Executable: https://github.com/sbcmfreitas/carsale/blob/master/target/carsale-spring-1.0.0.jar
   
   Run with command:
   java -jar carsale-spring-1.0.0.jar
   
   
   
 5) Among the experiences I had with blocking transactions, the first case was the need for a sql query that involved a large volume of data
		and at the same time a lot of new information was still inserted and updated, for issuing reports with a lot of information for South America.

	We chose to adopt the following measures, in order to minimize problems with locks in migration:
 
	Adopt a consultation timeout.

	Adopt associated connections, where it allows a customer with multiple connections to link them to a single transaction space, so the connections are not blocked.

	Schedules were also adopted where there was little use for executing large INSERT, UPDATE or DELETE batches. (Operations that block records)

	Use of Materialized Views for historical data and some occasions.

	Create a script for monitoring locks and query execution time for reporting and analysis.
 
 6) Sent
   
   
   
   
