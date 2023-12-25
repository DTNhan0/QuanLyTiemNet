package BLL.InFoLichSuNap;

import java.time.LocalDateTime;

public class LichSuNap {
    private int id;
    private String taiKhoan, sdt;
    private double soTien;
    private LocalDateTime tgNap;

    public LichSuNap(int id, String taiKhoan, String sdt, double soTien, LocalDateTime tgNap) {
        this.id = id;
        this.taiKhoan = taiKhoan;
        this.sdt = sdt;
        this.soTien = soTien;
        this.tgNap = tgNap;
    }

    public LichSuNap() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public double getSoTien() {
        return soTien;
    }

    public void setSoTien(double soTien) {
        this.soTien = soTien;
    }

    public LocalDateTime getTgNap() {
        return tgNap;
    }

    public void setTgNap(LocalDateTime tgNap) {
        this.tgNap = tgNap;
    }
}
