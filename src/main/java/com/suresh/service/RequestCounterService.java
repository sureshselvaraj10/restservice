package com.suresh.service;

import com.suresh.indexer.Indexer;
import com.suresh.util.CommonUtil;
import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service to get the number of requests and occurrences in files for the given word
 */
@Path("/counter")
public class RequestCounterService {

    // fields to track the request counts
    private static Map<String, Integer> mapRequestCount = new ConcurrentHashMap<String, Integer>();
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

        logger.info("count for " + queryParamValue + " - requestCount:" + requestCount + ", occurrenceCount:" + occurrenceCount);

        return CommonUtil.getServiceResponse(requestCount, occurrenceCount);
    }

    /**
     * Get the number of request count
     * @param queryParam
     * @return
     */
    public static synchronized int incrementRequestCount(String queryParam) {

        Integer requestCount = mapRequestCount.get(queryParam);

        if (null == requestCount) {
            requestCount = 1;
            mapRequestCount.put(queryParam, requestCount);
        } else {
            requestCount = requestCount + 1;
            mapRequestCount.put(queryParam, requestCount);
        }

        logger.info("request count for - " + queryParam + " : " + requestCount);

        return requestCount;
    }

    /**
     * Get the number occurrences of the word from files
     * @param queryParam
     * @return
     */
    public static synchronized int getNumberOfWordOccurencesCount(String queryParam) {
        return Indexer.getNumberOfWordOccurencesCount(queryParam);
    }

}
