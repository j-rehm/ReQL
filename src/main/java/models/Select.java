package models;

import controllers.ReQL_IO;

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

    public void execute() {
        List<String> rows = ReQL_IO.readFile(getTable().getFile());
        Pattern pattern = Pattern.compile(getTable().getPattern());

        for (String row : rows) {
            Matcher matcher = pattern.matcher(row);
            if (matcher.find()) {

                for (String col : getSelectColumns()) {
//                    System.out.println(getTable().getColumns().indexOf(col));
                    System.out.println(matcher.group(getTable().getColumns().indexOf(col) + 1));
                }
            } else {
                System.out.println("The pattern /" + getTable().getPattern() + "/ could not match the row\n" + row);
            }
        }

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
        sb.append("Select:     ").append(getSelectColumns().toArray()[0]).append("\n");
        for (int i = 1; i < getSelectColumns().size(); i++) {
            sb.append("            ").append(getSelectColumns().toArray()[i]).append("\n");
        }
        sb.append("From Table: ").append(getTable().getTitle()).append("\n");
        if (getWhereCol() != null) {
            sb.append("Condition:  ").append(getWhereCol()).append(getOperand().toString()).append(getWhereValue()).append("\n");
        }
        return sb.toString();
    }
}
