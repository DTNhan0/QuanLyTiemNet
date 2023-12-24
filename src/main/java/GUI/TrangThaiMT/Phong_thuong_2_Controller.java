package GUI.TrangThaiMT;

import BLL.InFoMayTinh.DanhSachMT;
import BLL.InFoMayTinh.LoaiPhong.DSMayPhongThuong1;
import BLL.InFoMayTinh.LoaiPhong.DSMayPhongThuong2;
import BLL.InFoMayTinh.MayTinh;
import BLL.InFoTaiKhoan.DanhSachTK;
import BLL.InFoTaiKhoan.TaiKhoan;
import BLL.InFoThongTinSD.DSThongTinSD;
import BLL.InFoThongTinSD.ThongTinSuDung;
import BLL.MainControllerStatusManagement;
import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import mainscript.quanlytiemnet.MainController;

import java.lang.reflect.Field;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class Phong_thuong_2_Controller implements Initializable {
    List<MayTinh> DSMay = new DSMayPhongThuong2().getDSMayPhong2();

    @FXML
    private JFXButton BiHongBT;

    @FXML
    private JFXButton CoSanBT;

    @FXML
    private JFXButton DangBaoTriBT;

    @FXML
    private Label HienIDMay;

    @FXML
    private JFXButton ImportNDHongBT;

    @FXML
    private JFXButton KetNoiBT;

    @FXML
    private JFXButton LamMoiBT;

    @FXML
    private Circle May019;

    @FXML
    private Circle May020;

    @FXML
    private Circle May021;

    @FXML
    private Circle May022;

    @FXML
    private Circle May023;

    @FXML
    private Circle May024;

    @FXML
    private Circle May025;

    @FXML
    private Circle May026;

    @FXML
    private Circle May027;

    @FXML
    private Circle May028;

    @FXML
    private Circle May029;

    @FXML
    private Circle May030;

    @FXML
    private Circle May031;

    @FXML
    private Circle May032;

    @FXML
    private Circle May033;

    @FXML
    private Circle May034;

    @FXML
    private Circle May035;

    @FXML
    private Circle May036;

    @FXML
    private TextField NDhong;

    @FXML
    private AnchorPane NhapNDHong;

    @FXML
    private PasswordField PassTF;

    @FXML
    private TextField SdtTF;

    @FXML
    private JFXButton TatMayBT;

    @FXML
    private TextField UserTF;
    public String selectedIDMayFormat;

    public void CapNhatLaiMainStatus(){
        MainController mainController = MainControllerStatusManagement.getMainController();
        if (mainController != null) {
            mainController.CapNhatMainStatus();
        }
    }

    public void ResetTrangThai(){
        {
            try {
                DSMay = new DSMayPhongThuong2().getDSMayPhong2();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        for (MayTinh mt : DSMay) {
            //Hỏng
            if (mt.isCoSan() == false && mt.isTrangThai() == false) {
                String IDMay = mt.getMaMay().substring(0, 1) + mt.getMaMay().substring(1).toLowerCase();
                try {
                    setCircleColor(IDMay, Color.RED);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            //Bảo trì
            if (mt.isCoSan() == true && mt.isTrangThai() == false) {
                String IDMay = mt.getMaMay().substring(0, 1) + mt.getMaMay().substring(1).toLowerCase();
                try {
                    setCircleColor(IDMay, Color.YELLOW);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            //Có người sử dụng
            if (mt.isCoSan() == false && mt.isTrangThai() == true) {
                String IDMay = mt.getMaMay().substring(0, 1) + mt.getMaMay().substring(1).toLowerCase();
                try {
                    setCircleColor(IDMay, Color.web("#00ff48"));
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            //Có sẵn
            if (mt.isCoSan() == true && mt.isTrangThai() == true) {
                String IDMay = mt.getMaMay().substring(0, 1) + mt.getMaMay().substring(1).toLowerCase();
                try {
                    setCircleColor(IDMay, (Color) Paint.valueOf("#969696"));
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static String chuyenChuoi(String input) {
        // Loại bỏ khoảng trắng và thay thế ký tự "Máy" thành "May"
        String result = input.replaceAll("\\s+", "").replace("Máy", "May");
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResetTrangThai();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void HienThiThongTinNhap(ActionEvent event) {
        // Lấy thông tin về nút được nhấn
        JFXButton clickedButton = (JFXButton) event.getSource();
        // Lấy text của nút và hiển thị nó trong Label
        HienIDMay.setText(clickedButton.getText());

        Circle selectedCircle = null;
        try {
            selectedCircle = getCircleById(selectedIDMayFormat);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        //Khi su dung
        if(selectedCircle.getFill().equals(Color.web("#00ff48"))){
            HienIDMay.setText(clickedButton.getText());
            ThongTinSuDung ttsd = new DSThongTinSD().TimTKdagSDtrongThongTinSD(selectedIDMayFormat.toUpperCase());
            UserTF.setText(ttsd.getUsername());
            SdtTF.setText(ttsd.getSdt());
            UserTF.setDisable(true);
            SdtTF.setDisable(true);
            PassTF.setDisable(true);
            TatMayBT.setDisable(false);
            KetNoiBT.setDisable(true);
            BiHongBT.setDisable(true);
            CoSanBT.setDisable(true);
            DangBaoTriBT.setDisable(true);
        }
        //Khi co san
        if(selectedCircle.getFill().equals(Paint.valueOf("#969696"))){
            HienIDMay.setText(clickedButton.getText());
            TatMayBT.setDisable(true);
            KetNoiBT.setDisable(false);
            UserTF.setDisable(false);
            SdtTF.setDisable(false);
            PassTF.setDisable(false);
            UserTF.setText(null);
            SdtTF.setText(null);
            PassTF.setText(null);
            BiHongBT.setDisable(false);
            CoSanBT.setDisable(false);
            DangBaoTriBT.setDisable(false);
        }
        //Khi bi hong
        if(selectedCircle.getFill().equals(Color.RED)){
            HienIDMay.setText(clickedButton.getText());
            KetNoiBT.setDisable(true);
            TatMayBT.setDisable(true);
            UserTF.setDisable(true);
            SdtTF.setDisable(true);
            PassTF.setDisable(true);
            BiHongBT.setDisable(true);
            CoSanBT.setDisable(false);
            DangBaoTriBT.setDisable(false);
            UserTF.setText(null);
            SdtTF.setText(null);
            PassTF.setText(null);
        }
        //Khi bao tri
        if(selectedCircle.getFill().equals(Color.YELLOW)){
            HienIDMay.setText(clickedButton.getText());
            KetNoiBT.setDisable(true);
            TatMayBT.setDisable(true);
            UserTF.setDisable(true);
            SdtTF.setDisable(true);
            PassTF.setDisable(true);
            BiHongBT.setDisable(false);
            CoSanBT.setDisable(false);
            DangBaoTriBT.setDisable(true);
            UserTF.setText(null);
            SdtTF.setText(null);
            PassTF.setText(null);
        }
    }

    private Circle getCircleById(String id) throws NoSuchFieldException, IllegalAccessException {
        // Lấy trường (field) Circle dựa trên tên
        Field field = getClass().getDeclaredField(id);
        // Cho phép truy cập private fields
        field.setAccessible(true);
        // Trả về giá trị của trường, tức là Circle
        return (Circle) field.get(this);
    }

    private void setCircleColor(String id, Color color) throws NoSuchFieldException, IllegalAccessException {
        Circle selectedCircle = getCircleById(id);
        if (selectedCircle != null) {
            selectedCircle.setFill(color);
        }
    }
    //Lấy ID máy được chọn
    @FXML
    public void chonButton(ActionEvent e){
        JFXButton clickedButton = (JFXButton) e.getSource();
        String IDMay = clickedButton.getText();
        NDhong.setText(null);
        NhapNDHong.setVisible(false);
        selectedIDMayFormat = chuyenChuoi(IDMay);
        HienThiThongTinNhap(e);

    }

    public void KhiKetNoi() {
        TaiKhoan tk = null;
        try {
            tk = new DanhSachTK().TimTKTraVeTK(UserTF.getText(), SdtTF.getText());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(tk == null || tk.getSoTienConLai() == 0) {
            showAlert("Lỗi!!!", "Vui lòng kiểm tra lại số tiền trong tài khoản hoặc thông tin nhập!!!", Alert.AlertType.ERROR);
            System.out.println("Vui lòng kiểm tra lại tài khoản");
        }else{
            System.out.println(selectedIDMayFormat);
            new DSThongTinSD().themSuLKgiuaTKvaMay(tk, selectedIDMayFormat.toUpperCase());
            showAlert("Thông báo", "Đã kết nối thành công tk có SĐT: " + SdtTF.getText() + " với " + HienIDMay.getText(), Alert.AlertType.CONFIRMATION);
        }
        ResetTrangThai();
        CapNhatLaiMainStatus();
    }

    public void KhiTatMay(){
        ThongTinSuDung ttsd = new DSThongTinSD().TimTKdagSDtrongThongTinSD(selectedIDMayFormat.toUpperCase());
        try {
            TaiKhoan tk = new DanhSachTK().TimTKTraVeTK(ttsd.getUsername(), ttsd.getSdt());
            showAlert("Thông báo", "Đã ngắt kết nối tk có SĐT: " + SdtTF.getText() + " với " + HienIDMay.getText(), Alert.AlertType.CONFIRMATION);
            tk.setDangSD(false);
            new DanhSachTK().CapNhatTaiKhoan(tk);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ResetTrangThai();
        CapNhatLaiMainStatus();
    }

    public void KhiBiHong() {
        NhapNDHong.setVisible(true);
        MayTinh mt = new DanhSachMT().TimMayTraVeMT(selectedIDMayFormat.toUpperCase());
        try {
            mt.setCoSan(false);
            mt.setTrangThai(false);
            new DanhSachMT().CapNhatMay(mt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ResetTrangThai();
        CapNhatLaiMainStatus();
    }

    public void KhiBaoTri() {
        NhapNDHong.setVisible(false);
        NDhong.setText(null);
        MayTinh mt = new DanhSachMT().TimMayTraVeMT(selectedIDMayFormat.toUpperCase());
        try {
            mt.setCoSan(true);
            mt.setTrangThai(false);
            new DanhSachMT().CapNhatMay(mt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ResetTrangThai();
        CapNhatLaiMainStatus();
    }

    public void KhiCoSan() {
        NhapNDHong.setVisible(false);
        NDhong.setText(null);
        MayTinh mt = new DanhSachMT().TimMayTraVeMT(selectedIDMayFormat.toUpperCase());
        try {
            mt.setCoSan(true);
            mt.setTrangThai(true);
            new DanhSachMT().CapNhatMay(mt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ResetTrangThai();
        CapNhatLaiMainStatus();
    }

    public void LamMoi() {
        HienIDMay.setText("NULL");
        UserTF.setDisable(true);
        SdtTF.setDisable(true);
        PassTF.setDisable(true);
        UserTF.setText(null);
        SdtTF.setText(null);
        PassTF.setText(null);
        KetNoiBT.setDisable(true);
        BiHongBT.setDisable(true);
        DangBaoTriBT.setDisable(true);
        CoSanBT.setDisable(true);
        NhapNDHong.setVisible(false);
        NDhong.setText(null);
        selectedIDMayFormat = null;
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> {
                LocalDateTime now = LocalDateTime.now();

                for(ThongTinSuDung ttsd : new DSThongTinSD().LayCacMayDagSDTrongTTSD()){
                    if(ttsd.getTgKetThuc().format(formatter).equals(now.format(formatter))){
                        try {
                            TaiKhoan tk = new DanhSachTK().TimTKTraVeTK(ttsd.getUsername(), ttsd.getSdt());
                            tk.setDangSD(false);
                            new DanhSachTK().CapNhatTaiKhoan(tk);

                            String temp = ttsd.getMaMay().toLowerCase();
                            String id = temp.substring(0, 1).toUpperCase() + temp.substring(1);
                            Circle circle = getCircleById(id);
                            circle.setFill(Paint.valueOf("#969696"));

                            ResetTrangThai();
                            CapNhatLaiMainStatus();
                            Platform.runLater(() -> {
                                showAlert("Thông báo", "Đã ngắt kết nối máy " + ttsd.getMaMay() + " do sđt " + ttsd.getSdt() + " đã hết tiền", Alert.AlertType.INFORMATION);
                            });
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                }
            })
    );
}
