# beer-api
API to browse and manage a beer catalogue

## Requirements
Our company manages a beer catalogue from different manufacturers (providers). Beer consumers
can look up the beer catalogue in order to inspire future purchases.  
For each manufacturer, we want to at least keep track of their name and nationality and for each
beer we want to at least have the following information: beer name, graduation, type, description
and the manufacturer.

### main-1
Create the data model and the CRUD endpoints for a beers API, HTTP RESTFUL
compliant, without the need to persist any information yet.
* Consider the different use cases (listing beers/manufacturers, adding new
beers/manufacturers, updating information, getting a beer/manufacturer
detail, etc...).
* Make use of the right HTTP status codes and verbs.

### main-2
Add the required persistence/service layer to the defined API.
* It's time to implement the CRUD operations to manage the model set. The
API should support sorting.

### bonus-1
Let's make this secure:
* Admin user can authenticate and edit any information of any manufacturer
or beer.
* Manufacturer can authenticate and edit their own info/catalogue but not
another manufacturer info/catalogue.
* Anonymous users can only retrieve beers information.

### bonus-2
* Add pagination to the collection endpoints

### bonus-3
* Include the beer search functionality to the API: users should be able to search by any
beer attribute, such as name, type, nationality, manufacturer...

### bonus-4
Fulfil missing information from other sources:
* Given I search for a beer that I can't find in my system, instead of not
returning any record, I'd like to get the information from punkapi.com
(https://punkapi.com/documentation/v2) as an "external" manufacturer.

### bonus-5
* Include a picture for each beer and allow uploading a file for a beer.

## Docker usage
Build the project and the Docker image with
```
mvn clean install
```
Then run the container exposing port 8080
```
docker run -it -p 8080:8080 beer-api:0.0.1-SNAPSHOT
