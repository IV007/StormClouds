package sidelance.com.stormclouds.modules.weekly.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import sidelance.com.stormclouds.R;
import sidelance.com.stormclouds.model.Weekly;

/**
 *
 */
public class WeeklyForecastAdapter extends BaseAdapter {

    private Context mContext;
    private Weekly[] mWeek;


    public WeeklyForecastAdapter(Context context, Weekly[] week) {
        mContext = context;
        mWeek = week;
    }


    @Override
    public int getCount() {
        return mWeek.length;
    }

    @Override
    public Object getItem(int position) {
        return mWeek[position];
    }

    @Override
    public long getItemId(int position) {
        return 0; //Used To tag items for easy reference.
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            //First time/brandnew
            convertView = LayoutInflater.from(mContext).inflate(R.layout.weekly_list_item, null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView)convertView.findViewById(R.id.iconImageView);
            holder.tempLabel = (TextView)convertView.findViewById(R.id.tempLabel);
            holder.dayLabel = (TextView)convertView.findViewById(R.id.dayNameLabel);
            holder.dailySummaryLabel = (TextView)convertView.findViewById(R.id.dailySummaryLabel);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        Weekly weekly = mWeek[position];

        holder.iconImageView.setImageResource(weekly.getIconId());
        holder.tempLabel.setText(weekly.getMaxTemp() + "");
        holder.dailySummaryLabel.setText(weekly.getDailySummary());

        if (position == 0){
            holder.dayLabel.setText("Today");
        }else {
            holder.dayLabel.setText(weekly.getDayOfTheWeek());

        }

        return convertView;
    }


    private static class ViewHolder{

        ImageView iconImageView;
        TextView tempLabel;
        TextView dayLabel;
        TextView dailySummaryLabel;
    }
}
