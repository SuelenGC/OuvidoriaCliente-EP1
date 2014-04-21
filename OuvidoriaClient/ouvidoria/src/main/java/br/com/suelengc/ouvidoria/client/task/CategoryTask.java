package br.com.suelengc.ouvidoria.client.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import br.com.suelengc.ouvidoria.client.dao.CategoryDAO;
import br.com.suelengc.ouvidoria.client.model.Category;
import br.com.suelengc.ouvidoria.client.preferences.Preferences;
import br.com.suelengc.ouvidoria.client.task.parser.CategoryParser;
import br.com.suelengc.ouvidoria.client.web.WebClient;

public class CategoryTask extends AsyncTask<Void, Void, List<Category>> {

    private final String URL = "http://uspservices.deusanyjunior.dj/categoriaslocal.json";
    private final Context context;
    private CategoryTaskCallback callback;

    public interface CategoryTaskCallback {
        public void onCategoryReturn(List<Category> categories);
    }

    public CategoryTask(Context context, CategoryTaskCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected List<Category> doInBackground(Void... voids) {
        String json = new WebClient(URL).get();

        List<Category> categories = new CategoryParser(context).toCategoryList(json);

        return categories;
    }

    @Override
    protected void onPostExecute(List<Category> categories) {
        new Preferences(context).setCategoriesLoaded(true);
        CategoryDAO dao = new CategoryDAO(context);
        dao.insert(categories);
        callback.onCategoryReturn(categories);
    }
}
