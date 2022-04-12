E-commerce
=====

e-commerce system

This is a simple e-commerce system that allow users to buy and sell products. The system allow its customers to find products, adding items to the cart and confirming & placing orders. The admin of this system can add and/or update product information as like inventory management. All the related information are stored into SQL database. 

#### Minimum features 
The system should provide the following minimum features. On top of this, developers are allowed to add extra features if they want to do.
1. Product browsing (List view / Detail view)
2. Product search (by name, etc.)
3. Shopping Cart
4. Ordering
5. Confirmation 
6. User management (Login, New user registration)
7. User's shopping profile 
8. Inventory management (Admin)

### Documentation

[DevSpec - The e-commerce system](https://confluence.rakuten-it.com/confluence/display/CCTECH/The+e-commerce+system)

#### Test Project Structure
This is a multi module maven project. Under the main project directory there are three primary modules. The common module is used by both api and application.
```
ecommerce
+-- api
|   +-- src
|   |   +-- main
|   |   |   +-- java
|   |   |   +-- resources
|   |   +-- test
+-- application
|   +-- src
|   |   +-- main
|   |   |   +-- java
|   |   |   +-- resources
|   |   +-- test
+-- common
|   +-- src
|   |   +-- main
|   |   |   +-- java
|   |   +-- test
```

### Run / Build

Java 11 is required to run this project.
This project is based on Springboot framework.
Both 'api' and 'application' should in running state in order to test this ecommerce system.  
This project uses a [Maven wrapper](https://github.com/takari/maven-wrapper),
so the `mvnw` or `mvnw.cmd` scripts can be used in place of a separate Maven installation.

### Contact
Talent Development Group

Commerce Tech Management Office (CTMO)
