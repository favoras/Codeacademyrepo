
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BankAccountMenu {

    static Long user_id;
    static String creditCardNum;
    static String debitCardNum;


    public static void printBankAccountInfo() throws SQLException {
        PreparedStatement preparedStatement = DatabaseConnection.connection.prepareStatement("SELECT * FROM BANK_ACCOUNT WHERE USER_ID = ?");
        preparedStatement.setLong(1, user_id);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            String creditCardNum = resultSet.getString("credit_card_num");
            double creditCardBalance = resultSet.getDouble("ccn_balance");
            String debitCardNum = resultSet.getString("debit_card_num");
            double debitCardBalance = resultSet.getDouble("dcn_balance");

            System.out.println("Credit Card Number: " + creditCardNum + " BALANCE : " + creditCardBalance);
            System.out.println("Debit Card Number: " + debitCardNum + " BALANCE : " + debitCardBalance);
        }

    }

    public static void getLoggedInUserID(String user_login) throws SQLException {
        PreparedStatement preparedStatement = DatabaseConnection.connection.prepareStatement("SELECT PERSONAL_CODE FROM USER WHERE USER_LOGIN = ?");
        preparedStatement.setString(1, user_login);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            System.out.println("");
        } else {
            user_id = resultSet.getLong("personal_code");
        }
        getLoggedInUserCreditCardNum();
        getLoggedInUserDebitCardNum();
    }

    public static String getLoggedInUserCreditCardNum() throws SQLException {
        PreparedStatement preparedStatement = DatabaseConnection.connection.prepareStatement("SELECT CREDIT_CARD_NUM FROM BANK_ACCOUNT WHERE USER_ID = ?");
        preparedStatement.setLong(1, user_id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            System.out.println("");
        } else {
            creditCardNum = resultSet.getString("credit_card_num");
        }
        return creditCardNum;
    }

    public static String getLoggedInUserDebitCardNum() throws SQLException {
        PreparedStatement preparedStatement = DatabaseConnection.connection.prepareStatement("SELECT DEBIT_CARD_NUM FROM BANK_ACCOUNT WHERE USER_ID = ?");
        preparedStatement.setLong(1, user_id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            System.out.println("");
        } else {
            debitCardNum = resultSet.getString("debit_card_num");
        }
        return debitCardNum;
    }

}
