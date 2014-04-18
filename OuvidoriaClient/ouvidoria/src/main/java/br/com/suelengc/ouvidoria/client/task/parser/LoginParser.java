package br.com.suelengc.ouvidoria.client.task.parser;

import org.json.JSONObject;

import br.com.suelengc.ouvidoria.client.model.User;
import br.com.suelengc.ouvidoria.client.task.LoginResponse;

public class LoginParser {

    public LoginResponse toLoginResponse(String json) {
        LoginResponse loginResponse = new LoginResponse();

        try {
            JSONObject jsonObject = new JSONObject(json);
            loginResponse.setStatus(jsonObject.getBoolean("ok"));

            if (loginResponse.getStatus()) {
                User user = new User();

                if (jsonObject.has("nusp")) {
                    user.setUspNumber(jsonObject.getString("nusp"));
                    user.setEmail(jsonObject.getString("email"));
                    user.setName(jsonObject.getString("username"));
                }

                loginResponse.setUser(user);

            } else {
                if (jsonObject.has("error")) {
                    loginResponse.setError(jsonObject.getString("error"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return loginResponse;
    }

}
