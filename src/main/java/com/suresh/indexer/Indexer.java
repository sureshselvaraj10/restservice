package com.suresh.indexer;

import org.apache.log4j.Logger;
import org.glassfish.jersey.internal.util.ExceptionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * This class is used to scan all the files from a folder and index it
 */
public class Indexer implements ServletContextListener {

    private final static Logger logger = Logger.getLogger(Indexer.class);
    private static Map<String, Integer> mapWordOccurrences = new HashMap<String, Integer>();
    private final String folderName = "txtfiles";

    /**
     * Overrides contextInitialized method to load and index all the file during startup
     * @param contextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        long startTime = System.currentTimeMillis();

        // indexing all the files
        indexAllFiles();

        long endTime = System.currentTimeMillis();

        logger.info("Total Time taken to index files :: " + (endTime - startTime));
    }


    /**
     * Index all the files from the given folder
     */
    public void indexAllFiles() {

        // Get all the file resources from the folder
        logger.info("start - indexing the files");
        InputStream folderInputStream = null;
        try {
            List<String> fileNameList = new ArrayList<String>();
            folderInputStream = getClass().getClassLoader()
                    .getResourceAsStream(folderName);
            String fileResource = null;

            if (folderInputStream != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        folderInputStream));
                try {
                    while ((fileResource = br.readLine()) != null) {
                        fileNameList.add(fileResource);
                    }
                } catch (IOException exception) {
                    logger.error("Exception while getting indexing the files: " +
                            ExceptionUtils.exceptionStackTraceAsString(exception));
                }
            }

            // index the files
            Iterator<String> fileNameIterator = fileNameList.listIterator();
            while (fileNameIterator.hasNext()) {
                String fileName = fileNameIterator.next();
                indexFile(fileName);
            }
        } catch(Exception exception) {
            logger.error("Exception while getting indexing the files: " +
                    ExceptionUtils.exceptionStackTraceAsString(exception));
        }

        logger.info("end - indexing the files");
    }


    /**
     * Index the given file
     * @param fileName
     */
    public void indexFile(String fileName) {
        logger.info("start - indexing the file: " + fileName);
        Reader fileReader = null;
        Scanner fileScanner = null;
        try {

            fileReader = new BufferedReader(new InputStreamReader(getClass()
                    .getClassLoader().getResourceAsStream(
                            folderName + "/" + fileName)));

            fileScanner = new Scanner(fileReader);

            while (fileScanner.hasNextLine()) {
                String[] words = fileScanner.nextLine().split("\\W+");
                for (String word : words) {
                    word = word.toLowerCase();
                    Integer noOfWordOccurrences = mapWordOccurrences.get(word);

                    if (null == noOfWordOccurrences) {
                        noOfWordOccurrences = 1;
                        mapWordOccurrences.put(word, noOfWordOccurrences);
                    } else {
                        noOfWordOccurrences = noOfWordOccurrences + 1;
                        mapWordOccurrences.put(word, noOfWordOccurrences);
                    }
                }

            }

        } catch (Exception exception) {
            logger.error("Exception while getting indexing the file: " + fileName + " :: " +
                    ExceptionUtils.exceptionStackTraceAsString(exception));
        } finally {
            if(fileScanner != null) {
                fileScanner.close();
            }
        }

        logger.info("end - indexing the file: " + fileName);
    }


    /**
     * Get the count of given string from the index
     * @param word
     * @return
     */
    public static int getNumberOfWordOccurencesCount(String word) {
        logger.info("getting number of occurrences for: " + word);
        Integer noOfWordOccurences = mapWordOccurrences.get(word);

        if (null == noOfWordOccurences) {
            noOfWordOccurences = 0;
        }
        logger.info("number of occurrences for- " + word + " : " + noOfWordOccurences);
        return noOfWordOccurences;
    }


    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {

    }
}