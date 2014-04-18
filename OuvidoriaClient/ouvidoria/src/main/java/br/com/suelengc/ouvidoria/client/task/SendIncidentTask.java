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

    public SendIncidentTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(context);
        //progress.show(context, "Enviando dados", "Aguarde enquanto enviamos o incidente...", false).show();
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
        //progress.dismiss();
        Log.i("Ouvidoria", "Response: " + jsonResponse);
        Toast.makeText(context, "Incidente enviado com sucesso!", Toast.LENGTH_LONG).show();
    }
}
