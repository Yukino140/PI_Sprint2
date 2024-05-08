package services;

import models.Account;
import models.Contact;
import models.Transaction;
import models.Transaction_Type;
import utils.MyDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContactService implements IService<Contact>{

    private Connection connection;

    public ContactService() {
        connection = MyDatabase.getInstance().getConnection()   ;
    }
    @Override
    public void create(Contact contact) throws SQLException {
        String sql = "insert into contact (`ownerId`, `contactName`, `contactAccount`,`isArchived`)"+
                "values(?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,contact.getOwnerId());
        statement.setString(2,contact.getContactName());
        statement.setString(3,contact.getContactAccount());
        statement.setInt(4,0);
        statement.executeUpdate();
    }

    @Override
    public void update(Contact contact) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public List<Contact> read() throws SQLException {
        String sql = "select * from contact";
        Statement statement = null;
        try {
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);
            List<Contact> contact = new ArrayList<>();
            while(rs.next()){
                Contact c = new Contact();
                c.setId(rs.getInt("id"));
                c.setOwnerId(rs.getInt("ownerId"));
                c.setContactName(rs.getString("contactName"));
                c.setContactAccount(rs.getString("contactAccount"));
                c.setIsArchived(rs.getInt("isArchived"));

                contact.add(c);
            }
            return contact;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Contact getById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Contact> readAllByAccounntNumber(String n) throws SQLException {
        return null;
    }
    public List<Contact> getByClient(int id){
        String sql = "select * from contact where ownerId="+id;
        Statement statement = null;
        try {
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);
            List<Contact> contact = new ArrayList<>();
            while(rs.next()){
                Contact c = new Contact();
                c.setId(rs.getInt("id"));
                c.setOwnerId(rs.getInt("ownerId"));
                c.setContactName(rs.getString("contactName"));
                c.setContactAccount(rs.getString("contactAccount"));
                c.setIsArchived(rs.getInt("isArchived"));

                contact.add(c);
            }
            return contact;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Contact getContact(int id, String name) {
        String sql = "SELECT * FROM contact WHERE ownerId = ? AND contactName = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setString(2, name);

            try (ResultSet rs = statement.executeQuery()) {
                Contact c = new Contact();
                while (rs.next()) {
                    c.setId(rs.getInt("id"));
                    c.setOwnerId(rs.getInt("ownerId"));
                    c.setContactName(rs.getString("contactName"));
                    c.setContactAccount(rs.getString("contactAccount"));
                    c.setIsArchived(rs.getInt("isArchived"));
                }
                return c;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
