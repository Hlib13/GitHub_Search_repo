# Company Management System

## Overview

The Company Management System is a microservices-based application designed to manage companies, departments, teams, and projects. It provides a set of APIs to handle CRUD operations for companies, departments, teams, and projects, while also allowing retrieval of related entities across different services.

## Technologies Used

- **Spring Boot**: Framework for building the microservices.
- **Spring Data JPA**: For data persistence and repository management.
- **WebClient**: For making HTTP requests between microservices.
- **Docker**: For containerization and running services in isolated environments.
- **PostgreSQL**: Database management system used in the services.

## Getting Started

### Prerequisites

- Java 21 or higher
- Docker and Docker Compose
- PostgreSQL

### Setup

1. Clone the repository:

    ```bash
    git clone https://github.com/Hlib13/company.git
    cd company
    ```

2. Navigate to the root directory of the project and start the services using Docker Compose:

    ```bash
    docker-compose up --build
    ```

3. Access the services at the following URLs:

    - Company Service: `http://localhost:8080`

## API Endpoints

### Company Service API

- **Search Companies**
    - `GET /company`
    - **Response:**
      ```json
      [
        {
          "id": 1,
          "companyName": "WOWcompany co",
          "departments": [
            {
              "departmentId": 1,
              "departmentName": "IT Department",
              "teams": [
                {
                  "teamId": 1,
                  "teamName": "Development Team",
                  "projects": [
                    {
                      "projectId": 1,
                      "projectName": "Epic project",
                      "manager": {
                        "managerName": "John Doe",
                        "email": "john.doe@example.com"
                      }
                    }
                  ]
                }
              ]
            }
          ]
        }
      ]
      ```

- **Create Company**
    - `POST /company`
    - **Request Body:**
      ```json
      {
        "companyName": "New Company"
      }
      ```
    - **Response:**
      ```http
      HTTP/1.1 201 Created
      ```

- **Update Company**
    - `PUT /company/{id}`
    - **Request Body:**
      ```json
      {
        "companyName": "Updated Company Name"
      }
      ```
    - **Response:**
      ```http
      HTTP/1.1 200 OK
      ```

- **Delete Company**
    - `DELETE /company/{id}`
    - **Response:**
      ```http
      HTTP/1.1 200 OK
      ```


## Contact

For any questions or issues, please open an issue on the GitHub repository or contact the project maintainers.

Feel free to adjust any sections or details as needed based on your specific project requirements!