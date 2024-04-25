package services;

import models.Account;
import models.Transaction;
import models.Transaction_Type;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountService implements IService<Account>{

    private Connection connection;

    public AccountService() {
        connection = MyDatabase.getInstance().getConnection();
    }



    @Override
    public void create(Account account) throws SQLException {

    }

    @Override
    public void update(Account account) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public List<Account> read() throws SQLException {
        return null;
    }

    @Override
    public Account getById(int id) throws SQLException {
        String query = "SELECT * FROM Account WHERE id ="+id;
        Statement statement = null;
        try {
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);
                Account a = new Account();
                while(rs.next()) {
                    a.setId(rs.getInt("id"));
                    a.setAccount_number(rs.getString("account_number"));
                    a.setBalance(rs.getDouble("balance"));
                    //a.setCreatedAt(rs.getDate("createdAt"));
                    a.setOwner_id(rs.getInt("owner_id"));
                    a.setAccount_type(Transaction_Type.valueOf(rs.getString("account_type")));
                    a.setCurrency(rs.getString("currency"));
                    a.setValidate(rs.getInt("validate"));
                }

            return a;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Account> readAllByAccounntNumber(String n) throws SQLException {
        return null;
    }
}
