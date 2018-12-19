# crudify
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

With this annotation, we will generate the endpoints on the Controller class, services and repositories.

## TODO

	- Create repository generator
	- Create services generator
	- Create controller generator
	- Improve documentation	
