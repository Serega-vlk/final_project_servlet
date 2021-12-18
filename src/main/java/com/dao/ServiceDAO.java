package com.dao;

import com.dto.Role;
import com.dto.Service;
import com.dto.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceDAO {
    private final Connection connection;
    private long tempId;

    public ServiceDAO(Connection connection) throws SQLException {
        this.connection = connection;
        this.tempId = getLastId();
    }

    public long incrementAndGetId(){
        return ++tempId;
    }

    public long getLastId() throws SQLException {
        List<Long> indexes = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from service;");
        while (resultSet.next()){
            indexes.add((long) resultSet.getInt("id"));
        }
        return indexes.stream().max((x, y) -> (int) (x - y)).orElse(0L);
    }

    private Service buildService(ResultSet resultSet) throws SQLException {
        return Service.builder()
                .setId(resultSet.getInt("id"))
                .setName(resultSet.getString("name"))
                .setPrice(resultSet.getInt("price"));
    }

    public List<Service> findAll() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from service;");
        List<Service> result = new ArrayList<>();
        while (resultSet.next()){
            result.add(buildService(resultSet));
        }
        return result;
    }

    public Service save(Service service) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into service values (?, ?, ?)");
        statement.setInt(1, (int) incrementAndGetId());
        statement.setString(2, service.getName());
        statement.setInt(3, service.getPrice());
        statement.executeUpdate();
        return getById(service.getId()).orElse(null);
    }

    public Optional<Service> getById(int id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from service where id=" + id +";");
        Service user = null;
        if (resultSet.next()){
            user = buildService(resultSet);
        }
        return Optional.ofNullable(user);
    }

    public Optional<Service> delete(Service service) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from service where id=" + service.getId() +";");
        return findByName(service.getName());
    }

    public Optional<Service> findByName(String name) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from service where name='" + name + "';");
        Service service = null;
        if (resultSet.next()){
            service = buildService(resultSet);
        }
        return Optional.ofNullable(service);
    }

    public List<Service> getUserServicesByUserId(int id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select s.id, s.name, s.price from service as s" +
                " join user_service as us on s.id = us.service_id where us.user_id = " + id + ";");
        List<Service> result = new ArrayList<>();
        while (resultSet.next()){
            result.add(buildService(resultSet));
        }
        return result;
    }
}
