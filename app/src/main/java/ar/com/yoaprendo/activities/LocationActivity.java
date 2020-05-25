package ar.com.yoaprendo.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import ar.com.yoaprendo.LoadingDialog;
import ar.com.yoaprendo.R;
import ar.com.yoaprendo.Utils;
import ar.com.yoaprendo.beans.Ubicacion;
import ar.com.yoaprendo.placepicker.TransparentProgressDialog;

public class LocationActivity extends AppCompatActivity {

    private final static int LOCATION_REQUEST_CODE = 23;
    private String uuid;
    FusedLocationProviderClient mFusedLocationClient;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ubicacion);

        uuid = getIntent().getStringExtra("UUID");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        loadingDialog = new LoadingDialog(this);

        Button btnElegirActual = (Button) findViewById(R.id.btnElegirActual);
        Button btnElegirMapa = (Button) findViewById(R.id.btnElegirMapa);
        Button btnOmitir = (Button) findViewById(R.id.ubicacion_btnOmitir);

        btnElegirActual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( ContextCompat.checkSelfPermission( LocationActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION )
                        != PackageManager.PERMISSION_GRANTED ) {

                    ActivityCompat.requestPermissions( LocationActivity.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                            LOCATION_REQUEST_CODE );

                }else{

                    seleccionarUbicacionActual();

                }

            }
        });

        btnElegirMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LocationActivity.this,PlacePickerActivity.class).putExtra("UUID",uuid));
            }
        });

        btnOmitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LocationActivity.this, MenuActivity.class).putExtra("UUID",uuid));
            }
        });

    }

    private boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    seleccionarUbicacionActual();

                } else {
                    Toast.makeText(LocationActivity.this,"Debe aceptar los permisos para elegir su ubicación actual", Toast.LENGTH_SHORT).show();
                }

                return;

            }
        }
    }

    public void seleccionarUbicacionActual(){

        if(!isLocationEnabled()){Toast.makeText(LocationActivity.this,"Asegúrese de activar la geolocalización", Toast.LENGTH_SHORT).show();return;}

        getLastLocation();

    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(){

        loadingDialog.startLoadingDialog();

                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {

                                Location location = task.getResult();

                                if (location == null) {

                                    requestNewLocationData();

                                } else {

                                    loadingDialog.dismissDialog();

                                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                                    db.getReference().child("users").child(uuid).child("ubicacion").setValue(new Ubicacion(location.getLatitude(),location.getLongitude()));
                                    startActivity(new Intent(LocationActivity.this, MenuActivity.class).putExtra("UUID",uuid));

                                }

                            }
                        });

    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {

            Location mLastLocation = locationResult.getLastLocation();

            loadingDialog.dismissDialog();

            FirebaseDatabase db = FirebaseDatabase.getInstance();

            db.getReference().child("users").child(uuid).child("ubicacion").setValue(new Ubicacion(mLastLocation.getLatitude(),mLastLocation.getLongitude()));

            startActivity(new Intent(LocationActivity.this, MenuActivity.class).putExtra("UUID",uuid));

        }

    };

}
