# Description
This API makes it easy to retrieve user-owned repository data. Using the GitHub API as a basis, it provides basic information about non-fork repositories in JSON format. The data includes repository names, owner logins and details about each branch, including their names and last SHA approval.

# Data
The data is returned in JSON format.
It returns a non-forks respository and contains information such as:
- Name of the repository
- Owner's login
- For each branch, its name and last sha approval

# Technologies
- Java 21
- Spring
- Lombok
- Jackson
- WebFlux

# Endpoints
Currently, the API offers a single endpoint: ```GET /v1/{username}```. This endpoint allows users to access repository information by specifying their GitHub username.


