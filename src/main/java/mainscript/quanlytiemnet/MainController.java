package mainscript.quanlytiemnet;

import BLL.InFoMayTinh.DanhSachMT;
import BLL.InFoTaiKhoan.DanhSachTK;
import BLL.InFoThongTinSD.DSThongTinSD;
import BLL.InFoThongTinSD.ThongTinSuDung;
import BLL.MainControllerStatusManagement;
import DAL.ThongTinSuDungDAO;
import GUI.TrangThaiMT.Phong_thuong_1_Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController implements Initializable {

    public static List<ThongTinSuDung> StackingOnl = new ArrayList<>();

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

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> {
                Iterator<ThongTinSuDung> iterator = StackingOnl.iterator();
                while (iterator.hasNext()) {
                    ThongTinSuDung ttsdStack = iterator.next();
                    for (ThongTinSuDung ttsd : new ThongTinSuDungDAO().getAll()) {
                        if (ttsd.getTgBatDau().format(formatter).equals(ttsdStack.getTgBatDau().format(formatter)) && !(ttsd.getDagSD())) {
                            iterator.remove();
                            ChuyenCanhFXML object = new ChuyenCanhFXML();
                            Pane view = object.getPage("/TrangThaiMayTinh/MainTrangThaiMT.fxml");
                            MainSwitching.setCenter(view);
                            Platform.runLater(() -> {
                                showAlert("Thông báo", "Đã ngắt kết nối máy " + ttsdStack.getMaMay() + " do sđt " + ttsdStack.getSdt() + " đã hết tiền", Alert.AlertType.INFORMATION);
                            });
                        }
                    }
                }
            })
    );
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StackingOnl = new DSThongTinSD().LayCacMayDagSDTrongTTSD();
        TenAdmin.setText(new DanhSachTK().getTaiKhoanDangNhap().getUsername());
        SoMayOnl.setText(String.valueOf(new DanhSachMT().MayDangONL()));
        SoMayCoSan.setText(String.valueOf(new DanhSachMT().MayCoSan()));
        MainControllerStatusManagement.setMainController(this);
        ChuyenCanhFXML object = new ChuyenCanhFXML();
        Pane view = object.getPage("/DanhSachTK/MainDSTK.fxml");
        MainSwitching.setCenter(view);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
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
