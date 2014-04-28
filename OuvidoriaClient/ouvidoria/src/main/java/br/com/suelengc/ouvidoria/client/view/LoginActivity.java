package br.com.suelengc.ouvidoria.client.view;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.suelengc.ouvidoria.client.R;
import br.com.suelengc.ouvidoria.client.dao.UserDAO;
import br.com.suelengc.ouvidoria.client.model.User;
import br.com.suelengc.ouvidoria.client.preferences.Preferences;
import br.com.suelengc.ouvidoria.client.task.LoginResponse;
import br.com.suelengc.ouvidoria.client.task.LoginTask;
import br.com.suelengc.ouvidoria.client.util.MessageUtil;
import br.com.suelengc.ouvidoria.client.util.NetworkUtil;


public class LoginActivity extends ActionBarActivity implements LoginTask.LoginCallback {

    public static final String USER = "user";
    private Button btnLogin;
    private EditText uspNumber, password;
    private User user;
    private Preferences preferences;
    private UserDAO dao;
    private MessageUtil messageUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        messageUtil = new MessageUtil(this);

        preferences = new Preferences(this);
        dao = new UserDAO(this);

        if (preferences.getLoggedUser()) {
            user = dao.getUniqueUser();
            openIncident();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        btnLogin = (Button) findViewById(R.id.btn_login);
        uspNumber = (EditText) findViewById(R.id.usp_number);
        password = (EditText) findViewById(R.id.password);

        uspNumber.setText("6109482");
        password.setText("Einstein@1");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new NetworkUtil(LoginActivity.this).isOnline()) {
                    new LoginTask(LoginActivity.this).execute();
                } else {
                    messageUtil.sendUserMessage(getString(R.string.no_connection));
                }
            }
        });
    }

    @Override
    public void onLoginReturn(LoginResponse jsonResult) {
        if (jsonResult.getStatus() == false) {
            String errorMessage = getString(R.string.wrong_user_or_password);
            if (jsonResult.getError() != null && jsonResult.getError() != "") {
                errorMessage += jsonResult.getError();
            }
            messageUtil.sendUserMessage(errorMessage);

        } else {
            messageUtil.sendUserMessage(getString(R.string.login_success));
            preferences.setLoggedUser(true);
            user = jsonResult.getUser();
            dao.insert(user);
            openIncident();
        }
    }

    private void openIncident() {
        Intent incident = new Intent(this, MainActivity.class);
        incident.putExtra(USER, user);
        startActivity(incident);
        finish();
    }
}
