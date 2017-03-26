#What does this application do:
- The application is accepting requests with an English word as a parameter (query param name: track) and return a json output composed of 2 integers:
	1. The number of requests sent to this server with this word as a parameter since service started
	2. The number of occurrences of this word in the set of files given within the application (https://github.com/sureshselvaraj10/restservice/tree/master/src/main/resources/txtfiles)

- The application is using jersey for creating restful service, Maven/gradle (gradle branch) for building the application

- Generate war files using maven or gradle 

- Service url - http://<hostname:port>/service/api/counter?track=<value>
	- Example: http://localhost:8080/service/api/counter?track=suresh
