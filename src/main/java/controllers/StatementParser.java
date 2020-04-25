package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatementParser {
    private static final Pattern CREATE_PATTERN = Pattern.compile("\\s*?CREATE\\s+?TABLE\\s+?'([A-Za-z]\\w*)'\\s+?\\(\\s*?((?:[\\w_]+,\\s*?)*[\\w_]+)\\s*?\\)\\s*?:\\s*?line\\s+?format\\s+?\\/(.*?)\\/\\s+?file\\s+?'(.*?\\.txt)\\s*?';\\s*");
    private static final Pattern SELECT_PATTERN = Pattern.compile("\\s*?SELECT\\s+?((?:(?:[\\w_]+,\\s*?)*[\\w_]+)|\\*)\\s+?FROM\\s+?([A-Za-z][\\w]*)\\s*?(?:\\s+?WHERE\\s+?([\\w_]+)\\s*?((?:=|>=|<=|>|<))\\s*'(.+?)'\\s*?)?;\\s*");

    public static String flattenInputStatement(List<String> multiLineInput) {
        // Flattens multiLineInput into flattenedInput
        StringBuilder inputBuilder = new StringBuilder();
        String flattenedInput = null;
        for (String line : multiLineInput) {
            inputBuilder.append(line).append(" ");
        }
        flattenedInput = inputBuilder.toString();

        // Replaces all extraneous spaces and returns with a single whitespace character
//        flattenedInput = flattenedInput.replaceAll("[\\s]+", " "); REMOVED - can interfere with regular expression

        return flattenedInput;
    }

    public static boolean isCreateStatement(String input) {
        Matcher m = CREATE_PATTERN.matcher(input);
        return m.find();
    }

    public static boolean isSelectStatement(String input) {
        Matcher m = SELECT_PATTERN.matcher(input);
        return m.find();
    }

    public static String[] matchCreatePattern(String input) {
        Matcher m = CREATE_PATTERN.matcher(input);
        String[] parameters = new String[4];

        if (m.find()) {
            parameters[0] = m.group(1);
            parameters[1] = m.group(2);
            parameters[2] = m.group(3);
            parameters[3] = m.group(4);
        } else {
            System.out.println("Your input was not recognized. Please, try again.");
        }

        return parameters;
    }

    public static String[] matchSelectPattern(String input) {
        Matcher m = SELECT_PATTERN.matcher(input);
        String[] parameters = new String[5];

        if (m.find()) {
            parameters[0] = m.group(1);
            parameters[1] = m.group(2);
            parameters[2] = m.group(3);
            parameters[3] = m.group(4);
            parameters[4] = m.group(5);
        } else {
            System.out.println("Your input was not recognized. Please, try again.");
        }

        return parameters;
    }

    public static List<String> getColumns(String csv) {
        List<String> columns = new ArrayList<>();
        for (String col : csv.split(",")) {
            columns.add(col.trim());
        }
        return columns;
    }
}
