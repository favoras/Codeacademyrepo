import java.sql.SQLException;

public class StartBankAPP {

    public static void main(String[] args) throws SQLException, InterruptedException {

        System.out.println("Bank application starting...");

        DatabaseConnection.connectToDatabase();
        MenusConfiguration.firstMenuConfiguration();

    }
}
