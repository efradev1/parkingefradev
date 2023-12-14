package es.ifp.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class VenderActivity extends AppCompatActivity {

    private MapView mapView;
    private IMapController mapController;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private MyLocationNewOverlay myLocationOverlay;
    private Button botonVolver;
    private Button botonSiguiente;

    private static final String latitud_key="latitud";
    private static final String longitud_key="longitud";
    private static final String PREFS_NAME = "Mi_Venta";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender);

        botonVolver=findViewById(R.id.botonVolver_VenderActivity);
        botonSiguiente=findViewById(R.id.botonSiguiente_VenderActivity);
        mapView=findViewById(R.id.mapView_VenderActivity);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        mapController = mapView.getController();
        mapController.setZoom(15.0);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);

        } else {
            actualizarLocalizacionManager();
        }
        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pasarPantalla = new Intent(VenderActivity.this, MenuUsuarioActivity.class);
                finish();
                startActivity(pasarPantalla);


            }
        });
        botonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mostrarDialogoConfirmacion();

            }
        });

    }
    private void actualizarLocalizacionManager() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                updateLocationMarker(new GeoPoint(location.getLatitude(), location.getLongitude()));
                GeoPoint newGeoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                updateLocationMarker(newGeoPoint);
                guardarUbicacion(newGeoPoint.getLatitude(), newGeoPoint.getLongitude());

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
        };

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);

        Location ultimaLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (ultimaLocation != null) {
            GeoPoint startPoint = new GeoPoint(ultimaLocation.getLatitude(), ultimaLocation.getLongitude());
            updateLocationMarker(startPoint);
        }

        myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), mapView);
        mapView.getOverlays().add(myLocationOverlay);
    }

    private void updateLocationMarker(GeoPoint newGeoPoint) {
        if (mapView != null) {
            mapController.animateTo(newGeoPoint);
        }
    }
    private void guardarUbicacion(double latitud, double longitud){
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(latitud_key, (float) latitud);
        editor.putFloat(longitud_key, (float) longitud);
        editor.apply();
    }
    private GeoPoint obtenerUbicacion(){
        SharedPreferences prefs= getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        double latitud = prefs.getFloat(latitud_key, 0);
        double longitud = prefs.getFloat(longitud_key, 0);
        return new GeoPoint(latitud, longitud);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();

        if (locationManager != null && locationListener != null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
        }

        if (myLocationOverlay != null) {
            myLocationOverlay.enableMyLocation();
            myLocationOverlay.enableFollowLocation();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();

        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }

        if (myLocationOverlay != null) {
            myLocationOverlay.disableMyLocation();
            myLocationOverlay.disableFollowLocation();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDetach();

        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }

        if (myLocationOverlay != null) {
            myLocationOverlay.disableMyLocation();
            myLocationOverlay.disableFollowLocation();
        }
    }
    private void mostrarDialogoConfirmacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Está seguro de que esta es la ubicación?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                GeoPoint ultimaUbicacion = obtenerUbicacion();

                Intent siguienteActividadIntent = new Intent(VenderActivity.this, DetallesVentaActivity.class);
                siguienteActividadIntent.putExtra("latitud", ultimaUbicacion.getLatitude());
                siguienteActividadIntent.putExtra("longitud", ultimaUbicacion.getLongitude());
                startActivity(siguienteActividadIntent);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // No hacer nada, simplemente cerrar el diálogo
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}