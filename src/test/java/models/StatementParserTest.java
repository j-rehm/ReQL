package models;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatementParserTest {

    List<String> validCreateStatement = Arrays.asList(
            "CREATE TABLE 'appointments' (",
            "    patient_name,",
            "    doctor_name,",
            "    apt_date,",
            "    apt_time,",
            "    topic",
            "): line format /(\\w*);(\\w*);([^ ]*) ([^ ]*);(.*$) /",
            "file 'C:/appts.txt';"
    );
    String validFlatCreateStatement = "CREATE TABLE 'appointments' (patient_name, doctor_name, apt_date, apt_time, topic): line format /(\\w*);(\\w*);([^ ]*) ([^ ]*);(.*$) / file 'C:/appts.txt';";

    List<String> validSelectStatement = Arrays.asList(
            "SELECT patient_name, topic, doctor_name",
            "FROM appointments",
            "WHERE apt_date = '3/1/2020'"
    );
    String validFlatSelectStatement = "SELECT patient_name, topic, doctor_name FROM appointments WHERE apt_date = '3/1/2020' ";

    @Test
    void flattenInputStatement() {
        // arrange
        String expected = validFlatCreateStatement;

        // act
        String actual = StatementParser.flattenInputStatement(validCreateStatement);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    void isCreateStatement() {
        // arrange
        String statement = StatementParser.flattenInputStatement(validCreateStatement);
        System.out.println(statement);
        boolean expected = true;

        // act
        boolean actual = StatementParser.isCreateStatement(statement);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    void isSelectStatement() {
        // arrange
        String statement = StatementParser.flattenInputStatement(validSelectStatement);
        System.out.println(statement);
        boolean expected = true;

        // act
        boolean actual = StatementParser.isSelectStatement(statement);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    void matchCreatePattern() {
        // arrange
        String expected = "appointments";

        // act
        String actual = StatementParser.matchCreatePattern(validFlatCreateStatement)[0];

        // assert
        assertEquals(expected, actual);
    }

    @Test
    void matchSelectPattern() {
        // arrange
        String expected = "appointments";

        // act
        String actual = StatementParser.matchSelectPattern(validFlatSelectStatement)[1];

        // assert
        assertEquals(expected, actual);
    }
}