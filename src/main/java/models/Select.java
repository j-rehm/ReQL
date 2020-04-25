package models;

import controllers.ReQL_IO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Select {
    private Table table;
    private List<String> selectColumns;
    private String whereCol;
    private WhereOperand operand;
    private String whereValue;

    public Select(List<String> columns, Table table) {
        this.selectColumns = columns;
        this.table = table;
    }

    public Select(List<String> columns, Table table, String whereCol, WhereOperand operand, String whereVal) {
        this.selectColumns = columns;
        this.table = table;
        this.whereCol = whereCol;
        this.operand = operand;
        this.whereValue = whereVal;
    }

    public void find() {
        List<String> rows = ReQL_IO.readFile(getTable().getFile());
        Pattern pattern = Pattern.compile(getTable().getPattern());

        List<String[]> tabluarData = new ArrayList<>();
//        tabluarData.add(getSelectColumns().toArray(new String[0])); // Column Titles

        for (String row : rows) {
            Matcher matcher = pattern.matcher(row);
            if (matcher.find()) {
                List<String> columns = new ArrayList<>();
                for (String col : getSelectColumns()) {
                    columns.add(matcher.group(getTable().getColumns().indexOf(col) + 1));
                }
                tabluarData.add(columns.toArray(new String[0]));
            } else {
                System.out.println("The pattern /" + getTable().getPattern() + "/ could not match the row\n" + row);
                break;
            }
        }

        filter(tabluarData);

        tabluarData.add(0, getSelectColumns().toArray(new String[0]));
        ReQL_IO.printTabularData(tabluarData);
    }

    private void filter(List<String[]> rows) {
        if (whereCol != null) {
            int colIndex = getSelectColumns().indexOf(whereCol);

            System.out.println("Equality debug:");
            for (int r = 0; r < rows.size(); r++) {
                String col = rows.get(r)[colIndex];
                System.out.println(r + ": " + col + " " + col.compareTo(whereValue));
            }
            System.out.println();

            List<String> filter = new ArrayList<>();
            for (int r = 0; r < rows.size(); r++) {
                String col = rows.get(r)[colIndex];
                if (!compare(col)) {
                    filter.add(col);
                }
            }

            for (String badCol : filter) {
                rows.removeIf(row -> row[colIndex].equals(badCol));
            }
        }
    }

    private boolean compare(String value) {
        boolean result = true;
        switch (operand) {
            case EQUALS:
                result = value.compareTo(whereValue) == 0;
                break;
            case GREATER_THAN:
                result = value.compareTo(whereValue) > 0;
                break;
            case LESS_THAN:
                result = value.compareTo(whereValue) < 0;
                break;
            case GREATER_THAN_EQUAL:
                result = value.compareTo(whereValue) >= 0;
                break;
            case LESS_THAN_EQUAL:
                result = value.compareTo(whereValue) <= 0;
                break;
        }
        return result;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public List<String> getSelectColumns() {
        return selectColumns;
    }

    public void setSelectColumns(List<String> selectColumns) {
        this.selectColumns = selectColumns;
    }

    public String getWhereCol() {
        return whereCol;
    }

    public void setWhereCol(String whereCol) {
        this.whereCol = whereCol;
    }

    public WhereOperand getOperand() {
        return operand;
    }

    public void setOperand(WhereOperand operand) {
        this.operand = operand;
    }

    public String getWhereValue() {
        return whereValue;
    }

    public void setWhereValue(String whereValue) {
        this.whereValue = whereValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Select:      ").append(getSelectColumns().toArray()[0]).append("\n");
        for (int i = 1; i < getSelectColumns().size(); i++) {
            sb.append("             ").append(getSelectColumns().toArray()[i]).append("\n");
        }
        sb.append("From Table:  ").append(getTable().getTitle()).append("\n");
        if (getWhereCol() != null) {
            sb.append("Condition:   ").append(getWhereCol()).append(getOperand().toString()).append(getWhereValue()).append("\n");
        }
        return sb.toString();
    }
}
