package com.matius.indodess.ui.slideshow;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
class User {

    private String username;
    private String email;
    private String password;
    private float saldo;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String email, String password, float saldo) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.saldo = saldo;
    }
    public User(float saldo){
        this.saldo = saldo;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public float getSaldo() { return saldo; }
}