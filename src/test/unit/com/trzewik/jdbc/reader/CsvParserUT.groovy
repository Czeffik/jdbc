package com.trzewik.jdbc.reader

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class CsvParserUT extends Specification implements FileReading {

    @Subject
    CsvParser parser = new CsvParser()

    def 'should parse csv file ignoring empty lines'() {
        when:
        def strings = parser.parseFile(getAbsolutePath('parser/correct.csv'))

        then:
        strings.size() == 5

        and:
        with(strings.first()) {
            get(0) == '"antoni"'
            get(1) == 'anto,ni@dwa.pl'
        }

        and:
        with(strings.get(1)) {
            get(0) == '"ta"de,k'
            get(1) == 'tadek@dwa.pl'
        }

        and:
        with(strings.get(2)) {
            get(0) == '"qweaadasdas'
            get(1) == 'asdasda@.dsds.sd"'
        }

        and:
        with(strings.get(3)) {
            get(0) == 'ale'
            get(1) == 'fajnie'
            get(2) == ''
        }

        and:
        with(strings.get(4)) {
            get(0) == ''
            get(1) == 'nie'
        }
    }

    def 'should parse empty file'() {
        when:
        def strings = parser.parseFile(getAbsolutePath('parser/empty.csv'))

        then:
        strings.isEmpty()
    }

    def 'should parse file with only empty lines'() {
        when:
        def strings = parser.parseFile(getAbsolutePath('parser/onlyLines.csv'))

        then:
        strings.isEmpty()
    }

    @Unroll
    def 'should throw exception because: #REASON'() {
        when:
        parser.parseFile(getAbsolutePath(FILE_NAME))

        then:
        thrown(IllegalArgumentException)

        where:
        FILE_NAME                                || REASON
        'parser/noEndingQuote.csv'               || 'missing ending quote'
        'parser/noSeparatorAfterEndingQuote.csv' || 'missing separator after ending quote'
    }

    def 'should throw exception when file not found'() {
        when:
        parser.parseFile('really/bad/path')

        then:
        thrown(FileNotFoundException)
    }
}
