package br.com.suelengc.ouvidoria.client.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.List;

import br.com.suelengc.ouvidoria.client.model.Category;
import br.com.suelengc.ouvidoria.client.model.User;

public class UserDAO extends BaseDAO {

    private static final String TABLE_NAME = "user";
    private List<Category> all;

    public UserDAO(Context context) {
        super(context);
        //database.addDao(this);
    }

    static String getDDL() {
        return "create table " + TABLE_NAME + " (id integer primary key, name text, usp_number text, email text)";
    }

    public void insert(User user) {
        ContentValues values = new ContentValues();
        values.put("email", user.getEmail());
        values.put("name", user.getName());
        values.put("usp_number", user.getUspNumber());

        database.getWritableDatabase().insert(TABLE_NAME, null, values);
        Log.i("Ouvidoria", user.getName() + " inserido no BD.");
    }

    public User getUniqueUser() {
        User user = null;

        Cursor c = database.getWritableDatabase().rawQuery("select * from " + TABLE_NAME + " where id = 1;", null);
        while (c.moveToNext()) {
            user = getUserFrom(c);
        }
        c.close();

        return user;
    }

    private User getUserFrom(Cursor c) {
        User user = new User();

        user.setName(c.getString(c.getColumnIndex("name")));
        user.setEmail(c.getString(c.getColumnIndex("email")));
        user.setUspNumber(c.getString(c.getColumnIndex("usp_number")));

        return user;
    }
}
