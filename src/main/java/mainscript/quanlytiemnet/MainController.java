package mainscript.quanlytiemnet;

import BLL.InFoMayTinh.DanhSachMT;
import BLL.InFoTaiKhoan.DanhSachTK;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainController implements Initializable {
    public BorderPane MainSwitching;

    @FXML
    public static AnchorPane StatusPane;
    @FXML
    public static Label SoMayCoSan;

    @FXML
    public static Label SoMayHong;

    @FXML
    public static Label SoMayOnl;

    @FXML
    private Label TenAdmin;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TenAdmin.setText(new DanhSachTK().getTaiKhoanDangNhap().getUsername());
    }

    public static void CapNhatStatus(){
        SoMayCoSan.setText(String.valueOf(new DanhSachMT().MayCoSan()));
        //Se viet them cac trang thai khac
    }
    @FXML
    public void ChonDSKH(ActionEvent event) {
        ChuyenCanhFXML object = new ChuyenCanhFXML();
        Pane view = object.getPage("/DanhSachTK/MainDSTK.fxml");
        MainSwitching.setCenter(view);
    }

    @FXML
    public void ChonTK(ActionEvent event) {
        ChuyenCanhFXML object = new ChuyenCanhFXML();
        Pane view = object.getPage("/ThongKe/MainTK.fxml");
        MainSwitching.setCenter(view);
    }

    @FXML
    public void ChonTrangThaiMT(ActionEvent event) {
        ChuyenCanhFXML object = new ChuyenCanhFXML();
        Pane view = object.getPage("/TrangThaiMayTinh/MainTrangThaiMT.fxml");
        MainSwitching.setCenter(view);
    }

    @FXML
    public void ChonDSMT(ActionEvent event) {
        ChuyenCanhFXML object = new ChuyenCanhFXML();
        Pane view = object.getPage("/DSMayTinh/MainDSMT.fxml");
        MainSwitching.setCenter(view);
    }
}
