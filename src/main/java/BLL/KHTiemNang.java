package BLL;

import BLL.InFoTaiKhoan.DanhSachTK;
import BLL.InFoTaiKhoan.TaiKhoan;
import BLL.InFoThongTinSD.DSThongTinSD;
import BLL.InFoThongTinSD.ThongTinSuDung;
import DAL.TaiKhoanDAO;
import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KHTiemNang {
    public LocalDateTime TimDateGanNhat(List<LocalDateTime> dateTimeList) {
        return dateTimeList.stream()
                .sorted((dateTime1, dateTime2) ->
                        (int) ChronoUnit.SECONDS.between(dateTime1, LocalDateTime.now()) -
                                (int) ChronoUnit.SECONDS.between(dateTime2, LocalDateTime.now()))
                .findFirst()
                .orElse(null); // Trong trường hợp danh sách rỗng
    }

    public boolean Cach3Thang(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        if (dateTime1 == null || dateTime2 == null) {
            return false;
        }

        // Thêm 3 tháng và xem xét năm
        LocalDateTime threeMonthsAgo = dateTime2.minusMonths(3);
        int year1 = dateTime1.getYear();
        int year2 = dateTime2.getYear();

        // Kiểm tra xem dateTime1 có nằm trong khoảng thời gian 3 tháng trở về trước không
        return year1 != year2 || dateTime1.isAfter(threeMonthsAgo);
    }

    public boolean check3thagkoONl(TaiKhoan tk){
        List <LocalDateTime> thoigian = new ArrayList<>();
        for(ThongTinSuDung thongTinSuDung : new DSThongTinSD().getDSThongTinSD()){
            if(tk.getSdt().equals(thongTinSuDung.getSdt()) && !(thongTinSuDung.getDagSD())){
                thoigian.add(thongTinSuDung.getTgKetThuc());
            }
        }

        LocalDateTime tgGanNhat = TimDateGanNhat(thoigian);

        // Kiểm tra nếu tgGanNhat là null, tránh lỗi NullPointerException
        if (tgGanNhat == null) {
            return false; // hoặc thực hiện xử lý phù hợp tùy vào yêu cầu của bạn
        }

        LocalDateTime now = LocalDateTime.now();
        return Cach3Thang(tgGanNhat, now);
    }

    public static List<TaiKhoan> getTop10SD(List<TaiKhoan> taiKhoanList) {
        return taiKhoanList.stream()
                .sorted((t1, t2) -> Integer.compare(t2.getSophutdadung(), t1.getSophutdadung()))
                .limit(Math.min(taiKhoanList.size(), 10))
                .collect(Collectors.toList());
    }

    public static List<TaiKhoan> getTop10STN(List<TaiKhoan> taiKhoanList) {
        return taiKhoanList.stream()
                .sorted((t1, t2) -> Double.compare(t2.getSotienTichLuy(), t1.getSotienTichLuy()))
                .limit(Math.min(taiKhoanList.size(), 10))
                .collect(Collectors.toList());
    }

    public List<TaiKhoan> LietKeTKkoSD3thang(){
        List <TaiKhoan> res = new ArrayList<>();
        for(TaiKhoan tk : new DanhSachTK().getDSTaiKhoan()){
            if(check3thagkoONl(tk)){
                res.add(tk);
            }
        }
        return res;
    }

    public List<TaiKhoan> top10SD(){
        List <TaiKhoan> res = new ArrayList<>();
        for(TaiKhoan tk : new DanhSachTK().getDSTaiKhoan()){
            if(!(LietKeTKkoSD3thang().contains(tk))){
                res.add(tk);
            }
        }
        return getTop10SD(res);
    }

    public List <TaiKhoan> top10STN(){
        return getTop10STN(new DanhSachTK().getDSTaiKhoan());
    }
}
