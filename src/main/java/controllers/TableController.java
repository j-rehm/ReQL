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

        boolean duplicateColumns = false;
        for (String col1 : columns) {
            List<String> others = new ArrayList<String>(columns);
            others.remove(col1);

            for (String col2 : others) {
                if (col1.equals(col2)) {
                    duplicateColumns = true;
                    break;
                }
            }
        }

        if (getTable(title) == null) {
            if (!duplicateColumns) {
                Table table = new Table(title, columns, parameters[2], parameters[3]);
                tables.add(table);

                System.out.println("Table Created!");
                ReQL_IO.saveSchema(parameters);
                System.out.println(table.toString());
            } else {
                System.out.println("Column names must be unique. Please, try again.\n");
            }
        } else {
            System.out.println("A table by that name already exists!\n");
        }
    }

    public static void select(String[] parameters) {
        parameters = cleanParameters(parameters);

        Table table = getTable(parameters[1]);
        if (table == null) {
            System.out.println("Table '" + parameters[1] + "' does not exist.\n");
        } else {
            List<String> columns = (parameters[0].length() == 1 && parameters[0].equals("*"))? table.getColumns() : StatementParser.getColumns(parameters[0]);

            Select select;
            if (parameters[2] == null) {
                select = new Select(columns, table);
            } else {
                select = new Select(columns, table, parameters[2], WhereOperand.parseFromString(parameters[3]), parameters[4]);
            }
            System.out.println(select.toString());

            List<String[]> result = select.find();
            ReQL_IO.printTabularData(result);
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
