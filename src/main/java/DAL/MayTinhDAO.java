package DAL;

import BLL.InFoMayTinh.MayTinh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MayTinhDAO {
    public List<MayTinh> getAll() throws Exception {
        List<MayTinh> lstMayTinh = new ArrayList<>();
        String sql = "SELECT * FROM MAYTINH";
        try (
                Connection con = new QLTiemNetConnectionDBS().getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()) {
                MayTinh mt = new MayTinh();
                mt.setMaMay(rs.getString("MAMAY"));
                mt.setBaoHanh(rs.getInt("BAOHANH"));
                mt.setNgayMua(rs.getDate("NGAYMUA"));
                mt.setThoiGianDung(rs.getInt("THOIGIANDUNG"));
                mt.setCoSan(rs.getBoolean("COSAN"));
                mt.setTrangThai(rs.getBoolean("TRANGTHAI"));
                mt.setPhong(rs.getString("PHONG"));
                lstMayTinh.add(mt);
            }
            return lstMayTinh;
        }
    }

    public static MayTinh findByMamay(String maMay) throws Exception {
        String sql = "SELECT * FROM MAYTINH WHERE MAMAY = ?";
        try (
                Connection con = new QLTiemNetConnectionDBS().getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        ) {
            pstm.setString(1, maMay);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                MayTinh mt = new MayTinh();
                mt.setMaMay(rs.getString("MAMAY"));
                mt.setBaoHanh(rs.getInt("BAOHANH"));
                mt.setNgayMua(rs.getDate("NGAYMUA"));
                mt.setThoiGianDung(rs.getInt("THOIGIANDUNG"));
                mt.setCoSan(rs.getBoolean("COSAN"));
                mt.setTrangThai(rs.getBoolean("TRANGTHAI"));
                mt.setPhong(rs.getString("PHONG"));
                return mt;
            }
            return null;
        }
    }

    public static void insertMayTinh(MayTinh mayTinh) throws Exception {
        String sql = "INSERT INTO MAYTINH (MAMAY, BAOHANH, NGAYMUA, THOIGIANDUNG, COSAN, TRANGTHAI, PHONG) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (
                Connection con = new QLTiemNetConnectionDBS().getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        ) {
            pstm.setString(1, mayTinh.getMaMay());
            pstm.setInt(2, mayTinh.getBaoHanh());
            pstm.setDate(3, new java.sql.Date(mayTinh.getNgayMua().getTime()));
            pstm.setInt(4, mayTinh.getThoiGianDung());
            pstm.setBoolean(5, mayTinh.isCoSan());
            pstm.setBoolean(6, mayTinh.isTrangThai());
            pstm.setString(7, mayTinh.getPhong());

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("MayTinh inserted successfully!");
            } else {
                System.out.println("Failed to insert MayTinh!");
            }
        }
    }

    public static void deleteMayTinh(MayTinh mayTinh) throws Exception {
        String sql = "DELETE FROM MAYTINH WHERE MAMAY = ? AND PHONG = ?";
        try (
                Connection con = new QLTiemNetConnectionDBS().getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        ) {
            pstm.setString(1, mayTinh.getMaMay());
            pstm.setString(2, mayTinh.getPhong());

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("MayTinh deleted successfully!");
            } else {
                System.out.println("Failed to delete MayTinh. MayTinh not found with MAMAY: " + mayTinh.getMaMay() + " in PHONG: " + mayTinh.getPhong());
            }
        }
    }

    public static void CapNhatMayTinh(MayTinh mayTinh) throws Exception {
        String sql = "UPDATE MAYTINH SET BAOHANH = ?, NGAYMUA = ?, THOIGIANDUNG = ?, COSAN = ?, TRANGTHAI = ? WHERE MAMAY = ? AND PHONG = ?";
        try (
                Connection con = new QLTiemNetConnectionDBS().getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        ) {
            pstm.setInt(1, mayTinh.getBaoHanh());
            pstm.setDate(2, new java.sql.Date(mayTinh.getNgayMua().getTime()));
            pstm.setInt(3, mayTinh.getThoiGianDung());
            pstm.setBoolean(4, true);
            pstm.setBoolean(5, false);
            pstm.setString(6, mayTinh.getMaMay());
            pstm.setString(7, mayTinh.getPhong());

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("MayTinh updated successfully!");
            } else {
                System.out.println("Failed to update MayTinh. MayTinh not found with MAMAY: " + mayTinh.getMaMay() + " in PHONG: " + mayTinh.getPhong());
            }
        }
    }

}
