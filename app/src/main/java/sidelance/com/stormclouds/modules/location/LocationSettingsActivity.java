package sidelance.com.stormclouds.modules.location;

import android.app.ListActivity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sidelance.com.stormclouds.R;
import sidelance.com.stormclouds.modules.location.adapters.LocationListAdapter;
import sidelance.com.stormclouds.modules.current.MainActivity;

public class LocationSettingsActivity extends ListActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = LocationSettingsActivity.class.getSimpleName();

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    /**
     * Google Client to interact with google API
     */
    private GoogleApiClient mGoogleApiClient;

    /**
     * Flag to trigger periodic location updates
     */
    private boolean shouldRequestLocationUpdates = false;

    private LocationRequest mLocationRequest;

    /**
     * User location, used to store user readable location (city, countryCode)
     */
    private String userLocation;


    Double latitude, longitude;
    /**
     * Location updates intervals in seconds
     */
    private static int UPDATE_INTERVAL = 10000; //10 SECS
    private static int FASTEST_INTERVAL = 5000; //5 SECS
    private static int DISPLACEMENT = 10; // 10 METERS

    @Bind(R.id.proceedButton) protected Button proceedButton;
    @Bind(R.id.locationTextView) protected TextView mLocationLabel;
    @Bind(R.id.locationButton) protected Button btnShowLocation;
    @Bind(R.id.addLocationButton) protected  Button addLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_settings);

        ButterKnife.bind(this);

        mLocationLabel.setText("");

        btnShowLocation.setEnabled(true);

        if (checkPlayServices()){
            buildGoogleApiClient();
        }

        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(LocationSettingsActivity.this, "Weather update retrieved from server", Toast.LENGTH_LONG).show();

            }
        });

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocationAndProceed();
            }
        });

        LocationListAdapter adapter = new LocationListAdapter(this);
        setListAdapter(adapter);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //TODO - List item clicks
    }

    private void buttonGetLocationClick() {

    }

    private void getLocationAndProceed() {
        Intent i = new Intent(this, MainActivity.class);
        if (latitude != null && longitude != null) {
            i.putExtra("latitude", latitude);
            i.putExtra("longitude", longitude);

            startActivity(i);
        }else{

            checkPlayServices();

        }

    }

    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (latitude == null && longitude == null){
            mLocationLabel.setText("");
            checkPlayServices();

        } else {

           mLocationLabel.getText();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (latitude == null && longitude == null) {
            mLocationLabel.setText("");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

        mLocationLabel.setText("");

        if (checkPlayServices()) mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection Failed: Error code --> " + connectionResult.getErrorCode());
    }

    /**
     * Creating google api client object
     */
    private synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

    }

    /**
     * Method to display location on UI
     */
    private boolean displayLocation() {

        boolean flag = false;

        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (lastLocation != null){
            latitude = lastLocation.getLatitude();
            longitude = lastLocation.getLongitude();

            userLocation = "Your coordinates: " + latitude + ", " + longitude;

            mLocationLabel.setText(userLocation);
            addLocationButton.setEnabled(false);

            //TODO - Change implementation to support user Friendly location.

            flag = true;
//            Persistence.with(this).save(AppConstants.LATITUDE, Double.doubleToLongBits(latitude));
//            Persistence.with(this).save(AppConstants.LONGITUDE, Double.doubleToLongBits(longitude));
        }

        return flag;
    }


    /**
     * Method to verify google play services on the device
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS){
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil
                        .getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }else{
                Toast.makeText(getApplicationContext(), "This device is not supported.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    @OnClick(R.id.addLocationButton) public void addLocationButtonClick(View view){


    }

    public String getUserLocation() {
        return userLocation;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
