package Database;

import ThongTin.MayTinh;
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
                Connection con = new QuanLyTiemNetDBS().getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        )
        {
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
                Connection con = new QuanLyTiemNetDBS().getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        )
        {
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
}
