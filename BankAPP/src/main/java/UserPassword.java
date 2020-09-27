import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserPassword {


    public String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public void checkPass(String plainPassword, String hashedPassword) {
        if (BCrypt.checkpw(plainPassword, hashedPassword)) {
            System.out.println("The password matches.");
            System.out.println("Successfully logged in");
        }
        else
            System.out.println("The password does not match.");
    }

    public void takePasswordFromDatabase(String user_password, String user_login) throws SQLException {
        PreparedStatement preparedStatement = DatabaseConnection.connection.prepareStatement("SELECT * FROM USER WHERE USER_LOGIN = ?");
        preparedStatement.setString(1, user_login);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            System.out.println("Please register.");
        } else {
            do {
                String data = resultSet.getString("user_password");
                checkPass(user_password, data);
            } while (resultSet.next());
        }
    }
}
