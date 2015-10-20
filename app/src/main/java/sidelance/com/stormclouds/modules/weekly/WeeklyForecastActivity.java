package sidelance.com.stormclouds.modules.weekly;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sidelance.com.stormclouds.R;
import sidelance.com.stormclouds.model.Weekly;
import sidelance.com.stormclouds.modules.current.MainActivity;
import sidelance.com.stormclouds.modules.weekly.adapters.WeeklyForecastAdapter;
import sidelance.com.stormclouds.utilities.MyViewAnimation;

public class WeeklyForecastActivity extends Activity {

    private static final String TAG = WeeklyForecastActivity.class.getSimpleName();
    public static final String DAY_OF_WEEK = "DAY_OF_WEEK";
    public static final String HI_TEMP = "HI_TEMP";
    public static final String CONDITIONS = "CONDITIONS";
    public static final String HUMIDITY = "HUMIDITY";
    public static final String PRESSURE = "PRESSURE";
    public static final String WIND = "WIND";
    public static final String LOW_TEMP = "LOW_TEMP";
    public static final String ICON = "ICON";

    private Double latitude, longitude;

    @Bind(android.R.id.empty) protected TextView listEmptyLabel;
    @Bind(R.id.weeklyRootLayout) protected RelativeLayout weeklyRootLayout;
    @Bind(R.id.playAnimationButton) protected TextView playAnimationButtonTrigger;
    @Bind(R.id.playAnimationButton2)protected TextView playAnimationButton2Trigger;
    @Nullable
    @Bind(R.id.locationLabel) protected  TextView mLocationLabel;
    @Bind(android.R.id.list) ListView mListView;

    /*
        @Bind(R.id.dateTextView)TextView dateLabel;
        @Bind(R.id.hiTempTextView)TextView hiTempLabel;
        @Bind(R.id.lowTempTextView)TextView lowTempLabel;
        @Bind(R.id.iconImageView)ImageView icon;
        @Bind(R.id.summaryText)TextView weeklySummaryLabel;
        @Bind(R.id.backButton)ImageView backButton;
    */
    private Weekly[] mWeekly;
    private String weeklyIcon;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_forecast);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        weeklyIcon = intent.getStringExtra(MainActivity.WEEKLY_ICON);
        if (weeklyIcon != null)
            weeklyRootLayout.setBackground(setLayoutBackgroundFromWeeklyIcon(weeklyIcon));

        Parcelable[] parcelable = intent.getParcelableArrayExtra(MainActivity.WEEKLY_FORECAST);
        mWeekly = Arrays.copyOf(parcelable, parcelable.length, Weekly[].class);
        WeeklyForecastAdapter adapter = new WeeklyForecastAdapter(this, mWeekly);
        mListView.setAdapter(adapter);
        mListView.setEmptyView(listEmptyLabel);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dayOfTheWeek = mWeekly[position].getListItemHour();
                String hiTemp = mWeekly[position].getMaxTemp()+ "";
//        String lowTemp = mWeekly[position].getMinTemp() + "";
                String conditions = mWeekly[position].getDailySummary();

                String message = String.format("On %s the high will be %s and it will be %s",
                        dayOfTheWeek,
                        hiTemp,
                        conditions);

                displayWeeklyForecastItem(message);

            }
        });


    }



    private void displayWeeklyForecastItem(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void newWeeklyweatherNotification() {

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notifier = new Notification(android.R.drawable.star_on, "Storm Clouds", System.currentTimeMillis());
        Weekly weekly = new Weekly();
        Context context = WeeklyForecastActivity.this;
        CharSequence title = "Weather for the week";
        CharSequence details = "" + weekly.getWeeklySummary();
        Intent intent = new Intent(context, WeeklyForecastActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        notifier.setLatestEventInfo(context, title, details, pendingIntent);
        vibrateAndShowLED(context);
        nm.notify(0, notifier);

    }

    private static NotificationCompat.Builder vibrateAndShowLED(Context context) {

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setWhen(System.currentTimeMillis());

        builder.setVibrate(new long[]{1000, 0, 1000, 1000, 1000, 1000}); //Vibrate format is delay, vibrate, delay, vibrate, delay, vibrate
        builder.setLights(Color.BLUE, 2000, 2000);

        return builder;
    }


    public Drawable setLayoutBackgroundFromWeeklyIcon(String data) {

        //clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night
        if (!TextUtils.isEmpty(data)) {
            Drawable gradientDrawable = null;
            switch (data) {
                case "clear-day":
                    gradientDrawable = getResources().getDrawable(R.drawable.clear_day_bg_gradient);
                    break;

                case "clear-night":
                    gradientDrawable = getResources().getDrawable(R.drawable.clear_night_bg_gradient);
                    break;

                case "rain":
                    gradientDrawable = getResources().getDrawable(R.drawable.light_rain_bg_gradient);
                    break;

                case "cloudy":
                    gradientDrawable = getResources().getDrawable(R.drawable.cloudy_bg_gradient);
                    break;

                case "partly-cloudy-day":
                    gradientDrawable = getResources().getDrawable(R.drawable.partly_cloudy_day_bg_gradient);
                    break;

                case "partly-cloudy-night":
                    gradientDrawable = getResources().getDrawable(R.drawable.cloudy_night_bg_gradient);
                    break;

            }

            return gradientDrawable;
        }
        return null;


    }


    @OnClick(R.id.playAnimationButton)
    public void playAnimationButtonTrigger(View view) {

        MyViewAnimation.spinButtonAnimation(view);
        MyViewAnimation.rotateButtonAnimation(view);
        playAnimationButtonTrigger.setText("Play");
        MyViewAnimation.fadeInAndBounceAnimation(view);

        YoYo.with(Techniques.SlideInLeft).duration(6000).playOn(mLocationLabel);


    }
}