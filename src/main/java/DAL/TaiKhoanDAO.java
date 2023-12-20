package DAL;

import BLL.InFoTaiKhoan.TaiKhoan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanDAO {
    public List<TaiKhoan> getAll() throws Exception {
        List<TaiKhoan> lstTaiKhoan = new ArrayList<>();
        String sql = "SELECT * FROM TAIKHOAN";
        try (
                Connection con = new QLTiemNetConnectionDBS().getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        )
        {
            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan();
                tk.setUsername(rs.getString("USERNAME"));
                tk.setSdt(rs.getString("SDT"));
                tk.setPassword(rs.getString("PASSWORD"));
                tk.setRole(rs.getBoolean("ROLE"));
                tk.setHangthanhvien(rs.getString("HANGTHANHVIEN"));
                tk.setSophutdadung(rs.getInt("SOPHUTDADUNG"));
                tk.setSotienTichLuy(rs.getDouble("SOTIENTICHLUY"));
                tk.setSoTienConLai(rs.getDouble("SOTIENCONLAI"));
                tk.setDangSD(rs.getBoolean("DANGSUDUNG"));
                lstTaiKhoan.add(tk);
            }
            return lstTaiKhoan;
        }
    }

    public void ThemTaiKhoanDBS(TaiKhoan tk){
        String sql = "INSERT INTO TAIKHOAN (USERNAME, SDT, PASSWORD, ROLE, HANGTHANHVIEN, SOPHUTDADUNG, SOTIENTICHLUY, SOTIENCONLAI, DANGSUDUNG) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (
                Connection con = new QLTiemNetConnectionDBS().getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        )
        {
            pstm.setString(1, tk.getUsername());
            pstm.setString(2, tk.getSdt());
            pstm.setString(3, tk.getPassword());
            pstm.setBoolean(4, tk.isRole());
            pstm.setString(5, tk.getHangthanhvien());
            pstm.setInt(6, 0);
            pstm.setDouble(7, 0);
            pstm.setDouble(8, 0);
            pstm.setBoolean(9, false);

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Tài khoản đã được thêm vào DBS!!!");
            } else {
                System.out.println("Không thể thêm tài khoản vào DBS!!!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void XoaTaiKhoanDBS(TaiKhoan tk) {
        String sql = "DELETE FROM TAIKHOAN WHERE USERNAME = ? AND SDT = ?";
        try (
                Connection con = new QLTiemNetConnectionDBS().getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        ) {
            pstm.setString(1, tk.getUsername());
            pstm.setString(2, tk.getSdt());

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Tài khoản đã được xóa khỏi DBS!!!");
            } else {
                System.out.println("Không thể xóa tài khoản khỏi DBS. Kiểm tra lại thông tin đầu vào!!!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void CapNhatTKDBS(TaiKhoan tk) {
        String sql = "UPDATE TAIKHOAN SET PASSWORD = ?, ROLE = ?, HANGTHANHVIEN = ? WHERE USERNAME = ? AND SDT = ?";
        try (
                Connection con = new QLTiemNetConnectionDBS().getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        ) {
            pstm.setString(1, tk.getPassword());
            pstm.setBoolean(2, tk.isRole());
            pstm.setString(3, tk.getHangthanhvien());
            pstm.setString(4, tk.getUsername());
            pstm.setString(5, tk.getSdt());

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Tài khoản đã được cập nhật trong DBS!!!");
            } else {
                System.out.println("Không thể cập nhật tài khoản trong DBS. Kiểm tra lại thông tin đầu vào!!!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TaiKhoan findByUsernameAndSdt(String username, String password) throws Exception {
        String sql = "SELECT * FROM TAIKHOAN WHERE USERNAME = ? AND PASSWORD = ?";
        try (
                Connection con = new QLTiemNetConnectionDBS().getConnection();
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
                tk.setSotienTichLuy(rs.getDouble("SOTIENTICHLUY"));
                tk.setSoTienConLai(rs.getDouble("SOTIENCONLAI"));
                tk.setDangSD(rs.getBoolean("DANGSUDUNG"));
                return tk;
            } else {
                System.out.println("Không có tài khoản nào được tìm thấy với username và password cung cấp.");
                return null;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insert(TaiKhoan tk) throws Exception {
        String sql = "INSERT INTO TAIKHOAN (USERNAME, SDT, PASSWORD, ROLE, HANGTHANHVIEN, SOPHUTDADUNG, SOTIENTICHLUY, SOTIENCONLAI, DANGSUDUNG) VALUES (?, ?, ?, ?, null, 0, 0, 0, 0)";
        try (
                Connection con = new QLTiemNetConnectionDBS().getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        )
        {
            pstm.setString(1, tk.getUsername());
            pstm.setString(2, tk.getSdt());
            pstm.setString(3, tk.getPassword());

            return pstm.executeUpdate() > 0;
        }
    }

    public boolean updatePassword(TaiKhoan tk) throws Exception {
        String sql = "UPDATE TAIKHOAN SET PASSWORD = ? WHERE USERNAME = ? AND SDT = ?";
        try (
                Connection con = new QLTiemNetConnectionDBS().getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        )
        {
            pstm.setString(2, tk.getUsername());
            pstm.setString(3, tk.getSdt());
            pstm.setString(1, tk.getPassword());

            return pstm.executeUpdate() > 0;
        }
    }

    public void NaptienTK(TaiKhoan tk, Double tienMoi){
        String sql = "UPDATE TAIKHOAN SET SOTIENTICHLUY = ?, SOTIENCONLAI = ? WHERE USERNAME = ? AND SDT = ?";
        try (
                Connection con = new QLTiemNetConnectionDBS().getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        ) {
            pstm.setDouble(1, tk.getSotienTichLuy() + tienMoi);
            pstm.setDouble(2, tk.getSoTienConLai() + tienMoi);
            pstm.setString(3, tk.getUsername());
            pstm.setString(4, tk.getSdt());

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Tài khoản đã được cập nhật trong DBS!!!");
            } else {
                System.out.println("Không thể cập nhật tài khoản trong DBS. Kiểm tra lại thông tin đầu vào!!!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}