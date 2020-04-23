package com.example.leaderboardapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NavigationListAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> NavItemsList;
    public NavigationListAdapter(Context context, ArrayList<String> NavItemsList) {

        this.context = context;
        this.NavItemsList = NavItemsList;
    }
    @Override
    public int getCount() {
        return NavItemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return NavItemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.profile_navigation_list, null, true);
        }

        TextView navitem = convertView.findViewById(R.id.navigationItem);
        navitem.setText(NavItemsList.get(position));
        return convertView;
    }
}
