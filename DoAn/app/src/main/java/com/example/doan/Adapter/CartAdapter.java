package com.example.doan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doan.Activity.CartActivity;
import com.example.doan.Activity.MainActivity;
import com.example.doan.R;
import com.example.doan.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> arrayGioHang;

    public CartAdapter(Context context, ArrayList<GioHang> arrayGioHang) {
        this.context = context;
        this.arrayGioHang = arrayGioHang;
    }

    @Override
    public int getCount() {
        return arrayGioHang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayGioHang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txtTenCart, txtGiaCart;
        public ImageView imageViewCart;
        public Button btnTru, btnCong, btnValue;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_cart,null);
            viewHolder.txtTenCart = (TextView) view.findViewById(R.id.textviewTenCart);
            viewHolder.txtGiaCart = (TextView) view.findViewById(R.id.textviewGiaCart);
            viewHolder.imageViewCart = (ImageView) view.findViewById(R.id.imageCart);
            viewHolder.btnTru = (Button) view.findViewById(R.id.buttonTru);
            viewHolder.btnValue = (Button) view.findViewById(R.id.buttonValues);
            viewHolder.btnCong = (Button) view.findViewById(R.id.buttonCong);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        GioHang gioHang = (GioHang) getItem(position);
        viewHolder.txtTenCart.setText(gioHang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
        viewHolder.txtGiaCart.setText(decimalFormat.format(gioHang.getGiasp()) + " Đ");
        Picasso.with(context).load(gioHang.getHinhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.images)
                .into(viewHolder.imageViewCart);
        viewHolder.btnValue.setText(gioHang.getSoluongsp() + "");
        int sl = Integer.parseInt(viewHolder.btnValue.getText().toString());
        if (sl >= 10){
            viewHolder.btnCong.setVisibility(View.INVISIBLE);
            viewHolder.btnTru.setVisibility(View.VISIBLE);
        } else if(sl <= 1){
            viewHolder.btnTru.setVisibility(View.INVISIBLE);
        } else if(sl >= 1){
            viewHolder.btnTru.setVisibility(View.VISIBLE);
            viewHolder.btnCong.setVisibility(View.VISIBLE);
        }
        ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnValue.getText().toString()) +1;
                int slht = MainActivity.mangGioHang.get(position).getSoluongsp();
                long giaht = MainActivity.mangGioHang.get(position).getGiasp();
                MainActivity.mangGioHang.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slht;
                MainActivity.mangGioHang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
                finalViewHolder.txtGiaCart.setText(decimalFormat.format(giamoinhat) + " Đ");
                CartActivity.EvenUltil();
                if (slmoinhat > 9){
                    finalViewHolder.btnCong.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnTru.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.btnTru.setVisibility(View.VISIBLE);
                    finalViewHolder.btnCong.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(slmoinhat));
                }
            }
        });
        finalViewHolder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    int slmoinhat = Integer.parseInt(finalViewHolder.btnValue.getText().toString()) - 1;
                    int slht = MainActivity.mangGioHang.get(position).getSoluongsp();
                    long giaht = MainActivity.mangGioHang.get(position).getGiasp();
                    MainActivity.mangGioHang.get(position).setSoluongsp(slmoinhat);
                    long giamoinhat = (giaht * slmoinhat) / slht;
                    MainActivity.mangGioHang.get(position).setGiasp(giamoinhat);
                    DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
                    finalViewHolder.txtGiaCart.setText(decimalFormat.format(giamoinhat) + " Đ");
                    CartActivity.EvenUltil();
                    if (slmoinhat < 2){
                        finalViewHolder.btnTru.setVisibility(View.INVISIBLE);
                        finalViewHolder.btnCong.setVisibility(View.VISIBLE);
                        finalViewHolder.btnValue.setText(String.valueOf(slmoinhat));
                    }else {
                        finalViewHolder.btnTru.setVisibility(View.VISIBLE);
                        finalViewHolder.btnCong.setVisibility(View.VISIBLE);
                        finalViewHolder.btnValue.setText(String.valueOf(slmoinhat));
                    }
            }
        });
        return view;
    }
}
