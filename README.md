# Event Manager Java

Event Manager is a project for event management written in Java. It allows you to add, update, delete, and filter events. It uses SQLite for data storage.

## Description

This project aims to create a system for managing events, where users can store, update, and view event information.

### Features:
- Add, update, and delete events.
- Store data in an SQLite database.
- View events with date filtering.

## Technologies

- Java 8+
- SQLite for database
- JDBC for database connection
- Maven for dependency management

## How to Run the Project

1. Clone the repository to your local machine:

   ```bash
   git clone https://github.com/RostyslavBehei/event_manager_java.git
   ```
2. Navigate to the project directory:

   ```bash
    cd event_manager_java
   ```
3. Install dependencies using Maven:

   ```bash
    mvn install
   ```
4. Run the application:

   ```bash
    mvn exec:java
   ```
## Project Structure
   ```bash
    event_manager_java/
      ├── src/
      │   ├── main/
      │   │   ├── java/
      │   │   │   ├── org/
      │   │   │   │   ├── example/
      │   │   │   │   │   ├── Main.java       
      │   │   │   │   │   ├── api/
      │   │   │   │   │   │   └── EventManager.java   
      │   │   │   │   │   ├── model/
      │   │   │   │   │   │   ├── DatabaseHelper.java
      │   │   │   │   │   │   └── Event.java
      │   │   │   │   │   └── service/
      │   │   │   │   │       ├── EventManagerImpl.java
      │   │   │   └── test/
      │   │   │       └── java/
      │   │   │           └── org/
      │   │   │               └── example/
      │   │   │                   └── service/
      │   │   │                       └── EventManagerImplTest.java
      └── pom.xml 
   ```
