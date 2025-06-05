package ui;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Resources {

    /**
     * Checks if a resource exists in the classpath.
     *
     * @param resourceName the name of the resource to check
     * @return true if the resource exists, false otherwise
     */
    public static boolean exists(String resourceName) {
        ClassLoader classLoader = Resources.class.getClassLoader();
        URL url = classLoader.getResource(resourceName);
        return url != null;
    }

    /**
     * Gets a file from the resources folder as an InputStream.
     * This method works consistently across different environments:
     * IDE, unit tests, and JAR files.
     *
     * @param fileName the name of the resource file to retrieve
     * @return InputStream containing the file content
     * @throws IllegalArgumentException if the file is not found
     */
    public static InputStream getFileFromResourceAsStream(String fileName) {
        // The class loader that loaded the class
        ClassLoader classLoader = Resources.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }

    /**
     * Gets a file from the resources folder as a String.
     * Reads the entire file content and returns it as a single String with
     * newline characters preserved.
     *
     * @param fileName the name of the resource file to retrieve
     * @return String containing the file content
     * @throws IllegalArgumentException if the file is not found (propagated from getFileFromResourceAsStream)
     */
    public static String getFileFromResouceAsString(String fileName) {
        InputStream inputStream = getFileFromResourceAsStream(fileName);

        String output = "";

        try (InputStreamReader streamReader =
                     new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                output += line + "\n";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}