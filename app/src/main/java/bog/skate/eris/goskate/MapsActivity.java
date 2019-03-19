package bog.skate.eris.goskate;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationListener;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) {
                Log.e("MapsActivity", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivity", "Can't find style. Error: ", e);
        }
        LatLng bogota = new LatLng(4.635035, -74.079072);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bogota,11));


        //SanCris

        LatLng sancris = new LatLng(4.572621, -74.082905);
        mMap.addMarker(new MarkerOptions().position(sancris).title("SanCris").icon(BitmapDescriptorFactory.fromResource(R.drawable.skatepark)));

        //Alpes

        LatLng Alpes = new LatLng(4.551314, -74.084214);
        mMap.addMarker(new MarkerOptions().position(Alpes).title("AlpesSkatePark").icon(BitmapDescriptorFactory.fromResource(R.drawable.skatepark)));

        //Tunal

        LatLng Tunal = new LatLng(4.571334, -74.136492);
        mMap.addMarker(new MarkerOptions().position(Tunal).title("TunalSkatePark").icon(BitmapDescriptorFactory.fromResource(R.drawable.skatepark)));

        //Palermo

        LatLng Palermo = new LatLng(4.541783, -74.110053);
        mMap.addMarker(new MarkerOptions().position(Palermo).title("PalermoSurf").icon(BitmapDescriptorFactory.fromResource(R.drawable.skatepark)));

        //LaFrancia

        LatLng Francia = new LatLng(4.625043, -74.108268);
        mMap.addMarker(new MarkerOptions().position(Francia).title("LaFranciaSkatePark").icon(BitmapDescriptorFactory.fromResource(R.drawable.skatepark)));

        //Guavio

        LatLng Guavio = new LatLng(4.589332, -74.071510);
        mMap.addMarker(new MarkerOptions().position(Guavio).title("Guavio").icon(BitmapDescriptorFactory.fromResource(R.drawable.skatepark)));


        //Villasdegranada

        LatLng Villas = new LatLng(4.715945, -74.122811);
        mMap.addMarker(new MarkerOptions().position(Villas).title("Villas").icon(BitmapDescriptorFactory.fromResource(R.drawable.skatepark)));


        //PISOWHITE

        //japon

        LatLng Japon = new LatLng(4.620252, -74.154743);
        mMap.addMarker(new MarkerOptions().position(Japon).title("Japon").icon(BitmapDescriptorFactory.fromResource(R.drawable.street)));

        //Ricaurte

        LatLng Ricaurte = new LatLng(4.613343, -74.154743);
        mMap.addMarker(new MarkerOptions().position(Ricaurte).title("Ricaurte").icon(BitmapDescriptorFactory.fromResource(R.drawable.street)));

        //Vertical

        LatLng Chorro = new LatLng(4.597288, -74.068543);
        mMap.addMarker(new MarkerOptions().position(Chorro).title("Chorro").icon(BitmapDescriptorFactory.fromResource(R.drawable.vertical)));


        //Cedritos

        LatLng Cedritos = new LatLng(4.723793, -74.033032);
        mMap.addMarker(new MarkerOptions().position(Cedritos).title("Cedritos").icon(BitmapDescriptorFactory.fromResource(R.drawable.vertical)));


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}



