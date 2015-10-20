package sidelance.com.stormclouds;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import sidelance.com.stormclouds.modules.current.MainActivity;
import sidelance.com.stormclouds.utilities.Persistence;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = SettingsActivity.class.getSimpleName();

    private LocationManager locationManager;

    private String city, country, provider;
    private Double x, y;
    private boolean clicked;
    private String userLocation = "";

    @Bind(R.id.locationTextView) protected TextView mUserLocationLabel;
    @Bind(R.id.imageView) protected ImageView gpsImageView;
    @Bind(R.id.backButton) protected Button backButton;
    @Bind(R.id.locationProgressBar) protected ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);

        mProgressBar.setVisibility(View.INVISIBLE);
        clicked = false;

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        gpsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked = true;
                getUserLocation();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousScreen();
            }
        });

    }

    private void showPreviousScreen() {
        if(x != null && y != null) {
            sendUserXYCoordinates();

        }else{

            Toast.makeText(this, R.string.device_location_unknown, Toast.LENGTH_LONG).show();
        }
    }

    private void sendUserXYCoordinates() {
        Intent sendIntent = new Intent(this, MainActivity.class);
        sendIntent.putExtra("latitude", getX());
        sendIntent.putExtra("longitude", getY());
        sendIntent.putExtra("city", getCity());
        sendIntent.putExtra("country", getCountry());
        startActivityForResult(sendIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1){

            if (resultCode == RESULT_OK){


            }
        }
    }

    private void getUserLocation() {

        try {

            MyLocationListener listener = new MyLocationListener();

            boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!gpsEnabled && !networkEnabled){

                Log.w(TAG, "--> Network and GPS disabled");

                checkNetworkSettings();

            }

            if (gpsEnabled){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
//                toggleRefresh();
            }
            if (networkEnabled){
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
//                toggleRefresh();
            }
        } catch (Throwable e) {
            Log.e(TAG, "Location Monitor: " + e.getMessage());
        }
    }


    private void toggleRefresh() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressBar.getVisibility() == View.INVISIBLE) {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                else{
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    private class MyLocationListener implements LocationListener {

        @SuppressWarnings("static-access")
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        public void onLocationChanged(Location location) {

            x = location.getLatitude();
            y = location.getLongitude();


            try {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.ENGLISH);
                List<Address> addressList = geocoder.getFromLocation(x, y, 1);
                StringBuilder stringBuilder = new StringBuilder();
                if(geocoder.isPresent()){

                    Log.i(TAG, "==> Geocoder Detected <==");

                    Log.e(TAG, "latitude: "+ x + "longitude: " + y);
                    Address returnAddress = addressList.get(0);

                    city = returnAddress.getLocality();
                    country = returnAddress.getCountryName();


                    /*String address = returnAddress.getAddressLine(0);
                    stringBuilder.append(address).append(", ");
                    */

                    stringBuilder.append(city).append(", ");
                    stringBuilder.append(country);
                    userLocation = stringBuilder.toString();
                    toggleRefresh();

                    mUserLocationLabel.setText(userLocation);

                    //Save Location to persistence
                    if ((x != null)&&(y != null)) {
                        Persistence.with(getApplicationContext()).save("UserLocationString", userLocation);
                        Persistence.with(getApplicationContext()).save("Latitude", Double.doubleToLongBits(x));
                        Persistence.with(getApplicationContext()).save("Longitude", Double.doubleToLongBits(y));
                    }
                }else{
                    Log.e(TAG, "==> Geocoder Not Detected <==");
                }

            }catch(IOException e){
                Log.e(TAG, "" + e.getMessage());
            }
        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }


        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }


    public void checkNetworkSettings() {

        new AlertDialog.Builder(this)
                .setTitle("Location Service")
                .setMessage("Network and GPS turned off, change settings?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }


                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        }).show();

    }

    public double getX() {
        Log.i(TAG, ": " + x);
        return x;
    }

    public double getY() {

        Log.i(TAG, ": " + y);
        return y;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }


}

