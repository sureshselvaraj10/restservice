package com.suresh.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Test class for Request count service - stand alone java class
 */
public class RequestCounterClient implements Callable<String> {

    private static int threadPoolSize = 100;
    private static int threadSize = 20000;
    private int threadSleepTime = 1000;

    private final static String serviceURL = "http://localhost:8080/api/counter?track=";

    @Override
    public String call() throws Exception {
        Thread.sleep(threadSleepTime);
        return Thread.currentThread().getName() + ": " + callService();
    }

    public static void main(String args[]) {
        //Get ExecutorService with pool size
        ExecutorService taskExecutor = Executors.newFixedThreadPool(threadPoolSize);

        //List to hold the Future object associated with Callable
        List<Future<String>> taskList = new ArrayList<Future<String>>();

        // creating thread service
        Callable<String> callableTask = new RequestCounterClient();

        // Run the tasks
        for (int i = 0; i < threadSize; i++) {
            Future<String> futureTask = taskExecutor.submit(callableTask);
            taskList.add(futureTask);
        }
        for (Future<String> task : taskList) {
            try {
                System.out.println(new Date() + "::" + task.get());
            } catch (InterruptedException | ExecutionException exception) {
                exception.printStackTrace();
            }
        }

        //shut down the executor service
        taskExecutor.shutdown();
    }

    public static String callService() {

        // string builder to form service response
        StringBuilder serviceResponse = new StringBuilder();

        // Sample words to test
        String[] inputString = {"suresh", "another", "the", "a", "test", "emphasis", "his", "Apple", "their", "for", "one"};

        // Test the sample words randomly
        Random randomGenerator = new Random();
        int randomIndex = randomGenerator.nextInt(10) + 1;

        try {

            String serviceUrl = serviceURL + inputString[randomIndex];

            URL url = new URL(serviceUrl);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/json");

            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + httpConnection.getResponseCode());
            }

            BufferedReader serviceResponseReader = new BufferedReader(new InputStreamReader(
                    (httpConnection.getInputStream())));

            String response;

            while ((response = serviceResponseReader.readLine()) != null) {
                serviceResponse.append(response);
            }

            httpConnection.disconnect();

        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return serviceResponse.toString();
    }

}