import model.BankAccount;
import model.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UserRegistration {

    public User registerNewUserToBank(User user){

        try{
            PreparedStatement preparedStatement = DatabaseConnection.connection.prepareStatement("INSERT INTO USER(USER_LOGIN, USER_PASSWORD, FIRST_NAME, LAST_NAME, BIRTH_DATE, PERSONAL_CODE) VALUES(?,?,?,?,?,?)");
            preparedStatement.setString(1, user.getUser_login());
            preparedStatement.setString(2, user.getUser_password());
            preparedStatement.setString(3, user.getFirst_name());
            preparedStatement.setString(4, user.getLast_name());
            preparedStatement.setDate(5, user.getBirth_date());
            preparedStatement.setLong(6, user.getPersonal_code());
            preparedStatement.execute();
            return user;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public BankAccount registerNewBankAccount(BankAccount bankAccount){

        try{
            PreparedStatement preparedStatement = DatabaseConnection.connection.prepareStatement("INSERT INTO BANK_ACCOUNT(CREDIT_CARD_NUM, CCN_BALANCE, DEBIT_CARD_NUM, DCN_BALANCE, DATE_CREATED, USER_ID) VALUES(?,?,?,?,?,?)");
            preparedStatement.setString(1, bankAccount.getCreditCardNum());
            preparedStatement.setDouble(2, bankAccount.getCreditCardBalance());
            preparedStatement.setString(3, bankAccount.getDebitCardNum());
            preparedStatement.setDouble(4, bankAccount.getDebitCardBalance());
            preparedStatement.setDate(5, bankAccount.getDateCreated());
            preparedStatement.setLong(6, bankAccount.getUserID());
            preparedStatement.execute();
            return bankAccount;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
