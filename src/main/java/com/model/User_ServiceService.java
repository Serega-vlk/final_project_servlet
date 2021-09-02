package com.model;

import com.dao.User_ServiceDAO;
import com.dto.Service;
import com.dto.User;

import java.sql.SQLException;

public class User_ServiceService {
    private User_ServiceDAO dao;

    public User_ServiceService(User_ServiceDAO dao){
        this.dao = dao;
    }

    public void addServiceToUser(Service service, User user) throws SQLException {
        dao.addServiceToUser(service, user);
    }

    public void deleteServiceFromUser(Service service, User user) throws SQLException {
        dao.deleteServiceFromUser(service, user);
    }

    public void deleteAllServicesFromUser(User user) throws SQLException {
        dao.deleteAllServicesFromUser(user);
    }

    public void deleteAllUsersFromService(Service service) throws SQLException {
        dao.deleteAllUsersFromService(service);
    }
}
