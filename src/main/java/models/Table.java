package models;

import java.util.List;

public class Table {
    private String title;
    private List<String> columns;
    private String pattern;
    private String file;

    public Table(String title, List<String> columns, String pattern, String file) {
        this.title = title;
        this.columns = columns;
        this.pattern = pattern;
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Title:    ").append(getTitle()).append("\n");
        sb.append("Columns:  ").append(getColumns().toArray()[0]).append("\n");
        for (int i = 1; i < getColumns().size(); i++) {
            sb.append("          ").append(getColumns().toArray()[i]).append("\n");
        }
        sb.append("Pattern:  ").append(getPattern()).append("\n");
        sb.append("File:     ").append(getFile()).append("\n");
        return sb.toString();
    }
}
