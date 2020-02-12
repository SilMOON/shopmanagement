# shopmanagement
## Introduction
This is a simple goods management site demo.

## Developing
Make sure you have the following installed:
* JDK 11
* MariaDB (MySQL)
* Intellij IDEA (Optional)

## Design Description
### Frontend
TBA

### Backend
TBA

## Deploy Instruction (Linux)
### I. Create The MySQL Database
Open a terminal and open your MySQL: 
```
mysql -u root --password
```

Then create a new database: 
```
MariaDB [(none)]> create database shop_manage_db;
MariaDB [(none)]> create user 'shopManageAdmin'@'localhost' identified by 'Your password';
MariaDB [(none)]> grant all on shop_manage_db.* to 'shopManageAdmin'@'localhost';
```

### II. Import the project and change the configuration
Import the project into Intellij IDEA and follow the instruction to build the Gradle project. Or simply build the project as a normal Gradle project in command line.

After that, modify the password to the one you set for the database in `src/main/resources/application.properties` file:
```
spring.datasource.password=Your password
```

### III. Run the project
Use command line to get into the project's root directory which the `gradlew` program is located in and run: 
```
./gradlew bootRun
```

Now open the browser and access `http://localhost:8080/`, (TBA).