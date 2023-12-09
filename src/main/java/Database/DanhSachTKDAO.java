package Database;

import ThongTin.TaiKhoan;
import javafx.application.Platform;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DanhSachTKDAO {
    List <TaiKhoan> MainDanhSachTK = new ArrayList<>();
    public List<TaiKhoan> getMainDanhSachTK() {
        return MainDanhSachTK;
    }
    public void setMainDanhSachTK(List<TaiKhoan> mainDanhSachTK) {
        MainDanhSachTK = mainDanhSachTK;
    }

    public List <TaiKhoan> layTaiKhoantuDBS() {

        Connection con = null;
        try {
            con = new QuanLyTiemNetDBS().getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Statement stmt = null;

        try {
            stmt = con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String sql = "SELECT * FROM TAIKHOAN";
        try {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String username = rs.getString(1);
                String sdt = rs.getString(2);
                String matKhau = rs.getString(3);
                boolean vaiTro = rs.getBoolean(4);
                String hangThanhVien = rs.getString(5);
                int soPhutSD = rs.getInt(6);
                double soTien = rs.getDouble(7);
                boolean trangThaiSD = rs.getBoolean(8);

                TaiKhoan kh = new TaiKhoan(username, sdt, matKhau, vaiTro, hangThanhVien,
                        soPhutSD, soTien, trangThaiSD);
                MainDanhSachTK.add(kh);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return MainDanhSachTK;
    }
    public boolean insert(TaiKhoan tk) throws Exception {
        String sql = "INSERT INTO TAIKHOAN(USERNAME, SDT, PASSWORD, ROLE, HANGTHANHVIEN, SOPHUTDADUNG, SOTIEN, DANGSUDUNG) " +
                "VALUES (?, ?, ?, 0, NULL, 0, 0, 0)";
        try (
                Connection con = new QuanLyTiemNetDBS().getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        )
        {
            pstm.setString(1, tk.getUsername());
            pstm.setString(2, tk.getSdt());
            pstm.setString(3, tk.getPassword());

            return pstm.executeUpdate() > 0;
        }
    }

    public void themTaiKhoanVaoDB(TaiKhoan tk, boolean NutAdmin) {
        Platform.runLater(() -> {
            Connection con = null;
            try {
                con = new QuanLyTiemNetDBS().getConnection();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            PreparedStatement pstmt = null;
            boolean checkAdmin = false;
            if(NutAdmin){
                checkAdmin = true;
            }
            try {
                String sql = "INSERT INTO TAIKHOAN (USERNAME, SDT, PASSWORD, ROLE, HANGTHANHVIEN, SOPHUTDADUNG, SOTIEN, DANGSUDUNG) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, tk.getUsername());
                pstmt.setString(2, tk.getSdt());
                pstmt.setString(3, tk.getPassword());
                pstmt.setBoolean(4, checkAdmin);
                pstmt.setString(5, tk.getHangthanhvien());
                pstmt.setInt(6, 0);

                BigDecimal soTienBigDecimal = BigDecimal.valueOf(tk.getSotien());
                pstmt.setBigDecimal(7, soTienBigDecimal);

                pstmt.setBoolean(8, false);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                // Đóng PreparedStatement ở đây nếu cần
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            MainDanhSachTK.add(tk);
        });
    }
    public TaiKhoan findByUsernameAndPassword(String username, String password) throws Exception {
        String sql = "SELECT * FROM TAIKHOAN WHERE USERNAME = ? AND PASSWORD = ?";
        try (
                Connection con = new QuanLyTiemNetDBS().getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        )
        {
            pstm.setString(1, username);
            pstm.setString(2, password);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                TaiKhoan tk = new TaiKhoan();
                tk.setUsername(rs.getString("USERNAME"));
                tk.setSdt(rs.getString("SDT"));
                tk.setPassword(rs.getString("PASSWORD"));
                tk.setRole(rs.getBoolean("ROLE"));
                tk.setHangthanhvien(rs.getString("HANGTHANHVIEN"));
                tk.setSophutdadung(rs.getInt("SOPHUTDADUNG"));
                tk.setSotien(rs.getDouble("SOTIEN"));
                tk.setDangsudung(rs.getBoolean("DANGSUDUNG"));
                return tk;
            }

            return null;
        }
    }

    public TaiKhoan findByUsernameAndSdt(String username, String sdt) throws Exception {
        String sql = "SELECT * FROM TAIKHOAN WHERE USERNAME = ? AND SDT = ?";
        try (
                Connection con = new QuanLyTiemNetDBS().getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        )
        {
            pstm.setString(1, username);
            pstm.setString(2, sdt);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                TaiKhoan tk = new TaiKhoan();
                tk.setUsername(rs.getString("USERNAME"));
                tk.setSdt(rs.getString("SDT"));
                tk.setPassword(rs.getString("PASSWORD"));
                tk.setRole(rs.getBoolean("ROLE"));
                tk.setHangthanhvien(rs.getString("HANGTHANHVIEN"));
                tk.setSophutdadung(rs.getInt("SOPHUTDADUNG"));
                tk.setSotien(rs.getDouble("SOTIEN"));
                tk.setDangsudung(rs.getBoolean("DANGSUDUNG"));
                return tk;
            }

            return null;
        }
    }

    public boolean update(TaiKhoan tk) throws Exception {
        String sql = "UPDATE TAIKHOAN SET PASSWORD = ?, ROLE = ?, HANGTHANHVIEN = ?, SOPHUTDADUNG = ?, SOTIEN = ?, DANGSUDUNG = ? WHERE USERNAME = ?, SDT = ?";
        try (
                Connection con = new QuanLyTiemNetDBS().getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        )
        {
            pstm.setString(7, tk.getUsername());
            pstm.setString(8, tk.getSdt());
            pstm.setString(1, tk.getPassword());
            pstm.setBoolean(2, tk.isRole());
            pstm.setString(3, tk.getHangthanhvien());
            pstm.setInt(4, tk.getSophutdadung());
            pstm.setDouble(5, tk.getSotien());
            pstm.setBoolean(7, tk.isDangsudung());

            return pstm.executeUpdate() > 0;
        }
    }

    public boolean updatePassword(TaiKhoan tk) throws Exception {
        String sql = "UPDATE TAIKHOAN SET PASSWORD = ? WHERE USERNAME = ? AND SDT = ?";
        try (
                Connection con = new QuanLyTiemNetDBS().getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        )
        {
            pstm.setString(2, tk.getUsername());
            pstm.setString(3, tk.getSdt());
            pstm.setString(1, tk.getPassword());

            return pstm.executeUpdate() > 0;
        }
    }

    public void xoaTaiKhoanKhoiDB(TaiKhoan tk) {
        Platform.runLater(() -> {
            Connection con = null;
            try {
                con = new QuanLyTiemNetDBS().getConnection();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            PreparedStatement pstmt = null;

            try {
                String sql = "DELETE FROM TAIKHOAN WHERE USERNAME = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, tk.getUsername());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                // Đóng PreparedStatement ở đây nếu cần
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void capNhatTaiKhoanTrongDB(TaiKhoan tk, String TaiKhoanTF, String PassTF, String HangTVTF, String SotienTF,boolean NutAdmin) {
        Connection con = null;
        try {
            con = new QuanLyTiemNetDBS().getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        PreparedStatement pstmt = null;
        boolean vaiTro = false;
        if(NutAdmin){
            vaiTro = true;
        }
        try {
            String sql = "UPDATE TAIKHOAN SET USERNAME=?, PASSWORD=?, ROLE=?, HANGTHANHVIEN=?, SOTIEN=? WHERE SDT=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, TaiKhoanTF);
            pstmt.setString(2, PassTF);
            pstmt.setBoolean(3, vaiTro);
            pstmt.setString(4, HangTVTF);
            BigDecimal soTienBigDecimal = BigDecimal.valueOf(Long.parseLong(SotienTF));
            pstmt.setBigDecimal(5, soTienBigDecimal);
            pstmt.setString(6, tk.getSdt());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
