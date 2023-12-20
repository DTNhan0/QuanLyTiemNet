package BLL.InFoTaiKhoan;

import DAL.TaiKhoanDAO;

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

    public TaiKhoan getTaiKhoanDangNhap() {
        return TaiKhoanDangNhap;
    }

    public void setTaiKhoanDangNhap(TaiKhoan taiKhoanDangNhap) {
        TaiKhoanDangNhap = taiKhoanDangNhap;
    }

    public List<TaiKhoan> getDSTaiKhoan() {
        return DSTaiKhoan;
    }

    public void setDSTaiKhoan(List<TaiKhoan> DSTaiKhoan) {
        this.DSTaiKhoan = DSTaiKhoan;
    }

    public void ThemTaiKhoan(TaiKhoan tk) {
        DSTaiKhoan.add(tk);
        new TaiKhoanDAO().ThemTaiKhoanDBS(tk);
    }

    public int TimTaiKhoan(String Username, String Sdt) {
        for (int i = 0; i < DSTaiKhoan.size(); i++) {
            TaiKhoan tk = DSTaiKhoan.get(i);
            if (tk.getUsername().equals(Username) && tk.getSdt().equals(Sdt)) {
                return i;
            }
        }
        return -1;
    }

    public void XoaTaiKhoan(TaiKhoan tk) {
        int index_Xoa = TimTaiKhoan(tk.getUsername(), tk.getSdt());
        DSTaiKhoan.remove(index_Xoa);
        new TaiKhoanDAO().XoaTaiKhoanDBS(tk);
    }

    public void CapNhatTaiKhoan(TaiKhoan tk) {
        int index_capNhat = TimTaiKhoan(tk.getUsername(), tk.getSdt());
        if (index_capNhat != -1) {
            DSTaiKhoan.set(index_capNhat, tk);
            new TaiKhoanDAO().CapNhatTKDBS(tk);
        } else {
            System.out.println("Cập nhật không thành công!!!");
        }
    }

    public void NapTien(TaiKhoan tk, double TienMoi) {
        int index_TK = TimTaiKhoan(tk.getUsername(), tk.getSdt());
        if (index_TK != -1) {
            DSTaiKhoan.set(index_TK, tk);
            new TaiKhoanDAO().NaptienTK(tk, TienMoi);
        } else {
            System.out.println("Cập nhật không thành công!!!");
        }
    }
}
