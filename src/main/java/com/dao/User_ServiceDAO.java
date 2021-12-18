package com.dao;

import com.dto.Service;
import com.dto.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User_ServiceDAO {
    private final Connection connection;

    public User_ServiceDAO(Connection connection) throws SQLException {
        this.connection = connection;
    }

    public void addServiceToUser(Service service, User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into user_service values (?, ?);");
        statement.setInt(1, user.getId());
        statement.setInt(2, service.getId());
        statement.executeUpdate();
    }

    public void deleteServiceFromUser(Service service, User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from user_service where user_id=? and service_id=?;");
        statement.setInt(1, user.getId());
        statement.setInt(2, service.getId());
        statement.executeUpdate();
    }

    public void deleteAllServicesFromUser(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from user_service where user_id=?;");
        statement.setInt(1, user.getId());
        statement.executeUpdate();
    }

    public void deleteAllUsersFromService(Service service) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from user_service where service_id=?;");
        statement.setInt(1, service.getId());
        statement.executeUpdate();
    }
}
