# Compfest 14 Software Engineering Academy Selection

## Trivia
- Builder Design Pattern
- Repository Service Pattern
- Clean Architecture
- Spring makes Java simple

## Made Using
- Spring Boot 2.6.8
- Spring Data JPA
- Spring Cloud Sleuth
- Lombok
- Postgres
- JUnit
- Mockito

## TODO
- [x] Get All Product
- [x] Add Product
- [x] Buy Product
- [x] Get Wallet Balance
- [x] Wallet Top Up
- [x] Wallet Withdraw
- [x] Get Wallet History
- [x] Get Wallet Summary
- [x] User Registration
- [x] User Login
- [x] User Logout
- [x] User Authentication / Authorization
- [x] Generic Error Response
- [x] Handle CORS
- [x] Deploy

## Deploy Information
- Heroku JVM Buildpack
- Heroku Posgres
- OpenJDK 11

## How To Run
- Run your Postgres
- change variable in properties according to your postgres credentials
- navigate to root project and run with command ```mvn spring-boot:run```
- api is ready to be consume with this baseurl ```http://localhost:8080/api/```

## API List
All api endpoint documented in controller package using JavaDoc
### Product Module
- ```GET /product``` -> Get All Product
- ```POST /product/add``` -> Add New Product
- ```POST /product/buy``` -> Buy Specific Product
### User Module
- ```POST /user/registration``` -> Register New User
- ```POST /user/login``` -> Login Existing User
### Wallet Module
- ```GET /wallet``` -> Get User Wallet
- ```POST /wallet``` -> Wallet Transaction
### Wallet History Module
- ```GET /wallet/history``` -> Get User Wallet History
- ```POST /wallet/history/summary``` -> Get User Wallet Summary  
