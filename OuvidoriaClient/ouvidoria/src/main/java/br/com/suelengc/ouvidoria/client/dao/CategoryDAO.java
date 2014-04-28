package br.com.suelengc.ouvidoria.client.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.suelengc.ouvidoria.client.model.Category;

public class CategoryDAO extends BaseDAO {
    private static String TABLE_NAME = "category";
    private List<Category> categories;

    public CategoryDAO(Context context) {
        super(context);
    }

    static String getDDL() {
        return "create table " + TABLE_NAME + " (id integer primary key, abbreviation text, name text)";
    }

    public void save(Category category) {
        ContentValues values = toContentValues(category);
        database.getWritableDatabase().insert(TABLE_NAME, null, values);
    }

    public void save(List<Category> categories) {
        for (Category category : categories) {
            save(category);
        }
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<Category>();

        Cursor c = database.getWritableDatabase().rawQuery("select * from " + TABLE_NAME, null);
        while (c.moveToNext()) {
            Category category = toCategory(c);
            categories.add(category);
        }
        c.close();

        return categories;
    }

    private Category toCategory(Cursor c) {
        Category category = new Category();

        category.setAbbreviation(c.getString(c.getColumnIndex("abbreviation")));
        category.setId(c.getLong(c.getColumnIndex("id")));
        category.setName(c.getString(c.getColumnIndex("name")));

        return category;
    }

    private ContentValues toContentValues(Category category) {
        ContentValues values = new ContentValues();
        values.put("abbreviation", category.getAbbreviation());
        values.put("name", category.getName());
        return values;
    }
}
