package BLL.InFoTaiKhoan;

import BLL.InFoLichSuNap.LichSuNap;
import BLL.InFoMayTinh.DanhSachMT;
import BLL.InFoMayTinh.MayTinh;
import BLL.InFoThongTinSD.DSThongTinSD;
import BLL.InFoThongTinSD.ThongTinSuDung;
import DAL.LichSuNapDAO;
import DAL.TaiKhoanDAO;
import GUI.TrangThaiMT.TrangThaiMTController;

import java.time.LocalDateTime;
import java.util.List;

public class DanhSachTK {
    List<TaiKhoan> DSTaiKhoan;
    {
        try {
            DSTaiKhoan = new TaiKhoanDAO().getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static TaiKhoan TaiKhoanDangNhap;
    public static TaiKhoan getTaiKhoanDangNhap() {
        return TaiKhoanDangNhap;
    }

    public static void setTaiKhoanDangNhap(TaiKhoan taiKhoanDangNhap) {
        TaiKhoanDangNhap = taiKhoanDangNhap;
    }

    public List<TaiKhoan> getDSTaiKhoan() {
        return DSTaiKhoan;
    }

    public void setDSTaiKhoan(List<TaiKhoan> DSTaiKhoan) {
        this.DSTaiKhoan = DSTaiKhoan;
    }

    public void ThemTaiKhoan(TaiKhoan tk){
        DSTaiKhoan.add(tk);
        new TaiKhoanDAO().ThemTaiKhoanDBS(tk);
    }

    public TaiKhoan TimTKTraVeTK(String Username, String Sdt) throws Exception {
        for (TaiKhoan tk : DSTaiKhoan) {
            String tkUsername = tk.getUsername();
            String tkSdt = tk.getSdt();
            if (tkUsername.equals(Username) && tkSdt.equals(Sdt)) {
                return tk;
            }
        }
        System.out.println("Không tìm thấy tài khoản!!!");
        return null;
    }

    public int TimTaiKhoan(String Username, String Sdt){
        for(int i = 0; i < DSTaiKhoan.size(); i++){
            TaiKhoan tk = DSTaiKhoan.get(i);
            if(tk.getUsername().equals(Username) && tk.getSdt().equals(Sdt)){
                return i;
            }
        }
        return -1;
    }

    public void XoaTaiKhoan(TaiKhoan tk){
        int index_Xoa = TimTaiKhoan(tk.getUsername(), tk.getSdt());
        DSTaiKhoan.remove(index_Xoa);
        new TaiKhoanDAO().XoaTaiKhoanDBS(tk);
    }

    public void CapNhatTaiKhoan(TaiKhoan tk){
        int index_capNhat = TimTaiKhoan(tk.getUsername(), tk.getSdt());
        if (index_capNhat != -1) {
            DSTaiKhoan.set(index_capNhat, tk);
            new TaiKhoanDAO().CapNhatTKDBS(tk);
        } else {
            System.out.println("Cập nhật không thành công!!!");
        }
    }

    public void NapTien(TaiKhoan tk, double TienMoi){
        int index_TK = TimTaiKhoan(tk.getUsername(), tk.getSdt());
        if (index_TK != -1) {
            new TrangThaiMTController().checkNapKhidagChoi();
            DSTaiKhoan.set(index_TK, tk);
            LichSuNap lsn = new LichSuNap(tk.getUsername(), tk.getSdt(), TienMoi, LocalDateTime.now());
            new LichSuNapDAO().themLichSuNapDBS(lsn);
            new TaiKhoanDAO().NaptienTK(tk, TienMoi);
        } else {
            System.out.println("Cập nhật không thành công!!!");
        }
    }
}
