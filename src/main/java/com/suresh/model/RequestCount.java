package com.suresh.model;

/**
 * pojo to store the number of requests and occurrences
 */
public class RequestCount {

	private int numberOfRequests;
	private int numberOfOccurrencesInFiles;

	public int getNumberOfRequests() {
		return numberOfRequests;
	}

	public void setNumberOfRequests(int numberOfRequests) {
		this.numberOfRequests = numberOfRequests;
	}

	public int getNumberOfOccurrencesInFiles() {
		return numberOfOccurrencesInFiles;
	}

	public void setNumberOfOccurrencesInFiles(int numberOfOccurrencesInFiles) {
		this.numberOfOccurrencesInFiles = numberOfOccurrencesInFiles;
	}

}
