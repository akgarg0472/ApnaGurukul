package com.akgarg.apnagurukul.entity;

import com.akgarg.apnagurukul.helper.DateAndTimeMethods;
import com.akgarg.apnagurukul.model.Notification;
import com.akgarg.apnagurukul.model.RecentActivity;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings({"FieldCanBeLocal", "JpaDataSourceORMInspection", "unused"})
@Entity
@Table(name = "users")
public class Users {

    @Id
    @NotNull(message = "Email can't be null")
    @NotBlank(message = "Email can't be empty")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Email format should be correct")
    private String username;

    @NotNull(message = "Password can't be null")
    @NotBlank(message = "Password can't be empty")
    @Size(min = 8, message = "Password should be of min 8 size")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,}$", message = "Password format should be correct")
    private String password;

    @NotBlank(message = "Name can't be empty")
    @NotNull(message = "Name can't be null")
    private String name;

    @NotBlank(message = "Phone can't be empty")
    @NotNull(message = "Phone can't be null")
    @Size(min = 10, max = 10, message = "Phone should be of 10 digit")
    private String phone;

    @NotBlank(message = "Address can't be empty")
    @NotNull(message = "Address can't be null")
    private String address;

    @NotBlank(message = "City can't be empty")
    @NotNull(message = "City can't be null")
    private String city;

    @NotBlank(message = "State can't be empty")
    @NotNull(message = "State can't be null")
    private String state;

    @Column(name = "pincode")
    @NotBlank(message = "Pincode can't be empty")
    @NotNull(message = "Pincode can't be null")
    @Size(min = 6, max = 6, message = "Pincode length should be 6")
    private String pincode;

    @NotBlank(message = "Country can't be empty")
    @NotNull(message = "Country can't be null")
    private String country;

    @ElementCollection
    @CollectionTable(name = "notifications", joinColumns = @JoinColumn(name = "username"))
    @Column(name = "username")
    private List<Notification> notifications;

    @ElementCollection
    @CollectionTable(name = "activities", joinColumns = @JoinColumn(name = "username"))
    @Column(name = "username")
    private List<RecentActivity> activities;

    private String profilePicture;
    private String joinDate;
    private String lastLoginDate;
    private String lastLoginTime;

    @Column(name = "user_role")
    @Value("ROLE_USER")
    private String role;

    @Column(name = "is_account_non_expired")
    private boolean accountNonExpired;

    @Column(name = "is_account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "is_credentials_non_expired")
    private boolean credentialsNonExpired;

    @Column(name = "is_user_enabled")
    private boolean enabled;

    public Users() {
        this.joinDate = DateAndTimeMethods.getCurrentDate();
        this.notifications = new LinkedList<>();
        this.activities = new LinkedList<>();
        this.setAccountNonExpired(true);
        this.setAccountNonLocked(true);
        this.setEnabled(true);
        this.setCredentialsNonExpired(true);
    }


    public Users(String username,
                 String password,
                 String name,
                 String phone,
                 String address,
                 String city,
                 String state,
                 String pincode,
                 String country,
                 String role) {
        this();
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.country = country;
        this.role = role;
    }


    public Users(String username,
                 String password,
                 String name,
                 String phone,
                 String address,
                 String city,
                 String state,
                 String pincode,
                 String country,
                 List<String> subjectsTeach,
                 List<Integer> classesTeach,
                 String profilePicture,
                 String role,
                 boolean accountNonExpired,
                 boolean accountNonLocked,
                 boolean credentialsNonExpired,
                 boolean enabled) {
        this();
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.country = country;
        this.profilePicture = profilePicture;
        this.role = role;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pinCode) {
        this.pincode = pinCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLogin) {
        this.lastLoginDate = lastLogin;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Override
    public String toString() {
        return "Users{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", pincode='" + pincode + '\'' +
                ", country='" + country + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", joinDate='" + joinDate + '\'' +
                ", lastLoginDate='" + lastLoginDate + '\'' +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                ", role='" + role + '\'' +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", enabled=" + enabled +
                '}';
    }
}