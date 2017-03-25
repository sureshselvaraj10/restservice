package com.suresh.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suresh.model.RequestCount;
import com.suresh.wrapper.ServiceResponse;

/**
 * Utility class for the service
 */
public class CommonUtil {

    /**
     * returns true if the given string is null or empty
     * @param input
     * @return
     */
    public static boolean isEmpty(String input) {
        if(input != null && !input.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * returns true if the given string is NOT null or NOT empty
     * @param input
     * @return
     */
    public static boolean isNotEmpty(String input) {

        return !isEmpty(input);
    }

    /**
     * Forming service response for request count service
     * @param requestCount
     * @param occurrenceCount
     * @return
     */
    public static String getServiceResponse(int requestCount, int occurrenceCount) {

        // Forming requestcount response
        RequestCount requestCountObj = new RequestCount();
        requestCountObj.setNumberOfRequests(requestCount);
        requestCountObj.setNumberOfOccurrencesInFiles(occurrenceCount);

        // Forming service response to return
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setPayload(requestCountObj);

        Gson jsonFormer = new GsonBuilder().setPrettyPrinting().create();
        String jsonResponse = jsonFormer.toJson(serviceResponse);

        return jsonResponse;

    }

}
