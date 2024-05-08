package models;

import javax.print.attribute.standard.DateTimeAtCreation;
import java.sql.Date;

public class Client {
    private int id;
    private String email;
    private String roles;
    private String password;
    private String first_name;
    private String last_name;
    private int phone_number;

    public Client() {
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                ", password='" + password + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", phone_number=" + phone_number +
                ", cin=" + cin +
                ", birth_date=" + birth_date +
                ", create_at=" + create_at +
                ", address='" + address + '\'' +
                ", transaction_limit=" + transaction_limit +
                ", account_type='" + account_type + '\'' +
                '}';
    }

    public Client(String email, String roles, String password, String first_name, String last_name, int phone_number, int cin, Date birth_date, Date create_at, String address, int transaction_limit, String account_type) {
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.cin = cin;
        this.birth_date = birth_date;
        this.create_at = create_at;
        this.address = address;
        this.transaction_limit = transaction_limit;
        this.account_type = account_type;
    }

    private int cin;

    public Client(int id, String email, String roles, String password, String first_name, String last_name, int phone_number, int cin, Date birth_date, Date create_at, String address, int transaction_limit, String account_type) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.cin = cin;
        this.birth_date = birth_date;
        this.create_at = create_at;
        this.address = address;
        this.transaction_limit = transaction_limit;
        this.account_type = account_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTransaction_limit() {
        return transaction_limit;
    }

    public void setTransaction_limit(int transaction_limit) {
        this.transaction_limit = transaction_limit;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    private Date birth_date;
    private Date create_at;
    private String address;
    private int transaction_limit;
    private String account_type;
}
