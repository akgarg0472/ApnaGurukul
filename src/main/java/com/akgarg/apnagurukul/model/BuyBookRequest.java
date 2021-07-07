package com.akgarg.apnagurukul.model;

/**
 * Author: Akhilesh Garg
 * GitHub: https://github.com/akgarg0472
 */

@SuppressWarnings("unused")
public class BuyBookRequest {
    private String name;
    private String email;
    private String phone;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private String message;


    public BuyBookRequest(String name,
                          String email,
                          String phone,
                          String city,
                          String state,
                          String country,
                          String pincode,
                          String message) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
        this.message = message;
    }

    public BuyBookRequest() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BuyBookRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", pincode='" + pincode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
