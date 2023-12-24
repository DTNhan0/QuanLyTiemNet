package BLL.InFoThongTinSD;

import BLL.InFoMayTinh.MayTinh;
import BLL.InFoTaiKhoan.TaiKhoan;
import DAL.TaiKhoanDAO;
import DAL.ThongTinSuDungDAO;
import java.time.Duration;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static mainscript.quanlytiemnet.MainController.StackingOnl;

public class DSThongTinSD {
    List<ThongTinSuDung> DSThongTinSD = new ThongTinSuDungDAO().getAll();

    public List<ThongTinSuDung> getDSThongTinSD() {
        return DSThongTinSD;
    }

    public void setDSThongTinSD(List<ThongTinSuDung> DSThongTinSD) {
        this.DSThongTinSD = DSThongTinSD;
    }

    public void themSuLKgiuaTKvaMay(TaiKhoan tk, String maMay){
        LocalDateTime now = LocalDateTime.now();
        ThongTinSuDung ttsd = new ThongTinSuDung(maMay, tk.getUsername(), tk.getSdt(), true, now, now);
        try {
            new ThongTinSuDungDAO().addThongTinSuDung(ttsd);
            StackingOnl.add(ttsd);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ThongTinSuDung TimTKdagSDtrongThongTinSD(String maMay){
        for(ThongTinSuDung ttsd : DSThongTinSD){
            if(ttsd.getMaMay().equals(maMay) && ttsd.getDagSD()){
                return ttsd;
            }
        }
        System.out.println("Không tìm được tài khoản trong thông tin sử dụng theo mã máy!!!");
        return null;
    }

    public ThongTinSuDung TimMTdagSDtrongThongTinSD(String sdt){
        for(ThongTinSuDung ttsd : DSThongTinSD){
            if(ttsd.getSdt().equals(sdt) && ttsd.getDagSD()){
                return ttsd;
            }
        }
        System.out.println("Không tìm được máy trong thông tin sử dụng theo sđt!!!");
        return null;
    }

    public List <ThongTinSuDung> LayCacMayDagSDTrongTTSD(){
        List <ThongTinSuDung> res = new ArrayList<>();
        for(ThongTinSuDung ttsd : DSThongTinSD){
            if(ttsd.getDagSD()){
                res.add(ttsd);
            }
        }
        return res;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public String TongGioChoiHomNay() {
        LocalTime sum = LocalTime.MIN;
        LocalDateTime now = LocalDateTime.now();
        for (ThongTinSuDung ttsd : new ThongTinSuDungDAO().getAll()) {
            if (!ttsd.getDagSD() && ttsd.getTgKetThuc().format(formatter2).equals(now.format(formatter2))) {
                LocalTime startTime = ttsd.getTgBatDau().toLocalTime();
                LocalTime endTime = ttsd.getTgKetThuc().toLocalTime();

                // Tính độ chênh lệch giữa thời điểm bắt đầu và kết thúc
                Duration duration = Duration.between(startTime, endTime);

                // Cộng dồn thời gian
                sum = sum.plusSeconds((long) duration.toSeconds());
            }
        }

        // Chuyển đổi tổng thời gian thành định dạng HH:mm
        return sum.format(formatter);
    }
}
