package sidelance.com.stormclouds.model;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import sidelance.com.stormclouds.R;

/**
 *
 */
public class Forecast {

    private static final String TAG = Forecast.class.getSimpleName();
    Context mContext;
    private Current mCurrent;
    private Weekly[] mWeekly;
    private Hourly[] mHourly;


    public Current getCurrent() {
        return mCurrent;
    }

    public void setCurrent(Current current) {
        mCurrent = current;
    }

    public Weekly[] getWeekly() {
        return mWeekly;
    }

    public void setWeekly(Weekly[] weekly) {
        mWeekly = weekly;
    }

    public Hourly[] getHourly() {
        return mHourly;
    }

    public void setHourly(Hourly[] hourly) {
        mHourly = hourly;
    }

    public static int getIconId(String iconString){


        int iconId = R.drawable.clear_day;

        switch (iconString) {
            case "clear-day":
                iconId = R.drawable.clear_day;
                break;
            case "clear-night":
                iconId = R.drawable.clear_night;
                break;
            case "rain":
                iconId = R.drawable.rain;
                break;
            case "snow":
                iconId = R.drawable.snow;
                break;
            case "sleet":
                iconId = R.drawable.sleet;
                break;
            case "wind":
                iconId = R.drawable.wind;
                break;
            case "fog":
                iconId = R.drawable.fog;
                break;
            case "cloudy":
                iconId = R.drawable.cloudy;
                break;
            case "partly-cloudy-day":
                iconId = R.drawable.partly_cloudy;
                break;
            case "partly-cloudy-night":
                iconId = R.drawable.cloudy_night;
                break;
        }

        return iconId;
    }

    public static int getIconId2(String id){
        //clear sky, few clouds, scattered clouds, broken clouds, shower rain, rain, thunderstorm, snow


        int iconId = R.drawable.clear_day;

        switch (id) {
            case "clear-day":
                iconId = R.drawable.clear_day;
                break;
            case "clear-night":
                iconId = R.drawable.clear_night;
                break;
            case "rain":
                iconId = R.drawable.rain;
                break;
            case "snow":
                iconId = R.drawable.snow;
                break;
            case "sleet":
                iconId = R.drawable.sleet;
                break;
            case "wind":
                iconId = R.drawable.wind;
                break;
            case "fog":
                iconId = R.drawable.art_fog;
                break;
            case "cloudy":
                iconId = R.drawable.art_clouds;
                break;
            case "partly-cloudy-day":
                iconId = R.drawable.partly_cloudy;
                break;
            case "partly-cloudy-night":
                iconId = R.drawable.cloudy_night;
                break;
        }

        return iconId;

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Drawable setLayoutBackground(String icon) {

        Log.e(TAG, "iconName: " + icon);
        if (!TextUtils.isEmpty(icon)) {
            Drawable gradientDrawable = null;
            switch (icon) {
                case "clear-day":
                    gradientDrawable = mContext.getResources().getDrawable(R.drawable.clear_day_bg_gradient, null);
                    break;

                case "clear-night":
                    gradientDrawable = mContext.getResources().getDrawable(R.drawable.clear_night_bg_gradient, null);
                    break;

                case "partly-cloudy-night":
                    gradientDrawable = mContext.getResources().getDrawable(R.drawable.cloudy_night_bg_gradient, null);
                    break;

                case "partly-cloudy-day":
                    gradientDrawable = mContext.getResources().getDrawable(R.drawable.partly_cloudy_day_bg_gradient, null);
                    break;

                case "rain":
                    gradientDrawable = mContext.getResources().getDrawable(R.drawable.light_rain_bg_gradient, null);
                    break;
            }


            return gradientDrawable;
        }
        return null;
    }

}

