Assignment
----------------------
Rest API implementation for CRUD operation on User. Data is persisted to User table.

Built With
-----------
Maven, Spring Boot and Security and Hibernate and HSQLDB.

Installation
------------
Copy the assignment folder to a preferred location.
Perform mvn clean install

Running the application
------------------------
mvn spring-boot:run

Testing the application
-----------------------
Make use of any of the rest clients available(Eg: Firefox RESTClient)
URL:http://localhost:9090/user
Basic Authentication:Username:admin/Password:admin
Content-Type:application/json

List All Users
http://localhost:9090/user/
METHOD:GET

Get User By Id
http://localhost:9090/user/<id>
METHOD:GET

Create New User
http://localhost:9090/user/
METHOD:POST
Sample Input: 	{
		"username":"user",
		"password":"user",
		"status":"Deactivated"
		}

Update User By Id
http://localhost:9090/user/<id>
METHOD:PUT

Delete User By Id
http://localhost:9090/user/<id>
METHOD:DELETE

Troubleshooting and FAQ
------------------------
Logs information to assignment.log

Logger settings(log4j.properties) and application settings (application.properties) located in classpath
