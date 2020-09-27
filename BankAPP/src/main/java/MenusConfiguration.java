import model.BankAccount;
import model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MenusConfiguration {

    static Scanner scanner = new Scanner(System.in);
    static UserPassword userPassword = new UserPassword();
    static MoneyTransaction moneyTransaction = new MoneyTransaction();


    public static void firstMenuConfiguration() throws SQLException, InterruptedException {
        Menus.printMainMenu();
        String pick;
        do{
            System.out.println("Pick your option");
            pick = scanner.nextLine().toUpperCase();
            switch (pick){
                case "1":
                    System.out.println("LOGIN MENU... "); // Done, but need to fix "bugs" // login and password(with hash) working
                    loginMenu();
                    System.out.println("Account loading..");
                    TimeUnit.SECONDS.sleep(3);
                    secondMenuConfiguration();
                    break;
                case "2":
                    System.out.println("REGISTER MENU..."); // Done, but need to fix "bugs" // password hash working
                    registrationMenu();
                    Menus.printMainMenu();
                    break;
            }
            Menus.printMainMenu();
        }while(!pick.equals("X"));
        System.out.println("Bye bye bye...");
        DatabaseConnection.disconnectFromDatabase();
        System.exit(0);
    }

    public static void secondMenuConfiguration() throws SQLException, InterruptedException {
        Menus.printUserInfo();
        String pick;
        do{
            pick = scanner.nextLine().toUpperCase();
            switch (pick){
                case "1":
                    System.out.println("Balance info: ");
                    BankAccountMenu.printBankAccountInfo();
                    break;
                case "2":
                    System.out.println("Deposit money.");
                    System.out.println("Where you want to deposit? (Credit or Debit) card");
                    String cardPick = scanner.nextLine().toUpperCase();
                    if(cardPick.equals("CREDIT")) {
                        System.out.println("How much money you want to deposit?");
                        double depositAmount = scanner.nextDouble();
                        moneyTransaction.depositToCreditCard(depositAmount);
                    } else if (cardPick.equals("DEBIT")){
                        System.out.println("How much money you want to deposit?");
                        double depositAmount = scanner.nextDouble();
                        moneyTransaction.depositToDebitCard(depositAmount);
                    } else {
                        System.out.println("Wrong pick..");
                    }
                    break;
                case "3":
                    System.out.println("Withdraw money.");
                    System.out.println("Which account you want to use? (Credit or Debit) card");
                    cardPick = scanner.nextLine().toUpperCase();
                    if(cardPick.equals("CREDIT")) {
                        System.out.println("How much money you want to withdraw?");
                        double withdrawAmount = scanner.nextDouble();
                        moneyTransaction.withdrawFromCreditCard(withdrawAmount);
                    } else if (cardPick.equals("DEBIT")){
                        System.out.println("How much money you want to withdraw?");
                        double withdrawAmount = scanner.nextDouble();
                        moneyTransaction.withdrawFromDebitCard(withdrawAmount);
                    } else {
                        System.out.println("Wrong pick..");
                    }
                    break;
                case "4":
                    System.out.println("Full transaction history");
                    TransactionHistory.printTransactionHistory();
                    break;
                case "5":
                    System.out.println("Send money");
                    System.out.println("Which account you want to use to send money? (Credit or Debit)");
                    cardPick = scanner.nextLine().toUpperCase();
                    if(cardPick.equals("CREDIT")) {
                        System.out.println("How much you want to send?");
                        double sendingAmount = scanner.nextDouble();
                        System.out.println("Type receiver Debit or Credit card number: ");
                        String receiverNum = scanner.next();
                        moneyTransaction.sendMoneyFromCreditCard(receiverNum, sendingAmount);
                    } else if (cardPick.equals("DEBIT")){
                        System.out.println("How much you want to send?");
                        double sendingAmount = scanner.nextDouble();
                        System.out.println("Type receiver Debit or Credit card number: ");
                        String receiverNum = scanner.nextLine();
                        moneyTransaction.sendMoneyFromDebitCard(receiverNum, sendingAmount);
                    } else {
                        System.out.println("Wrong pick..");
                    }
                    break;
                case "6":
                    System.out.println("Transaction History by Date");
                    System.out.println("NOT WORKING... SORRY");
//                    System.out.println("Type start date: (YYYY-MM-DD)");
//                    String start = scanner.next();
//                    System.out.println("Type end date: (YYYY-MM-DD)");
//                    String end = scanner.next();
//                    TransactionHistory.transactionHistoryToFile(start, end);
                    break;
                case "9":
                    System.out.println("Go back to login/register menu");
                    firstMenuConfiguration();
                    break;
            }
            Menus.printUserInfo();
        }while (!pick.equals("X"));
        System.out.println("Goodbye..");
        DatabaseConnection.disconnectFromDatabase();
        System.exit(0);
    }

    public static void registrationMenu() throws InterruptedException {
        UserRegistration userRegistration = new UserRegistration();
        CardNumberGenerator cardNumberGenerator = new CardNumberGenerator();

        System.out.println("Welcome to new user registration");
        System.out.println("Create your User_login: ");
        String user_login = scanner.next();
        System.out.println("Create your User_password: ");
        String user_password = scanner.next();
        System.out.println("What is your first name? ");
        String first_name = scanner.next().toUpperCase();
        System.out.println("What is your last name? ");
        String last_name = scanner.next().toUpperCase();
        System.out.println("Your birth date: Format(YYYY-MM-DD)");
        String birth_date = scanner.next();
        System.out.println("And your personal code/indentify code");
        Long personal_code = scanner.nextLong();
        String blackhole = scanner.nextLine();

        userRegistration.registerNewUserToBank(new User(
                user_login,
                userPassword.hashPassword(user_password),
                first_name,
                last_name,
                Date.valueOf(birth_date),
                personal_code
                )
        );
        userRegistration.registerNewBankAccount(new BankAccount(
                cardNumberGenerator.generate("1111", 16),
                cardNumberGenerator.generate("5555", 16),
                Date.valueOf(LocalDate.now()),
                personal_code
                )
        );

        System.out.println("Checking if everything is okey..");
        TimeUnit.SECONDS.sleep(5);

        System.out.println("Successfully registered to bank.");
    }

    public static void loginMenu() throws SQLException {
        PreparedStatement preparedStatement = DatabaseConnection.connection.prepareStatement("SELECT * FROM USER WHERE USER_LOGIN = ?");
        System.out.println("Welcome our user");
        System.out.println("What is your user_login?");
        String user_login = scanner.nextLine();
        BankAccountMenu.getLoggedInUserID(user_login); // Sending login to personal id taker..
        System.out.println("What is your user_password?");
        String user_password = scanner.nextLine();
        userPassword.takePasswordFromDatabase(user_password, user_login);
        preparedStatement.setString(1, user_login);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            System.out.println("");
        } else {
            do {
                String data = resultSet.getString("user_login");
            } while (resultSet.next());
        }
    }
}
