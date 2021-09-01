package com.dao;

import com.dataBase.DBConnection;
import com.dto.Role;
import com.dto.Service;
import com.dto.User;

import javax.jws.soap.SOAPBinding;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO {
    private final Connection connection;
    private long tempId;

    public UserDAO(Connection connection) throws SQLException {
        this.connection = connection;
        this.tempId = getLastId();
    }

    public long incrementAndGet(){
        return ++tempId;
    }

    public long getLastId() throws SQLException {
        List<Long> indexes = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from project_db.user;");
        while (resultSet.next()){
            indexes.add((long) resultSet.getInt("id"));
        }
        return indexes.stream().max((x, y) -> (int) (x - y)).orElse(0L);
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .setId(resultSet.getInt("id"))
                .setEmail(resultSet.getString("email"))
                .setLogin(resultSet.getString("login"))
                .setName(resultSet.getString("name"))
                .setPassword(resultSet.getString("password"))
                .setRole(Role.valueOf(resultSet.getString("role")))
                .setMoney(resultSet.getInt("money"));
    }

    public List<User> findAll() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from project_db.user;");
        List<User> result = new ArrayList<>();
        while (resultSet.next()){
            result.add(buildUser(resultSet));
        }
        return result;
    }

    public Optional<User> findByLogin(String login) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from project_db.user where login='" + login +"';");
        User user = null;
        if (resultSet.next()){
            user = buildUser(resultSet);
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> save(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into project_db.user values (?, ?, ?, ?, ?, ?, ?)");
        statement.setInt(1, (int) incrementAndGet());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getLogin());
        statement.setString(4, user.getName());
        statement.setString(5, user.getPassword());
        statement.setString(6, user.getRole().name());
        statement.setInt(7, user.getMoney());
        statement.executeUpdate();
        return findByLogin(user.getLogin());
    }

    public Optional<User> getById(int id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from project_db.user where id=" + id +";");
        User user = null;
        if (resultSet.next()){
            user = buildUser(resultSet);
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> delete(User user) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from project_db.user where id=" + user.getId() +";");
        return findByLogin(user.getLogin());
    }

    public Optional<User> update(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update project_db.user set name=?, password=?, role=?, money=? where id=" + user.getId() +";");
        statement.setString(1, user.getName());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getRole().name());
        statement.setInt(4, user.getMoney());
        statement.executeUpdate();
        return findByLogin(user.getLogin());
    }

    public Optional<User> findByEmail(String email) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from project_db.user where email='" + email + "';");
        User user = null;
        if (resultSet.next()){
            user = buildUser(resultSet);
        }
        return Optional.ofNullable(user);
    }
}
