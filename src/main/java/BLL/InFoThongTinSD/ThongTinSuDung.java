package BLL.InFoThongTinSD;

public class ThongTinSuDung {
    private String maMay, username, sdt;
    private Boolean DagSD;
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

    public ThongTinSuDung(String maMay, String username, String sdt, Boolean dagSD) {
        this.maMay = maMay;
        this.username = username;
        this.sdt = sdt;
        DagSD = dagSD;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
