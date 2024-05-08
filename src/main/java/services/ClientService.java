package services;

import models.Client;
import models.Facture;
import utils.MyDatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ClientService implements IService<Client> {

    private Connection connection;

    public ClientService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void create(Client client) throws SQLException {

    }

    @Override
    public void update(Client client) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public List<Client> read() throws SQLException {
        return null;
    }

    @Override
    public Client getById(int id) throws SQLException {
        String sql="Select * from Client where id="+id;
        Statement statement=null;
        try {
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);
            Client c = new Client();
            while(rs.next()) {
              /*  c.setId(rs.getInt("id"));
                f.setId_transaction_id(rs.getInt("id_transaction_id"));
                f.setTax(rs.getDouble("tax"));
                //a.setCreatedAt(rs.getDate("createdAt"));
                f.setMontant_ttc(rs.getInt("montant_ttc"));*/
                c.setId(rs.getInt("id"));
                c.setAccount_type(rs.getString("account_type"));
                c.setAddress(rs.getString("address"));
                c.setBirth_date(rs.getDate("birth_date"));
                c.setCin(rs.getInt("cin"));
                c.setCreate_at(rs.getDate("created_at"));
                c.setLast_name(rs.getString("last_name"));
                c.setFirst_name(rs.getString("first_name"));
                c.setPassword(rs.getString("password"));
                c.setEmail(rs.getString("email"));
                c.setTransaction_limit(rs.getInt("transaction_limit"));
                c.setRoles(rs.getString("roles"));
                c.setPhone_number(rs.getInt("phone_number"));

            }

            return c;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Client> readAllByAccounntNumber(String n) throws SQLException {
        return null;
    }
}
