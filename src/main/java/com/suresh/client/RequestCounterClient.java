package com.suresh.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Test class for Request count service - stand alone java class
 */
public class RequestCounterClient implements Callable<String> {

    private static int threadPoolSize = 1000;
    private static int totalRequestCount = 1000;

    private final static String requestCounterServiceURL = "http://localhost:8080/restservice/api/counter?track=";
    private final static String totalCountServiceURL = "http://localhost:8080/restservice/api/counter/count";

    private String inputToken;

    public RequestCounterClient(String inputToken) {
        this.inputToken = inputToken;
    }

    @Override
    public String call() throws Exception {
        callRequestCounterService(requestCounterServiceURL + inputToken);
        return Thread.currentThread().getName();
    }

    public static void main(String args[]) {
        // Get ExecutorService with pool size
        ExecutorService taskExecutor = Executors
                .newFixedThreadPool(threadPoolSize);

        // List to hold the Future object associated with Callable
        List<Future<String>> taskList = new ArrayList<Future<String>>();

        Callable<String> callableTask;

        // Sample words to test
        String[] inputString = {"suresh", "another", "the", "a", "test",
                "emphasis", "his", "Apple", "their", "for", "one"};

        for (int inputIndex = 0; inputIndex < inputString.length; inputIndex++) {
            for (int tokenCount = 1; tokenCount <= totalRequestCount; tokenCount++) {

                // creating thread service
                callableTask = new RequestCounterClient(inputString[inputIndex]);

                // Run the tasks
                Future<String> futureTask = taskExecutor.submit(callableTask);
                taskList.add(futureTask);
            }
        }

        for (Future<String> task : taskList) {
            try {
                task.get();
            } catch (InterruptedException | ExecutionException exception) {
                exception.printStackTrace();
            }
        }

        // shut down the executor service
        taskExecutor.shutdown();
        System.out.println(callRequestCounterService(totalCountServiceURL));

    }


    public static String callRequestCounterService(String serviceUrl) {

        // string builder to form service response
        StringBuilder serviceResponse = new StringBuilder();

        try {

            URL url = new URL(serviceUrl);
            HttpURLConnection httpConnection = (HttpURLConnection) url
                    .openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/json");

            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + httpConnection.getResponseCode());
            }

            BufferedReader serviceResponseReader = new BufferedReader(
                    new InputStreamReader((httpConnection.getInputStream())));

            String response;

            while ((response = serviceResponseReader.readLine()) != null) {
                serviceResponse.append(response);
            }

            serviceResponseReader.close();
            httpConnection.disconnect();


        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return serviceResponse.toString();
    }
}
