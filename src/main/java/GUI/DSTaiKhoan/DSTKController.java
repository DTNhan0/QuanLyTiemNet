package GUI.DSTaiKhoan;
import BLL.InFoTaiKhoan.DanhSachTK;
import BLL.InFoTaiKhoan.TaiKhoan;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.w3c.dom.events.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DSTKController implements Initializable {
        List <TaiKhoan> DSTK = new DanhSachTK().getDSTaiKhoan();

        @FXML
        private TextField TimKiemTF;

        @FXML
        private JFXButton TimKiemBT;

        @FXML
        private TableView<TaiKhoan> BangDSKH;

        @FXML
        private TableColumn<TaiKhoan, String> HangTVCol;

        @FXML
        private TextField HangTVTF;

        @FXML
        private JFXButton LamMoi;

        @FXML
        private RadioButton NutAdmin;

        @FXML
        private RadioButton NutKH;

        @FXML
        private TableColumn<TaiKhoan, String> PassCol;

        @FXML
        private TextField PassTF;

        @FXML
        private ToggleGroup RoleRB;

        @FXML
        private TableColumn<TaiKhoan, Double> STConLaiCol;

        @FXML
        private TableColumn<TaiKhoan, Double> STTichLuyCol;

        @FXML
        private TableColumn<TaiKhoan, String> SdtCol;

        @FXML
        private TextField SdtTF;

        @FXML
        private TableColumn<TaiKhoan, Integer> SoPhutSDCol;

        @FXML
        private TextField SoTienNapTF;

        @FXML
        private JFXButton SuaTK;

        @FXML
        private TextField TaiKhoanTF;

        @FXML
        private JFXButton ThemTK;

        @FXML
        private TableColumn<TaiKhoan, Boolean> TrangThaiCol;

        @FXML
        private TableColumn<TaiKhoan, String> UsernameCol;

        @FXML
        private TableColumn<TaiKhoan, Boolean> VaiTroCol;

        @FXML
        private JFXButton XoaTK;

        @FXML
        private JFXButton NapTienBT;

        public void CapNhatLaiTableView(){
                DSTK = new DanhSachTK().getDSTaiKhoan();
                // Cập nhật lại TableView
                BangDSKH.getItems().clear();
                BangDSKH.getItems().addAll(DSTK);
                BangDSKH.refresh();
        }

        @FXML
        public void ResetThongTinNhap() {
                TimKiemTF.clear();
                HangTVTF.clear();
                PassTF.clear();
                SdtTF.clear();
                SoTienNapTF.clear();
                TaiKhoanTF.clear();

                TimKiemTF.setPromptText("(Nhập sđt)");
                SoTienNapTF.setPromptText("(Vd: 10000)");
                TaiKhoanTF.setEditable(true);
                TaiKhoanTF.setDisable(false);
                SdtTF.setEditable(true);
                SdtTF.setDisable(false);

                NutAdmin.setDisable(false);
                NutKH.setDisable(false);
                HangTVTF.setDisable(false);
                HangTVTF.setEditable(true);
                PassTF.setEditable(true);
                PassTF.setDisable(false);
                SoTienNapTF.setDisable(true);

                XoaTK.setDisable(false);
                SuaTK.setDisable(false);
                NapTienBT.setDisable(true);
                ThemTK.setDisable(false);

                NutAdmin.setSelected(false);
                NutKH.setSelected(false);

                BangDSKH.getSelectionModel().clearSelection();
        }

        @FXML
        public void capNhatTaiKhoan() {
                if (!(BangDSKH.getSelectionModel().isEmpty())) {
                        // Hiển thị hộp thoại xác nhận trước khi cập nhật
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Xác nhận cập nhật");
                        alert.setHeaderText(null);
                        alert.setContentText("Bạn có chắc chắn muốn cập nhật thông tin cho tài khoản có SĐT: " + SdtTF.getText());

                        // Sử dụng Optional để xác nhận người dùng chọn OK hoặc Cancel
                        alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                        boolean checkAdmin;
                        if(NutAdmin.isSelected()){
                                checkAdmin = true;
                        }else{
                                checkAdmin = false;
                        }
                        TaiKhoan tk = new TaiKhoan(TaiKhoanTF.getText(), PassTF.getText(), SdtTF.getText(), HangTVTF.getText(), checkAdmin);
                        new DanhSachTK().CapNhatTaiKhoan(tk);
                        CapNhatLaiTableView();
                }
                    });
                } else {
                    showAlert("Lỗi!!!", "Vui lòng chọn một tài khoản để cập nhật.", Alert.AlertType.ERROR);
                }
        }

        @FXML
        public void themTaiKhoan() {
                boolean checkAdmin = NutAdmin.isSelected();

                if (TaiKhoanTF.getText().isEmpty() || PassTF.getText().isEmpty() || SdtTF.getText().isEmpty() || (!NutKH.isSelected() && !NutAdmin.isSelected())) {
                        showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin (username, password, sđt và Role)", Alert.AlertType.ERROR);
                } else {
                        TaiKhoan tk = new TaiKhoan(TaiKhoanTF.getText(), PassTF.getText(), SdtTF.getText(), HangTVTF.getText(), checkAdmin);
                        if(CheckTKdaTonTai(tk)){
                                showAlert("Lỗi", "Lỗi, có tài khoản đã tồn tại sđt này!!!", Alert.AlertType.ERROR);
                                return;
                        }
                        if(tk.getSdt().length() != 10 || !(tk.getSdt().matches("\\d+"))){
                                showAlert("Lỗi", "Lỗi, sđt tài khoản phải có 10 CHỮ SỐ!!!", Alert.AlertType.ERROR);
                                return;
                        }
                          new DanhSachTK().ThemTaiKhoan(tk);
                          CapNhatLaiTableView();
                          showAlert("Thông báo", "Tài khoản đã được thêm thành công!", Alert.AlertType.INFORMATION);

                }
        }

        public boolean CheckTKdaTonTai(TaiKhoan tk){
                for(TaiKhoan taiKhoan : DSTK){
                        if(tk.getSdt().equals(taiKhoan.getSdt())){
                                return true;
                        }
                }
                return false;
        }
        @FXML
        public void xoaTaiKhoan() {
                if (!(BangDSKH.getSelectionModel().isEmpty())) {
                    // Hiển thị hộp thoại xác nhận trước khi xóa
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Xác nhận xóa");
                    alert.setHeaderText(null);
                    alert.setContentText("Bạn có chắc chắn muốn xóa tài khoản có SĐT: " + SdtTF.getText());

                    // Sử dụng Optional để xác nhận người dùng chọn OK hoặc Cancel
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                                TaiKhoan tk = BangDSKH.getSelectionModel().getSelectedItem();
                                new DanhSachTK().XoaTaiKhoan(tk);
                                CapNhatLaiTableView();
                                ResetThongTinNhap();
                        }
                    });
                } else {
                    showAlert("Lỗi!!!", "Vui lòng chọn một tài khoản để xóa.", Alert.AlertType.ERROR);
                }
        }

        private void hienThiThongTinTaiKhoan(TaiKhoan tk) {
                TaiKhoan dangnhap = new DanhSachTK().getTaiKhoanDangNhap();
                TaiKhoanTF.setText(tk.getUsername());
                SdtTF.setText(tk.getSdt());
                PassTF.setText(tk.getPassword());
                TaiKhoanTF.setDisable(true);
                SdtTF.setDisable(true);
                if(tk.isRole()){
                        NutAdmin.setSelected(true);
                }else{
                        NutKH.setSelected(true);
                }
                HangTVTF.setText(tk.getHangthanhvien());
                SoTienNapTF.setText(null);
                SoTienNapTF.setDisable(false);
                NapTienBT.setDisable(false);

                if((tk.getUsername().equals(dangnhap.getUsername()) && tk.getSdt().equals(dangnhap.getSdt()))){
                      PassTF.setDisable(true);
                      NutAdmin.setDisable(true);
                      NutKH.setDisable(true);
                      HangTVTF.setDisable(true);

                      ThemTK.setDisable(true);
                      XoaTK.setDisable(true);
                      SuaTK.setDisable(true);
                }else {
                        PassTF.setDisable(false);
                        NutAdmin.setDisable(false);
                        NutKH.setDisable(false);
                        HangTVTF.setDisable(false);

                        ThemTK.setDisable(false);
                        XoaTK.setDisable(false);
                        SuaTK.setDisable(false);
                }
        }

        public void NapTien() {
                if (BangDSKH.getSelectionModel().isEmpty()) {
                        showAlert("Lỗi!!!", "Vui lòng chọn một tài khoản để nạp!!!", Alert.AlertType.ERROR);
                        return;
                }

                try {
                        String soTienNap = SoTienNapTF.getText();
                        if (soTienNap.isEmpty()) {
                                throw new NullPointerException();
                        }
                        if(!(SoTienNapTF.getText().matches("\\d+"))){
                                showAlert("Lỗi!!!", "Lỗi, số tiền nạp không hợp lệ!!!", Alert.AlertType.ERROR);
                        } else {
                                TaiKhoan tk = BangDSKH.getSelectionModel().getSelectedItem();
                                showAlert("Nạp tiền thành công!!!", "Bạn đã nạp " + soTienNap + " thành công vào tk có sđt:" + tk.getSdt(), Alert.AlertType.CONFIRMATION);
                                new DanhSachTK().NapTien(tk, Double.parseDouble(soTienNap));
                                CapNhatLaiTableView();
                                BangDSKH.refresh();
                        }
                } catch (NullPointerException exception) {
                        showAlert("Lỗi!!!", "Không được để trống số tiền nạp!!!", Alert.AlertType.ERROR);
                }
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                ResetThongTinNhap();
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
                STTichLuyCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getSotienTichLuy()).asObject());
                STConLaiCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getSoTienConLai()).asObject());
                TrangThaiCol.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isDangSD()).asObject());
                UsernameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
                VaiTroCol.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isRole()).asObject());
                BangDSKH.getItems().addAll(DSTK);
        }

        public void TimKiemTK(){
                if(TimKiemTF.getText().isEmpty()){
                        showAlert("Lỗi", "Lỗi, vui lòng không bỏ trống!!!", Alert.AlertType.ERROR);
                }else if (TimKiemTF.getText().length() != 10){
                        showAlert("Lỗi", "Lỗi, vui lòng nhập đủ 10 số!!!", Alert.AlertType.ERROR);
                }else if (!(TimKiemTF.getText().matches("\\d+"))) {
                        showAlert("Lỗi", "Lỗi, vui lòng nhập số điện thoại hợp lệ!!!", Alert.AlertType.ERROR);
                }else{
                        boolean check = true;
                        for(TaiKhoan tk : DSTK){
                                if(tk.getSdt().equals(TimKiemTF.getText())){
                                        BangDSKH.getSelectionModel().select(tk);
                                        BangDSKH.scrollTo(tk);
                                        BangDSKH.requestFocus();
                                        showAlert("Xác nhận", "Đã tìm thấy tk có sđt: " + TimKiemTF.getText(), Alert.AlertType.CONFIRMATION);
                                        check = false;
                                        break;
                                }
                        }
                        if (check){
                                showAlert("Thông báo", "Không tìm thấy tài khoản có sđt: " + TimKiemTF.getText() + ". Vui lòng thử lại", Alert.AlertType.CONFIRMATION);
                        }
                }
        }

        private void showAlert(String title, String content, Alert.AlertType alertType) {
                Alert alert = new Alert(alertType);
                alert.setTitle(title);
                alert.setHeaderText(null);
                alert.setContentText(content);
                alert.showAndWait();
        }
}
