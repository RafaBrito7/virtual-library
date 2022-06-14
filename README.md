# virtual-library app
OpenSource API for Trainning the Back end of application. Developed using the Java version 11, Spring Boot, Spring Web, Spring Data with JPA, Spring Security, JWT for authentication token, Request Handlers for custom error message, Swagger for documentation, Javax for Validations of Transport Object and MariaDB for SQL DataBase

The app has deployed in AWS Service, using a Virtual Machine(Instance) of EC2.

## Test APP
There are 3 ways to test the application

1. **In AWS with Public DNS and FrontEnd Angular**
  * Access the frontend page deployed in AWS: "http://virtual-library-angular.s3-website-us-east-1.amazonaws.com/login"
  * Choose one of the users from the "Users to Test" field (Becareful with permissions roles)
  * Note: The application front is not finished, just support login, authentication and listing of users and books

2. **In AWS with Public DNS and Postman**
  * Based Url to Endpoint Requests: "http://ec2-184-72-114-28.compute-1.amazonaws.com:8080"
  * You need to have postman installed in your environment
  * I made a postman collection available with all the endpoints of the pre-established rules in the application requirements document
     * You can download the collection from the link: [Postman Collection](https://drive.google.com/file/d/1U-Ed9nQrTqginHlVsaa4tHM8KwL_hzBs/view?usp=sharing)
  * Choose one of the users from the "Test Users" field (Becareful with permissions roles)
  * After successfully logging, use your token in all requests for authentication
  * Note: Because of AWS dollar costs, I always shut down the instance, so AWS will always change the machine's public DNS every time it starts. If you can't make the requests, talk to me so I can start the instance
  * Note: Once the instance is started, you only need to change the based URL of postman requests

3. **In LocalHost**
  * Clone the repository in a folder
  * Import in your IDE using a Maven
  * Start the application(Default in "http://localhost:8080/")
  * Choose one of the users from the "Test Users" field (Becareful with permissions roles)
  * * After successfully logging, use your token in all requests for authentication

## Considerations
O backend foi quase que completamente finalizado, ficou faltando apenas a implementação dos Testes Unitários da aplicação, por causa de alguma configuração com o SpringSecurity.

## API Documentation
All API documentation is in Swagger, with all possible endpoint responses and required values. Please note that the documentation based url may vary due to the DNS that AWS changes when turning the instance off and on.

**AWS Link Swagger:** "http://ec2-184-72-114-28.compute-1.amazonaws.com:8080/swagger-ui/"

If you want to access it from your localhost, just access the link: "http://localhost:8080/swagger-ui/"

## Users to Test:
1. Lucas -> (email: 'lucas@library.com', password: '789456') - Permission Role: Role Common User
2. Luan -> (email: 'luan@library.com', password: 'luan7845658') - Permission Role: Role Admin
3. Anonymous -> (email: 'any@library.com', password: 'any') - Permission Role: Role Viewer

## SonarQube Report
![image](https://user-images.githubusercontent.com/55331551/173482224-ba500a6a-7115-4649-8c3b-d9e41ff4d369.png)

![image](https://user-images.githubusercontent.com/55331551/173482446-f22d331d-7657-4ae7-bf81-52f02a351a4d.png)



