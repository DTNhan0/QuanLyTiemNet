package BLL.InFoMayTinh.LoaiPhong;

import BLL.InFoMayTinh.DanhSachMT;
import BLL.InFoMayTinh.MayTinh;

import java.util.ArrayList;
import java.util.List;

public class DSMayPhongThuongVIP {
    static List<MayTinh> DSMayPhongVIP;

    static {
        try {
            DSMayPhongVIP = new DanhSachMT().LayDSMayTheoPhong("VIP01");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<MayTinh> getDSMayPhongVIP() {
        return DSMayPhongVIP;
    }

    public void setDSMayPhong1(List<MayTinh> DSMayPhong1) {
        this.DSMayPhongVIP = DSMayPhong1;
    }

    public DSMayPhongThuongVIP() throws Exception {
        System.out.println("Không thể lấy các máy nằm ở phòng T01!!! ");
    }

    public List<MayTinh> HienCacMayDangOnl() {
        List<MayTinh> CacMayONL = new ArrayList<>();
        for (MayTinh mt : DSMayPhongVIP) {
            if (mt.isTrangThai() == true && mt.isCoSan() == false) {
                CacMayONL.add(mt);
            }
        }
        return CacMayONL;
    }
}
