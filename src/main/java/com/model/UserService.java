package com.model;

import com.dao.UserDAO;
import com.dataBase.DBConnection;
import com.dto.Role;
import com.dto.Service;
import com.dto.User;
import com.exeptions.UserNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserDAO dao;

    public UserService(UserDAO dao){
        this.dao = dao;
    }

    public User getUserByUsername(String username) throws UserNotFoundException, SQLException {
        return dao.findByLogin(username).orElseThrow(UserNotFoundException::new);
    }

    public User getById(int id) throws SQLException, UserNotFoundException {
        return dao.getById(id).orElseThrow(UserNotFoundException::new);
    }

    public User updatePassword(String username, String newPassword) throws UserNotFoundException, SQLException {
        return dao.update(
                getUserByUsername(username)
                .setPassword(newPassword)
        ).orElseThrow(UserNotFoundException::new);
    }

    public boolean isUserIsAdmin(String username) throws UserNotFoundException, SQLException {
        return getUserByUsername(username).getRole().equals(Role.ADMIN);
    }

    public List<User> getAllUsers() throws SQLException {
        return dao.findAll();
    }

    public String getUserPasswordByUsername(String username) throws UserNotFoundException, SQLException {
        return getUserByUsername(username).getPassword();
    }

    public boolean isBlocked(User user){
        return user.getRole().equals(Role.BLOCKED);
    }

    public Optional<User> saveUser(User user) throws SQLException, UserNotFoundException {
        return dao.save(
                user
                .setRole(Role.USER)
                .setMoney(0)
        );
    }

    public Optional<User> blockUserById(int id) throws SQLException, UserNotFoundException {
        return dao.update(
                getById(id)
                .setRole(Role.BLOCKED)
        );
    }

    public Optional<User> unblockUserById(int id) throws SQLException, UserNotFoundException {
        return dao.update(
                getById(id)
                .setRole(Role.USER)
        );
    }

    public void deleteById(int id, User_ServiceService user_serviceService) throws SQLException, UserNotFoundException {
        DBConnection connection = DBConnection.getDBConnection();
        synchronized (DBConnection.class) {
            connection.offAutoCommit();
            connection.commit();
            try {
                User user = getById(id);
                user_serviceService.deleteAllServicesFromUser(user);
                dao.delete(user);
                connection.onAutoCommit();
            } catch (SQLException e){
                connection.rollBack();
                connection.onAutoCommit();
            }
        }
    }

    public boolean hasUsername(String username) throws SQLException {
        return dao.findByLogin(username).isPresent();
    }

    public boolean hasEmail(String email) throws SQLException {
        return dao.findByEmail(email).isPresent();
    }

    public Optional<User> addMoneyByUsername(String username, int money) throws SQLException, UserNotFoundException {
        DBConnection connection = DBConnection.getDBConnection();
        synchronized (DBConnection.class) {
            connection.offAutoCommit();
            connection.commit();
            Optional<User> result = Optional.empty();
            try {
                User user = getUserByUsername(username);
                int userMoney = user.getMoney();
                result = dao.update(user.setMoney(userMoney + money));
                connection.onAutoCommit();
            } catch (SQLException e){
                connection.rollBack();
                connection.onAutoCommit();
            }
            return result;
        }
    }

    public User payForService(User user, Service service) throws SQLException { ;
        return dao.update(user.setMoney(user.getMoney() - service.getPrice())).orElse(null);
    }

    public boolean checkEnoughMoney(User user, Service service){
        return user.getMoney() >= service.getPrice();
    }

    public boolean isCorrectPassword(String username, String password) throws UserNotFoundException, SQLException {
        return getUserByUsername(username).getPassword().equals(password);
    }
}
