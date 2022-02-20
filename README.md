#BSfTransferService
# Instruction to run the project
- Install JDK 11 or higher on your machine
- Install Maven on your machine
- Install any IDE Tool ( IntelliJ)
# Steps to run the service 
- apply command (maven clean install)
- there is two way to start this service 
  - From IDE
    - Open the project from IDE tool
    - make sure you install JDK 1.8 or higher 
    - Make sure install maven in your system 
    - Apply command mvn clean install 
    - Start the server from the IDE will run on port 7171
  - Use Docker Image 
    - Install Docker on your machine 
    - pull image from docker hub with command ( docker pull ahmedlabib/bsfservicedocker:latest)
    - run docker image (docker run -d -p 7171:7171 ahmedlabib/bsfservicedocker:latest)
- Server will be up and running on port 7171  , you can view the swagger through this URL   
  http://localhost:7171/swagger-ui/index.html#/
- you can view the h2 console through this url
  http://localhost:7171/h2-console/login.jsp
  with username : account & password : account 
# What is Purpose of This service
- This service contain two main section 
  - first section is related for account 
    - You will be able to create a new account with default balance for simplicty
    - you will be able to get account details by accountId
  - second section is related for transfer
    - you will be able to transfer money from one account to another account 
# Technology used

- Spring Boot version 2.3.7.RELEASE
- Java 11
- Swageer Documentation
- Spring Data H2 DB
- Lombok
- Spring boot test
- jacoco for test coverage 
- Docker