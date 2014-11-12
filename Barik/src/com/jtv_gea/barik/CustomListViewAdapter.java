package com.jtv_gea.barik;

import java.util.List;

import com.jtv_gea.barik.modelo.DrawerItem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListViewAdapter extends ArrayAdapter<DrawerItem> {
	Context context;
	 
    public CustomListViewAdapter(Context context, int resourceId, List<DrawerItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }
    
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        DrawerItem drawerItem = getItem(position);
         
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.list_item);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
                 
        holder.txtTitle.setText(drawerItem.getTitle());
        holder.imageView.setImageResource(drawerItem.getIconId());
         
        return convertView;
    }
}
