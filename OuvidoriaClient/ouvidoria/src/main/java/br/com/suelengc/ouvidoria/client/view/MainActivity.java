package br.com.suelengc.ouvidoria.client.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import br.com.suelengc.ouvidoria.client.R;
import br.com.suelengc.ouvidoria.client.model.User;
import br.com.suelengc.ouvidoria.client.preferences.Preferences;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (User) getIntent().getSerializableExtra(NewIncidentFragment.USER);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        mNavigationDrawerFragment.setUserName("Número USP: " + user.getUspNumber());
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment fragment = null;
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();

        switch (position) {
            case 0:
                fragment = new NewIncidentFragment();

                Bundle args = new Bundle();
                args.putSerializable(NewIncidentFragment.USER, user);
                fragment.setArguments(args);

                tx.replace(R.id.container, fragment).commit();

                break;
            case 1:
                fragment = new ListIncidentFragment();
                tx.replace(R.id.container, fragment).commit();

                break;
            case 2:
                new Preferences(this).setLoggedUser(false);
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                finish();

                break;
        }
    }

    protected boolean isDrawerOpen() {
        return mNavigationDrawerFragment.isDrawerOpen();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
