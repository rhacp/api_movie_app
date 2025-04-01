# Movie App

Java service structured as a RESTful API managing a movie library using Spring Boot, JWT, PostgreSQL, and integrating a public movies API

Trello: <br>
https://trello.com/b/qaXNaswf/movie-app-web-application

---

### Credits

This is a portfolio project, not intended for any commercial use.

Many thanks to "The Movie DB"! Their public API is used to fetch movies for the project: <br>
https://www.themoviedb.org/

---

### Run the Project

1. Download the repository and unzip it.
2. Open the `src/main/resources/application.yaml` and fill in the `movie.api.key` assigned to your TMDB account and the `security.secret` for JWT encoding.
3. Open the main folder (the one containing all the other folders), then open the command prompt.
4. Run `mvn clean package` to build the project with maven and generate the jar file.
5. Run `docker build -t movie-app-api .` to generate the Docker image.
6. Run `docker-compose up -d` to create the docker stack and start it.

---

### Instructions

1. Use Postman to import the collection and environment present in the root directory of the project (`postman-collection` and `postman-environment`).
2. If there is no user present in the database, a new admin will be automatically created with the username `admin@test.com` and password `admin` (only an admin user can use the `Register` endpoint to create a new user).
3. Use the `Generate Token` request along the credentials mentioned above to get an API token.
4. Test the rest of the API.

---

### API Documentation

Swagger: <br>
https://rhacp.github.io/MAA_Swagger/

---

### Tech Stack

- Java 21
- Maven
- Spring Boot
- Docker
- PostgreSQL
- JWT
- Swagger
- Actuator

---