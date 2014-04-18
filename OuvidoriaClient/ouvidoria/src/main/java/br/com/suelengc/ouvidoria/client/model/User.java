package br.com.suelengc.ouvidoria.client.model;

import java.io.Serializable;

public class User implements Serializable {
    private String email;
    private String uspNumber;
    private String userName;

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
        return userName;
    }

    public void setName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return userName + " / " + email + " / " + uspNumber;
    }
}
