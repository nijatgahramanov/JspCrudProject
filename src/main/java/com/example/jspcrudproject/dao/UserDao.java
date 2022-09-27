package com.example.jspcrudproject.dao;

import com.example.jspcrudproject.model.User;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends AbstractDao {


    private static final String INSERT_USER = "insert into users" +
            " (name,email,country) values " + " (?,?,?,?)";

    private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id=?";

    private static final String SELECT_ALL_USER = "select * from users";
    private static final String DELETE_USER_BY_ID = "delete user from where id=?;";
    private static final String UPDATE_USER = "update users set name = ?,email = ?,country = ? where id = ?;";


    public void insertUser(User user) throws SQLException {
        try (Connection c = getConnection()) {
            PreparedStatement stmt = c.prepareStatement(INSERT_USER);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getCountry());
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection c = getConnection()) {
            PreparedStatement stmt = c.prepareStatement(UPDATE_USER);

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getCountry());
            stmt.setInt(4, user.getId());

            rowUpdated = stmt.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public User selectUser(int id) throws SQLException {
        User user = null;

        try (Connection c = getConnection();
             PreparedStatement stmt = c.prepareStatement(SELECT_USER_BY_ID);) {
            stmt.setInt(1, id);
            System.out.println(stmt);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                user = new User(id, name, email, country);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return user;
    }

    public List<User> selectAllUser() throws SQLException {
        List<User> users = new ArrayList<>();

        try (Connection c = getConnection()) {
            PreparedStatement stmt = c.prepareStatement(SELECT_USER_BY_ID);

            System.out.println(stmt);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, name, email, country));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return users;
    }

    public boolean deleteUser(int id)throws SQLException {
        boolean rowDeleted = false;

        try (Connection c = getConnection();
             PreparedStatement stmt = c.prepareStatement(DELETE_USER_BY_ID);) {
            stmt.setInt(1, id);
            rowDeleted = stmt.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowDeleted;
    }


}
