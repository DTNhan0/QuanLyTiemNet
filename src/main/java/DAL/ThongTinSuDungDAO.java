package DAL;

import BLL.InFoTaiKhoan.TaiKhoan;
import BLL.InFoThongTinSD.ThongTinSuDung;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThongTinSuDungDAO {
    public List<ThongTinSuDung> getAll(){
        List<ThongTinSuDung> lstThongTinSD = new ArrayList<>();
        String sql = "SELECT * FROM THONGTINSUDUNG";
        try (
                Connection con = new QLTiemNetConnectionDBS().getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        )
        {
            while (rs.next()) {
                ThongTinSuDung ttsd = new ThongTinSuDung();
                ttsd.setUsername(rs.getString("TAIKHOAN"));
                ttsd.setSdt(rs.getString("SDT"));
                ttsd.setMaMay(rs.getString("MAY"));
                ttsd.setDagSD(rs.getBoolean("DANGSUDUNG"));
                lstThongTinSD.add(ttsd);
            }
            return lstThongTinSD;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void addThongTinSuDung(ThongTinSuDung thongTinSuDung) throws Exception {
        String sql = "INSERT INTO THONGTINSUDUNG (TAIKHOAN, SDT, MAY) VALUES (?, ?, ?)";

        try (
                Connection con = new QLTiemNetConnectionDBS().getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        ) {
            pstm.setString(2, thongTinSuDung.getUsername());
            pstm.setString(3, thongTinSuDung.getSdt());
            pstm.setString(1, thongTinSuDung.getMaMay());

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Dữ liệu đã được thêm vào bảng THONGTINSUDUNG.");
            } else {
                System.out.println("Không có dữ liệu nào được thêm vào bảng THONGTINSUDUNG.");
            }
        }
    }
    public void KhiTatMay(ThongTinSuDung thongTinSuDung){
        String sql = "UPDATE SET DANGSUDUNG = 0 WHERE MAY = ? AND SDT = ?";

        try (
                Connection con = new QLTiemNetConnectionDBS().getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        ) {
            pstm.setString(2, thongTinSuDung.getSdt());
            pstm.setString(1, thongTinSuDung.getMaMay());

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Dữ liệu đã được thêm vào bảng THONGTINSUDUNG.");
            } else {
                System.out.println("Không có dữ liệu nào được thêm vào bảng THONGTINSUDUNG.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
