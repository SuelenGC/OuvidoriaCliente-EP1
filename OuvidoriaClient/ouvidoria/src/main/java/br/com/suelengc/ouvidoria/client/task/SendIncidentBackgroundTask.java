package br.com.suelengc.ouvidoria.client.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import br.com.suelengc.ouvidoria.client.model.Incident;
import br.com.suelengc.ouvidoria.client.task.parser.IncidentParser;
import br.com.suelengc.ouvidoria.client.web.WebClient;

public class SendIncidentBackgroundTask extends AsyncTask<Object, Incident, String> {
    private static String URL = "http://uspservices.deusanyjunior.dj/incidente";
    private SendIncidentBackgroundCallback callback;

    public SendIncidentBackgroundTask(SendIncidentBackgroundCallback callback) {
        this.callback = callback;
    }

    public interface SendIncidentBackgroundCallback {
        public void onSendIncidentReturn(String response);
    }

    @Override
    protected String doInBackground(Object[] params) {
        Incident incident = (Incident) params[0];
        String json = new IncidentParser().toJson(incident);

        Log.i("Ouvidoria", "Request: " + json);
        String jsonResponse = new WebClient(URL).post(json);
        Log.i("Ouvidoria", "Request: " + jsonResponse);
        return jsonResponse;
    }

    @Override
    protected void onPostExecute(String jsonResponse) {
        callback.onSendIncidentReturn(jsonResponse);
    }
}
