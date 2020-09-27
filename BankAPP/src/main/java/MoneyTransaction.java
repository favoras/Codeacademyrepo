
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MoneyTransaction {

    public void depositToCreditCard(double depositAmount) throws SQLException {
        PreparedStatement preparedStatement = DatabaseConnection.connection.prepareStatement("UPDATE BANK_ACCOUNT set CCN_BALANCE = CCN_BALANCE + ? where USER_ID = ?");
        preparedStatement.setDouble(1, depositAmount);
        preparedStatement.setLong(2, BankAccountMenu.user_id);
        preparedStatement.execute();
        System.out.println("Successfully deposited " + depositAmount);
        PreparedStatement depositHistory = DatabaseConnection.connection.prepareStatement("INSERT INTO TRANSACTION(RECEIVER_BANK_NUM, AMOUNT_RECEIVED, DATE) VALUES(?,?,?)");
        depositHistory.setString(1, BankAccountMenu.getLoggedInUserCreditCardNum());
        depositHistory.setDouble(2, depositAmount);
        depositHistory.setDate(3, Date.valueOf(LocalDate.now()));
        depositHistory.execute();
    }

    public void depositToDebitCard(double depositAmount) throws SQLException {
        PreparedStatement preparedStatement = DatabaseConnection.connection.prepareStatement("UPDATE BANK_ACCOUNT set DCN_BALANCE = DCN_BALANCE + ? where USER_ID = ?");
        preparedStatement.setDouble(1, depositAmount);
        preparedStatement.setLong(2, BankAccountMenu.user_id);
        preparedStatement.execute();
        System.out.println("Successfully deposited " + depositAmount);
        PreparedStatement depositHistory = DatabaseConnection.connection.prepareStatement("INSERT INTO TRANSACTION(RECEIVER_BANK_NUM, AMOUNT_RECEIVED, DATE) VALUES(?,?,?)");
        depositHistory.setString(1, BankAccountMenu.getLoggedInUserDebitCardNum());
        depositHistory.setDouble(2, depositAmount);
        depositHistory.setDate(3, Date.valueOf(LocalDate.now()));
        depositHistory.execute();
    }

    public void withdrawFromCreditCard(double withdrawAmount) throws SQLException {
        PreparedStatement preparedStatement = DatabaseConnection.connection.prepareStatement("UPDATE BANK_ACCOUNT set CCN_BALANCE = CCN_BALANCE - ? where USER_ID = ?");
        preparedStatement.setDouble(1, withdrawAmount);
        preparedStatement.setLong(2, BankAccountMenu.user_id);
        preparedStatement.execute();
        System.out.println("Successfully withdrawn " + withdrawAmount);
        PreparedStatement withdrawHistory = DatabaseConnection.connection.prepareStatement("INSERT INTO TRANSACTION(SENDER_BANK_NUM, AMOUNT_SENT, DATE) VALUES(?,?,?)");
        withdrawHistory.setString(1, BankAccountMenu.getLoggedInUserCreditCardNum());
        withdrawHistory.setDouble(2, withdrawAmount);
        withdrawHistory.setDate(3, Date.valueOf(LocalDate.now()));
        withdrawHistory.execute();
    }

    public void withdrawFromDebitCard(double withdrawAmount) throws SQLException {
        if(availableMoneyCheckerDebit() - withdrawAmount < 0){
            System.out.println("Balance to low.. check your balance first..");
        } else {
            PreparedStatement preparedStatement = DatabaseConnection.connection.prepareStatement("UPDATE BANK_ACCOUNT set DCN_BALANCE = DCN_BALANCE - ? where USER_ID = ?");
            preparedStatement.setDouble(1, withdrawAmount);
            preparedStatement.setLong(2, BankAccountMenu.user_id);
            preparedStatement.execute();
            System.out.println("Successfully withdrawn " + withdrawAmount);
            PreparedStatement withdrawHistory = DatabaseConnection.connection.prepareStatement("INSERT INTO TRANSACTION(SENDER_BANK_NUM, AMOUNT_SENT, DATE) VALUES(?,?,?)");
            withdrawHistory.setString(1, BankAccountMenu.getLoggedInUserDebitCardNum());
            withdrawHistory.setDouble(2, withdrawAmount);
            withdrawHistory.setDate(3, Date.valueOf(LocalDate.now()));
            withdrawHistory.execute();
        }
    }

    private Long availableMoneyCheckerDebit() throws SQLException {
        PreparedStatement preparedStatement = DatabaseConnection.connection.prepareStatement("SELECT DCN_BALANCE FROM BANK_ACCOUNT WHERE USER_ID = ?");
        preparedStatement.setLong(1, BankAccountMenu.user_id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(!resultSet.next()){
            System.out.println();
        } else {
            return resultSet.getLong("dcn_balance");
        }
        return null;
    }

    public void sendMoneyFromCreditCard(String receiverAccountNum, double amount) throws SQLException {
        DatabaseConnection.connection.setAutoCommit(false);
        try{
            PreparedStatement sender = DatabaseConnection.connection.prepareStatement("UPDATE BANK_ACCOUNT SET CCN_BALANCE = CCN_BALANCE - ? WHERE CREDIT_CARD_NUM = ?");
            PreparedStatement receiver = DatabaseConnection.connection.prepareStatement("UPDATE BANK_ACCOUNT SET CCN_BALANCE = CCN_BALANCE + ? WHERE CREDIT_CARD_NUM = ?");
            sender.setDouble(1, amount);
            sender.setString(2, BankAccountMenu.getLoggedInUserCreditCardNum());
            receiver.setDouble(1, amount);
            receiver.setString(2, receiverAccountNum);
            if(receiverChecker(receiverAccountNum)){
                sender.execute();
                receiver.execute();
                DatabaseConnection.connection.commit();
                PreparedStatement senderHistory = DatabaseConnection.connection.prepareStatement("INSERT INTO TRANSACTION(RECEIVER_BANK_NUM,SENDER_BANK_NUM, AMOUNT_SENT, AMOUNT_RECEIVED, DATE) VALUES(?,?,?,?,?)");
                senderHistory.setString(1, receiverAccountNum);
                senderHistory.setString(2, BankAccountMenu.getLoggedInUserCreditCardNum());
                senderHistory.setDouble(3, amount);
                senderHistory.setDouble(4, amount);
                senderHistory.setDate(5, Date.valueOf(LocalDate.now()));
                senderHistory.execute();
            } else {
                System.out.println("Wrong Creditcard number.. try again..");
                DatabaseConnection.connection.rollback();
            }
        }catch (SQLException e){
            DatabaseConnection.connection.rollback();
            e.printStackTrace();
        } finally {
            DatabaseConnection.connection.setAutoCommit(true);
        }
    }

    public void sendMoneyFromDebitCard(String receiverAccountNum, double amount) throws SQLException {
        if(availableMoneyCheckerDebit() - amount < 0){
            System.out.println("Balance to low.. check your balance first..");
        } else {
            DatabaseConnection.connection.setAutoCommit(false);
            try{
                PreparedStatement sender = DatabaseConnection.connection.prepareStatement("UPDATE BANK_ACCOUNT SET DCN_BALANCE = DCN_BALANCE - ? WHERE DEBIT_CARD_NUM = ?");
                PreparedStatement receiver = DatabaseConnection.connection.prepareStatement("UPDATE BANK_ACCOUNT SET DCN_BALANCE = DCN_BALANCE + ? WHERE DEBIT_CARD_NUM = ?");
                sender.setDouble(1, amount);
                sender.setString(2, BankAccountMenu.getLoggedInUserCreditCardNum());
                receiver.setDouble(1, amount);
                receiver.setString(2, receiverAccountNum);
                if(receiverChecker(receiverAccountNum)){
                    sender.execute();
                    receiver.execute();
                    DatabaseConnection.connection.commit();
                    PreparedStatement senderHistory = DatabaseConnection.connection.prepareStatement("INSERT INTO TRANSACTION(RECEIVER_BANK_NUM, SENDER_BANK_NUM, AMOUNT_SENT, AMOUNT_RECEIVED, DATE) VALUES(?,?,?,?,?)");
                    senderHistory.setString(1, receiverAccountNum);
                    senderHistory.setString(2, BankAccountMenu.getLoggedInUserDebitCardNum());
                    senderHistory.setDouble(3, amount);
                    senderHistory.setDouble(4, amount);
                    senderHistory.setDate(5, Date.valueOf(LocalDate.now()));
                    senderHistory.execute();
                } else {
                    System.out.println("Wrong Debitcard number.. try again..");
                    DatabaseConnection.connection.rollback();
                }
            }catch (SQLException e){
                DatabaseConnection.connection.rollback();
                e.printStackTrace();
            } finally {
                DatabaseConnection.connection.setAutoCommit(true);
            }
        }
    }

    public boolean receiverChecker(String receiverAccountNum) throws SQLException {
        PreparedStatement receiverCheckerCredit = DatabaseConnection.connection.prepareStatement("SELECT CREDIT_CARD_NUM FROM BANK_ACCOUNT WHERE CREDIT_CARD_NUM = ?");
        PreparedStatement receiverCheckerDebit = DatabaseConnection.connection.prepareStatement("SELECT DEBIT_CARD_NUM FROM BANK_ACCOUNT WHERE DEBIT_CARD_NUM = ?");
        receiverCheckerCredit.setString(1, receiverAccountNum);
        receiverCheckerDebit.setString(1, receiverAccountNum);
        ResultSet resultSetCredit = receiverCheckerCredit.executeQuery();
        ResultSet resultSetDebit = receiverCheckerDebit.executeQuery();

        if(!resultSetCredit.next()){
            if(!resultSetDebit.next()){
                return false;
            }
            return false;
        }
        return true;
    }

}
