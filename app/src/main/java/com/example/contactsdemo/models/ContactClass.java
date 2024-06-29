package com.example.contactsdemo.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * @author Phobos
 */
public class ContactClass implements Serializable {
    private int id;
    private String name;
    private String tel;

    public ContactClass(){

    }

    public ContactClass(int id, String name, String tel){
        this.name=name;
        this.id = id;
        this.tel=tel;

    }

    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "ContactClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
