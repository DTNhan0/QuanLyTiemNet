package BLL.InFoThongTinSD;

import BLL.InFoMayTinh.MayTinh;
import BLL.InFoTaiKhoan.TaiKhoan;
import DAL.TaiKhoanDAO;
import DAL.ThongTinSuDungDAO;

import java.time.LocalDateTime;
import java.util.List;

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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public ThongTinSuDung TimTKtrongThongTinSD(String maMay){
        for(ThongTinSuDung ttsd : DSThongTinSD){
            if(ttsd.getMaMay().equals(maMay)){
                return ttsd;
            }
        }
        System.out.println("Khong tim dc ttsd theo ma may!!!");
        return null;
    }

}
