package mainscript.quanlytiemnet;

import BLL.InFoMayTinh.DanhSachMT;
import BLL.InFoTaiKhoan.DanhSachTK;
import BLL.MainControllerStatusManagement;
import GUI.TrangThaiMT.Phong_thuong_1_Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController implements Initializable {
    public BorderPane MainSwitching;

    @FXML
    private AnchorPane StatusPane;

    @FXML
    private Label SoMayCoSan;

    @FXML
    private Label SoMayHong;

    @FXML
    private Label SoMayOnl;

    @FXML
    private Label TenAdmin;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TenAdmin.setText(new DanhSachTK().getTaiKhoanDangNhap().getUsername());
        SoMayOnl.setText(String.valueOf(new DanhSachMT().MayDangONL()));
        SoMayCoSan.setText(String.valueOf(new DanhSachMT().MayCoSan()));
        MainControllerStatusManagement.setMainController(this);
        ChuyenCanhFXML object = new ChuyenCanhFXML();
        Pane view = object.getPage("/DanhSachTK/MainDSTK.fxml");
        MainSwitching.setCenter(view);
    }

    public void CapNhatMainStatus(){
        SoMayOnl.setText(String.valueOf(new DanhSachMT().MayDangONL()));
        SoMayCoSan.setText(String.valueOf(new DanhSachMT().MayCoSan()));
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
    public void ChonTrangThaiMT() {
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
