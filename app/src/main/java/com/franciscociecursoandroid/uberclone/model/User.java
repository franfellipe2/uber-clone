package com.franciscociecursoandroid.uberclone.model;

import com.google.firebase.database.Exclude;

public class User {

    @Exclude
    public static String TABLENAME = "users";
    String id, name, email, password, type;

    public User() {
    }

    public User(String id, String name, String email, String password, String type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.setType(type);
    }

    public void setType(String type) {
        this.type = UserType.valueOf(type).toString();
    }

    public String getType() {
        return type != null ? type.toString() : null;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
