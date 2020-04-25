import controllers.ReQL_IO;
import controllers.TableController;
import controllers.StatementParser;

public class ReQL {

    public static void main(String[] args) {
        init();
        run();
    }

    private static void run() {
        boolean exit = false;
        String exitString = "exit;".trim();
        System.out.println("Type '" + exitString + "' to exit.");
        do {
            String input = StatementParser.flattenInputStatement(ReQL_IO.getStatement());
            exit = input.trim().equals(exitString);

            if (StatementParser.isCreateStatement(input)) {
                TableController.create(StatementParser.matchCreatePattern(input));
            } else if (StatementParser.isSelectStatement(input)) {
                TableController.select(StatementParser.matchSelectPattern(input));
            } else if (!exit) {
                ReQL_IO.unrecognizedStatement();
            }
        } while (!exit);
    }

    private static void init() {
        ReQL_IO.loadSchemas();
    }
}
