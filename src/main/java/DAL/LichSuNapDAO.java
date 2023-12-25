package DAL;

import BLL.InFoLichSuNap.LichSuNap;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LichSuNapDAO {
    public List<LichSuNap> getAll() throws Exception {
        List<LichSuNap> lst = new ArrayList<>();
        String sql = "SELECT * FROM LICHSUNAP";
        try (
                Connection con = QLTiemNetConnectionDBS.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        )
        {
            while(rs.next()) {
                LichSuNap lichSuNap = new LichSuNap();
                lichSuNap.setId(rs.getInt("ID"));
                lichSuNap.setTaiKhoan(rs.getString("TAIKHOAN"));
                lichSuNap.setSdt(rs.getString("SDT"));
                lichSuNap.setSoTien(rs.getDouble("SOTIEN"));
                lichSuNap.setTgNap(rs.getTimestamp("TGNAP").toLocalDateTime());
                lst.add(lichSuNap);
            }
            return lst;
        }
    }

    public List<LichSuNap> getAllByDate(LocalDate localDate) throws Exception {
        List<LichSuNap> lst = new ArrayList<>();
        String sql = "SELECT * FROM LICHSUNAP WHERE TGNAP >= ? AND TGNAP <= ?";
        try (
                Connection con = QLTiemNetConnectionDBS.getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        )
        {
            pstm.setTimestamp(1, Timestamp.valueOf(localDate.atStartOfDay()));
            pstm.setTimestamp(2, Timestamp.valueOf(localDate.atStartOfDay().plusDays(1)));

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    LichSuNap lichSuNap = new LichSuNap();
                    lichSuNap.setId(rs.getInt("ID"));
                    lichSuNap.setTaiKhoan(rs.getString("TAIKHOAN"));
                    lichSuNap.setSdt(rs.getString("SDT"));
                    lichSuNap.setSoTien(rs.getDouble("SOTIEN"));
                    lichSuNap.setTgNap(rs.getTimestamp("TGNAP").toLocalDateTime());
                    lst.add(lichSuNap);
                }
            }
            return lst;
        }
    }

    public Double getDoanhThuTheoNgay(LocalDate localDate) throws Exception {
        String sql = "SELECT SUM(SOTIEN) FROM LICHSUNAP WHERE TGNAP >= ? AND TGNAP <= ?";
        try (
                Connection con = QLTiemNetConnectionDBS.getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        )
        {
            pstm.setTimestamp(1, Timestamp.valueOf(localDate.atStartOfDay()));
            pstm.setTimestamp(2, Timestamp.valueOf(localDate.atStartOfDay().plusDays(1)));
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }
            return null;
        }
    }

    public LichSuNap getDoanhThuLonNhatTheoNgay(LocalDate localDate) throws Exception {
        String sql = "SELECT TOP 1 TAIKHOAN, SDT, SUM(SOTIEN) FROM LICHSUNAP WHERE TGNAP >= ? AND TGNAP <= ? GROUP BY TAIKHOAN, SDT ORDER BY SUM(SOTIEN) DESC";
        try (
                Connection con = QLTiemNetConnectionDBS.getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        )
        {
            pstm.setTimestamp(1, Timestamp.valueOf(localDate.atStartOfDay()));
            pstm.setTimestamp(2, Timestamp.valueOf(localDate.atStartOfDay().plusDays(1)));
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                LichSuNap lichSuNap = new LichSuNap();
                lichSuNap.setTaiKhoan(rs.getString("TAIKHOAN"));
                lichSuNap.setSdt(rs.getString("SDT"));
                lichSuNap.setSoTien(rs.getDouble(3));
                return lichSuNap;
            }
            return null;
        }
    }

    public Double getDoanhThuTheoTime(LocalDate ngayBatDau, LocalDate ngayKetThuc) throws Exception {
        String sql = "SELECT SUM(SOTIEN) FROM LICHSUNAP WHERE TGNAP >= ? AND TGNAP <= ?";
        try (
                Connection con = QLTiemNetConnectionDBS.getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        )
        {
            pstm.setTimestamp(1, Timestamp.valueOf(ngayBatDau.atStartOfDay()));
            pstm.setTimestamp(2, Timestamp.valueOf(ngayKetThuc.atStartOfDay()));
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }
            return null;
        }
    }

    public LichSuNap getDoanhThuLonNhatTheoTime(LocalDate ngayBatDau, LocalDate ngayKetThuc) throws Exception {
        String sql = "SELECT TOP 1 TAIKHOAN, SDT, SUM(SOTIEN) FROM LICHSUNAP WHERE TGNAP >= ? AND TGNAP <= ? GROUP BY TAIKHOAN, SDT ORDER BY SUM(SOTIEN) DESC";
        try (
                Connection con = QLTiemNetConnectionDBS.getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        )
        {
            pstm.setTimestamp(1, Timestamp.valueOf(ngayBatDau.atStartOfDay()));
            pstm.setTimestamp(2, Timestamp.valueOf(ngayKetThuc.atStartOfDay()));
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                LichSuNap lichSuNap = new LichSuNap();
                lichSuNap.setTaiKhoan(rs.getString("TAIKHOAN"));
                lichSuNap.setSdt(rs.getString("SDT"));
                lichSuNap.setSoTien(rs.getDouble(3));
                return lichSuNap;
            }
            return null;
        }
    }

    public List<LichSuNap> getAllByTime(LocalDate tgBatDau, LocalDate tgKetThuc) throws Exception {
        List<LichSuNap> lst = new ArrayList<>();
        String sql = "SELECT * FROM LICHSUNAP WHERE TGNAP >= ? AND TGNAP <= ?";
        try (
                Connection con = QLTiemNetConnectionDBS.getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        )
        {
            pstm.setTimestamp(1, Timestamp.valueOf(tgBatDau.atStartOfDay()));
            pstm.setTimestamp(2, Timestamp.valueOf(tgKetThuc.atStartOfDay().plusDays(1)));

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    LichSuNap lichSuNap = new LichSuNap();
                    lichSuNap.setId(rs.getInt("ID"));
                    lichSuNap.setTaiKhoan(rs.getString("TAIKHOAN"));
                    lichSuNap.setSdt(rs.getString("SDT"));
                    lichSuNap.setSoTien(rs.getDouble("SOTIEN"));
                    lichSuNap.setTgNap(rs.getTimestamp("TGNAP").toLocalDateTime());
                    lst.add(lichSuNap);
                }
            }
            return lst;
        }
    }

    public LichSuNap getDoanhThuMax(LocalDate ngayBatDau, LocalDate ngayKetThuc) throws Exception {
        String sql = "SELECT CONVERT(DATE, TGNAP) as THOIGIAN, SUM(SOTIEN) as TONGTIEN\n" +
                "FROM LICHSUNAP\n" +
                "WHERE TGNAP >= ? AND TGNAP <= ?\n" +
                "GROUP BY CONVERT(DATE, TGNAP)";
        try (
                Connection con = QLTiemNetConnectionDBS.getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        )
        {
            pstm.setTimestamp(1, Timestamp.valueOf(ngayBatDau.atStartOfDay()));
            pstm.setTimestamp(2, Timestamp.valueOf(ngayKetThuc.atStartOfDay()));
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                LichSuNap lichSuNap = new LichSuNap();
                lichSuNap.setTgNap(rs.getTimestamp("THOIGIAN").toLocalDateTime());
                lichSuNap.setSoTien(rs.getDouble("TONGTIEN"));
                return lichSuNap;
            }
            return null;
        }
    }

    public void themLichSuNapDBS(LichSuNap lichSuNap) {
        String sql = "INSERT INTO LICHSUNAP(TAIKHOAN, SDT, SOTIEN, TGNAP) VALUES (?, ?, ?, GETDATE())";
        try (
                Connection con = QLTiemNetConnectionDBS.getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
        )
        {
            pstm.setString(1, lichSuNap.getTaiKhoan());
            pstm.setString(2, lichSuNap.getSdt());
            pstm.setDouble(3, lichSuNap.getSoTien());

            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Lịch sử nạp đã được cập nhật trong DBS!!!");
            } else {
                System.out.println("Không thể cập nhật lịch sử nạp trong DBS. Kiểm tra lại thông tin đầu vào!!!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
