package com.example.leaderboardapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DataModel> dataModelArrayList;

    public ListAdapter(Context context, ArrayList<DataModel> dataModelArrayList) {

        this.context = context;
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return dataModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_player, null, true);

            holder.iv = (ImageView) convertView.findViewById(R.id.iv);
            holder.tvname = (TextView) convertView.findViewById(R.id.name);
            holder.tvcountry = (TextView) convertView.findViewById(R.id.country);
            holder.tvcity = (TextView) convertView.findViewById(R.id.city);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }
        if(dataModelArrayList.get(position).getImgURL().length()==0)
        {
            Picasso.get().load("https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png").into(holder.iv);
        }
        else
        {
            byte[] decodedString = Base64.decode(dataModelArrayList.get(position).getImgURL().split(",",2)[1], Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.iv.setImageBitmap(decodedByte);
        }

        holder.tvname.setText("UserName: "+dataModelArrayList.get(position).getName());
        holder.tvcountry.setText("Score: "+dataModelArrayList.get(position).getScore());
        holder.tvcity.setText(""+dataModelArrayList.get(position).getCity());

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname, tvcountry, tvcity;
        protected ImageView iv;
    }
}
