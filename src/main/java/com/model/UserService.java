package com.model;

import com.dao.UserDAO;
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
        return dao.save(
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

    public void deleteById(int id) throws SQLException, UserNotFoundException {
        dao.delete(getById(id));
    }

    public boolean hasUsername(String username) throws SQLException {
        return dao.findByLogin(username).isPresent();
    }

    public boolean hasEmail(String email) throws SQLException {
        return dao.findByEmail(email).isPresent();
    }

    public Optional<User> addMoneyByUsername(String username, int money) throws SQLException, UserNotFoundException {
        User user = getUserByUsername(username);
        int userMoney = user.getMoney();
        return dao.update(user.setMoney(userMoney + money));
    }

//    public User addServiceByUsername(String username, Service service) throws UserNotFoundException, SQLException {
//        User user = getUserByUsername(username);
//        payForService(user, service);
//        user.addService(service);
//        return dao.save(user);
//    }

    public int payForService(User user, Service service){
        return
                user
                .setMoney(user.getMoney() - service.getPrice())
                .getMoney();
    }

//    public User deleteServiceByUsername(String username, Service service) throws UserNotFoundException, SQLException {
//        User user = getUserByUsername(username);
//        dao.deleteService(user, service);
//        return dao.save(user);
//    }

    public boolean checkEnoughMoney(User user, Service service){
        return user.getMoney() >= service.getPrice();
    }

    public boolean isCorrectPassword(String username, String password) throws UserNotFoundException, SQLException {
        User user = getUserByUsername(username);
        return user.getPassword().equals(password);
    }
}
