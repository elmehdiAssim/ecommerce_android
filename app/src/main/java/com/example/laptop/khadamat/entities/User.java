package com.example.laptop.khadamat.entities;

import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable {

    private String idUser;
    private String fullName;
    private String email;
    private String username;
    private String password;
    private String userType;
    private String phoneNumber;
    private String city;
    private String address;
    private String scheduleDays;
    private String scheduleHours;


    public String getIdUser() {
        return idUser;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getScheduleDays() {
        return scheduleDays;
    }

    public String getScheduleHours() {
        return scheduleHours;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setScheduleDays(String scheduleDays) {
        this.scheduleDays = scheduleDays;
    }

    public void setScheduleHours(String scheduleHours) {
        this.scheduleHours = scheduleHours;
    }

}
