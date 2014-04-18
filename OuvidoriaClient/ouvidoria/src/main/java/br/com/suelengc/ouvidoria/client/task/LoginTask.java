package br.com.suelengc.ouvidoria.client.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import br.com.suelengc.ouvidoria.client.task.parser.LoginParser;
import br.com.suelengc.ouvidoria.client.web.WebClient;

public class LoginTask extends AsyncTask<Objects, Objects, String> {
    private static final String URL_STOA = "https://maxwell.stoa.usp.br/plugin/stoa/authenticate/";
    private LoginCallback callback;
    private Context context;
    private ProgressDialog progress;

    public interface LoginCallback {
        public void onLoginReturn(LoginResponse response);
    }

    public LoginTask(LoginCallback callback) {
        this.callback = callback;
        this.context = (Context) callback;
    }


    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(context);
        progress.show(context, "Verificando usuário", "Login sendo realizado...", false, false).show();
    }

    @Override
    protected String doInBackground(Objects... objectses) {
        WebClient webClient = new WebClient(URL_STOA);

        Map<String, String> params = new HashMap<String, String>();
        params.put("usp_id", "6109482");
        params.put("password", "einstein@1");

        return webClient.postHttps(params);
    }

    @Override
    protected void onPostExecute(String jsonResponse) {
        progress.dismiss();
        LoginResponse loginResponse = new LoginParser().toLoginResponse(jsonResponse);
        callback.onLoginReturn(loginResponse);
    }
}
