package com.example.myapplication.Adapter;

public class Usuarios {

    public Usuarios() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String id;
    String name;
    String username;
    String email;
    String phone;
    String website;

    Direccion address;
    Compañia company;


}
