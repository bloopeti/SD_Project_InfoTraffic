package sd.proj.peter.infotrafficapp.uicontrollers.listAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sd.proj.peter.infotrafficapp.R;
import sd.proj.peter.infotrafficapp.common.model.Alert;

public class CustomListAdapter extends ArrayAdapter<Alert> {
    private static List<Alert> alerts;
    private LayoutInflater mInflater;

    public CustomListAdapter(@NonNull Context context, int resource, @NonNull List<Alert> objects) {
        super(context, resource, objects);
        this.mInflater = LayoutInflater.from(context);
        alerts = (List<Alert>) objects;
    }

    @Override
    public int getCount() {
        return alerts.size();
    }

    @Override
    public Alert getItem(int position) {
        return alerts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_alert_list_element, parent, false);
            holder = new ViewHolder();
            holder.alert_type = (TextView) convertView.findViewById(R.id.alert_type);
            holder.alert_location = (TextView) convertView.findViewById(R.id.alert_location);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.alert_type.setText(alerts.get(position).getType());
        holder.alert_location.setText(alerts.get(position).getLocation());

        return convertView;
    }

    static class ViewHolder {
        TextView alert_type;
        TextView alert_location;
    }
}
