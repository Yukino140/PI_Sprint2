package models;

import java.util.Date;

public class Transaction {
    private int id,isArchived;
    private String account_number;

    public Transaction(String account_number, Transaction_Type transaction_type, double amount, double authenticator_code,String receiver_account_number,int isArchived) {
        this.account_number = account_number;
        this.transaction_type = transaction_type;
        this.amount = amount;
        this.authenticator_code = authenticator_code;
        this.receiver_account_number=receiver_account_number;
        this.isArchived=isArchived;
    }

    private Transaction_Type transaction_type;
    private double amount;
    private Date date;
    private String description;
    private double fee;
    private double authenticator_code;
    private String receiver_account_number;

    public Transaction(String account_number, Transaction_Type transaction_type, double amount, Date date, String description, double fee, double authenticator_code, String receiver_account_number,int isArchived) {
        this.account_number = account_number;
        this.transaction_type = transaction_type;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.fee = fee;
        this.authenticator_code = authenticator_code;
        this.receiver_account_number = receiver_account_number;
        this.isArchived=isArchived;

    }

    public int getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(int isArchived) {
        this.isArchived = isArchived;
    }

    public Transaction(int id, String account_number, Transaction_Type transaction_type, double amount, Date date, String description, double fee, double authenticator_code, String receiver_account_number, int isArchived) {
        this.id = id;
        this.account_number = account_number;
        this.transaction_type = transaction_type;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.fee = fee;
        this.authenticator_code = authenticator_code;
        this.receiver_account_number = receiver_account_number;
        this.isArchived=isArchived;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public Transaction_Type getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(Transaction_Type transaction_type) {
        this.transaction_type = transaction_type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getAuthenticator_code() {
        return authenticator_code;
    }

    public void setAuthenticator_code(double authenticator_code) {
        this.authenticator_code = authenticator_code;
    }

    public String getReceiver_account_number() {
        return receiver_account_number;
    }

    public Transaction() {
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", account_number='" + account_number + '\'' +
                ", transaction_type=" + transaction_type +
                ", amount=" + amount +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", fee=" + fee +
                ", authenticator_code=" + authenticator_code +
                ", receiver_account_number='" + receiver_account_number + '\'' +
                '}';
    }

    public void setReceiver_account_number(String receiver_account_number) {
        this.receiver_account_number = receiver_account_number;
    }
}
