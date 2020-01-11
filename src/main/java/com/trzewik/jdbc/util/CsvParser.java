package com.trzewik.jdbc.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CsvParser {
    private static final char SEPARATOR = ',';
    private static final char QUOTE = '"';

    public static List<List<String>> parseFile(String pathToFile) throws FileNotFoundException {
        Scanner scanner = createScanner(pathToFile);
        List<List<String>> lines = new ArrayList<>();
        while (scanner.hasNext()) {
            List<String> line = parseLine(scanner.nextLine());
            if (!line.isEmpty()) {
                lines.add(line);
            }
        }
        return lines;
    }

    private static List<String> parseLine(String line) {
        List<String> result = new ArrayList<>();
        if (StringUtils.isEmpty(line)) {
            return result;
        }

        StringBuffer current = new StringBuffer();
        boolean inQuotes = false;
        char[] chars = line.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (inQuotes) {
                if (chars[i] == QUOTE) {
                    int numberOfNextQuotes = countQuotes(chars, i);
                    boolean endQuote = startOrEndQuote(numberOfNextQuotes);
                    if (endQuote) {
                        inQuotes = false;
                        if (numberOfNextQuotes > 1) {
                            for (int j = 0; j < (numberOfNextQuotes - 1) / 2; j++) {
                                current.append(QUOTE);
                            }
                            i += numberOfNextQuotes - 1;
                        }
                        if (i < chars.length - 1 && chars[i + 1] != SEPARATOR) {
                            throw new IllegalArgumentException("After ending quote must be separator!");
                        }
                    } else {
                        for (int j = 0; j < numberOfNextQuotes / 2; j++) {
                            current.append(QUOTE);
                        }
                        i += numberOfNextQuotes - 1;
                    }
                } else {
                    if (i == chars.length - 1) {
                        throw new IllegalArgumentException("Missing ending quote!");
                    }
                    current.append(chars[i]);
                }
            } else {
                if (chars[i] == QUOTE) {
                    int numberOfNextQuotes = countQuotes(chars, i);
                    boolean startQuote = startOrEndQuote(numberOfNextQuotes);
                    if (startQuote) {
                        inQuotes = true;
                        if (numberOfNextQuotes > 1) {
                            for (int j = 0; j < (numberOfNextQuotes - 1) / 2; j++) {
                                current.append(QUOTE);
                            }
                            i += numberOfNextQuotes - 1;
                        }
                    } else {
                        for (int j = 0; j < numberOfNextQuotes / 2; j++) {
                            current.append(QUOTE);
                        }
                        i += numberOfNextQuotes - 1;
                    }
                } else if (chars[i] == SEPARATOR) {
                    result.add(current.toString());
                    current = new StringBuffer();
                } else {
                    current.append(chars[i]);
                }
            }
        }
        result.add(current.toString());

        return result;
    }

    private static boolean startOrEndQuote(int quotesNumber) {
        return quotesNumber % 2 == 1;
    }

    private static int countQuotes(char[] chars, int startIndex) {
        int counter = 0;
        while (startIndex < chars.length && chars[startIndex] == QUOTE) {
            counter++;
            startIndex++;
        }
        return counter;
    }

    private static Scanner createScanner(String path) throws FileNotFoundException {
        return new Scanner(createFile(path));
    }

    private static File createFile(String path) {
        return new File(path);
    }
}
