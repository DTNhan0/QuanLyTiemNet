package BLL.InFoLichSuNap;

import DAL.LichSuNapDAO;
import DAL.TaiKhoanDAO;

import java.time.LocalDate;
import java.util.List;

public class DSLichSuNap {
    List<LichSuNap> dsLichSuNap;
    {
        try {
            dsLichSuNap = new LichSuNapDAO().getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<LichSuNap> getDsLichSuNap() {
        return dsLichSuNap;
    }

    public void setDsLichSuNap(List<LichSuNap> dsLichSuNap) {
        this.dsLichSuNap = dsLichSuNap;
    }

    public List<LichSuNap> getDsLichSuNapTheoNgay(LocalDate localDate) throws Exception {
        return dsLichSuNap = new LichSuNapDAO().getAllByDate(localDate);
    }

    public List<LichSuNap> getDsLichSuNapTheoTime(LocalDate tgBatDau, LocalDate tgKetThuc) throws Exception {
        return dsLichSuNap = new LichSuNapDAO().getAllByTime(tgBatDau, tgKetThuc);
    }

    public Double getDoanhThuTheoNgay(LocalDate ngay) throws Exception {
        return new LichSuNapDAO().getDoanhThuTheoNgay(ngay);
    }

    public LichSuNap getDoanhThuLonNhatTheoNgay(LocalDate ngay) throws Exception {
        LichSuNap lichSuNap = new LichSuNapDAO().getDoanhThuLonNhatTheoNgay(ngay);
        return lichSuNap;
    }

    public Double getDoanhThuTheoTime(LocalDate ngayBatDau, LocalDate ngayKetThuc) throws Exception {
        return new LichSuNapDAO().getDoanhThuTheoTime(ngayBatDau, ngayKetThuc);
    }

    public LichSuNap getDoanhThuLonNhatTheoTime(LocalDate ngayBatDau, LocalDate ngayKetThuc) throws Exception {
        LichSuNap lichSuNap = new LichSuNapDAO().getDoanhThuLonNhatTheoTime(ngayBatDau, ngayKetThuc);
        return lichSuNap;
    }

    public LichSuNap getDoanhThuMax(LocalDate ngayBatDau, LocalDate ngayKetThuc) throws Exception {
        LichSuNap lichSuNap = new LichSuNapDAO().getDoanhThuMax(ngayBatDau, ngayKetThuc);
        return lichSuNap;
    }

    public void themLichSuNap(LichSuNap lichSuNap) {
        dsLichSuNap.add(lichSuNap);
        new LichSuNapDAO().themLichSuNapDBS(lichSuNap);
    }
}
