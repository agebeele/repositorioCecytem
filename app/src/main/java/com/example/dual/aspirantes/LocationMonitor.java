package com.example.dual.aspirantes;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class LocationMonitor implements LocationListener {
    private LocationManager locationManager;
    private Activity_RecorridoGeorreferenciado Activity_RecorridoGeorreferenciado;

    public LocationMonitor(com.example.dual.aspirantes.Activity_RecorridoGeorreferenciado mainActivity) {
        this.Activity_RecorridoGeorreferenciado = mainActivity;
        locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);
    }

    public void startMonitoring() {
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void stopMonitoring() {
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Activity_RecorridoGeorreferenciado.onLocationUpdate(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(Activity_RecorridoGeorreferenciado, "La ubicación está desactivada. Actívala para usar la aplicación.", Toast.LENGTH_SHORT).show();
    }
}
