package br.com.suelengc.ouvidoria.client.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.suelengc.ouvidoria.client.model.Category;
import br.com.suelengc.ouvidoria.client.model.Incident;
import br.com.suelengc.ouvidoria.client.model.Local;
import br.com.suelengc.ouvidoria.client.model.User;

public class IncidentDAO extends BaseDAO {

    private static final String TABLE_NAME = "incident";

    public IncidentDAO(Context context) {
        super(context);
    }

    static String getDDL() {
        return "create table " + TABLE_NAME + " (id integer primary key, description text, latitude real, " +
                "longitude real, user_usp_number text, user_usp_name text, category_abbreviation text, " +
                "category_name text, photo_base_64 text, photo_path text)";
    }

    public void save(Incident incident) {
        ContentValues values = toContentValues(incident);
        database.getWritableDatabase().insert(TABLE_NAME, null, values);

        Log.i("Ouvidoria", incident.getDescription() + " inserido no BD.");
    }

    private ContentValues toContentValues(Incident incident) {
        ContentValues values = new ContentValues();
        values.put("description", incident.getDescription());

        if (incident.getLocation() != null) {
            values.put("latitude", incident.getLocation().getLatitude());
            values.put("longitude", incident.getLocation().getLongitude());
        }

        values.put("user_usp_number", incident.getUser().getUspNumber());
        values.put("user_usp_name", incident.getUser().getName());
        values.put("category_abbreviation", incident.getCategory().getAbbreviation());
        values.put("category_name", incident.getCategory().getName());
        values.put("photo_base_64", incident.getPhotoBase64());
        values.put("photo_path", incident.getPhotoPath());
        return values;
    }

    public List<Incident> getAll() {
        List<Incident> incidents = new ArrayList<Incident>();

        Cursor cursor = database.getReadableDatabase().rawQuery("select * from " + TABLE_NAME, null);

        while (cursor.moveToNext()) {
            Incident incident = new Incident();
            incident.setId(cursor.getLong(cursor.getColumnIndex("id")));
            incident.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            incident.setPhotoBase64(cursor.getString(cursor.getColumnIndex("photo_base_64")));
            incident.setPhotoPath(cursor.getString(cursor.getColumnIndex("photo_path")));

            String categoryAbbreviation = cursor.getString(cursor.getColumnIndex("category_abbreviation"));
            String categoryName = cursor.getString(cursor.getColumnIndex("category_name"));
            incident.setCategory(new Category(categoryAbbreviation, categoryName));

            double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
            double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
            incident.setLocation(new Local(latitude, longitude));

            String uspNumber = cursor.getString(cursor.getColumnIndex("user_usp_number"));
            String uspName = cursor.getString(cursor.getColumnIndex("user_usp_name"));
            incident.setUser(new User(uspNumber, uspName));

            incidents.add(incident);
        }

        return incidents;
    }

    public void delete(Incident incident) {
        String[] id = {incident.getId().toString()};
        database.getWritableDatabase().delete(TABLE_NAME, "id=?", id);
    }
}
