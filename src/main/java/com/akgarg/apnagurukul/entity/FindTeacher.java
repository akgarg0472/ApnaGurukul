package com.akgarg.apnagurukul.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@SuppressWarnings("unused")
@Entity
public class FindTeacher {

    @Id
    private int id;

    private double experience;
    private String name;
    private String email;
    private String subject;
    private String mode;
    private String city;
    private String state;
    private String country;

    public FindTeacher() {
    }

    public FindTeacher(double experience,
                       String name,
                       String email,
                       String subject,
                       String mode,
                       String city,
                       String state,
                       String country) {
        this.experience = experience;
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.mode = mode;
        this.city = city;
        this.state = state;
        this.country = country;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public double getExperience() {
        return experience;
    }


    public void setExperience(double experience) {
        this.experience = experience;
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


    public String getSubject() {
        return subject;
    }


    public void setSubject(String subject) {
        this.subject = subject;
    }


    public String getMode() {
        return mode;
    }


    public void setMode(String mode) {
        this.mode = mode;
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


    @Override
    public String toString() {
        return "FindTeacher{" +
                "id=" + id +
                ", experience=" + experience +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", mode='" + mode + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}