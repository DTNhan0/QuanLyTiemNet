package BLL.InFoThongTinSD;

import java.time.LocalDateTime;

public class ThongTinSuDung {
    private Integer id;
    private String maMay, username, sdt;
    private Boolean DagSD;
    private LocalDateTime tgBatDau, tgKetThuc;
    public Boolean getDagSD() {
        return DagSD;
    }

    public void setDagSD(Boolean dagSD) {
        DagSD = dagSD;
    }

    public String getMaMay() {
        return maMay;
    }

    public void setMaMay(String maMay) {
        this.maMay = maMay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSdt() {
        return sdt;
    }

    public ThongTinSuDung() {super();}

    public ThongTinSuDung(String maMay, String username, String sdt, Boolean dagSD, LocalDateTime tgBatDau, LocalDateTime tgKetThuc) {
        this.maMay = maMay;
        this.username = username;
        this.sdt = sdt;
        DagSD = dagSD;
        this.tgBatDau = tgBatDau;
        this.tgKetThuc = tgKetThuc;
    }

    public ThongTinSuDung(String username, String sdt, LocalDateTime tgKetThuc) {
        this.username = username;
        this.sdt = sdt;
        this.tgKetThuc = tgKetThuc;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public LocalDateTime getTgBatDau() {
        return tgBatDau;
    }

    public void setTgBatDau(LocalDateTime tgBatDau) {
        this.tgBatDau = tgBatDau;
    }

    public LocalDateTime getTgKetThuc() {
        return tgKetThuc;
    }

    public void setTgKetThuc(LocalDateTime tgKetThuc) {
        this.tgKetThuc = tgKetThuc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
