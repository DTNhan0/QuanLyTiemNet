package DAL;

import BLL.InFoTaiKhoan.TaiKhoan;
import BLL.InFoThongTinSD.ThongTinSuDung;

import java.sql.*;
import java.time.LocalDate;
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

    public List<ThongTinSuDung> getThongTinSuDung(LocalDate tgBatDau, LocalDate tgKetThuc) throws Exception {
        List<ThongTinSuDung> lst = new ArrayList<>();
        String sql = "SELECT *\n" +
                "FROM (\n" +
                "\tSELECT\n" +
                "\t\tID, TAIKHOAN, SDT, MAY,\n" +
                "\t\tCASE\n" +
                "\t\t\tWHEN TGBATDAU >= ? AND TGKETTHUC < ? THEN TGBATDAU\n" +
                "\t\t\tWHEN TGBATDAU >= ? AND TGBATDAU < ? AND TGKETTHUC >= ? THEN TGBATDAU\n" +
                "\t\t\tWHEN TGBATDAU < ? AND TGKETTHUC >= ? AND TGKETTHUC < ? THEN ?\n" +
                "\t\t\tWHEN TGBATDAU < ? AND TGKETTHUC >= ? THEN ?\n" +
                "\t\t\tELSE NULL\n" +
                "\t\tEND AS STARTTIME,\n" +
                "\t\tCASE\n" +
                "\t\t\tWHEN TGBATDAU >= ? AND TGKETTHUC < ? THEN TGKETTHUC\n" +
                "\t\t\tWHEN TGBATDAU >= ? AND TGBATDAU < ? AND TGKETTHUC >= ? THEN ?\n" +
                "\t\t\tWHEN TGBATDAU < ? AND TGKETTHUC >= ? AND TGKETTHUC < ? THEN TGKETTHUC\n" +
                "\t\t\tWHEN TGBATDAU < ? AND TGKETTHUC >= ? THEN ?\n" +
                "\t\t\tELSE NULL\n" +
                "\t\tEND AS ENDTIME\n" +
                "\tFROM thongtinsudung\n" +
                "\tWHERE DANGSUDUNG = 0\n" +
                "\t) AS subquery\n" +
                "WHERE STARTTIME IS NOT NULL OR ENDTIME IS NOT NULL;";
        try (
                Connection con = QLTiemNetConnectionDBS.getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        )
        {
            pstm.setTimestamp(1, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(3, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(6, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(7, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(9, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(10, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(12, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(13, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(15, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(19, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(20, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(22, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(2, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(4, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(5, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(8, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(11, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(14, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(16, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(17, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(18, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(21, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(23, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(24, Timestamp.valueOf(tgKetThuc.atStartOfDay()));

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    ThongTinSuDung thongTinSuDung = new ThongTinSuDung();
                    thongTinSuDung.setId(rs.getInt("ID"));
                    thongTinSuDung.setUsername(rs.getString("TAIKHOAN"));
                    thongTinSuDung.setSdt(rs.getString("SDT"));
                    thongTinSuDung.setMaMay(rs.getString("MAY"));
                    thongTinSuDung.setTgBatDau(rs.getTimestamp("STARTTIME").toLocalDateTime());
                    thongTinSuDung.setTgKetThuc(rs.getTimestamp("ENDTIME").toLocalDateTime());
                    lst.add(thongTinSuDung);
                }
            }
            return lst;
        }
    }

    public Integer getTongThoiGianChoi(LocalDate tgBatDau, LocalDate tgKetThuc) throws Exception {
        String sql = "WITH BangPhu AS(\n" +
                "\t\tSELECT *\n" +
                "\tFROM (\n" +
                "\t\tSELECT\n" +
                "\t\t\tID, TAIKHOAN, SDT, MAY,\n" +
                "\t\t\tCASE\n" +
                "\t\t\t\tWHEN TGBATDAU >= ? AND TGKETTHUC < ? THEN TGBATDAU\n" +
                "\t\t\t\tWHEN TGBATDAU >= ? AND TGBATDAU < ? AND TGKETTHUC >= ? THEN TGBATDAU\n" +
                "\t\t\t\tWHEN TGBATDAU < ? AND TGKETTHUC >= ? AND TGKETTHUC < ? THEN ?\n" +
                "\t\t\t\tWHEN TGBATDAU < ? AND TGKETTHUC >= ? THEN ?\n" +
                "\t\t\t\tELSE NULL\n" +
                "\t\t\tEND AS STARTTIME,\n" +
                "\t\t\tCASE\n" +
                "\t\t\t\tWHEN TGBATDAU >= ? AND TGKETTHUC < ? THEN TGKETTHUC\n" +
                "\t\t\t\tWHEN TGBATDAU >= ? AND TGBATDAU < ? AND TGKETTHUC >= ? THEN ?\n" +
                "\t\t\t\tWHEN TGBATDAU < ? AND TGKETTHUC >= ? AND TGKETTHUC < ? THEN TGKETTHUC\n" +
                "\t\t\t\tWHEN TGBATDAU < ? AND TGKETTHUC >= ? THEN ?\n" +
                "\t\t\t\tELSE NULL\n" +
                "\t\t\tEND AS ENDTIME\n" +
                "\t\tFROM thongtinsudung\n" +
                "\t\tWHERE DANGSUDUNG = 0\n" +
                "\t\t) AS subquery\n" +
                "\tWHERE STARTTIME IS NOT NULL OR ENDTIME IS NOT NULL\n" +
                ")\n" +
                "SELECT SUM(DATEDIFF(MINUTE, STARTTIME, ENDTIME)) as TongThoiGian\n" +
                "FROM BangPhu";
        try (
                Connection con = QLTiemNetConnectionDBS.getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        )
        {
            pstm.setTimestamp(1, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(3, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(6, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(7, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(9, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(10, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(12, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(13, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(15, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(19, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(20, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(22, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(2, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(4, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(5, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(8, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(11, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(14, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(16, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(17, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(18, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(21, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(23, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            pstm.setTimestamp(24, Timestamp.valueOf(tgKetThuc.atStartOfDay()));
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
            return null;
        }
    }
}
