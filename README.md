# GitHub Repository Search Service

## Description
The GitHub Repository Search Service is a web application that allows users to search for GitHub repositories by username and retrieve non-forked repositories and branch details. The service also caches data using Redis for improved performance and resilience.

## Features
- **Search Repositories**: Fetch and display non-fork repositories for a given GitHub username.
- **Branch Information**: Retrieve branch details for selected repositories.
- **Error Handling**: Custom error handling for 404 and other HTTP errors.

## Technologies

- **Java 21**
- **Spring Boot 3**
- **WebFlux**
- **Redis**
- **Resilience4j**
- **Mockito**
- **JUnit 5**

## Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/github-search-repo.git

2. **Navigate to the Project Directory**
   ```bash
   cd github-search-repo
3. **Run Docker-compose**
   ```bash
    docker-compose up --build
4. **Build the Project**
Ensure you have Maven installed, then run:
   ```bash
   mvn clean install
5. **Run the Application**
   ```bash
   mvn spring-boot:run

## Usage
 **API Endpoints:**

- **Search Repositories:** GET /api/github/users/{username}/repos
**Example:**

**To fetch non-forked repositories for a user:**

GET http://localhost:8080/api/github/users/testuser/repos

- Headers: Accept: application/json
- This header specifies the media types that the client wants to receive from the server. By including Accept: application/json, the client informs the server that it expects a response in JSON format.
- Description: Retrieves all repositories for the specified GitHub user that are not forks.

Response Format:
```[
    {
        "name": "repo_name_1",
        "owner": {
            "login": "owner_name"
        },
        "branches": [
            {
                "name": "branch_name_1",
                "commit": {
                    "sha": "commit_sha_1"
                }
            }
        ]
    },
    {
        "name": "repo_name_2",
        "owner": {
            "login": "owner_name"
        },
        "branches": [
            {
                "name": "branch_name_2",
                "commit": {
                    "sha": "commit_sha_2"
                }
            }
        ]
    }
]
```

**Scheme:**
![start](src/main/resources/png/start.jpg "start")

## Testing
**Example Unit Tests**

- **Controller Tests:** Ensure the controller endpoints return the correct responses.
- **Service Tests:** Test the core business logic for fetching repositories and branches.


1. **To run the tests, use the following command:**
```bash
docker-compose up --build
mvn test
```

## Error Handling
Handle Non-Existing User
Endpoint: GET /repositories/{username}

Description: If the specified GitHub user does not exist, a 404 response is returned.

Response Format:

Handle Rate limit exceeded
Description: when the user exceeded the number of server requests, a 403 response is returned.
```
{
    "status": 403,
    "message": "Rate limit exceeded"
}
```

```
{
    "status": 404,
    "message": "This user does not exist"
}
```

Handle Http Media Type Not Acceptable (e.g. application/xml) 
Description: If client specified not acceptable media type format, a 406 response is returned.
```
{
    "status": 406,
    "message": "Not acceptable format"
}
```
## Contributors
- GitHub - https://github.com/Hlib13
- E-mail: - hlib.chebotarov@gmail.com

## License
This project is licensed under the MIT License.
