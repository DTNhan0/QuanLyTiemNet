package BLL.InFoThongTinSD;

import BLL.InFoMayTinh.MayTinh;
import BLL.InFoTaiKhoan.TaiKhoan;
import DAL.ThongTinSuDungDAO;

import java.util.List;

public class DSThongTinSD {
    List<ThongTinSuDung> DSThongTinSD = new ThongTinSuDungDAO().getAll();
    public List<ThongTinSuDung> getDSThongTinSD() {
        return DSThongTinSD;
    }
    public void setDSThongTinSD(List<ThongTinSuDung> DSThongTinSD) {
        this.DSThongTinSD = DSThongTinSD;
    }
    public void themSuLKgiuaTKvaMay(TaiKhoan tk, MayTinh mt){
        ThongTinSuDung ttsd = new ThongTinSuDung(mt.getMaMay(), tk.getUsername(), tk.getSdt(), true);
        try {
            new ThongTinSuDungDAO().addThongTinSuDung(ttsd);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void TatMay(TaiKhoan tk, MayTinh mt){

    }
}
