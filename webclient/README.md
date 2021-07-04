### Webclient

#### Project Description
The project is about:
- Read file data using `FileReader` from the project directory under resources folder (ref: workflows/JsonPayloadWorkflow.java)
- Parse the files using `JsonElement` and `JsonParser` (ref: workflows/JsonPayloadWorkflow.java)
- Iterates the stream of JSON object using `JsonWorkflow.getJsonStream()` class (ref: workflows/JsonWorkflow.java)
- Validate the values in the JSON object using hard assertions 

#### Components
```properties
POJOs (model) -> NO POJOs
Services      -> src/main/java/com/webclient
Workflows     -> src/main/java/com/webclient/workflows
JSON files    -> src/main/resources/json
Tests         -> src/test/java/com/webclient
```
#### Tools Used
```properties
Testing framework  -> Junit 5
Build Management   -> Maven
Rest API           -> Springframework's WebClient
Method Annotations -> Spring Framework and Lombok
```

#### Author:
```properties
anurag muthyam
anu.drumcoder@gmail.com
https://github.com/aryaghan-mutum
```

