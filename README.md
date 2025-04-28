PetGame Project - README

Overview

PetGame is a Java-based pet simulation game built with JavaFX for the user interface and Maven for dependency management. The game includes a variety of pet interactions and is designed to run on Java 17 and above.

Prerequisites
To build and run this project, you must have the following tools and software installed:

Java 23

To check if Java is installed, run the following command in a terminal/command prompt:
java -version
If Java is not installed, you can download and install it from Oracle JDK.
Maven (for managing dependencies and building the project)

To check if Maven is installed, run:
mvn -v
If Maven is not installed, you can download it from Apache Maven.
IDE (Optional): An IDE like IntelliJ IDEA, Eclipse, or NetBeans will make it easier to manage the project.

Setting Up the Project
Clone the repository (or unzip the project files) to your local machine:

git clone <repository-url>
Navigate to the project folder:

cd PetGame
Install dependencies using Maven:

mvn install
This will download all the required libraries specified in the pom.xml file, including JavaFX, JUnit, and other dependencies.

Building the Project
To build the project (compile the code and package the application):

mvn clean package
This will compile the project and create a .jar file in the target folder (e.g., target/PetGame-1.0-SNAPSHOT.jar).
Running the Project
To run the project using the Maven JavaFX plugin (after building the project):

mvn javafx:run
This will start the application with the main class specified in the pom.xml (group47.cs2212.petgame.Main).

Alternatively, you can run the generated .jar file:

java -jar target/PetGame-1.0-SNAPSHOT.jar
Running Unit Tests
The project uses JUnit 5 for unit testing. To run the tests, use the following Maven command:

mvn test
This will execute all tests defined in the project. If you have any specific tests or test files, Maven will run them automatically.

Additional Information
JavaFX Dependencies: The project uses the JavaFX libraries for building the GUI. Maven handles the dependencies, and the necessary JavaFX modules (such as javafx-controls, javafx-fxml, and javafx-graphics) are included in the pom.xml file.

Test Framework: The project uses JUnit 5 for unit testing, which is configured through Maven.

IDE Setup: If you are using an IDE like IntelliJ IDEA or Eclipse, you may need to configure Maven and JavaFX support manually. Most IDEs have built-in support for Maven.

Troubleshooting
JavaFX Errors: If you run into errors related to missing JavaFX libraries, ensure that the correct version of JavaFX is included in the pom.xml, and you are using a compatible version of Java (Java 17 or higher).
Test Failures: If unit tests are failing, check the specific error message for guidance on what might be wrong (e.g., missing dependencies or incorrect test data).
Dependencies
The project uses the following dependencies (as defined in pom.xml):

org.openjfx:javafx-controls:17.0.6
org.openjfx:javafx-fxml:17.0.6
org.openjfx:javafx-media:21.0.2
org.junit.jupiter:junit-jupiter-api:5.10.2
org.junit.jupiter:junit-jupiter-engine:5.10.2
org.apiguardian:apiguardian-api:1.1.2
org.opentest4j:opentest4j:1.3.0
All dependencies are managed through Maven.

This README provides a step-by-step guide to setting up and running the PetGame project. If you encounter any issues, refer to the troubleshooting section or reach out for support.