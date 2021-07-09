package com.example.doan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doan.R;
import com.example.doan.util.KiemTraKetNoi;
import com.example.doan.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinKhachHangActivity extends AppCompatActivity {
    EditText editTenKH, editSDT, editEmail, editDiachi;
    Button btnXacnhan, btnTrove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);
        AnhXa();
        btnTrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (KiemTraKetNoi.haveNetworkConnection(getApplicationContext())){
            EvenButton();
        }else {
            KiemTraKetNoi.show_Toast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
        }
    }

    private void EvenButton() {
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten = editTenKH.getText().toString().trim();
                final String sdt = editSDT.getText().toString().trim();
                final String email = editEmail.getText().toString().trim();
                final String diachi = editDiachi.getText().toString().trim();
                if (ten.length() > 0 && sdt.length() > 0 && email.length() > 0 && diachi.length() > 0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest =new StringRequest(Request.Method.POST, Server.DuongDanDonHang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhang",madonhang);
                            if (Integer.parseInt(madonhang) > 0){
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.DuongDanChiTiet, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")){
                                            MainActivity.mangGioHang.clear();
                                            KiemTraKetNoi.show_Toast_Short(getApplicationContext(), "Bạn đã đặt hàng thành công!");
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            KiemTraKetNoi.show_Toast_Short(getApplicationContext(), "Mời bạn tiếp tục mua hàng!");
                                        }else {
                                            KiemTraKetNoi.show_Toast_Short(getApplicationContext(), "Dữ liệu giỏ hàng bị lỗi!");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i = 0 ; i < MainActivity.mangGioHang.size() ; i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang", madonhang);
                                                jsonObject.put("masanham", MainActivity.mangGioHang.get(i).getIdsp());
                                                jsonObject.put("giasanpham", MainActivity.mangGioHang.get(i).getGiasp());jsonObject.put("tensanpham", MainActivity.mangGioHang.get(i).getTensp());
                                                jsonObject.put("soluongsanpham", MainActivity.mangGioHang.get(i).getSoluongsp());

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json", jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError{
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai", sdt);
                            hashMap.put("email", email);
                            hashMap.put("diachi",diachi);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else {
                    KiemTraKetNoi.show_Toast_Short(getApplicationContext(),"Hãy kiểm tra lại dữ liệu!");
                }
            }
        });
    }

    private void AnhXa() {
        editTenKH = (EditText) findViewById(R.id.edittextTenKH);
        editSDT = (EditText) findViewById(R.id.edittextSdt);
        editEmail = (EditText) findViewById(R.id.edittextEmail);
        editDiachi = (EditText) findViewById(R.id.edittextDiachi);
        btnXacnhan = (Button) findViewById(R.id.buttonXacnhan);
        btnTrove = (Button) findViewById(R.id.buttonTrove);
    }
}