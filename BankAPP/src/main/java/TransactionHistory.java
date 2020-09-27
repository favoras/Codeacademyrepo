import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class TransactionHistory {

    public static void printTransactionHistory() throws SQLException {
        PreparedStatement preparedStatement = DatabaseConnection.connection.prepareStatement("SELECT * FROM TRANSACTION WHERE RECEIVER_BANK_NUM = ? or SENDER_BANK_NUM = ? or RECEIVER_BANK_NUM = ? or SENDER_BANK_NUM = ?");
        preparedStatement.setString(1, BankAccountMenu.getLoggedInUserCreditCardNum());
        preparedStatement.setString(2, BankAccountMenu.getLoggedInUserCreditCardNum());
        preparedStatement.setString(3, BankAccountMenu.getLoggedInUserDebitCardNum());
        preparedStatement.setString(4, BankAccountMenu.getLoggedInUserDebitCardNum());
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            String receiverBankNum = resultSet.getString("receiver_bank_num");
            String senderBankNum = resultSet.getString("sender_bank_num");
            double amountSent = resultSet.getDouble("amount_sent");
            double amountReceived = resultSet.getDouble("amount_received");
            Date date = resultSet.getDate("date");
            System.out.println("Bank Account Number: " + senderBankNum + " sent: " + amountSent + " Date : " + date);
            System.out.println("Bank Account Number: " + receiverBankNum + " received: " + amountReceived + " Date : " + date);
        }
    }

//    public static void transactionHistoryToFile(String start, String end) throws SQLException {
//        PreparedStatement preparedStatement = DatabaseConnection.connection.prepareStatement("SELECT * FROM TRANSACTION WHERE date between ? and ?");
//        preparedStatement.setString(1, BankAccountMenu.getLoggedInUserCreditCardNum());
//        preparedStatement.setString(2, BankAccountMenu.getLoggedInUserCreditCardNum());
//        preparedStatement.setString(3, BankAccountMenu.getLoggedInUserDebitCardNum());
//        preparedStatement.setString(4, BankAccountMenu.getLoggedInUserDebitCardNum());
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        while(resultSet.next()){
//            String receiverBankNum = resultSet.getString("receiver_bank_num");
//            String senderBankNum = resultSet.getString("sender_bank_num");
//            double amountSent = resultSet.getDouble("amount_sent");
//            double amountReceived = resultSet.getDouble("amount_received");
//            Date date = resultSet.getDate("date");
//            try {
//                FileWriter myWriter = new FileWriter("transactionhistorybydate.txt");
//
//            } catch (IOException e){
//                System.out.println("Error with FILE..");
//                e.printStackTrace();
//            }
//        }
//    }

}
