package controllers;

import models.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TableController {

    private static List<Table> tables = new ArrayList<>();

    public static void create(String[] parameters) {
        List<String> columns = new ArrayList<>();
        for (String col : parameters[1].split(",")) {
            columns.add(col.trim());
        }
        Table t = new Table(parameters[0].trim(), columns, parameters[2], parameters[3].trim());
        tables.add(t);
        System.out.println("Table Created!");
        System.out.println(t.toString());
    }

    public static void select(String[] parameters) {
        for (String param : parameters) {
            System.out.println(param);
        }
    }

}
