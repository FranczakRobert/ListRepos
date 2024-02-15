# Description

This api is created to retrieve data about the user's repository.

Api uses GitHub Api as backing API.

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

# How to
For now, Api offers one endpoint, which is:
```GET /v1/{username}```

