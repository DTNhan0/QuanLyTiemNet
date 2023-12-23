package GUI.TrangThaiMT;

import BLL.InFoMayTinh.LoaiPhong.DSMayPhongThuong1;
import BLL.InFoMayTinh.LoaiPhong.DSMayPhongThuong2;
import BLL.InFoMayTinh.MayTinh;
import BLL.InFoTaiKhoan.DanhSachTK;
import BLL.InFoTaiKhoan.TaiKhoan;
import BLL.InFoThongTinSD.DSThongTinSD;
import BLL.InFoThongTinSD.ThongTinSuDung;
import com.jfoenix.controls.JFXButton;
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

import java.lang.reflect.Field;
import java.net.URL;
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
    private String selectedIDMayFormat;

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
    }

    public void HienThiThongTinNhap(ActionEvent event) {
        // Lấy thông tin về nút được nhấn
        JFXButton clickedButton = (JFXButton) event.getSource();
        // Lấy text của nút và hiển thị nó trong Label
        HienIDMay.setText(clickedButton.getText());
        UserTF.setDisable(false);
        SdtTF.setDisable(false);
        PassTF.setDisable(false);
        KetNoiBT.setDisable(false);
        BiHongBT.setDisable(false);
        DangBaoTriBT.setDisable(false);
        CoSanBT.setDisable(false);
        Circle selectedCircle = null;
        try {
            selectedCircle = getCircleById(selectedIDMayFormat);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        if(selectedCircle.getFill().equals(Color.web("#00ff48"))){
            ThongTinSuDung ttsd = new DSThongTinSD().TimTKtrongThongTinSD(selectedIDMayFormat.toUpperCase());
            UserTF.setText(ttsd.getUsername());
            SdtTF.setText(ttsd.getSdt());
            UserTF.setDisable(true);
            SdtTF.setDisable(true);
            TatMayBT.setDisable(false);
            KetNoiBT.setDisable(true);
        }else{
            UserTF.setDisable(false);
            SdtTF.setDisable(false);
            UserTF.setText(null);
            SdtTF.setText(null);
            PassTF.setText(null);
            KetNoiBT.setDisable(false);
            TatMayBT.setDisable(true);
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
    public void KhiKetNoi() throws NoSuchFieldException, IllegalAccessException {
        Circle selectedCircle = getCircleById(selectedIDMayFormat);
        selectedCircle.setFill(Color.web("#00ff48"));
        TaiKhoan tk = null;
        try {
            tk = new DanhSachTK().TimTKTraVeTK(UserTF.getText(), SdtTF.getText());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(tk != null) {
            new DSThongTinSD().themSuLKgiuaTKvaMay(tk, selectedIDMayFormat.toUpperCase());
            showAlert("Thông báo", "Đã kết nối thành công tk có SĐT" + SdtTF.getText() + "với máy" + HienIDMay.getText(), Alert.AlertType.CONFIRMATION);
        }else{
            System.out.println("Ko ket noi duoc, vui long kiem tra lai tk!!!!");
        }
    }
    public void KhiTatMay() throws NoSuchFieldException, IllegalAccessException {
        Circle selectedCircle = getCircleById(selectedIDMayFormat);
        selectedCircle.setFill(Paint.valueOf("#969696"));
        ThongTinSuDung ttsd = new DSThongTinSD().TimTKtrongThongTinSD(selectedIDMayFormat.toUpperCase());
        try {
            TaiKhoan tk = new DanhSachTK().TimTKTraVeTK(ttsd.getUsername(), ttsd.getSdt());
            showAlert("Thông báo", "Đã ngắt kết nối tk có SĐT" + SdtTF.getText() + "với máy" + HienIDMay.getText(), Alert.AlertType.CONFIRMATION);
            tk.setDangSD(false);
            new DanhSachTK().CapNhatTKdagSD(tk);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    //Lấy ID máy được chọn
    @FXML
    public void chonButton(ActionEvent e) throws NoSuchFieldException, IllegalAccessException {
        JFXButton clickedButton = (JFXButton) e.getSource();
        String IDMay = clickedButton.getText();
        NDhong.setText(null);
        NhapNDHong.setVisible(false);
        selectedIDMayFormat = chuyenChuoi(IDMay);
        HienThiThongTinNhap(e);
    }

    public void KhiBiHong() throws NoSuchFieldException, IllegalAccessException {
        Circle selectedCircle = getCircleById(selectedIDMayFormat);
        selectedCircle.setFill(javafx.scene.paint.Color.RED);
        NhapNDHong.setVisible(true);
    }

    public void KhiBaoTri() throws NoSuchFieldException, IllegalAccessException {
        Circle selectedCircle = getCircleById(selectedIDMayFormat);
        selectedCircle.setFill(Color.YELLOW);
        NhapNDHong.setVisible(false);
        NDhong.setText(null);
    }

    public void KhiCoSan() throws NoSuchFieldException, IllegalAccessException {
        Circle selectedCircle = getCircleById(selectedIDMayFormat);
        selectedCircle.setFill(Paint.valueOf("#969696"));
        NhapNDHong.setVisible(false);
        NDhong.setText(null);
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
}
