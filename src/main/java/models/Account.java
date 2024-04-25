package models;

import javax.print.attribute.standard.DateTimeAtCreation;

public class Account {
    private int id,owner_id;
    private String account_number;
    private  Transaction_Type account_type;
    private String currency;
    private int validate;
    private double balance;
    private DateTimeAtCreation createdAt;

    public Account(int id, int owner_id, String account_number, Transaction_Type account_type, String currency, int validate, double balance, DateTimeAtCreation createdAt) {
        this.id = id;
        this.owner_id = owner_id;
        this.account_number = account_number;
        this.account_type = account_type;
        this.currency = currency;
        this.validate = validate;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public Account(int owner_id, String account_number, Transaction_Type account_type, String currency, int validate, double balance, DateTimeAtCreation createdAt) {
        this.owner_id = owner_id;
        this.account_number = account_number;
        this.account_type = account_type;
        this.currency = currency;
        this.validate = validate;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public Account() {
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public Transaction_Type getAccount_type() {
        return account_type;
    }

    public void setAccount_type(Transaction_Type account_type) {
        this.account_type = account_type;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getValidate() {
        return validate;
    }

    public void setValidate(int validate) {
        this.validate = validate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public DateTimeAtCreation getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", owner_id=" + owner_id +
                ", account_number='" + account_number + '\'' +
                ", account_type=" + account_type +
                ", currency='" + currency + '\'' +
                ", validate=" + validate +
                ", balance=" + balance +
                ", createdAt=" + createdAt +
                '}';
    }

    public void setCreatedAt(DateTimeAtCreation createdAt) {
        this.createdAt = createdAt;
    }
}
