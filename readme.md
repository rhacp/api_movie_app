# Movie App

Java service structured as a RESTful API managing a movie library and integrating a public movies API

Trello: <br>
https://trello.com/b/qaXNaswf/movie-app-web-application

TMDB API used to fetch movies: <br>
https://www.themoviedb.org/

---

### Run the Project

1. Download the repository and unzip it.
2. Open the `src/main/resources/application.yaml` and fill in the `movie.api.key` and the `security.secret`.
3. Open the main folder, then open the command prompt.
4. Run `mvn clean package` to build the project with maven and generate the jar file.
5. Run `docker build -t movie-app-api .` to generate the Docker image.
6. Run `docker-compose up -d` to create the docker stack and start it.

---

### Instructions

1. Use Postman to import the collection and environment present in the root directory (`Movie App API.postman_environment.json` and `MovieApp.postman_collection.json`).
2. If there is no user present in the database, a new admin will be automatically created with the username `admin@test.com` and password `admin`.
3. Use the `Generate Token` request along the credentials mentioned above to get an API token.
4. Test the rest of the API (the other endpoints require the authentication token).

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