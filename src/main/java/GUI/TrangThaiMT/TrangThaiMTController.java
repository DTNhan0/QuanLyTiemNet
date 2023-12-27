package GUI.TrangThaiMT;

import BLL.InFoMayTinh.DanhSachMT;
import BLL.InFoMayTinh.MayTinh;
import BLL.InFoTaiKhoan.DanhSachTK;
import BLL.InFoTaiKhoan.TaiKhoan;
import BLL.InFoThongTinSD.DSThongTinSD;
import BLL.InFoThongTinSD.ThongTinSuDung;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import mainscript.quanlytiemnet.ChuyenCanhFXML;
import mainscript.quanlytiemnet.MainController;

import java.net.URL;
import java.util.ResourceBundle;

public class TrangThaiMTController implements Initializable {
    @FXML
    private JFXComboBox<String> LoaiPhong;

    @FXML
    private BorderPane ChuyenPhong;

    public void checkNapKhidagChoi(){
        for(TaiKhoan tk : new DanhSachTK().getDSTaiKhoan()){
            if(tk.isDangSD()){
                ThongTinSuDung ttsd = new DSThongTinSD().TimMTdagSDtrongThongTinSD(tk.getSdt());
                MayTinh mt = new DanhSachMT().TimMayTraVeMT(ttsd.getMaMay());
                mt.setCoSan(false);
                mt.setTrangThai(true);
                try {
                    new DanhSachMT().CapNhatMay(mt);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] cacPhong = {"Phòng thường 1", "Phòng thường 2", "Phòng VIP"};
        LoaiPhong.getItems().addAll(cacPhong);
        LoaiPhong.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-text-fill: WHITE; -fx-font-weight: bold;");
        ChuyenCanhFXML object = new ChuyenCanhFXML();
        Pane view = object.getPage("/TrangThaiMayTinh/Phong_thuong_1.fxml");
        ChuyenPhong.setCenter(view);
        LoaiPhong.setValue("Phòng thường 1");
    }

    public void chonPhong() {
        if (LoaiPhong.getValue() == "Phòng thường 1") {
            ChuyenCanhFXML object = new ChuyenCanhFXML();
            Pane view = object.getPage("/TrangThaiMayTinh/Phong_thuong_1.fxml");
            ChuyenPhong.setCenter(view);
        } else if (LoaiPhong.getValue() == "Phòng thường 2") {
            ChuyenCanhFXML object = new ChuyenCanhFXML();
            Pane view = object.getPage("/TrangThaiMayTinh/Phong_thuong_2.fxml");
            ChuyenPhong.setCenter(view);
        } else if (LoaiPhong.getValue() == "Phòng VIP") {
            ChuyenCanhFXML object = new ChuyenCanhFXML();
            Pane view = object.getPage("/TrangThaiMayTinh/Phong_VIP.fxml");
            ChuyenPhong.setCenter(view);
        }
    }
}
