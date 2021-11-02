package com.akgarg.apnagurukul.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@SuppressWarnings("unused")
public class FindStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id = -1;

    private String userEmail;
    private String name;
    private String email;
    private String currentClass;
    private String subjects;
    private String mode;
    private String village;
    private String city;
    private String state;
    private String country;
    private String pincode;

    public FindStudent() {
    }

    public FindStudent(String userEmail,
                       String name,
                       String email,
                       String currentClass,
                       String subjects,
                       String mode,
                       String village,
                       String city,
                       String state,
                       String country,
                       String pincode) {
        this.userEmail = userEmail;
        this.name = name;
        this.email = email;
        this.currentClass = currentClass;
        this.subjects = subjects;
        this.mode = mode;
        this.village = village;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(String currentClass) {
        this.currentClass = currentClass;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        return "FindStudent{" +
                "id=" + id +
                ", userEmail='" + userEmail + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", currentClass='" + currentClass + '\'' +
                ", subjects='" + subjects + '\'' +
                ", mode='" + mode + '\'' +
                ", village='" + village + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", pincode='" + pincode + '\'' +
                '}';
    }
}
