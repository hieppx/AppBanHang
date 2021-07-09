package com.example.doan.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan.Adapter.LoaiSpAdapter;
import com.example.doan.Adapter.SanPhamAdapter;
import com.example.doan.R;
import com.example.doan.model.GioHang;
import com.example.doan.model.LoaiSp;
import com.example.doan.model.SanPham;
import com.example.doan.util.KiemTraKetNoi;
import com.example.doan.util.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ListView listView;
    RecyclerView recyclerView;
    ViewFlipper viewFlipper;
    ArrayList<LoaiSp> MangLoaiSp;
    LoaiSpAdapter loaiSpAdapter;
    int id = 0;
    String tenloaisanpham = "";
    String hinhanh = "";
    ArrayList<SanPham> MangSanPham;
    SanPhamAdapter sanPhamAdapter;
    public static ArrayList<GioHang> mangGioHang;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        if (KiemTraKetNoi.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFliper();
            GetDuLieuLoaiSP();
            GetDuLieuSPMoiNhat();
            ChonItemListView();
        } else {
            KiemTraKetNoi.show_Toast_Short(getApplicationContext(),"Hãy kiểm tra lại kết nối!");
            finish();
        }
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

    private void ChonItemListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (KiemTraKetNoi.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        } else {
                            KiemTraKetNoi.show_Toast_Short(getApplicationContext(),"Bạn hãy kiểm tra kết nối! ");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (KiemTraKetNoi.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this,ModuleActivity.class);
                            startActivity(intent);
                            getIntent().putExtra("idloaisanpham",MangLoaiSp.get(position).getId());
                        }else {
                            KiemTraKetNoi.show_Toast_Short(getApplicationContext(),"Bạn hãy kiểm tra kết nối!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (KiemTraKetNoi.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this,CamBienActivity.class);
                            startActivity(intent);
                            getIntent().putExtra("idloaisanpham",MangLoaiSp.get(position).getId());
                        }else {
                            KiemTraKetNoi.show_Toast_Short(getApplicationContext(),"Bạn hãy kiểm tra kết nối!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (KiemTraKetNoi.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this,SmartHomeActivity.class);
                            startActivity(intent);
                            getIntent().putExtra("idloaisanpham",MangLoaiSp.get(position).getId());
                        }else {
                            KiemTraKetNoi.show_Toast_Short(getApplicationContext(),"Bạn hãy kiểm tra kết nối!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (KiemTraKetNoi.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this,SanPhamCoSanActivity.class);
                            startActivity(intent);
                            getIntent().putExtra("idloaisanpham",MangLoaiSp.get(position).getId());
                        }else {
                            KiemTraKetNoi.show_Toast_Short(getApplicationContext(),"Bạn hãy kiểm tra kết nối!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if (KiemTraKetNoi.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this,LienHeActivity.class);
                            startActivity(intent);
                        }else {
                            KiemTraKetNoi.show_Toast_Short(getApplicationContext(),"Bạn hãy kiểm tra kết nối!");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
            }
        });
    }

    private void GetDuLieuSPMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanSPMoiNhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                   int ID = 0;
                   String tensanpham = " ";
                   Integer Giasanpham = 0;
                   String Hinhanhsanpham = "";
                   String Motasanpham = "";
                   int IDsanpham = 0;
                   for (int i = 0; i < response.length(); i++){
                       try {
                           JSONObject jsonObject = response.getJSONObject(i);
                           ID = jsonObject.getInt("id");
                           tensanpham = jsonObject.getString("tensanpham");
                           Giasanpham = jsonObject.getInt("giasanpham");
                           Hinhanhsanpham = jsonObject.getString("hinhanhsanpham");
                           Motasanpham = jsonObject.getString("motasanpham");
                           IDsanpham = jsonObject.getInt("idsanpham");
                           MangSanPham.add(new SanPham(ID,tensanpham,Giasanpham,Hinhanhsanpham,Motasanpham,IDsanpham));
                           sanPhamAdapter.notifyDataSetChanged();
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }

                   }
                    }
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    private void GetDuLieuLoaiSP() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongDanLoaiSp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    for (int i = 0 ; i < response.length() ; i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisanpham = jsonObject.getString("tenloaisanpham");
                            hinhanh = jsonObject.getString("hinhanh");
                            MangLoaiSp.add(new LoaiSp(id, tenloaisanpham, hinhanh));
                            loaiSpAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    MangLoaiSp.add(5, new LoaiSp(0, "Contact us", "https://static.vecteezy.com/system/resources/thumbnails/002/363/153/small/contact-icon-free-vector.jpg"));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                KiemTraKetNoi.show_Toast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFliper() {
        ArrayList<String> MangQuangCao = new ArrayList<>();
        MangQuangCao.add("https://innovativehub.com.vn/wp-content/uploads/2020/12/nganh-linh-kien-dien-tu-truc-tuyen.jpg");
        MangQuangCao.add("https://www.schaeffler.vn/remotemedien/media/_shared_media_rwd/04_sectors_1/industry_1/productronics/00017D8F_16_9-schaeffler-productronics-circuit-board_rwd_600.jpg");
        MangQuangCao.add("https://1.bp.blogspot.com/-W9-B35LMVXo/XWMsZy5HK3I/AAAAAAAAADo/RzuK9P6FIrISejS0cAaoL0H4xE0WC0kXgCLcBGAs/s1600/cac-loai-linh-kien-dien-tu.jpg");
        MangQuangCao.add("https://laodongnhatban.com/wp-content/uploads/2017/08/xkld-nganh-dien-tu.jpeg");
        for (int i = 0; i < MangQuangCao.size() ; i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(MangQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
            viewFlipper.setFlipInterval(5000);
            viewFlipper.setAutoStart(true);
            Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
            Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
            viewFlipper.setInAnimation(animation_slide_in);
            viewFlipper.setOutAnimation(animation_slide_out);
        }
    }

    private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }


    private void AnhXa() {
        toolbar = (Toolbar) findViewById(R.id.tootlbarMHC);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        listView = (ListView) findViewById(R.id.listviewMHC);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewfliper);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        MangLoaiSp = new ArrayList<>();
        MangLoaiSp.add(0,new LoaiSp(0,"Trang chính","https://i.pinimg.com/474x/fc/16/ef/fc16eff6cd8c7ff12538799b8bd8f82e.jpg"));
        loaiSpAdapter = new LoaiSpAdapter(MangLoaiSp,getApplicationContext());
        listView.setAdapter(loaiSpAdapter);
        MangSanPham = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(),MangSanPham);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(sanPhamAdapter);
        if (mangGioHang != null) {

        }else {
            mangGioHang = new ArrayList<>();
        }
    }
}