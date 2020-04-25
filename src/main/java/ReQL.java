import controllers.ReQL_IO;
import controllers.TableController;
import controllers.StatementParser;

public class ReQL {

    public static void main(String[] args) {
        String input;

        // TODO remove - creates default table
        TableController.create(StatementParser.matchCreatePattern("CREATE TABLE 'mylog' (date, time, log_level, src_ip, username, msg) : line format /(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:\\d{2}:\\d{2}) (\\([A-Z]+\\) \\[[a-z]+-\\d+-[a-z]+-\\d+\\]) ((?:\\d{1,3}.){3}\\d{1,3}) ([a-z]+) : (.*$)/ file 'src/main/resources/mylog.txt';"));

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
