import controllers.ReQL_IO;
import controllers.TableController;
import controllers.StatementParser;

public class ReQL {

    public static void main(String[] args) {
        String input;

        String exitString = "exit".trim();
        System.out.println("Type '" + exitString + "' to exit.");
        do {
            input = StatementParser.flattenInputStatement(ReQL_IO.getStatement());
            if (StatementParser.isCreateStatement(input)) {
                TableController.create(StatementParser.matchCreatePattern(input));
            } else if (StatementParser.isSelectStatement(input)) {
                TableController.select(StatementParser.matchSelectPattern(input));
            } else {
                ReQL_IO.unrecognizedStatement();
            }
        } while (!input.trim().equals(exitString));
    }
}
