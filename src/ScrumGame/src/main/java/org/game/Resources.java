package org.game;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class Resources {

    // get a file from the resources folder
    // works everywhere, IDEA, unit test and JAR file.
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

    // print input stream
//    private static void printInputStream(InputStream is) {
//
//        try (InputStreamReader streamReader =
//                     new InputStreamReader(is, StandardCharsets.UTF_8);
//             BufferedReader reader = new BufferedReader(streamReader)) {
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    // print a file
//    private static void printFile(File file) {
//
//        List<String> lines;
//        try {
//            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
//            lines.forEach(System.out::println);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

}
