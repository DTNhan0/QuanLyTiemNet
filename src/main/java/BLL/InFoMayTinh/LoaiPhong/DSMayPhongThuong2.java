package BLL.InFoMayTinh.LoaiPhong;

import BLL.InFoMayTinh.DanhSachMT;
import BLL.InFoMayTinh.MayTinh;

import java.util.ArrayList;
import java.util.List;

public class DSMayPhongThuong2 {
    List <MayTinh> DSMayPhong2;
    {
        try {
            DSMayPhong2 = new DanhSachMT().LayDSMayTheoPhong("T02");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<MayTinh> getDSMayPhong2() {
        return DSMayPhong2;
    }

    public void setDSMayPhong1(List<MayTinh> DSMayPhong1) {
        this.DSMayPhong2 = DSMayPhong1;
    }

    public List<MayTinh> HienCacMayDangOnl() {
        List<MayTinh> CacMayONL = new ArrayList<>();
        for (MayTinh mt : DSMayPhong2) {
            if (mt.isTrangThai() == true && mt.isCoSan() == false) {
                CacMayONL.add(mt);
            }
        }
        return CacMayONL;
    }
}
