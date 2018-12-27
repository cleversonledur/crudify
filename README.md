<<<<<<< Updated upstream
# Crudify - Because life is too short to CRUD
=======
  # Crudify - Because life is too short to CRUD :zap:

  ##(Under development)

>>>>>>> Stashed changes
This is a library that provides the @Crudify annotation in order to facilitate the creation of CRUD endpoints based on model classes.

This library is under development. The objective is to be able of generating a whole CRUD API simply by adding an annotation on a Model declaration inside a Controller class.

Example:

```java
@Api(value = "MyModelClass CRUD")
@RestController
@RequestMapping("/my-model-class")
public class MyModelClassController {

    @Crudify
    MyModelClass myModelClass;

    ...
}

```

<<<<<<< Updated upstream
With this annotation, we will generate the endpoints on the Controller class, services and repositories.
=======
With this annotation, we will generate the endpoints on the Controller class, services and repositories. After building and running the application you will be able to do POST, GET, UPDATE and DELETE requests, for example:

 - GET by id: https://localhost:8080/my-great-model/{id}
 - POST: https://localhost:8080/my-great-model/
 - UPDATE: https://localhost:8080/my-great-model/
 - DELETE: https://localhost:8080/my-great-model/

P.S.: Crudify do not generate security classes. This is up to you to certify your API is secure. We only generate repositories, services and the basic CRUD endpoints.
>>>>>>> Stashed changes

## TODO

	- Create repository generator
	- Create services generator
	- Create controller generator
	- Improve documentation

Do you want to contribute for this project?
Contact me: cleversonledur at gmail dot com
