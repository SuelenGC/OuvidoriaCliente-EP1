package br.com.suelengc.ouvidoria.client.task.parser;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.suelengc.ouvidoria.client.dao.CategoryDAO;
import br.com.suelengc.ouvidoria.client.model.Category;

public class CategoryParser {
    private Context context;

    public CategoryParser(Context context) {
        this.context = context;
    }

    public List<Category> toCategoryList(String json) {
        List<Category> categories = new ArrayList<Category>();

        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                Category category = new Category();
                JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject("placescategory");

                category.setAbbreviation(jsonObject.getString("abbreviation"));
                category.setName(jsonObject.getString("name"));
                category.setId(jsonObject.getLong("id"));

                Log.i("Ouvidoria", category.getAbbreviation());
                categories.add(category);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return categories;
    }
}
