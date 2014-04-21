package br.com.suelengc.ouvidoria.client.task.parser;

import org.json.JSONException;
import org.json.JSONStringer;

import br.com.suelengc.ouvidoria.client.model.Incident;

public class IncidentParser {

    public String toJson(Incident incident) {
        JSONStringer js = new JSONStringer();

        try {
            js.object();
            js.key("incidentrecord").object();
            js.key("user").value(incident.getUser().getUspNumber());
            js.key("description").value(incident.getDescription());
            js.key("localization").value(incident.getCategory().getAbbreviation());

            if (incident.getLocation() != null) {
                js.key("latitude").value(incident.getLocation().getLatitude());
                js.key("longitude").value(incident.getLocation().getLongitude());
            } else {
                js.key("latitude").value(0);
                js.key("longitude").value(0);
            }

            js.key("photo").value(incident.getPhotoBase64());
            js.endObject();
            js.endObject();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return js.toString();
    }
}
