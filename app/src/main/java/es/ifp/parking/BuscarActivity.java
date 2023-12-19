package es.ifp.parking;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BuscarActivity extends AppCompatActivity {

    private MapView mapView;
    private IMapController mapController;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private MyLocationNewOverlay myLocationOverlay;
    private Button botonVolver;
    private Button botonInicio;
    private String contenidoFecha = "";
    private String contenidoHora = "";
    private Double contenidoLatitud = null;
    private Double contenidoLongitud = null;
    private String contenidoDetalles = "";
    private BaseDatosVentas bdv;
    protected BaseDatosUsuario bd;
    protected BaseDatosReservas bdr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Configuración del mapa
        Configuration.getInstance().load(this, getPreferences(MODE_PRIVATE));
        setContentView(R.layout.activity_buscar);

        bdv = new BaseDatosVentas(this);
        bdr = new BaseDatosReservas(this);
        bd = new BaseDatosUsuario(this);
        botonVolver = findViewById(R.id.botonVolver_BuscarActivity);
        botonInicio = findViewById(R.id.botonInicio_BuscarActivity);
        mapView = findViewById(R.id.mapView_BuscarActivity);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        mapController = mapView.getController();
        mapController.setZoom(15.0);
        // Verificación de permisos y configuración del LocationManager
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);

        } else {
            // Configuración del LocationManager y mostrar plazas en venta en el mapa
            setupLocationManager();
            mostrarPlazasEnVentaMapa();
        }
        // Configuración de botones
        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pasarPantalla = new Intent(BuscarActivity.this, MenuUsuarioActivity.class);
                finish();
                startActivity(pasarPantalla);


            }
        });
        botonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pasarPantalla = new Intent(BuscarActivity.this, InicioSesionActivity.class);
                finish();
                startActivity(pasarPantalla);

            }
        });
    }
    // Configuración del LocationManager

    private void setupLocationManager() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                updateLocationMarker(new GeoPoint(location.getLatitude(), location.getLongitude()));
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

        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastKnownLocation != null) {
            GeoPoint startPoint = new GeoPoint(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            updateLocationMarker(startPoint);
        }

        myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), mapView);
        mapView.getOverlays().add(myLocationOverlay);
    }
    // Actualización del marcador de ubicación
    private void updateLocationMarker(GeoPoint newGeoPoint) {
        if (mapView != null) {
            mapController.animateTo(newGeoPoint);
        }
    }
    // Mostrar plazas en venta en el mapa
    private void mostrarPlazasEnVentaMapa() {
        List<UnaVenta> ventas = bdv.obtenerTodasLasVentas();

        if (myLocationOverlay != null && myLocationOverlay.getMyLocation() != null) {
            GeoPoint miUbicacion = myLocationOverlay.getMyLocation();
            Marker marcadorUsuario = new Marker(mapView);
            marcadorUsuario.setPosition(miUbicacion);
            marcadorUsuario.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

            mapView.getOverlays().add(marcadorUsuario);
            ;
        }

        Calendar calendarioActual = Calendar.getInstance();

        for (UnaVenta venta : ventas) {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

            try {
                String fechaHoraV= venta.getFecha()+" "+venta.getHora();
                Date fechaHoraVenta = dateTimeFormat.parse(fechaHoraV);

                if(fechaHoraVenta.before(calendarioActual.getTime())){
                    eliminarMarcadorReservado(venta.getLatitud(),venta.getLongitud());
                }else{
                    GeoPoint puntoVenta = new GeoPoint(venta.getLatitud(), venta.getLongitud());
                    Marker marcadorV = new Marker(mapView);
                    marcadorV.setPosition(puntoVenta);
                    marcadorV.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    String direccion = obtenerDireccion(venta.getLatitud(), venta.getLongitud());

                    mapView.getOverlays().add(marcadorV);

                    marcadorV.setOnMarkerClickListener((marker, mapView1) -> {
                        contenidoFecha = venta.getFecha();
                        contenidoHora = venta.getHora();
                        contenidoLatitud = venta.getLatitud();
                        contenidoLongitud = venta.getLongitud();
                        contenidoDetalles = venta.getDetalles();
                        obtenerDireccion(venta.getLatitud(), venta.getLongitud());
                        mostrarDialogoConfirmacion();
                        return true;

                    });

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
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
   //Traduce la latitud y longitud en Direccion
    private String obtenerDireccion(double latitud, double longitud) {
        String direccionString = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> direcciones = geocoder.getFromLocation(latitud, longitud, 1);
            if (direcciones != null && direcciones.size() > 0) {
                Address direccion = direcciones.get(0);
                StringBuilder direccionBuilder = new StringBuilder();

                for (int i = 0; i <= direccion.getMaxAddressLineIndex(); i++) {
                    direccionBuilder.append(direccion.getAddressLine(i)).append(" ");
                }
                direccionString = direccionBuilder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return direccionString;
    }
    //Dialogo confirmacion reserva
    private void mostrarDialogoConfirmacion() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Quiere hacer una reserva?\n"
                + "Direccion: " + obtenerDireccion(contenidoLatitud, contenidoLongitud)
                + "\nFecha y Hora: " + contenidoFecha + " " + contenidoHora
                + "\nDetalles: " + contenidoDetalles);
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences preferences = getSharedPreferences("usuario_info", Context.MODE_PRIVATE);
                String email = preferences.getString("email", "");
                String password = preferences.getString("password", "");
                bdr.insertReserva(bd.obtenerIdUsuario(email, password), contenidoFecha, contenidoHora, contenidoLatitud, contenidoLongitud, contenidoDetalles);
                Toast toast = Toast.makeText(BuscarActivity.this, "Tu plaza ha sido reservada con éxito." +
                        "Puede ver los detalles en Mis Reservas.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                eliminarMarcadorReservado(contenidoLatitud, contenidoLongitud);

                Intent pasarPantalla = new Intent(BuscarActivity.this, MenuUsuarioActivity.class);
                finish();
                startActivity(pasarPantalla);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    //Eliminar marcador de punto seleccionado para reserva
    private void eliminarMarcadorReservado(double latitud, double longitud) {
        if (mapView != null) {
            for (Overlay overlay : mapView.getOverlays()) {
                if (overlay instanceof Marker) {
                    Marker marker = (Marker) overlay;
                    GeoPoint geoPoint = marker.getPosition();
                    if (geoPoint.getLatitude() == latitud && geoPoint.getLongitude() == longitud) {
                        mapView.getOverlays().remove(marker);
                        bdv.deleteVenta(bdv.obtenerIdVenta(contenidoLatitud, contenidoLongitud));
                        mapView.invalidate();
                        break;
                    }
                }
            }
        }
    }
}