package controllers;

import models.Table;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ReQL_IO {
    private static String schemaPath = "src/main/resources/schema/schemas.txt";

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

    public static List<String> readFile(String path) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader buffy = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = buffy.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("The file '" + path + "' cannot be found.");
        }
        return lines;
    }

    private static void writeFile(String path, String line) {
        StringBuilder sb = new StringBuilder();
        for (String schema : readFile(schemaPath)) {
            sb.append(schema).append("\n");
        }
        sb.append(line);
        try (BufferedWriter buffy = new BufferedWriter(new FileWriter(path))) {
            buffy.write(sb.toString());
        } catch (IOException e) {
            System.out.println("Could not save schema.");
        }
    }

    public static void printTabularData(List<String[]> tabluarData) {
        // Get the maximum width of each column
        int[] maxColWidth = new int[tabluarData.get(0).length]; // array length equal to length of arbitrary row (all rows should the same length)
        for (String[] row : tabluarData) { // iterate over each row
            for (int c = 0; c < row.length; c++) { // iterate over each column
                maxColWidth[c] = Math.max(maxColWidth[c], row[c].length());
            }
        }

        // Print each column with proper spacing
        for (String[] row : tabluarData) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < row.length; c++) {
                sb.append(String.format("%-" + (maxColWidth[c] + 3) + "s", row[c]));
            }
            System.out.println(sb.toString());
        }
    }

    public static void unrecognizedStatement() {
        System.out.println("Sorry, the statement you provided was not recognized. Please, try again.\n");
    }

    public static void saveSchema(String[] newSchema) {
        List<String> schemas = readFile(schemaPath);
        boolean duplicate = false;
        for (String rawSchema : schemas) {
            String[] schema = StatementParser.matchCreatePattern(rawSchema);
            if (newSchema[0].equals(schema[0])) {
                duplicate = true;
                break;
            }
//            System.out.println(newSchema[0] + " =? " + schema[0] + " : " + duplicate);
        }
        if(!duplicate) {
            writeFile(schemaPath, flattenCreateParameters(newSchema));
            System.out.println("Table '" + newSchema[0] + "' Schema Saved!");
        }
    }

    private static String flattenCreateParameters(String[] parameters) {
        StringBuilder sb = new StringBuilder();
        List<String> cols = StatementParser.getColumns(parameters[1]);

        sb.append("CREATE TABLE '").append(parameters[0]).append("' (");
        for (int c = 0; c < cols.size(); c++) {
            sb.append(cols.get(c)).append((c < cols.size() - 1)? ", " : "");
        }
        sb.append(") : line format /").append(parameters[2]).append("/ file '").append(parameters[3]).append("';");

        return sb.toString();
    }

    public static void loadSchemas() {
        for (String schema : readFile(schemaPath)) {
            TableController.create(StatementParser.matchCreatePattern(schema));
        }
    }
}
