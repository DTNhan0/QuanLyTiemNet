package ThongTin;

public class TaiKhoan {
    private String Username;
    private String Sdt;
    private String Password;
    private boolean Role;
    private String Hangthanhvien;
    private int Sophutdadung;
    private double Sotien;
    private boolean Dangsudung;

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "Username='" + Username + '\'' +
                ", Sdt='" + Sdt + '\'' +
                ", Password='" + Password + '\'' +
                ", Role=" + Role +
                ", Hangthanhvien='" + Hangthanhvien + '\'' +
                ", Sophutdadung=" + Sophutdadung +
                ", Sotiennap=" + Sotien +
                ", Dangsudung=" + Dangsudung +
                '}';
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setSdt(String sdt) {
        Sdt = sdt;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public boolean isRole() {
        return Role;
    }

    public TaiKhoan() {super();}

    public TaiKhoan(String username, String sdt, String password, boolean role, String hangthanhvien, int sophutdadung, double sotien, boolean dangsudung) {
        Username = username;
        Sdt = sdt;
        Password = password;
        Role = role;
        Hangthanhvien = hangthanhvien;
        Sophutdadung = sophutdadung;
        Sotien = sotien;
        Dangsudung = dangsudung;
    }

    public void setRole(boolean role) {
        Role = role;
    }

    public String getHangthanhvien() {
        return Hangthanhvien;
    }

    public void setHangthanhvien(String hangthanhvien) {
        Hangthanhvien = hangthanhvien;
    }

    public int getSophutdadung() {
        return Sophutdadung;
    }

    public void setSophutdadung(int sophutdadung) {
        Sophutdadung = sophutdadung;
    }

    public double getSotien() {
        return Sotien;
    }

    public void setSotien(double sotiennap) {
        Sotien = sotiennap;
    }

    public boolean isDangsudung() {
        return Dangsudung;
    }

    public void setDangsudung(boolean dangsudung) {
        Dangsudung = dangsudung;
    }
}
