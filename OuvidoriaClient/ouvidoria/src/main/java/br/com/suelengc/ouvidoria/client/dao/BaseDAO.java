package br.com.suelengc.ouvidoria.client.dao;

import android.content.Context;

abstract class BaseDAO {

    protected static String TABLE_NAME;
    protected SQLiteBase database;

    public BaseDAO(Context context) {
        database = SQLiteBase.getInstance(context);
    }
}
