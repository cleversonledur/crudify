  # Crudify - Because life is too short to CRUD
This is a library that provides the @Crudify annotation in order to facilitate the creation of CRUD endpoints based on model classes.

This library is under development. The objective is to be able of generating a whole CRUD API simply by adding an annotation on your Model Class.

Example:

```java
@Crudify  //<--- You will just insert this annotation
@Document(collection = "myGreatModel")
public class MyGreatModel{

    @Id
    private String id;

    private String name;

    private String information;

    //Constructors...

    //Getters/Setters and other methods...
}

```

With this annotation, we will generate the endpoints on the Controller class, services and repositories.

## TODO

	- Create repository generator
	- Create services generator
	- Create controller generator
	- Improve documentation
