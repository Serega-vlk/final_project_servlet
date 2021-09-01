package com.dto;

import java.util.Objects;

public class Service {
    private int id;
    private String name;
    private int price;

    public Service(){
    }

    public Service(String name, int price){
        this.name = name;
        this.price = price;
    }

    public static Service builder(){
        return new Service();
    }

    public Service setId(int id) {
        this.id = id;
        return this;
    }

    public Service setName(String name) {
        this.name = name;
        return this;
    }

    public Service setPrice(int price) {
        this.price = price;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return id == service.id && price == service.price && name.equals(service.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}