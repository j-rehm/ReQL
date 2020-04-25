package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReQLConsoleIO {
    public static List<String> getStatement() {
        BufferedReader buffy = new BufferedReader(new InputStreamReader(System.in));

        List<String> multiLineInput = new ArrayList<>();

        // Continually gets user input, adding each line to multiLineInput, until user enters command terminator (;)
        String lastLine = "";
        do {
            System.out.print("> ");
            try {
                String input = buffy.readLine();
                multiLineInput.add(input);
                lastLine = input;
            } catch (IOException ex) {
                System.out.println("An unknown error has occurred. Please, enter the sequence again.");
                multiLineInput.clear();
            }
        } while (!lastLine.trim().endsWith(";"));

        return multiLineInput;
    }

    public static void unrecognizedStatement() {
        System.out.println("Sorry, the statement you provided was not recognized. Please, try again.\n");
    }
}