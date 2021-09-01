package com.model;

import com.dao.ServiceDAO;
import com.dao.User_ServiceDAO;
import com.dto.Service;
import com.dto.User;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServicesService {
    private final ServiceDAO dao;
    
    public ServicesService(ServiceDAO dao){
        this.dao = dao;
    }
    
    public Service saveService(Service service) throws SQLException {
        return dao.save(service);
    }

    public List<Service> getALlServices() throws SQLException {
        return dao.findAll();
    }

    public Optional<Service> getById(int id) throws SQLException {
        return dao.getById(id);
    }

    public List<Service> getAllSortedByID() throws SQLException {
        return getALlServices()
                .stream()
                .sorted(Comparator.comparingInt(Service::getId))
                .collect(Collectors.toList());
    }

    public List<Service> getAllSortedByName() throws SQLException {
        return getALlServices()
                .stream()
                .sorted(Comparator.comparing(Service::getName))
                .collect(Collectors.toList());
    }

    public List<Service> getAllSortedByPrice() throws SQLException {
        return getALlServices()
                .stream()
                .sorted(Comparator.comparingInt(Service::getPrice))
                .collect(Collectors.toList());
    }

    public List<Service> getAllSorted(String sort) throws SQLException {
        List<Service> services;
        switch (sort){
            case "id":
                services = getAllSortedByID();
                break;
            case "name":
                services = getAllSortedByName();
                break;
            case "price":
                services = getAllSortedByPrice();
                break;
            default:
                services = getALlServices();
        }
        return services;
    }

    public Service getCheapestService() throws SQLException {
        List<Service> services = getALlServices();
        return services.stream()
                .min(Comparator.comparingInt(Service::getPrice))
                .orElseThrow(RuntimeException::new);
    }

    public void deleteById(int id) throws SQLException {
        dao.deleteById(id);
    }
    
    public boolean hasService(Service service) throws SQLException {
        return dao.findByName(service.getName()).isPresent();
    }

    public List<Service> getServicesWithOutUser(List<Service> services, User user) throws SQLException {
        List<Service> userServices = dao.getUserServicesByUserId(user.getId());
        return services
                .stream()
                .filter(accept -> !userServices.contains(accept))
                .collect(Collectors.toList());
    }

    public List<Service> getUserServices(User user) throws SQLException {
        return dao.getUserServicesByUserId(user.getId());
    }
}
