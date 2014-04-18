package br.com.suelengc.ouvidoria.client.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

import br.com.suelengc.ouvidoria.client.R;
import br.com.suelengc.ouvidoria.client.dao.CategoryDAO;
import br.com.suelengc.ouvidoria.client.location.MyLocationListener;
import br.com.suelengc.ouvidoria.client.model.Category;
import br.com.suelengc.ouvidoria.client.model.Incident;
import br.com.suelengc.ouvidoria.client.model.User;
import br.com.suelengc.ouvidoria.client.preferences.Preferences;
import br.com.suelengc.ouvidoria.client.task.CategoryTask;
import br.com.suelengc.ouvidoria.client.task.SendIncidentTask;
import br.com.suelengc.ouvidoria.client.util.ImageUtil;

public class IncidentActivity extends Activity {
    public static final String USER = "user";

    private Incident incident = new Incident();
    private String photoPath;
    private User user;
    private Location location;
    private MyLocationListener locationListener;

    private ImageView photo;
    private EditText description;
    private AutoCompleteTextView categoryList;
    private Preferences preferences;
    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident);

        preferences = new Preferences(this);

        if (!preferences.getCategoriesLoaded()) {
            new CategoryTask(this, new CategoryTask.CategoryTaskCallback() {
                @Override
                public void onCategoryReturn(List<Category> categories) {
                    loadCategories(categories);
                }
            }).execute();
        }

        startToGetLocation();

        user = (User) getIntent().getSerializableExtra(USER);

        photo = (ImageView) findViewById(R.id.photo);
        description = (EditText) findViewById(R.id.description);
        categoryList = (AutoCompleteTextView) findViewById(R.id.category);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoPath = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";

                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(photoPath)));
                startActivityForResult(camera, 1);
            }
        });

        loadCategories(null);
    }

    private void startToGetLocation() {
        locationListener = new MyLocationListener(this, new MyLocationListener.LocationListenerCallback() {
            @Override
            public void afterGetLocation(Location newLocation) {
                location = newLocation;
            }
        });
    }

    private void loadCategories(List<Category> categories) {

        if (categories == null) {
            CategoryDAO categoryDAO = new CategoryDAO(this);
            categories = categoryDAO.getAll();
        }
        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1, categories);
        categoryList.setAdapter(adapter);

        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
                category = (Category) adapter.getItemAtPosition(pos);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
            bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false);

            incident.setPhotoBase64(ImageUtil.encodeTobase64(bitmap));
            photo.setImageBitmap(bitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_incident, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            incident.setDescription(description.getText().toString());
            incident.setPhotoPath(photoPath);
            incident.setLocation(location);
            incident.setCategory(category);

            incident.setUser(user);

            new SendIncidentTask(this).execute(incident);
        }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationListener.cancelUpdates();
    }
}
