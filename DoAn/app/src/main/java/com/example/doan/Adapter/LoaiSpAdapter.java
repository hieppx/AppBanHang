package com.example.doan.Adapter;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doan.R;
import com.example.doan.model.LoaiSp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiSpAdapter  extends BaseAdapter {
    ArrayList<LoaiSp> arrayListloaisp;
    Context context;

    public LoaiSpAdapter(ArrayList<LoaiSp> arrayListloaisp, Context context) {
        this.arrayListloaisp = arrayListloaisp;
        this.context = context;
    }

//    private int i;

    @Override
    public int getCount() {
        return arrayListloaisp.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListloaisp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        TextView txtTenLoaiSp;
        ImageView imgLoaiSp;
    }
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_loaisp,null);
            viewHolder.txtTenLoaiSp = (TextView) ((View) view).findViewById(R.id.textviewloaisanpham);
            viewHolder.imgLoaiSp = (ImageView ) ((View) view).findViewById(R.id.imageloaisp);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        LoaiSp loaiSp = (LoaiSp) getItem(i);
        viewHolder.txtTenLoaiSp.setText(loaiSp.getTenloaisanpham());
        Picasso.with(context).load(loaiSp.getHinhanhloaisanpham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.images)
                .into(viewHolder.imgLoaiSp);
        return view;
    }
}
