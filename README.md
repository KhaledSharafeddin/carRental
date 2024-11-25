# Car Rental System

## Overview
The Car Rental System is a Spring Boot-based application designed to manage car rentals. It allows users to:
- Search for available cars.
- Make reservations.
- Add additional services or equipment to reservations.
- Manage cars and reservations.

This project demonstrates a layered architecture using Spring Boot, JPA, and MySQL, providing a RESTful API for all operations.

---

## Features
- Search available cars based on car type and transmission.
- Make a reservation for a car, specifying the pick-up and drop-off locations, additional services, and equipment.
- Add additional services or equipment to an existing reservation.
- View all rented or reserved cars.
- Return a car or cancel a reservation.
- Delete cars or reservations from the system.

---

## Technologies Used
- Backend: Spring Boot (Java)
- Database: MySQL with Spring Data JPA
- Build Tool: Gradle
- Testing: JUnit and Mockito

---


## Setup Instructions

### 1. Prerequisites
- Java 17 or higher
- Gradle
- MySQL Server
- Optional: MySQL Workbench for database management

### 2. Clone the Repository
git clone https://github.com/yourusername/car-rental-system.git
cd car-rental-system

### 3. Configure the Database
Make sure MySQL is running.
Create a database named car_rental_db:
CREATE DATABASE car_rental_db;

### Update the application.properties file with your MySQL credentials:
spring.datasource.url=jdbc:mysql://localhost:3306/car_rental_db
spring.datasource.username=your-username
spring.datasource.password=your-password

### 4. Run the Application
# Start the application using Gradle:
./gradlew bootRun

### The application will be accessible at:
http://localhost:8080

### 5. Test the APIs
# Use Postman or cURL to test the endpoints.

---

## Key Endpoints

### Car Operations
| Method   | Endpoint                 | Description                     |
|----------|--------------------------|---------------------------------|
| `GET`    | /api/cars/available      | Search available cars           |
| `GET`    | /api/cars/rented         | Get all rented or reserved cars |
| `POST`   | /api/cars                | Add a new car                   |
| `DELETE` | /api/cars/{carId}        | Delete a car                    |

### Reservation Operations
| Method   | Endpoint                     | Description                       |
|----------|------------------------------|-----------------------------------|
| `POST`   | /api/reservations            | Make a reservation                |
| `POST`   | /api/reservations/{id}/services | Add a service to a reservation   |
| `POST`   | /api/reservations/{id}/equipment | Add equipment to a reservation  |
| `PUT`    | /api/reservations/{id}/return   | Return a car                     |
| `DELETE` | /api/reservations/{id}       | Cancel or delete a reservation    |

---

## Testing

### Unit Tests:
### All service methods are tested using JUnit and Mockito.
./gradlew test

## Integration Tests:
### Tests for API endpoints are provided using Spring Boot's MockMvc.

---

# UML Class Diagram
![Screenshot 2024-11-20 205519](https://github.com/user-attachments/assets/e7927813-7700-4f40-8933-787496079615)
