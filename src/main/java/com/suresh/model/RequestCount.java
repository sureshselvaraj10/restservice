package com.suresh.model;

/**
 * pojo to store the number of requests and occurrences
 */
public class RequestCount {

	private String inputToken;
	private int numberOfRequests;
	private int numberOfOccurrencesInFiles;

	public String getInputToken() {
		return inputToken;
	}

	public void setInputToken(String inputToken) {
		this.inputToken = inputToken;
	}

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
