import models.ReQLConsoleIO;
import controllers.TableController;
import models.StatementParser;

public class ReQL {

    public static void main(String[] args) {
        String input = StatementParser.flattenInputStatement(ReQLConsoleIO.getStatement());
        if (StatementParser.isCreateStatement(input)) {
            TableController.create(StatementParser.matchCreatePattern(input));
        } else if (StatementParser.isSelectStatement(input)) {
            TableController.select(StatementParser.matchSelectPattern(input));
        } else {
            ReQLConsoleIO.unrecognizedStatement();
        }
    }
}
