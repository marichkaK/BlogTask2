+ **Create MySQL database**

	```bash
	mysql> create database articles
	```

+ **Configure database username and password**

	```yml
	# spring-social/src/main/resources/application.yml
	spring:
	    datasource:
	        url: jdbc:mysql://localhost:3306/spring_social?useSSL=false
	        username: <YOUR_DB_USERNAME>
	        password: <YOUR_DB_PASSWORD>
	```
+ **Run spring-social**

	```bash
	mvn spring-boot:run
	```