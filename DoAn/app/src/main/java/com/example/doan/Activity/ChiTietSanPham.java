package com.example.doan.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.doan.R;
import com.example.doan.model.GioHang;
import com.example.doan.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {
    Toolbar toolbarChiTietSanPham;
    ImageView imgChiTietSanPham;
    TextView txtTen, txtGia, txtMota;
    Spinner spinner;
    Button buttonChiTietSanPham;

    int id = 0;
    String TenChiTiet = "";
    int GiaChiTiet = 0;
    String HinhAnhChiTiet = "";
    String MotaChiTiet = "";
    int IdSanPham = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        AnhXa();
        ActionToolBar();
        GetInformation();
        CatchEventSpinner();
        EventButton();
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuCart:
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventButton() {
        buttonChiTietSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mangGioHang.size() > 0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i = 0 ; i < MainActivity.mangGioHang.size(); i++){
                        if (MainActivity.mangGioHang.get(i).getIdsp() == id){
                            MainActivity.mangGioHang.get(i).setSoluongsp(MainActivity.mangGioHang.get(i).getSoluongsp() + sl);
                            if (MainActivity.mangGioHang.get(i).getSoluongsp() >= 10){
                                MainActivity.mangGioHang.get(i).setSoluongsp(10);
                            }
                            MainActivity.mangGioHang.get(i).setGiasp(GiaChiTiet * MainActivity.mangGioHang.get(i).getSoluongsp());
                            exists = true;
                        }
                    }
                    if (exists == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi = soluong * GiaChiTiet;
                        MainActivity.mangGioHang.add(new GioHang(id,TenChiTiet,Giamoi,HinhAnhChiTiet,soluong));
                    }
                }else{
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi = soluong * GiaChiTiet;
                    MainActivity.mangGioHang.add(new GioHang(id,TenChiTiet,Giamoi,HinhAnhChiTiet,soluong));
                }
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer [] soLuong = new Integer[] {1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item,soLuong);
        spinner.setAdapter(arrayAdapter);
    }
    private void GetInformation() {
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanPham.getID();
        TenChiTiet = sanPham.getTenSanPham();
        GiaChiTiet = sanPham.getGiaSanPham();
        HinhAnhChiTiet = sanPham.getHinhAnhSanPham();
        MotaChiTiet = sanPham.getMoTaSanPham();
        IdSanPham = sanPham.getIDSanPham();
        txtTen.setText(TenChiTiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
        txtGia.setText("Giá : " + decimalFormat.format(GiaChiTiet) + " Đ");
        txtMota.setText(MotaChiTiet);
        Picasso.with(getApplicationContext()).load(HinhAnhChiTiet)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.images)
                .into(imgChiTietSanPham);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarChiTietSanPham);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTietSanPham.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void AnhXa() {
        toolbarChiTietSanPham = (Toolbar) findViewById(R.id.tootlbarChiTietSanPham);
        imgChiTietSanPham = (ImageView) findViewById(R.id.imageChiTietSanPham);
        txtTen = (TextView) findViewById(R.id.texviewTenChiTietSanPham);
        txtGia = (TextView) findViewById(R.id.texviewGiaChiTietSanPham);
        txtMota = (TextView) findViewById(R.id.textViewMotaCTSP);
        spinner = (Spinner) findViewById(R.id.spinner);
        buttonChiTietSanPham = (Button) findViewById(R.id.buttonAddCart);
    }
}