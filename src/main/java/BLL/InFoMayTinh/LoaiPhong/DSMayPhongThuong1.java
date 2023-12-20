package BLL.InFoMayTinh.LoaiPhong;

import BLL.InFoMayTinh.DanhSachMT;
import BLL.InFoMayTinh.MayTinh;

import java.util.ArrayList;
import java.util.List;

public class DSMayPhongThuong1 extends DanhSachMT {
    static List<MayTinh> DSMayPhong1;

    static {
        try {
            DSMayPhong1 = new DanhSachMT().LayDSMayTheoPhong("T01");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<MayTinh> getDSMayPhong1() {
        return DSMayPhong1;
    }

    public void setDSMayPhong1(List<MayTinh> DSMayPhong1) {
        this.DSMayPhong1 = DSMayPhong1;
    }

    public DSMayPhongThuong1() throws Exception {
        System.out.println("Không thể lấy các máy nằm ở phòng T01!!! ");
    }

    public List<MayTinh> HienCacMayDangOnl() {
        List<MayTinh> CacMayONL = new ArrayList<>();
        for (MayTinh mt : DSMayPhong1) {
            if (mt.isTrangThai() == true && mt.isCoSan() == false) {
                CacMayONL.add(mt);
            }
        }
        return CacMayONL;
    }

}
