package br.com.suelengc.ouvidoria.client.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import br.com.suelengc.ouvidoria.client.model.Incident;
import br.com.suelengc.ouvidoria.client.task.parser.IncidentParser;
import br.com.suelengc.ouvidoria.client.web.WebClient;

public class SendIncidentTask extends AsyncTask<Object, Incident, String> {
    private static String URL = "http://uspservices.deusanyjunior.dj/incidente";
    private Context context;
    private ProgressDialog progress;
    private SendIncidentCallback callback;

    public SendIncidentTask(Context context, SendIncidentCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public interface SendIncidentCallback {
        public void onSendIncidentReturn(String response);
    }

    @Override
    protected void onPreExecute() {
        progress = ProgressDialog.show(context, "Enviando dados", "Aguarde enquanto enviamos o incidente...", true, true);
    }

    @Override
    protected String doInBackground(Object[] params) {
        Incident incident = (Incident) params[0];
        String json = new IncidentParser().toJson(incident);

        Log.i("Ouvidoria", "Request: " + json);
        String jsonResponse = new WebClient(URL).post(json);
        return jsonResponse;
    }

    @Override
    protected void onPostExecute(String jsonResponse) {
        progress.dismiss();
        callback.onSendIncidentReturn(jsonResponse);
    }
}
