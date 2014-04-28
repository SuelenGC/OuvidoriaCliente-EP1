package br.com.suelengc.ouvidoria.client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import br.com.suelengc.ouvidoria.client.dao.IncidentDAO;
import br.com.suelengc.ouvidoria.client.model.Incident;
import br.com.suelengc.ouvidoria.client.task.SendIncidentBackgroundTask;
import br.com.suelengc.ouvidoria.client.util.MessageUtil;
import br.com.suelengc.ouvidoria.client.util.NetworkUtil;

public class ConectionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (new NetworkUtil(context).isOnline()) {
            final IncidentDAO dao = new IncidentDAO(context);
            List<Incident> incidents = dao.getAll();

            for (final Incident incident : incidents) {
                new SendIncidentBackgroundTask(new SendIncidentBackgroundTask.SendIncidentBackgroundCallback() {
                    @Override
                    public void onSendIncidentReturn(String response) {
                        dao.delete(incident);
                        new MessageUtil(context).sendUserMessage("Incidente '" + incident.getDescription() +
                                "' enviado com sucesso!");
                    }
                }).execute(incident);
            }
        }
    }
}
