# KALAH
The game is an implementation of the  `kalah` game which its definition 
and instruction can be found [here](https://en.wikipedia.org/wiki/Kalah).


The current game is a rest service with no ui and is handling the game on the memory 
with minimum coupling between the game rules, and the game storing mechanism.

## Installation
Th project is developed by [java](https://www.java.com/) programming language, and 
to launch it java should be installed on the machine. 
The instruction of installation of java can be found [here](https://www.java.com/en/download/help/download_options.html).

The project is created based on [Maven](https://maven.apache.org/index.html) 
to manage dependencies and install the artifacts.
The instruction of installing Maven can be found [here](https://maven.apache.org/install.html).


The project specifically is using [Spring Boot](https://spring.io/projects/spring-boot) 
to manage beans lifecycle, and it is using [Spring MVC](https://spring.io/guides/gs/serving-web-content/)
for exposing rest web services.

With having the source code it is possible to build and start the project with one of the
following procedure.

## Execution
There are two ways to launch the project.
It is possible to choose either one.

In the CLI head to the root folder of the project.
### JAVA
Use following command to build the project. 

```bash
mvn clean install
```
Use following command to launch the project.

```bash
java -jar target/kalah-1.0-SNAPSHOT.jar
```

### Spring Boot
Use following command to build and launch the project in one step.

```bash
mvn spring-boot:run
```
