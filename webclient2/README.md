### Webclient2

#### Project Description
The project is about:

- Call Get, POST, DELETE services (ref: webclient2/model/service)
- While getting the response, pass the JSON data to POJO (Plain Old Java Objects) using WebClient (ref: webclient2/model)
- Validate the values in the JSON object using hard assertions 

Example using WebClient and POJO: 
```java
// Call GET service and pass the Employee response into Employee POJO
private EmployeeService employeeResponse = new EmployeeService();
Employee employees = employeeResponse.getEmployeeUserResponse(EMPLOYEE_URL);

// Employee util methods are in EmployeeWorkflow
EmployeeWorkflow employeeWorkflow = new EmployeeWorkflow(employees);
Long totalEmployees = employeeWorkflow.getTotalEmployees();
Assertions.assertEquals(24, totalEmployees);
```

#### Components
```properties
POJOs (model) -> src/main/java/com/webclient2/model
Services      -> src/main/java/com/webclient2/model/service
Workflows     -> src/main/java/com/webclient2/model/workflow
Tests         -> src/test/java/com/webclient2
```
#### Tools Used
```properties
Testing framework  -> Junit 5
Build Management   -> Maven
Rest API           -> Springframeworks WebClient
Method Annotations -> Spring Framework and Lombok
```

#### Author:
```properties
anurag muthyam
anu.drumcoder@gmail.com
https://github.com/aryaghan-mutum
```

