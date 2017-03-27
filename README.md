What does this application do:
==============================
- The application is accepting requests with an English word as a parameter (query param name: track) and return a json output composed of 2 integers:
	1. The number of requests sent to this server with this word as a parameter since service started
	2. The number of occurrences of this word in the set of files given within the application (https://github.com/sureshselvaraj10/restservice/tree/master/src/main/resources/txtfiles)

- The application is using jersey for creating restful service, Maven/gradle (gradle branch) for building the application

- Generate war files using maven or gradle 

- Service url - http://<hostname:port>/service/api/counter?track=<value>
	- Example: http://localhost:8080/service/api/counter?track=suresh

- It has stand alone multi-threaded java program (RequestCounterClient.java) to test the application

- Apache Benchmarking
```
This is ApacheBench, Version 2.3 <$Revision: 1663405 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 200 requests
Completed 400 requests
Completed 600 requests
Completed 800 requests
Completed 1000 requests
Completed 1200 requests
Completed 1400 requests
Completed 1600 requests
Completed 1800 requests
Completed 2000 requests
Finished 2000 requests


Server Software:        Jetty(9.2.15.v20160210)
Server Hostname:        localhost
Server Port:            8090

Document Path:          /service/api/counter?track=suresh
Document Length:        86 bytes

Concurrency Level:      100
Time taken for tests:   1.532 seconds
Complete requests:      2000
Failed requests:        1923
   (Connect: 0, Receive: 0, Length: 1923, Exceptions: 0)
Total transferred:      456946 bytes
HTML transferred:       174946 bytes
Requests per second:    1305.77 [#/sec] (mean)
Time per request:       76.583 [ms] (mean)
Time per request:       0.766 [ms] (mean, across all concurrent requests)
Transfer rate:          291.34 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    1   1.0      0       6
Processing:     2   74  46.3     73     194
Waiting:        1   74  46.3     73     194
Total:          2   75  46.4     74     195
WARNING: The median and mean for the initial connection time are not within a normal deviation
        These results are probably not that reliable.

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
