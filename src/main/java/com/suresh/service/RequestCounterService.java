package com.suresh.service;

import com.suresh.indexer.Indexer;
import com.suresh.model.RequestCount;
import com.suresh.util.CommonUtil;
import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Service to get the number of requests and occurrences in files for the given word
 */
@Path("/counter")
public class RequestCounterService {

    // fields to track the request counts
    private static Map<String, AtomicInteger> mapRequestCount = new ConcurrentHashMap<String, AtomicInteger>();
    private String queryParamName = "track";

    private final static Logger logger = Logger.getLogger(RequestCounterService.class);

    /**
     * Service GET method to get the number of requests and occurrences in files for the given word
     * @param info
     * @return
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getCount(@Context UriInfo info) {
        String queryParamValue = info.getQueryParameters().getFirst(queryParamName);

        int requestCount = 0;
        int occurrenceCount = 0;
        if(CommonUtil.isNotEmpty(queryParamValue)) {
            requestCount = RequestCounterService.incrementRequestCount(queryParamValue.toLowerCase());
            occurrenceCount = RequestCounterService.getNumberOfWordOccurencesCount(queryParamValue.toLowerCase());
        }

        return CommonUtil.getServiceResponse(queryParamValue, requestCount, occurrenceCount);
    }

    @GET
    @Path("count")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllRequestCount() {
        String response = "";
        List<RequestCount> requestCountList = new ArrayList<RequestCount>();
        Iterator requestCountMapIterator = mapRequestCount.entrySet().iterator();
        while (requestCountMapIterator.hasNext()) {
            Map.Entry<String, AtomicInteger> requestCountPair = (Map.Entry<String, AtomicInteger>)requestCountMapIterator.next();

            RequestCount requestCount = new RequestCount();
            requestCount.setInputToken(requestCountPair.getKey());
            requestCount.setNumberOfRequests(requestCountPair.getValue().intValue());
            requestCount.setNumberOfOccurrencesInFiles(getNumberOfWordOccurencesCount(requestCountPair.getKey()));
            requestCountList.add(requestCount);
        }
        return CommonUtil.getServiceResponse(requestCountList);
    }

    /**
     * Get the number of request count
     * @param queryParam
     * @return
     */
    public static int incrementRequestCount(String queryParam) {

        AtomicInteger currentRequestCount = mapRequestCount.get(queryParam);

        if (null == currentRequestCount) {
            currentRequestCount = new AtomicInteger(1);
            mapRequestCount.put(queryParam, currentRequestCount);
        } else {
            currentRequestCount.incrementAndGet();
            mapRequestCount.put(queryParam, currentRequestCount);
        }

        // custome logging to show the count
        if(currentRequestCount.intValue() == 1000) {
            logger.info("request count for - " + queryParam + " : " + currentRequestCount.intValue());
        }

        return currentRequestCount.intValue();
    }

    /**
     * Get the number occurrences of the word from files
     * @param queryParam
     * @return
     */
    public static int getNumberOfWordOccurencesCount(String queryParam) {
        return Indexer.getNumberOfWordOccurencesCount(queryParam);
    }

}
