# beer-api
An API to browse and manage beer catalogue

## Docker usage
Build the project and the Docker image with
```
mvn clean install
```
Then run the container exposing port 8080
```
docker run -it -p 8080:8080 beer-api:0.0.1-SNAPSHOT
