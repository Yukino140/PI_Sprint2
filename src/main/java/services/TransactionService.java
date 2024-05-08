package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Facture;
import models.Transaction;
import models.Transaction_Type;
import utils.MyDatabase;

import java.time.LocalDateTime;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionService implements IService<Transaction> {

    private Connection connection;

    public TransactionService() {
        connection = MyDatabase.getInstance().getConnection()   ;
    }

    @Override
    public void create(Transaction transaction) throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        String sql = "insert into transaction (`account_number`, `transaction_type`, `amount`,`date`,`description`,`fee`,authenticator_code,`receiver_account_number`,`isArchived`)"+
                "values(?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,transaction.getAccount_number());
        statement.setString(2,transaction.getTransaction_type().toString());
        statement.setDouble(3,transaction.getAmount());
        statement.setDate(4, Date.valueOf(LocalDate.now()));
        statement.setString(5,"");
        statement.setDouble(6,0);
        statement.setDouble(7,transaction.getAuthenticator_code());
        statement.setString(8,transaction.getReceiver_account_number());
        statement.setInt(9,transaction.getIsArchived());
        statement.executeUpdate();
    }

    @Override
    public void update(Transaction t) throws SQLException {

        String sql="update transaction set isArchived=1 where id=?";
        PreparedStatement statement=connection.prepareStatement(sql);
        statement.setInt(1,t.getId());
        statement.executeUpdate();

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public List<Transaction> read() {
        String sql = "select * from transaction";
        Statement statement = null;
        try {
            statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(sql);
        List<Transaction> transactions = new ArrayList<>();
        while(rs.next()){
            Transaction t = new Transaction();
            t.setId(rs.getInt("id"));
            t.setAccount_number(rs.getString("account_number"));
            t.setAmount(rs.getDouble("amount"));
            t.setTransaction_type(Transaction_Type.valueOf(rs.getString("transaction_type")));
            t.setDate(rs.getDate("date"));
            t.setDescription(rs.getString("description"));
            t.setFee(rs.getDouble("fee"));
            t.setAuthenticator_code(rs.getDouble("authenticator_code"));
            t.setReceiver_account_number(rs.getString("receiver_account_number"));
            transactions.add(t);
        }
        return transactions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Transaction getById(int id) throws SQLException {
        String sql="Select * from Transaction where id="+id;
        Statement statement=null;
        try {
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);
            Transaction t = new Transaction();
            while(rs.next()){
                t.setId(rs.getInt("id"));
                t.setAccount_number(rs.getString("account_number"));
                t.setAmount(rs.getDouble("amount"));
                t.setTransaction_type(Transaction_Type.valueOf(rs.getString("transaction_type")));
                t.setDate(rs.getDate("date"));
                t.setDescription(rs.getString("description"));
                t.setFee(rs.getDouble("fee"));
                t.setAuthenticator_code(rs.getDouble("authenticator_code"));
                t.setReceiver_account_number(rs.getString("receiver_account_number"));
            }
            return t;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Transaction> readAllByAccounntNumber(String n) throws SQLException {
        return null;
    }


    public List<Transaction> readAllByClient(int id) throws SQLException {
        String sql = "SELECT T.* FROM Client C " +
                "INNER JOIN Account A ON A.Owner_id = ? " +
                "INNER JOIN transaction T ON T.account_number = A.account_number AND T.isArchived=0;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                List<Transaction> transactions = new ArrayList<>();
                while (rs.next()) {
                    Transaction t = new Transaction();
                    t.setId(rs.getInt("id"));
                    t.setAccount_number(rs.getString("account_number"));
                    t.setAmount(rs.getDouble("amount"));
                    t.setTransaction_type(Transaction_Type.valueOf(rs.getString("transaction_type")));
                    t.setDate(rs.getDate("date"));
                    t.setDescription(rs.getString("description"));
                    t.setFee(rs.getDouble("fee"));
                    t.setAuthenticator_code(rs.getDouble("authenticator_code"));
                    t.setReceiver_account_number(rs.getString("receiver_account_number"));
                    transactions.add(t);
                }
                return transactions;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
