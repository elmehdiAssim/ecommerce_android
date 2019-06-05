package com.example.laptop.khadamat.entities;

import java.io.Serializable;

public class Service implements Serializable {

    private String idService;
    private String serviceName;
    private String experience;
    private String rate;
    private User user;

    public String getIdService() {
        return idService;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getExperience() {
        return experience;
    }

    public String getRate() {
        return rate;
    }

    public User getUser() {
        return user;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
