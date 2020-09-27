import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection connection;

    public static void connectToDatabase(){
        try {
            connection = DriverManager.getConnection(
                    "jdbc:h2:~/bank.db",
                    "sa",
                    ""
            );
            System.out.println("Successfully connected to database");
        } catch (Exception e){
            System.out.println("ERROR : Something went wrong with database");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void disconnectFromDatabase() throws SQLException {
        connection.close();
        System.out.println("Database disconnected.");
    }

}
