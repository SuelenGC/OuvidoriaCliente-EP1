package br.com.suelengc.ouvidoria.client.task;

import br.com.suelengc.ouvidoria.client.model.User;

public class LoginResponse {

    private boolean status;
    private String error;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
