# City Connectivity API

A REST based web service built on the the following technologies:

* Java 1.8
* Spring Boot 2.3.1
* Maven

The service uses an undirected graph to process and navigate between connected cities as defined in the `CityConnections.txt` resources file to arrive at a solution. 

The application architecture of the web service is a typical MVC model with the following components:
* Model: Object which represents a logical entity as it relates to the API
* Controller: Provides an interface and gateway to the service; processes incoming request and provides a logical response.
* Service: Implements business logic and handles processing of stored files and data

# Prerequisites
* JDK 1.8
* Eclipse, STS or another IDE with Spring Tools plugin installed.
* [Lombok](https://projectlombok.org/download) plugin installed on your IDE.

# Installing the application
* Git clone the application and import it as a maven project in your IDE of choice. 
* Clean and install the application locally with- 
	mvn clean install

# Run the service
* Run the application as a Spring Boot App in your IDE or from the command line as below- 
	mvn package
	java -jar /path/to/jar/location/city-connectivity-api-0.0.1-SNAPSHOT com.cityconnectivity.api.cities.CityConnectivityAPIApplication
* This should start the service in port 8080 (default) at http://localhost:8080/

# Using the REST API
**URL** : `/connected`

**Method** : `GET`

**URL Params**

   **Required:**
   `origin=[string]`
   `destination=[string]`
   
**Constraints** : `Origin/Destination must contain only alphabets and spaces and must not be null or empty.`

**Success Response:**

**Code** : `200 OK`

**Content** : 
```json
{
    "origin": "Boston",
    "destination": "Newark",
    "isConnected": "Yes"
}
```
**Error Response:**

**Code:** 400 BAD_REQUEST <br />
  
**Content:** 
```json
{
	"timestamp":"2020-07-15T21:43:13.892+00:00", 
	"status":400, 
	"error":"Bad Request", 
	"message":"getConnectivityInformation.destination: Destination value must contain only alphabets and spaces.", 
	"path":"/connected"
}
```

**Sample Call:**
`curl -i -H 'Accept: application/json' 'http://localhost:8080/connected?origin=Boston&destination=Newark`

**Sample Response**
```
Content-Type: application/json
Transfer-Encoding: chunked
Date: Wed, 15 Jul 2020 21:46:40 GMT

{"origin":"Boston","destination":"Newark","isConnected":"Yes"}
```

# SWAGGER Spec
Swagger 2 has been integrated with the API for convenience sake. A user can directly examine the API spec by making a request to the Swagger endpoint- 

**URL** : `/v2/api-docs`

**Method** : `GET`

**Sample Call:**
`curl -i -H 'Accept: application/json' 'http://localhost:8080/v2/api-docs`
