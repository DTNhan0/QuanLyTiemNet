package Controller;

import Database.DanhSachTKDAO;
import ThongTin.TaiKhoan;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DSTKController implements Initializable{
    DanhSachTKDAO TaiKhoanDB = new DanhSachTKDAO();
    public boolean checkAdmin = false;
    //Lay danh sach tai khoan tu database
    List <TaiKhoan> DanhSachTK = TaiKhoanDB.layTaiKhoantuDBS();

    @FXML
    private TableView<TaiKhoan> BangDSKH;

    @FXML
    private TableColumn<TaiKhoan, String> HangTVCol;

    @FXML
    private TableColumn<TaiKhoan, String> PassCol;

    @FXML
    private TableColumn<TaiKhoan, String> SdtCol;

    @FXML
    private TableColumn<TaiKhoan, Integer> SoPhutSDCol;

    @FXML
    private TableColumn<TaiKhoan, Double> SoTienCol;

    @FXML
    private TableColumn<TaiKhoan, Boolean> TrangThaiCol;

    @FXML
    private TableColumn<TaiKhoan, String> UsernameCol;

    @FXML
    private TableColumn<TaiKhoan, Boolean> VaiTroCol;

    @FXML
    private TextField HangTVTF;

    @FXML
    private JFXButton LamMoi;

    @FXML
    private TextField PassTF;

    @FXML
    private ToggleGroup RoleRB;

    @FXML
    private TextField SdtTF;

    @FXML
    private TextField SoTienNapTF;

    @FXML
    private JFXButton SuaTK;

    @FXML
    private TextField TaiKhoanTF;

    @FXML
    private JFXButton ThemTK;

    @FXML
    private JFXButton XoaTK;

    @FXML
    private RadioButton NutAdmin;

    @FXML
    private RadioButton NutKH;
    private void hienThiThongTinTaiKhoan(TaiKhoan tk) {
        HangTVTF.setText(tk.getHangthanhvien());
        PassTF.setText(tk.getPassword());
        SdtTF.setText(tk.getSdt());
        SoTienNapTF.setText(String.valueOf(tk.getSotien()));
        TaiKhoanTF.setText(tk.getUsername());
        if(tk.isRole()){
            NutAdmin.setSelected(true);
        }else{
            NutKH.setSelected(true);
        }
        // Ẩn nút "Thêm" và làm cho TextField "SĐT" không editable
        ThemTK.setDisable(true);
        SdtTF.setEditable(false);
        SdtTF.setDisable(true);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BangDSKH.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Hiển thị thông tin tài khoản được chọn lên các TextField
                hienThiThongTinTaiKhoan(newSelection);
            }
        });
        HangTVCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHangthanhvien()));
        PassCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
        SdtCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSdt()));
        SoPhutSDCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSophutdadung()).asObject());
        SoTienCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getSotien()).asObject());
        TrangThaiCol.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isDangsudung()).asObject());
        UsernameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        VaiTroCol.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isRole()).asObject());
        BangDSKH.getItems().addAll(DanhSachTK);
    }
    @FXML
    private void themTaiKhoan() {
        String username = TaiKhoanTF.getText();
        String sdt = SdtTF.getText();
        String matKhau = PassTF.getText();
        boolean vaiTro = false;
        if (NutAdmin.isSelected()) {
            vaiTro = true;
        }
        String hangThanhVien = HangTVTF.getText();
        int soPhutSD = Integer.parseInt(SoTienNapTF.getText());
        double soTien = Double.parseDouble(SoTienNapTF.getText());
        boolean trangThaiSD = false;
        TaiKhoan tkMoi = new TaiKhoan(username, sdt, matKhau, vaiTro, hangThanhVien, soPhutSD, soTien, trangThaiSD);

        // Thêm vào danh sách DSKH
        DanhSachTK.add(tkMoi);
        TaiKhoanDB.setMainDanhSachTK(DanhSachTK);
        // Thêm vào cơ sở dữ liệu
        TaiKhoanDB.themTaiKhoanVaoDB(tkMoi, checkAdmin);
        BangDSKH.getItems().add(tkMoi);
    }
    @FXML
    private void xoaTaiKhoan() {
        int selectedIndex = BangDSKH.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            TaiKhoan selectedTaiKhoan = BangDSKH.getItems().get(selectedIndex);

            // Hiển thị hộp thoại xác nhận trước khi xóa
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận xóa");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn muốn xóa tài khoản có SĐT: " + selectedTaiKhoan.getSdt());

            // Sử dụng Optional để xác nhận người dùng chọn OK hoặc Cancel
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Xóa khỏi cơ sở dữ liệu
                    TaiKhoanDB.xoaTaiKhoanKhoiDB(selectedTaiKhoan);

                    // Xóa khỏi danh sách DSKH
                    DanhSachTK.remove(selectedTaiKhoan);
                    TaiKhoanDB.setMainDanhSachTK(DanhSachTK);
                    // Cập nhật lại TableView
                    BangDSKH.getItems().remove(selectedIndex);
                }
            });
        } else {
            showAlert("Lỗi!!!", "Vui lòng chọn một tài khoản để xóa.", Alert.AlertType.ERROR);
        }
    }
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    private void capNhatTaiKhoan() {
        int selectedIndex = BangDSKH.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            TaiKhoan selectedTaiKhoan = BangDSKH.getItems().get(selectedIndex);

            // Hiển thị hộp thoại xác nhận trước khi cập nhật
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận cập nhật");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn muốn cập nhật thông tin cho tài khoản có SĐT: " + selectedTaiKhoan.getSdt());

            // Sử dụng Optional để xác nhận người dùng chọn OK hoặc Cancel
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    //TaiKhoan tk, String TaiKhoanTF, String PassTF, String HangTVTF, double SotienTF,boolean NutAdmin
                    // Cập nhật thông tin trong cơ sở dữ liệu
                    TaiKhoanDB.capNhatTaiKhoanTrongDB(selectedTaiKhoan, TaiKhoanTF.getText(), PassTF.getText(), HangTVTF.getText(), SoTienNapTF.getText(), checkAdmin);

                    // Cập nhật lại thông tin trong danh sách DSKH
                    capNhatThongTinTaiKhoan(selectedTaiKhoan);
                    TaiKhoanDB.setMainDanhSachTK(DanhSachTK);

                    // Cập nhật lại TableView
                    BangDSKH.refresh();
                }
            });
        } else {
            showAlert("Lỗi!!!", "Vui lòng chọn một tài khoản để cập nhật.", Alert.AlertType.ERROR);
        }
    }
    private void capNhatThongTinTaiKhoan(TaiKhoan tk) {
        boolean vaiTro = false;
        if(NutAdmin.isSelected()){
            vaiTro = true;
        }
        tk.setHangthanhvien(HangTVTF.getText());
        tk.setPassword(PassTF.getText());
        tk.setSdt(SdtTF.getText());
        tk.setSotien(Double.parseDouble(SoTienNapTF.getText()));
        tk.setUsername(TaiKhoanTF.getText());
        tk.setRole(vaiTro);
        tk.setSophutdadung((int) Double.parseDouble(SoTienNapTF.getText()));
        tk.setDangsudung(false);

        // Cập nhật lại TableView
        BangDSKH.refresh();
    }
    public void ResetThongTinNhap(){
        HangTVTF.setText(null);
        PassTF.setText(null);
        SdtTF.setText(null);
        SoTienNapTF.setText(null);
        TaiKhoanTF.setText(null);
        NutAdmin.setSelected(false);
        NutKH.setSelected(false);
        BangDSKH.getSelectionModel().clearSelection();
    }

}
