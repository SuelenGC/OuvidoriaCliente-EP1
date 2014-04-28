package br.com.suelengc.ouvidoria.client.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.suelengc.ouvidoria.client.model.Incident;

class SQLiteBase extends SQLiteOpenHelper {
    private static final String BD_NAME = "OuvidoriaBD";
    private static SQLiteBase instance;

    public static SQLiteBase getInstance(Context context) {
        if (instance == null) {
            instance = new SQLiteBase(context);
        }
        return instance;
    }

    private SQLiteBase(Context context) {
        super(context, BD_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CategoryDAO.getDDL());
        db.execSQL(UserDAO.getDDL());
        db.execSQL(IncidentDAO.getDDL());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
    }
}
