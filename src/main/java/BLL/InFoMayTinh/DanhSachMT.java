package BLL.InFoMayTinh;

import DAL.MayTinhDAO;

import java.util.ArrayList;
import java.util.List;

public class DanhSachMT {
    List <MayTinh> DSMayTinh;
    {
        try {
            DSMayTinh = new MayTinhDAO().getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<MayTinh> getDSMayTinh() {
        return DSMayTinh;
    }

    public void setDSMayTinh(List<MayTinh> DSMayTinh) {
        this.DSMayTinh = DSMayTinh;
    }

    public List<MayTinh> LayDSMayTheoPhong(String phong) throws Exception {
        List<MayTinh> ds = new DanhSachMT().getDSMayTinh();
        List<MayTinh> DSMayTheoPhong = new ArrayList<>();
        for (MayTinh mt : ds) {
            if (mt.getPhong().equals(phong)) {
                DSMayTheoPhong.add(mt);
            }
        }
        return DSMayTheoPhong;
    }

    public MayTinh TimMayTraVeMT(String maMay){
        for(MayTinh mt : DSMayTinh){
            if(mt.getMaMay().equals(maMay)){
                return mt;
            }
        }
        System.out.println("Không tìm thấy mã máy!!!");
        return null;
    }
    public int TimMay(String maMay, String phong) {
        for (int i = 0; i < DSMayTinh.size(); i++) {
            MayTinh may = DSMayTinh.get(i);
            if (may.getMaMay().equals(maMay) && may.getPhong().equals(phong)) {
                return i;
            }
        }
        return -1;
    }

    public void ThemMay(MayTinh may) throws Exception {
        DSMayTinh.add(may);
        MayTinhDAO.ThemMayTinh(may);
    }

    public void XoaMay(MayTinh mayTinh) throws Exception {
        int index_MayXoa = TimMay(mayTinh.getMaMay(), mayTinh.getPhong());
        if (index_MayXoa != -1 && DSMayTinh.get(index_MayXoa).getPhong().equals(mayTinh.getPhong())) {
            DSMayTinh.remove(index_MayXoa);
            MayTinhDAO.XoaMayTinh(mayTinh);
        } else {
            System.out.println("Lỗi không tìm thấy máy có mã: " + mayTinh.getMaMay() + "và thuộc phòng: " + mayTinh.getPhong());
        }
    }

    public void CapNhatMay(MayTinh may) throws Exception {
        int indexCapNhat = TimMay(may.getMaMay(), may.getPhong());
        if (indexCapNhat != -1) {
            DSMayTinh.set(indexCapNhat, may);
            MayTinhDAO.CapNhatMayTinh(may);
        } else {
            System.out.println("Failed to update MayTinh. MayTinh not found with MAMAY: " + may.getMaMay() + " in PHONG: " + may.getPhong());
        }
    }

    public int MayDangONL(){
        int count = 0;
        try {
            for(MayTinh mt : new MayTinhDAO().getAll()){
                if(mt.isCoSan() == false && mt.isTrangThai() == true){
                    count++;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public int MayCoSan(){
        int count = 0;
        try {
            for(MayTinh mt : new MayTinhDAO().getAll()){
                if(mt.isCoSan() == true && mt.isTrangThai() == true){
                    count++;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return count;
    }

}
