package DAL;

import BLL.InFoMayTinh.MayTinh;

import java.sql.*;
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

    public static MayTinh TimTheoMaMay(String maMay) throws Exception {
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
            System.out.println("Không tìm thấy mã máy trong DBS!!!");
            return null;
        }
    }

    public static void ThemMayTinh(MayTinh mayTinh) throws Exception {
        String sql = "INSERT INTO MAYTINH (MAMAY, BAOHANH, NGAYMUA, THOIGIANDUNG, COSAN, TRANGTHAI, PHONG) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (
                Connection con = new QLTiemNetConnectionDBS().getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        ) {
            pstm.setString(1, mayTinh.getMaMay());
            pstm.setInt(2, mayTinh.getBaoHanh());
            pstm.setDate(3, new java.sql.Date(mayTinh.getNgayMua().getTime()));
            pstm.setInt(4, mayTinh.getThoiGianDung());
            pstm.setBoolean(5, true);
            pstm.setBoolean(6, true);
            pstm.setString(7, mayTinh.getPhong());

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Thêm máy vào DBS thành công!!!");
            } else {
                System.out.println("Lỗi không thể thêm máy vào DBS!!!");
            }
        }
    }

    public static void XoaMayTinh(MayTinh mayTinh) throws Exception {
        String sql = "DELETE FROM MAYTINH WHERE MAMAY = ? AND PHONG = ?";
        try (
                Connection con = new QLTiemNetConnectionDBS().getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        ) {
            pstm.setString(1, mayTinh.getMaMay());
            pstm.setString(2, mayTinh.getPhong());

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Đã xóa máy trong DBS thành công!!!");
            } else {
                System.out.println("Lỗi không thể xóa máy, không tìm thấy máy có mã: " + mayTinh.getMaMay() + " trong phòng: " + mayTinh.getPhong());
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
            pstm.setBoolean(4, mayTinh.isCoSan());
            pstm.setBoolean(5, mayTinh.isTrangThai());
            pstm.setString(6, mayTinh.getMaMay());
            pstm.setString(7, mayTinh.getPhong());

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Đã cập nhật máy trong DBS thành công!!!");
            } else {
                System.out.println("Lỗi không thể cập nhật máy, không tìm thấy máy có mã: " + mayTinh.getMaMay() + " trong phòng: " + mayTinh.getPhong());
            }
        }
    }

    public static boolean getTrangThaiMay(String IDMay) throws SQLException {
        String sql = "SELECT TRANGTHAI FROM MAYTINH WHERE MAMAY = ?";
        try (
                Connection con = QLTiemNetConnectionDBS.getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        ) {
            pstm.setString(1, IDMay);

            try (ResultSet resultSet = pstm.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("TRANGTHAI");
                }
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi khi lấy trạng thái từ CSDL:");
            ex.printStackTrace();
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false; // Trả về giá trị mặc định nếu không có kết quả hoặc xảy ra lỗi
    }

}
