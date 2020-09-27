package model;

import java.sql.Date;

public class BankAccount {

    private String creditCardNum;
    private double creditCardBalance;
    private String debitCardNum;
    private double debitCardBalance;
    private Date dateCreated;
    private String companyName;
    private Long userID;

    public BankAccount(String creditCardNum, String debitCardNum, Date dateCreated, String companyName, Long userID) {
        this.creditCardNum = creditCardNum;
        this.creditCardBalance = 0;
        this.debitCardNum = debitCardNum;
        this.debitCardBalance = 0;
        this.dateCreated = dateCreated;
        this.companyName = companyName;
        this.userID = userID;
    }

    public BankAccount(String creditCardNum, String debitCardNum, Date dateCreated, Long userID) {
        this.creditCardNum = creditCardNum;
        this.creditCardBalance = 0;
        this.debitCardNum = debitCardNum;
        this.debitCardBalance = 0;
        this.dateCreated = dateCreated;
        this.userID = userID;
    }

    public String getCreditCardNum() {
        return creditCardNum;
    }

    public void setCreditCardNum(String creditCardNum) {
        this.creditCardNum = creditCardNum;
    }

    public double getCreditCardBalance() {
        return creditCardBalance;
    }

    public void setCreditCardBalance(double creditCardBalance) {
        this.creditCardBalance = creditCardBalance;
    }

    public String getDebitCardNum() {
        return debitCardNum;
    }

    public void setDebitCardNum(String debitCardNum) {
        this.debitCardNum = debitCardNum;
    }

    public double getDebitCardBalance() {
        return debitCardBalance;
    }

    public void setDebitCardBalance(double debitCardBalance) {
        this.debitCardBalance = debitCardBalance;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

}
