package sidelance.com.stormclouds.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 */
public class Hourly implements Parcelable{

    private long mTime;
    private String mSummary;
    private double mTemperature;
    private String mIcon;
    private String mTimeZone;

    public Hourly() {
    }

    public long getTime() {
        return mTime;
    }

    public String getHour(){

        SimpleDateFormat formatter = new SimpleDateFormat("h a", Locale.getDefault());
//        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date date = new Date(mTime * 1000);

        return formatter.format(date);

    }

    public String getListItemHour(){
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE h a MMMM", Locale.ENGLISH);
        Date date = new Date(mTime * 1000);

        return  formatter.format(date);
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public int getTemperature() {
        return (int) Math.round(mTemperature);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
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

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
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

        dest.writeLong(mTime);
        dest.writeString(mSummary);
        dest.writeString(mIcon);
        dest.writeDouble(mTemperature);
        dest.writeString(mTimeZone);
    }

    private Hourly(Parcel in){

        mTime = in.readLong();
        mSummary = in.readString();
        mIcon = in.readString();
        mTemperature = in.readDouble();
        mTimeZone = in.readString();
    }

    public static final Creator<Hourly> CREATOR = new Creator<Hourly>() {
        @Override
        public Hourly createFromParcel(Parcel source) {
            return new Hourly(source);
        }

        @Override
        public Hourly[] newArray(int size) {
            return new Hourly[size];
        }
    };
}
