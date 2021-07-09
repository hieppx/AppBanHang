package com.example.doan.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    public int ID;
    public String TenSanPham;
    public Integer GiaSanPham;
    public String HinhAnhSanPham;
    public String MoTaSanPham;
    public int IDSanPham;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }

    public Integer getGiaSanPham() {
        return GiaSanPham;
    }

    public void setGiaSanPham(Integer giaSanPham) {
        GiaSanPham = giaSanPham;
    }

    public String getHinhAnhSanPham() {
        return HinhAnhSanPham;
    }

    public void setHinhAnhSanPham(String hinhAnhSanPham) {
        HinhAnhSanPham = hinhAnhSanPham;
    }

    public String getMoTaSanPham() {
        return MoTaSanPham;
    }

    public void setMoTaSanPham(String moTaSanPham) {
        MoTaSanPham = moTaSanPham;
    }

    public int getIDSanPham() {
        return IDSanPham;
    }

    public void setIDSanPham(int IDSanPham) {
        this.IDSanPham = IDSanPham;
    }

    public SanPham(int ID, String tenSanPham, Integer giaSanPham, String hinhAnhSanPham, String moTaSanPham, int IDSanPham) {
        this.ID = ID;
        TenSanPham = tenSanPham;
        GiaSanPham = giaSanPham;
        HinhAnhSanPham = hinhAnhSanPham;
        MoTaSanPham = moTaSanPham;
        this.IDSanPham = IDSanPham;
    }
}
