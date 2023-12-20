package BLL.InFoMayTinh;

import DAL.MayTinhDAO;

import java.util.ArrayList;
import java.util.List;

public class DanhSachMT {
    static List <MayTinh> DanhSachMay;
    static {
        try {
            DanhSachMay = new MayTinhDAO().getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<MayTinh> getDanhSachMay() {
        return DanhSachMay;
    }
    public void setDanhSachMay(List<MayTinh> danhSachMay) {
        DanhSachMay = danhSachMay;
    }

    public List <MayTinh> LayDSMayTheoPhong(String phong) throws Exception {
        List <MayTinh> ds = new DanhSachMT().getDanhSachMay();
        List <MayTinh> DSMayTheoPhong = new ArrayList<>();
        for(MayTinh mt : ds){
            if(mt.getPhong().equals(phong)){
                DSMayTheoPhong.add(mt);
            }
        }
        return DSMayTheoPhong;
    }
    public DanhSachMT() throws Exception {
    }
    public static int TimMay(String maMay, String phong) {
        for (int i = 0; i < DanhSachMay.size(); i++) {
            MayTinh may = DanhSachMay.get(i);
            if (may.getMaMay().equals(maMay) && may.getPhong().equals(phong)) {
                return i;
            }
        }
        return -1;
    }

    public static void ThemMay(MayTinh may) throws Exception {
        DanhSachMay.add(may);
        MayTinhDAO.insertMayTinh(may);
    }
    public static void XoaMay(MayTinh mayTinh) throws Exception {
        int index_MayXoa = TimMay(mayTinh.getMaMay(), mayTinh.getPhong());
        if (index_MayXoa != -1 && DanhSachMay.get(index_MayXoa).getPhong().equals(mayTinh.getPhong())) {
            DanhSachMay.remove(index_MayXoa);
            MayTinhDAO.deleteMayTinh(mayTinh);
        } else {
            System.out.println("Lỗi không tìm thấy máy có mã: " + mayTinh.getMaMay() + "và thuộc phòng: " + mayTinh.getPhong());
        }
    }

    public static void CapNhatMay(MayTinh may) throws Exception {
        int indexCapNhat = TimMay(may.getMaMay(), may.getPhong());
        if (indexCapNhat != -1) {
            DanhSachMay.set(indexCapNhat, may);
            MayTinhDAO.CapNhatMayTinh(may);
        } else {
            System.out.println("Failed to update MayTinh. MayTinh not found with MAMAY: " + may.getMaMay() + " in PHONG: " + may.getPhong());
        }
    }
}
