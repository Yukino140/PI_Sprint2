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


    public void updateBalance(String acc,double m) throws SQLException {
        String sql="update account set balance=balance+? where account_number=?";
        PreparedStatement statement=connection.prepareStatement(sql);
        statement.setDouble(1,m);
        statement.setString(2,acc);
        statement.executeUpdate();

    }

    public Account getAccountByAccountType(String at, int id) {
        String query = "SELECT * FROM Account WHERE account_type = ? AND owner_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Set parameters
            statement.setString(1, at);
            statement.setInt(2, id);

            // Execute query
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Account a = new Account();
                    a.setId(rs.getInt("id"));
                    a.setAccount_number(rs.getString("account_number"));
                    a.setBalance(rs.getDouble("balance"));
                    //a.setCreatedAt(rs.getDate("createdAt"));
                    a.setOwner_id(rs.getInt("owner_id"));
                    a.setAccount_type(Transaction_Type.valueOf(rs.getString("account_type")));
                    a.setCurrency(rs.getString("currency"));
                    a.setValidate(rs.getInt("validate"));
                    return a;
                } else {
                    return null; // Account not found
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public List<Account> getAccountsOfClient(int id){
        String query = "SELECT * FROM Account WHERE owner_id ="+id;
        Statement statement = null;
        try {
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);
            List<Account> ls=new ArrayList<>();
            while(rs.next()) {
                Account a = new Account();

                a.setId(rs.getInt("id"));
                a.setAccount_number(rs.getString("account_number"));
                a.setBalance(rs.getDouble("balance"));
                //a.setCreatedAt(rs.getDate("createdAt"));
                a.setOwner_id(rs.getInt("owner_id"));
                a.setAccount_type(Transaction_Type.valueOf(rs.getString("account_type")));
                a.setCurrency(rs.getString("currency"));
                a.setValidate(rs.getInt("validate"));
                ls.add(a);
            }

            return ls;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Account> readAllByAccounntNumber(String n) throws SQLException {
        return null;
    }

    public boolean accountExist(String an) throws SQLException {
        String query = "SELECT COUNT(*) FROM account WHERE account_number = ?";
             PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, an);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }

        return false;
    }

}
