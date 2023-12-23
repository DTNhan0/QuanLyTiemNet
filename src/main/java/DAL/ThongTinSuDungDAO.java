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
                ttsd.setTgBatDau(rs.getTimestamp("TGBATDAU").toLocalDateTime());
                ttsd.setTgKetThuc(rs.getTimestamp("TGKETTHUC").toLocalDateTime());
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
        String sql = "INSERT INTO THONGTINSUDUNG (TAIKHOAN, SDT, MAY, TGBATDAU, TGKETTHUC, DANGSUDUNG) VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection con = QLTiemNetConnectionDBS.getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        ) {
            pstm.setString(1, thongTinSuDung.getUsername());
            pstm.setString(2, thongTinSuDung.getSdt());
            pstm.setString(3, thongTinSuDung.getMaMay());

            Timestamp tgBatDauTimestamp = Timestamp.valueOf(thongTinSuDung.getTgBatDau());
            Timestamp tgKetThucTimestamp = Timestamp.valueOf(thongTinSuDung.getTgKetThuc());

            pstm.setTimestamp(4, tgBatDauTimestamp);
            pstm.setTimestamp(5, tgKetThucTimestamp);
            pstm.setBoolean(6, true);

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Dữ liệu đã được thêm vào bảng THONGTINSUDUNG.");
            } else {
                System.out.println("Không có dữ liệu nào được thêm vào bảng THONGTINSUDUNG.");
            }
        }
    }
}
