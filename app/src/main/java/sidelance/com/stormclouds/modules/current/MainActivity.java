package sidelance.com.stormclouds.modules.current;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sidelance.com.stormclouds.utilities.AppConstants;
import sidelance.com.stormclouds.modules.hourly.HourlyForecastActivity;
import sidelance.com.stormclouds.modules.location.LocationSettingsActivity;
import sidelance.com.stormclouds.R;
import sidelance.com.stormclouds.modules.weekly.WeeklyForecastActivity;
import sidelance.com.stormclouds.model.Current;
import sidelance.com.stormclouds.model.Forecast;
import sidelance.com.stormclouds.model.Hourly;
import sidelance.com.stormclouds.model.Weekly;
import sidelance.com.stormclouds.ui.AlertDialogFragment;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String WEEKLY_FORECAST = "weeklyforecast";
    public static final String WEEKLY_ICON = "WEEKLYICON";
    public static final String HOURLY_FORECAST = "hourlyforecast";
    private Forecast mForecast;
    private String weeklyIcon;
    private String dailyIcon;
    private boolean isActivityStarted;

    @Bind(R.id.rootLayout) protected RelativeLayout rootLayout;
    @Bind(R.id.time_label) protected TextView mTimeLabel;
    @Bind(R.id.summaryLabel) protected TextView mSummaryLabel;
    @Bind(R.id.temperature_label) protected TextView mTempLabel;
    @Bind(R.id.degreeImageView) protected  ImageView mDegreeImage;
    @Bind(R.id.humidityValue) protected  TextView mHumidity;
    @Bind(R.id.rainValue) protected TextView mRainValue;
    @Bind(R.id.icon_imageView) protected ImageView mIconImageView;
    @Bind(R.id.location_label) protected TextView mLocationLabel;
    @Bind(R.id.refreshImageView) protected ImageView mRefreshImageView;
    @Bind(R.id.progressBar) protected ProgressBar mProgressBar;
    @Bind(R.id.settingsImageView) protected ImageView appSettings;

    public Double latitude;
    private Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        isActivityStarted = false;
        mProgressBar.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        latitude = intent.getExtras().getDouble("latitude");
        longitude = intent.getExtras().getDouble("longitude");

        Log.e(TAG, "lat: " + latitude + "long: " + longitude);


        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isActivityStarted = false;
                getForecast(latitude, longitude);
                animateViews();
            }
        });


        getForecast(latitude, longitude);
        isActivityStarted = true;

//        viewAnimations();
        Log.d(TAG, "Main UI code running");
    }

    private void settingsClicked() {

        Intent intent = new Intent(getApplicationContext(), LocationSettingsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();

        if (isNetworkAvailable()) {

            if (latitude != null && longitude != null) {

                getForecast(latitude, longitude);

            }

        } else {

            /*String toastMessage = getString(R.string.location_unknown) + getResources().getDrawable(R.drawable.settings, null);
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();*/
            Log.d(TAG, "Network disconnected");
        }

        viewAnimations();

    }

    private void viewAnimations() {


        mDegreeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Bounce).duration(1000).playOn(mDegreeImage);
            }
        });

        mTempLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.DropOut).duration(2000).playOn(mHumidity);
                YoYo.with(Techniques.DropOut).duration(2000).playOn(mRainValue);
                YoYo.with(Techniques.DropOut).duration(3000).playOn(mTempLabel);



            }
        });

        mIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.FlipInX).duration(2000).playOn(mIconImageView);
            }
        });
    }

    private void getForecast(double latitude, double longitude) {


        String url = AppConstants.URL + latitude + ","+ longitude;

        if (isNetworkAvailable()) {
            toggleRefresh();

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url).build();

            Log.i(TAG, "URL: " + request);

            Call call = client.newCall(request);

            Log.i(TAG, "Client Request: " + call);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    String jsonData = response.body().string();
                    Log.v(TAG, ": " + jsonData);
                    try {
                        if (response.isSuccessful()) {
                            mForecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();


                                }
                            });


                        } else {
                            alertUserAboutError();
                        }
                    } catch (JSONException t) {
                        Log.e(TAG, "Exception Caught: ", t);
                    }
                }
            });

        }else {
            checkNetworkSettings();
        }

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void updateDisplay() {
        Current current = mForecast.getCurrent();

        mTempLabel.setText(current.getTemperature()+ "");
        mTimeLabel.setText("At " + current.getFormattedTime() + " it will be");
        mHumidity.setText(current.getHumidity() + "");
        mRainValue.setText(current.getPrecipChance() + "%");
        mSummaryLabel.setText(current.getSummary());
        mLocationLabel.setText(current.getTimeZone());

        Drawable drawable = getResources().getDrawable(mForecast.getCurrent().getIconId());
        mIconImageView.setImageDrawable(drawable);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleRefresh();
                rootLayout.setBackground(setLayoutBackground());
                YoYo.with(Techniques.FadeIn).duration(1000).playOn(rootLayout);
                if (isActivityStarted) {
                    YoYo.with(Techniques.DropOut).duration(2000).playOn(mHumidity);
                    YoYo.with(Techniques.DropOut).duration(2000).playOn(mRainValue);
                    YoYo.with(Techniques.DropOut).duration(3000).playOn(mTempLabel);
                    YoYo.with(Techniques.FlipInX).duration(2000).playOn(mIconImageView);

                    isActivityStarted = false;
                }
            }
        });


//        MyViewAnimation.shortFadeIn(mIconImageView);


    }

    private void animateViews() {
        YoYo.with(Techniques.BounceIn).duration(2000).playOn(rootLayout);
        YoYo.with(Techniques.Bounce).duration(5000).playOn(mDegreeImage);
        YoYo.with(Techniques.SlideInLeft).duration(5000).playOn(mIconImageView);
        YoYo.with(Techniques.Bounce).duration(5000).playOn(mIconImageView);
//        YoYo.with(Techniques.BounceIn).duration(5000).playOn(mIconImageView);
        YoYo.with(Techniques.SlideInLeft).duration(6000).playOn(mRefreshImageView);
        YoYo.with(Techniques.SlideInLeft).duration(6000).playOn(appSettings);
    }

    public void checkNetworkSettings() {

        new AlertDialog.Builder(this)
       .setTitle("Storm Clouds")
        .setMessage("Network and Wifi turned off, please change settings to Proceed.")
        .setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                getUserLocation();
            }


        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }
        }).show();


/*
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
*/
    }

    private void getUserLocation() {

        if(!isNetworkAvailable()) {
            Intent intent = new Intent(getApplicationContext(), LocationSettingsActivity.class);
            startActivity(intent);
        }
    }

    private void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        }
        else{
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }





    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public Drawable setLayoutBackground() {
        String iconName = mForecast.getCurrent().getIcon();
        if (!TextUtils.isEmpty(iconName)) {
            //clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night
            Drawable gradientDrawable = null;
            switch (iconName) {
                case "clear-day":
                    gradientDrawable = getResources().getDrawable(R.drawable.clear_day_bg_gradient);
                    break;

                case "clear-night":
                    gradientDrawable = getResources().getDrawable(R.drawable.clear_night_bg_gradient);
                    break;

                case "partly-cloudy-night":
                    gradientDrawable = getResources().getDrawable(R.drawable.cloudy_night_bg_gradient);
                    break;

                case "partly-cloudy-day":
                    gradientDrawable = getResources().getDrawable(R.drawable.partly_cloudy_day_bg_gradient);
                    break;

                case "cloudy":
                    gradientDrawable = getResources().getDrawable(R.drawable.cloudy_bg_gradient);
                    break;

                case "rain":
                    gradientDrawable = getResources().getDrawable(R.drawable.light_rain_bg_gradient);
                    break;

            }

            toggleRefresh();

            return gradientDrawable;
        }
        return null;
    }

    private Forecast parseForecastDetails(String jsonData)throws JSONException {
        Forecast forecast = new Forecast();

        forecast.setCurrent(getCurrentDetails(jsonData));
        forecast.setHourly(getHourlyForecast(jsonData));
        forecast.setWeekly(getWeeklyForecast(jsonData));

        return forecast;
    }

    private Current getCurrentDetails(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        String timeZone = forecast.getString("timezone");
        Log.i(TAG, "From Json: " + timeZone);


        JSONObject currently = forecast.getJSONObject("currently");

        Current current = new Current();
        current.setHumidity(currently.getDouble("humidity"));
        current.setTime(currently.getLong("time"));
        current.setPrecipChance(currently.getDouble("precipProbability"));
        current.setSummary(currently.getString("summary"));
        current.setTemperature(currently.getDouble("temperature"));
        current.setIcon(currently.getString("icon"));
        current.setTimeZone(timeZone);

//        Log.e(TAG, ": " + current);
        return current;
    }

    private Hourly[] getHourlyForecast(String jsonData) throws JSONException{

        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");
        int dataLength = data.length();
        Hourly[] hours = null;
        if (dataLength > 0) {
            hours = new Hourly[dataLength];

            for (int i = 0; i < dataLength; i++) {
                JSONObject jsonHour = data.getJSONObject(i);
                Hourly hour = new Hourly();

                hour.setTimeZone(timezone);
                hour.setIcon(jsonHour.getString("icon"));
                hour.setSummary(jsonHour.getString("summary"));
                hour.setTime(jsonHour.getLong("time"));
                hour.setTemperature(jsonHour.getDouble("temperature"));

                hours[i] = hour;

            }
        }

        return hours;

    }

    private Weekly[] getWeeklyForecast(String jsonData) throws JSONException{

        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject dailyObject = forecast.getJSONObject("daily");
        String weeklySummary = dailyObject.getString("summary");
        weeklyIcon = dailyObject.getString("icon");
        JSONArray data = dailyObject.getJSONArray("data");
        int dataLength = data.length();
        Weekly[] mWeekly = new Weekly[dataLength];

        for (int i = 0; i < dataLength; i++){

            JSONObject jsonWeek = data.getJSONObject(i);
            Weekly weekly = new Weekly();

            weekly.setTimeZone(timezone);
            weekly.setWeeklySummary(weeklySummary);
            weekly.setTime(jsonWeek.getLong("time"));
            weekly.setIcon(jsonWeek.getString("icon"));
            weekly.setDailySummary(jsonWeek.getString("summary"));
            weekly.setHumidity(jsonWeek.getDouble("humidity"));
            weekly.setMaxTemp(jsonWeek.getDouble("temperatureMax"));
            weekly.setMinTemp(jsonWeek.getDouble("temperatureMin"));
            weekly.setPressure(jsonWeek.getDouble("pressure"));
            weekly.setWind(jsonWeek.getDouble("windSpeed"));
            weekly.setWeeklyIcon(weeklyIcon);

            mWeekly[i] = weekly;
        }

        return mWeekly;

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable;
        if (networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        else{
            isAvailable = false;
            //checkNetworkSettings();
        }

        return  isAvailable;
    }

    private void alertUserAboutError() {

        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");

    }

    public String getWeeklyIcon() {
        return weeklyIcon;
    }

    public String getDailyIcon() {
        return dailyIcon;
    }

    @OnClick(R.id.weeklyButton) public void startWeeklyActivity(View view){

        Intent intent = new Intent(this, WeeklyForecastActivity.class);
        intent.putExtra(WEEKLY_FORECAST, mForecast.getWeekly());
        intent.putExtra(WEEKLY_ICON, weeklyIcon);
        startActivity(intent);
    }

    @OnClick(R.id.hourlyButton) public void startHourlyActivity(View view){

        Intent intent = new Intent(this, HourlyForecastActivity.class);
        intent.putExtra(HOURLY_FORECAST, mForecast.getHourly());
        startActivity(intent);

    }

    @OnClick(R.id.settingsImageView)public void settings(View view){
        settingsClicked();
    }



}
