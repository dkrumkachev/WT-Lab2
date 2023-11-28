package com.example.movieratingwebapp.beans;

import com.example.movieratingwebapp.enums.UserRole;
import com.example.movieratingwebapp.enums.UserStatus;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {
    private int id;
    private String login;
    private String password;
    private UserRole role;
    private UserStatus status;
    private int rating;

    public User() {
    }

    public User(int id, String login, String password, UserRole role, UserStatus status, int rating) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.status = status;
        this.rating = rating;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(login, user.login) && Objects.equals(password, user.password)
                && role == user.role && status == user.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, role, status);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }

}
