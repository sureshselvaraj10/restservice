What does this application do:
==============================
- The application is accepting requests with an English word as a parameter (query param name: track) and return a json output composed of 2 integers:
	1. The number of requests sent to this server with this word as a parameter since service started
	2. The number of occurrences of this word in the set of files given within the application (https://github.com/sureshselvaraj10/restservice/tree/master/src/main/resources/txtfiles)

- The application is using jersey for creating restful service, Maven/gradle for building the application

- Generate war files using maven or gradle 

- Service url - http://<hostname:port>/restservice/api/counter?track=<value>
	- Example: http://localhost:8080/restservice/api/counter?track=suresh

- It has stand alone multi-threaded java program (RequestCounterClient.java) to test the application

- Apache Benchmarking
```
Percentage of the requests served within a certain time (ms)
  50%     74
  66%     98
  75%    114
  80%    121
  90%    138
  95%    151
  98%    164
  99%    169
 100%    195 (longest request)
 ```

How to run the application:
===========================
1. git clone https://github.com/sureshselvaraj10/restservice.git
2. cd restservice
3. ./gradlew jettyRunWar
	-  Application will be started in port 8881 (the default port given in build.gradle)
	-  If you want to start the application in port 8080, start the application using `./gradlew jettyRunWar -PhttpPort=8080`
	-  sample application url: http://localhost:8881/restservice/api/counter?track=suresh
