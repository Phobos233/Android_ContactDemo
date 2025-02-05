package com.example.contactsdemo.models;

import androidx.annotation.NonNull;

/**
 * @author Phobos
 */
public class Users {
    private int id;
    private String username;
    private String password;
    public Users(){

    }
    public Users(int id,String username,String password){
        this.id=id;
        this.username=username;
        this.password=password;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
