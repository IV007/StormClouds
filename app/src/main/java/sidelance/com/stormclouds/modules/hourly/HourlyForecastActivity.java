package sidelance.com.stormclouds.modules.hourly;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import sidelance.com.stormclouds.R;
import sidelance.com.stormclouds.model.Hourly;
import sidelance.com.stormclouds.modules.current.MainActivity;
import sidelance.com.stormclouds.modules.hourly.adapters.HourlyForecastAdapter;

public class HourlyForecastActivity extends AppCompatActivity {

    private static final String TAG = HourlyForecastActivity.class.getSimpleName();
    @Bind(R.id.recyclerView) protected RecyclerView mRecyclerView;

    private Hourly[] mHourly;
    private Hourly hourlyIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hourly_forecast);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);
        mHourly = Arrays.copyOf(parcelables, parcelables.length, Hourly[].class);

        HourlyForecastAdapter adapter = new HourlyForecastAdapter(mHourly, this);
        mRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);


    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public Drawable setLayoutBackground() {

        Hourly hourlyIcon = new Hourly();
        String icon = hourlyIcon.getIcon();


        Log.e(TAG, "iconName: " +icon);

        if (!TextUtils.isEmpty(icon)) {
            Drawable gradientDrawable = null;
            switch (icon) {
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

                case "rain":
                    gradientDrawable = getResources().getDrawable(R.drawable.light_rain_bg_gradient);
                    break;
            }


            return gradientDrawable;
        }
        return null;
    }


}
