package models;

public enum WhereOperand {
    EQUALS(" is equal to "),
    GREATER_THAN(" is greater than "),
    LESS_THAN(" is less than "),
    GREATER_THAN_EQUAL(" is greater than or equal to "),
    LESS_THAN_EQUAL(" is less than or equal to ");

    private String value;
    private WhereOperand(String value) {
        this.value = value;
    }

    public static WhereOperand parseFromString(String input) {
        return switch (input) {
            default -> EQUALS;
            case "=" -> EQUALS;
            case ">" -> GREATER_THAN;
            case "<" -> LESS_THAN;
            case ">=" -> GREATER_THAN_EQUAL;
            case "<=" -> LESS_THAN_EQUAL;
        };
    }

    @Override
    public String toString() {
        return this.value;
    }
}
