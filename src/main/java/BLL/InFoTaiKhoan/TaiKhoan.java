package BLL.InFoTaiKhoan;

public class TaiKhoan {
    private String Username, Password, Sdt, Hangthanhvien;
    private boolean Role, DangSD, TonTai;
    private int Sophutdadung;
    private double SotienTichLuy, SoTienConLai;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setSdt(String sdt) {
        Sdt = sdt;
    }

    public String getHangthanhvien() {
        return Hangthanhvien;
    }

    public void setHangthanhvien(String hangthanhvien) {
        Hangthanhvien = hangthanhvien;
    }

    public boolean isRole() {
        return Role;
    }

    public void setRole(boolean role) {
        Role = role;
    }

    public boolean isDangSD() {
        return DangSD;
    }

    public void setDangSD(boolean dangSD) {
        DangSD = dangSD;
    }

    public boolean isTonTai() {
        return TonTai;
    }

    public void setTonTai(boolean tonTai) {
        TonTai = tonTai;
    }

    public int getSophutdadung() {
        return Sophutdadung;
    }

    public void setSophutdadung(int sophutdadung) {
        Sophutdadung = sophutdadung;
    }

    public double getSotienTichLuy() {
        return SotienTichLuy;
    }

    public void setSotienTichLuy(double sotienTichLuy) {
        SotienTichLuy = sotienTichLuy;
    }

    public double getSoTienConLai() {
        return SoTienConLai;
    }

    public void setSoTienConLai(double soTienConLai) {
        SoTienConLai = soTienConLai;
    }

    public TaiKhoan() {
        super();
    }

    public TaiKhoan(String username, String password, String sdt, String hangthanhvien, boolean role) {
        Username = username;
        Password = password;
        Sdt = sdt;
        Hangthanhvien = hangthanhvien;
        Role = role;
    }

}
