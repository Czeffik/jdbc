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
            if (chars[i] == QUOTE) {
                int quotesNumber = countQuotes(chars, i);
                i = appendQuotesAndReturnIndex(current, i, quotesNumber);
                if (isStartOrEndQuote(quotesNumber)) {
                    if (afterEndingQuoteMissingSeparator(inQuotes, chars, i)) {
                        throw new IllegalArgumentException("After ending quote must be separator!");
                    }
                    inQuotes = !inQuotes;
                }
            } else if (!inQuotes && chars[i] == SEPARATOR) {
                result.add(current.toString());
                current = new StringBuffer();
            } else {
                if (inQuotes && isLastIndex(chars, i)) {
                    throw new IllegalArgumentException("Missing ending quote!");
                }
                current.append(chars[i]);
            }
        }
        result.add(current.toString());

        return result;
    }

    private static boolean afterEndingQuoteMissingSeparator(boolean inQuotes, char[] chars, int index) {
        return inQuotes && isNotLastIndex(chars, index) && nextCharIsNotSeparator(chars, index);
    }

    private static boolean isNotLastIndex(char[] array, int currentIndex) {
        return currentIndex != array.length - 1;
    }

    private static boolean nextCharIsNotSeparator(char[] chars, int index) {
        return chars[index + 1] != SEPARATOR;
    }

    private static int appendQuotesAndReturnIndex(StringBuffer currentWord, int currentIndex, int numberOfNextQuotes) {
        for (int j = 0; j < numberOfQuotesToAdd(numberOfNextQuotes); j++) {
            currentWord.append(QUOTE);
        }
        currentIndex += numberOfNextQuotes - 1;
        return currentIndex;
    }

    private static int numberOfQuotesToAdd(int numberOfNextQuotes) {
        return (numberOfNextQuotes - (numberOfNextQuotes % 2)) / 2;
    }

    private static boolean isLastIndex(char[] array, int currentIndex) {
        return currentIndex == array.length - 1;
    }

    private static boolean isStartOrEndQuote(int quotesNumber) {
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
