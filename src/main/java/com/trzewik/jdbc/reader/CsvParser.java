package com.trzewik.jdbc.reader;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class CsvParser {
    private final char separator;
    private final char quote;

    CsvParser() {
        this.separator = ',';
        this.quote = '"';
    }

    public List<List<String>> parseFile(String pathToFile) throws FileNotFoundException {
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

    private List<String> parseLine(String line) {
        List<String> result = new ArrayList<>();
        if (StringUtils.isEmpty(line)) {
            return result;
        }

        StringBuffer current = new StringBuffer();
        boolean inQuotes = false;
        char[] chars = line.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == quote) {
                int quotesNumber = countQuotes(chars, i);
                i = appendQuotesAndReturnIndex(current, i, quotesNumber);
                if (isStartOrEndQuote(quotesNumber)) {
                    if (afterEndingQuoteMissingSeparator(inQuotes, chars, i)) {
                        throw new IllegalArgumentException("After ending quote must be separator!");
                    }
                    inQuotes = !inQuotes;
                }
            } else if (!inQuotes && chars[i] == separator) {
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

    private boolean afterEndingQuoteMissingSeparator(boolean inQuotes, char[] chars, int index) {
        return inQuotes && isNotLastIndex(chars, index) && nextCharIsNotSeparator(chars, index);
    }

    private boolean isNotLastIndex(char[] array, int currentIndex) {
        return currentIndex != array.length - 1;
    }

    private boolean nextCharIsNotSeparator(char[] chars, int index) {
        return chars[index + 1] != separator;
    }

    private int appendQuotesAndReturnIndex(StringBuffer currentWord, int currentIndex, int numberOfNextQuotes) {
        for (int j = 0; j < numberOfQuotesToAdd(numberOfNextQuotes); j++) {
            currentWord.append(quote);
        }
        currentIndex += numberOfNextQuotes - 1;
        return currentIndex;
    }

    private int numberOfQuotesToAdd(int numberOfNextQuotes) {
        return (numberOfNextQuotes - (numberOfNextQuotes % 2)) / 2;
    }

    private boolean isLastIndex(char[] array, int currentIndex) {
        return currentIndex == array.length - 1;
    }

    private boolean isStartOrEndQuote(int quotesNumber) {
        return quotesNumber % 2 == 1;
    }

    private int countQuotes(char[] chars, int startIndex) {
        int counter = 0;
        while (startIndex < chars.length && chars[startIndex] == quote) {
            counter++;
            startIndex++;
        }
        return counter;
    }

    private Scanner createScanner(String path) throws FileNotFoundException {
        return new Scanner(createFile(path));
    }

    private File createFile(String path) {
        return new File(path);
    }
}
