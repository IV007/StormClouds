package sidelance.com.stormclouds.modules.daily;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import sidelance.com.stormclouds.R;
import sidelance.com.stormclouds.model.Forecast;
import sidelance.com.stormclouds.modules.weekly.WeeklyForecastActivity;

public class DailyDetailsActivity extends AppCompatActivity {

    private static final String TAG = DailyDetailsActivity.class.getSimpleName();
    private String dayOfWeek, hiTemp, lowTemp, conditions, humidity, pressure, wind, image;

    @Bind(R.id.dailyDetailsLabel)protected TextView dailyDetailsLabel;
    @Bind(R.id.detailsDayLabel) protected TextView dayLabel;
    @Bind(R.id.timeDetailsLabel) protected TextView timeLabel;
    @Bind(R.id.maxTempLabel) protected TextView maxTempLabel;
    @Bind(R.id.minTempLabel) protected TextView minTempLabel;
    @Bind(R.id.dailyIcon) protected ImageView iconImageView;
    @Bind(R.id.mHumidityLabel) protected TextView mHumidityLabel;
    @Bind(R.id.mPressureLabel) protected TextView mPressureLabel;
    @Bind(R.id.mWindLabel) protected TextView mWindLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_details);

        ButterKnife.bind(this);

        Log.i(TAG, "Weekly Details Activity Started");

        Intent intent = getIntent();
        dayOfWeek = intent.getStringExtra(WeeklyForecastActivity.DAY_OF_WEEK);
        hiTemp  = intent.getStringExtra(WeeklyForecastActivity.HI_TEMP);
        lowTemp = intent.getStringExtra(WeeklyForecastActivity.LOW_TEMP);
        conditions = intent.getStringExtra(WeeklyForecastActivity.CONDITIONS);
        humidity = intent.getStringExtra(WeeklyForecastActivity.HUMIDITY);
        pressure = intent .getStringExtra(WeeklyForecastActivity.PRESSURE);
        wind = intent.getStringExtra(WeeklyForecastActivity.WIND);
        image = intent.getStringExtra(WeeklyForecastActivity.ICON);

        dayLabel.setText(dayOfWeek);
        maxTempLabel.setText(hiTemp);
        minTempLabel.setText(lowTemp);
        dailyDetailsLabel.setText(conditions);
        mHumidityLabel.setText("Humidity: " + humidity);
        mPressureLabel.setText("Pressure: " + pressure);
        mWindLabel.setText("Wind: " + wind);

        iconImageView.setImageResource(Forecast.getIconId2(image));



    }

    public static int getIconId(String image){
        //clear sky, few clouds, scattered clouds, broken clouds, shower rain, rain, thunderstorm, snow
        int iconId = 0;

        return iconId;
    }


}
