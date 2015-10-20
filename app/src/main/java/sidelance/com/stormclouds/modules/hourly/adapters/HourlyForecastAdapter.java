
package sidelance.com.stormclouds.modules.hourly.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import sidelance.com.stormclouds.R;
import sidelance.com.stormclouds.model.Hourly;

/**
 *
 */
public class HourlyForecastAdapter extends RecyclerView.Adapter<HourlyForecastAdapter.HourViewHolder> {


    public Hourly[] mHourly;
    private Context mContext;

    public HourlyForecastAdapter(Hourly[] mHourly, Context mContext) {
        this.mHourly = mHourly;
        this.mContext = mContext;
    }

    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.hourly_list_item, viewGroup, false);

        HourViewHolder viewHolder = new HourViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HourViewHolder hourViewHolder, int i) {
        hourViewHolder.bindHour(mHourly[i]);
    }

    @Override
    public int getItemCount() {
        return mHourly.length;
    }

    public class HourViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView mTimeLabel;
        public TextView mSummaryLabel;
        public TextView mTemperatureLabel;
        public ImageView mIconImageView;

        public HourViewHolder(View itemView) {
            super(itemView);

            mTimeLabel = (TextView) itemView.findViewById(R.id.timeLabel);
            mSummaryLabel = (TextView) itemView.findViewById(R.id.hourlySummaryLabel);
            mTemperatureLabel = (TextView) itemView.findViewById(R.id.hourlyTempLabel);
            mIconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);

            itemView.setOnClickListener(this);
        }

        public void bindHour(Hourly hour){
            mTimeLabel.setText(hour.getHour());
            mSummaryLabel.setText(hour.getSummary());
            mTemperatureLabel.setText(hour.getTemperature() + "");
            mIconImageView.setImageResource(hour.getIconId());

        }

        @Override
        public void onClick(View v) {
            String time = mTimeLabel.getText().toString();
            String temp = mTemperatureLabel.getText().toString();
            String summary = mSummaryLabel.getText().toString();

            String message = String.format("At %s it will be %s and %s",
                    time,
                    temp,
                    summary);

            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }
    }

}
