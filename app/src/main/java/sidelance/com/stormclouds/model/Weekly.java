package sidelance.com.stormclouds.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 */
public class Weekly implements Parcelable {


    //time, hi-temp, low-temp, icon, dailySummary, weeklySummary, humidity, pressure, wind
    /**
    * The Time of forecast, could be in any format
    */
    private long mTime;

    /**
    * Required for formatting time
    */
    private String mTimeZone;

    /**
     * The maximum temperature for the day
     */
    private double mMaxTemp;

    /**
     * The minimum temperature for the day
     */
    private double mMinTemp;

    /**
     * The Associated temperature icon
     */
    private String mIcon;

    /**
     * Summary for the day
     */
    private String mDailySummary;

    /**
     * Summary for the entire week
     */
    private String mWeeklySummary;
    /**
     * Humidity for the day
     */
    private double mHumidity;

    /**
     * Pressure for the day
     */
    private double mPressure;

    /**
     * Wind speed
     */
    private double mWind;

    /**
     * Weekly icon string used for mapping
     */
    private String weeklyIcon;

    /**
     * Constructor
     */
    public Weekly(){ }


    ///////////////////////////////////////
    //      Getters & Setters           //
    /////////////////////////////////////


    public long getTime() {
        return mTime;
    }


    public String getDayOfTheWeek(){

        SimpleDateFormat formatter = new SimpleDateFormat("EEEE ", Locale.getDefault());
        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dateTime = new Date(getTime() * 1000);

        return formatter.format(dateTime);
    }

    public String getListItemHour(){
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM dd ", Locale.ENGLISH);
        Date date = new Date(mTime * 1000);

        return  formatter.format(date);
    }
    public void setTime(long time) {
        mTime = time;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public int getMaxTemp() {
        return (int) Math.round(mMaxTemp);
    }

    public void setMaxTemp(double maxTemp) {
        mMaxTemp = maxTemp;
    }

    public int getMinTemp() {
        return (int) Math.round(mMinTemp);
    }

    public void setMinTemp(double minTemp) {
        mMinTemp = minTemp;
    }

    public String getIcon() {
        return mIcon;
    }

    public int getIconId(){
        return Forecast.getIconId(mIcon);
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getDailySummary() {
        return mDailySummary;
    }

    public void setDailySummary(String dailySummary) {
        this.mDailySummary = dailySummary;
    }

    public String getWeeklySummary() {
        return mWeeklySummary;
    }

    public void setWeeklySummary(String weeklySummary) {
        mWeeklySummary = weeklySummary;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public double getPressure() {
        return mPressure;
    }

    public void setPressure(double pressure) {
        mPressure = pressure;
    }

    public double getWind() {
        return mWind;
    }

    public void setWind(double wind) {
        mWind = wind;
    }

    public String getWeeklyIcon() {
        return weeklyIcon;
    }

    public void setWeeklyIcon(String weeklyIcon) {
        this.weeklyIcon = weeklyIcon;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        //time, hi-temp, low-temp, icon, dailySummary, weeklySummary, humidity, pressure, wind, timeZone
        dest.writeLong(mTime);
        dest.writeDouble(mMaxTemp);
        dest.writeDouble(mMinTemp);
        dest.writeString(mIcon);
        dest.writeString(mDailySummary);
        dest.writeString(mWeeklySummary);
        dest.writeDouble(mHumidity);
        dest.writeDouble(mPressure);
        dest.writeDouble(mWind);
        dest.writeString(mTimeZone);

    }

    private Weekly(Parcel in){
        //Read data in order of writeToParcel
        mTime = in.readLong();
        mMaxTemp = in.readDouble();
        mMinTemp = in.readDouble();
        mIcon = in.readString();
        mDailySummary = in.readString();
        mWeeklySummary = in.readString();
        mHumidity = in.readDouble();
        mPressure = in.readDouble();
        mWind = in.readDouble();
        mTimeZone = in.readString();

    }

    public static final Creator<Weekly> CREATOR = new Creator<Weekly>() {
        @Override
        public Weekly createFromParcel(Parcel source) {
            return new Weekly(source);
        }

        @Override
        public Weekly[] newArray(int size) {
            return new Weekly[size];
        }
    };
}
