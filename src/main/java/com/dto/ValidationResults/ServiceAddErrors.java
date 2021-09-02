package com.dto.ValidationResults;

public class ServiceAddErrors implements IErrors {
    private boolean nameEmpty;
    private boolean serviceNameExist;
    private boolean priceLessThanZero;

    public static ServiceAddErrors builder(){
        return new ServiceAddErrors();
    }

    public ServiceAddErrors setNameEmpty(boolean nameEmpty) {
        this.nameEmpty = nameEmpty;
        return this;
    }

    public ServiceAddErrors setPriceLessThanZero(boolean priceLessThanZero) {
        this.priceLessThanZero = priceLessThanZero;
        return this;
    }

    public ServiceAddErrors setServiceNameExist(boolean serviceNameExist) {
        this.serviceNameExist = serviceNameExist;
        return this;
    }

    public boolean isNameEmpty() {
        return nameEmpty;
    }

    public boolean isPriceLessThanZero() {
        return priceLessThanZero;
    }

    public boolean isServiceNameExist() {
        return serviceNameExist;
    }

    @Override
    public boolean hasErrors() {
        return nameEmpty ||
                serviceNameExist ||
                priceLessThanZero;
    }
}
