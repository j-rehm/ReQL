package controllers;

import models.Select;
import models.Table;
import models.WhereOperand;

import java.util.ArrayList;
import java.util.List;

public class TableController {

    private static List<Table> tables = new ArrayList<>();

    public static void create(String[] parameters) {
        parameters = cleanParameters(parameters);
        String title = parameters[0];
        List<String> columns = StatementParser.getColumns(parameters[1]);
        Table table = new Table(title, columns, parameters[2], parameters[3]);
        if (getTable(title) != null) {
            System.out.println("A table by that name already exists!");
        } else {
            tables.add(table);
            System.out.println("Table Created!");
            System.out.println(table.toString());
        }
    }

    public static void select(String[] parameters) {
        parameters = cleanParameters(parameters);

        Table table = getTable(parameters[1]);
        List<String> columns = (parameters[0].length() == 1 && parameters[0].equals("*"))? table.getColumns() : StatementParser.getColumns(parameters[0]);

        if (table == null) {
            System.out.println("Table '" + parameters[1] + "' does not exist.");
        } else {
            Select select;
            if (parameters[2] == null) {
                select = new Select(columns, table);
            } else {
                select = new Select(columns, table, parameters[2], WhereOperand.parseFromString(parameters[3]), parameters[4]);
            }
            System.out.println(select.toString());
            select.execute();
        }
    }

    public static Table getTable(String title) {
        for (Table t : tables) {
            if (t.getTitle().equals(title)) {
                return t;
            }
        }
        return null;
    }

    private static String[] cleanParameters(String[] parameters) {
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = parameters[i] == null ? parameters[i] : parameters[i].trim();
        }
        return parameters;
    }

}
