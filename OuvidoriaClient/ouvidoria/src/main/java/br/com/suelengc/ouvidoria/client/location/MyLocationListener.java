package br.com.suelengc.ouvidoria.client.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class MyLocationListener implements LocationListener {
    private LocationListenerCallback callback;
    private LocationManager manager;

    public interface LocationListenerCallback {
        public void afterGetLocation(Location location);
    }

    public MyLocationListener(Context context, LocationListenerCallback callback) {
        this.callback = callback;
        manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        callback.afterGetLocation(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void cancelUpdates() {
        manager.removeUpdates(this);
    }


}
