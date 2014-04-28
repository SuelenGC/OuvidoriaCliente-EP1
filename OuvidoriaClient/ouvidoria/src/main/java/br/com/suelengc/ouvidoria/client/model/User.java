package br.com.suelengc.ouvidoria.client.model;

import java.io.Serializable;

public class User implements Serializable {
    private String email;
    private String uspNumber;
    private String name;

    public User() {}

    public User(String uspNumber, String uspName) {
        this.uspNumber = uspNumber;
        this.name = uspName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUspNumber() {
        return uspNumber;
    }

    public void setUspNumber(String uspNumber) {
        this.uspNumber = uspNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String userName) {
        this.name = userName;
    }

    @Override
    public String toString() {
        return name + " / " + email + " / " + uspNumber;
    }
}
