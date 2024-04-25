package services;

import models.Facture;
import models.Transaction;
import utils.MyDatabase;

import java.sql.*;
import java.util.List;

public class FactureService implements IService<Facture> {


    private Connection connection;

    public FactureService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void create(Facture facture) throws SQLException {
        String sql="insert into Facture (`id_transaction_id`,`tax`,`montant_ttc`)"+
                "values(?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,facture.getId_transaction_id());
        statement.setDouble(2,facture.getTax());
        statement.setDouble(3,facture.getMontant_ttc());
        statement.executeUpdate();
    }



    @Override
    public void update(Facture facture) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public List<Facture> read() throws SQLException {
        return null;
    }

    @Override
    public Facture getById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Facture> readAllByAccounntNumber(String n) throws SQLException {
        return null;
    }

    public boolean hasFacture(int id) throws SQLException {
        String sql="SELECT * from Facture where id_transaction_id="+id;
        Statement statement = null;
        ResultSet rs = null;
        boolean hasResult = false;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            hasResult = rs.next(); // Check if ResultSet has at least one row
        } finally {
            // Close ResultSet and Statement
            if (rs != null) {
                rs.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
        return hasResult;

    }
}
