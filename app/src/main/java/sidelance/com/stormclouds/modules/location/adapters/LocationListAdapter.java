package sidelance.com.stormclouds.modules.location.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import sidelance.com.stormclouds.R;

/**
 *
 */
public class LocationListAdapter extends BaseAdapter {

    private Context mContext;


    public LocationListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 0;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){

            convertView = LayoutInflater.from(mContext).inflate(R.layout.location_settings_list_item, parent);
            holder = new ViewHolder();
            holder.savedLocationLabel = (TextView)convertView.findViewById(R.id.savedLocationLabel);
            holder.savedLocationDateLabel = (TextView)convertView.findViewById(R.id.savedLocationDateLabel);
            holder.deleteSavedLocation = (ImageView)convertView.findViewById(R.id.deleteSavedLocationLabel);

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder)convertView.getTag();
        }



        return convertView;
    }

    private static class ViewHolder{

        TextView savedLocationLabel;
        TextView savedLocationDateLabel;
        ImageView deleteSavedLocation;

    }
}
