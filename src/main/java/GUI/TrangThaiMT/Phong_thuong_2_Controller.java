package GUI.TrangThaiMT;

import BLL.InFoMayTinh.LoaiPhong.DSMayPhongThuong1;
import BLL.InFoMayTinh.LoaiPhong.DSMayPhongThuong2;
import BLL.InFoMayTinh.MayTinh;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class Phong_thuong_2_Controller implements Initializable{
    List <MayTinh> DSMay;
    {
        try {
            DSMay = new DSMayPhongThuong2().getDSMayPhong2();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private JFXButton ImportNDHongBT;
    @FXML
    private JFXButton BiHongBT;

    @FXML
    private JFXButton CoSanBT;

    @FXML
    private JFXButton DangBaoTriBT;

    @FXML
    private Label HienIDMay;

    @FXML
    private JFXButton KetNoiBT;

    @FXML
    private JFXButton LamMoiBT;

    @FXML
    private Circle May001;

    @FXML
    private Circle May002;

    @FXML
    private Circle May003;

    @FXML
    private Circle May004;

    @FXML
    private Circle May005;

    @FXML
    private Circle May006;

    @FXML
    private Circle May007;

    @FXML
    private Circle May008;

    @FXML
    private Circle May009;

    @FXML
    private Circle May010;

    @FXML
    private Circle May011;

    @FXML
    private Circle May012;

    @FXML
    private Circle May013;

    @FXML
    private Circle May014;

    @FXML
    private Circle May015;

    @FXML
    private Circle May016;

    @FXML
    private Circle May017;

    @FXML
    private Circle May018;
    @FXML
    private TextField NDhong;
    @FXML
    private AnchorPane NhapNDHong;

    @FXML
    private PasswordField PassTF;

    @FXML
    private TextField SdtTF;

    @FXML
    private TextField UserTF;
    private String selectedIDMayFormat;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(MayTinh mt : DSMay){
            //Hỏng
            if(mt.isCoSan() == false && mt.isTrangThai() == false){
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
            if(mt.isCoSan() == true && mt.isTrangThai() == false){
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
            if(mt.isCoSan() == false && mt.isTrangThai() == true){
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
            if(mt.isCoSan() == true && mt.isTrangThai() == true){
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
    public void HienThiThongTinNhap(ActionEvent event){
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
    public void chonButton(ActionEvent e) throws NoSuchFieldException, IllegalAccessException {
        HienThiThongTinNhap(e);
        JFXButton clickedButton = (JFXButton) e.getSource();
        String IDMay = clickedButton.getText();
        String soThuTu = IDMay.substring(4);
        NDhong.setText(null);
        NhapNDHong.setVisible(false);

        selectedIDMayFormat = "May" + soThuTu;
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
    public void LamMoi(){
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
}
