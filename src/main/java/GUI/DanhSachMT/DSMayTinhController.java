package GUI.DanhSachMT;

import BLL.InFoMayTinh.DanhSachMT;
import BLL.InFoMayTinh.MayTinh;
import BLL.InFoTaiKhoan.DanhSachTK;
import BLL.InFoTaiKhoan.TaiKhoan;
import BLL.MainControllerStatusManagement;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import mainscript.quanlytiemnet.MainController;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class DSMayTinhController implements Initializable {
    List <MayTinh> DSMayTinh = new DanhSachMT().getDSMayTinh();

    @FXML
    private TextField TimKiemTF;

    @FXML
    private TableView<MayTinh> BangDSMT;

    @FXML
    private TableColumn<MayTinh, Integer> BaoHanhCol;

    @FXML
    private TextField BaoHanhTF;

    @FXML
    private TableColumn<MayTinh, Boolean> CoSanCol;

    @FXML
    private JFXButton LamMoiBT;

    @FXML
    private TableColumn<MayTinh, String> MMayCol;

    @FXML
    private TextField MMayTF;

    @FXML
    private TableColumn<MayTinh, Date> NgayMuaCol;

    @FXML
    private DatePicker NgayMuaTF;

    @FXML
    private JFXComboBox<String> PhongCB;

    @FXML
    private TableColumn<MayTinh, String> PhongCol;

    @FXML
    private JFXButton SuaBT;

    @FXML
    private TableColumn<MayTinh, Integer> TGDungCol;

    @FXML
    private JFXButton ThemBT;

    @FXML
    private TableColumn<MayTinh, Boolean> TrangThaiCol;

    @FXML
    private JFXButton XoaBT;

    public void CapNhatLaiTableView(){
        DSMayTinh = new DanhSachMT().getDSMayTinh();
        // Cập nhật lại TableView
        BangDSMT.getItems().clear();
        BangDSMT.getItems().addAll(DSMayTinh);
        BangDSMT.refresh();
    }

    private DatePicker convertToDatePicker(Date date) {
        if (date != null) {
            LocalDate localDate = date.toLocalDate();
            return new DatePicker(localDate);
        } else {
            return new DatePicker();
        }
    }

    private void hienThiThongTinTaiKhoan(MayTinh mt) {
        MMayTF.setText(mt.getMaMay());
        BaoHanhTF.setText(String.valueOf(mt.getBaoHanh()));
        NgayMuaTF.setValue(convertToDatePicker(mt.getNgayMua()).getValue());
        MMayTF.setText(mt.getMaMay());

        if (mt.getPhong().equals("T01")) {
            PhongCB.setValue("T01");
        } else if (mt.getPhong().equals("T02")) {
            PhongCB.setValue("T02");
        } else if (mt.getPhong().equals("VIP01")) {
            PhongCB.setValue("VIP01");
        }
        ThemBT.setDisable(true);
        MMayTF.setDisable(true);
        PhongCB.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LamMoi();
        String[] cacPhong = {"T01", "T02", "VIP01"};
        PhongCB.getItems().addAll(cacPhong);
        PhongCB.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12px; -fx-text-fill: WHITE;");
        PhongCB.setPromptText("(Chọn phòng)");
        BangDSMT.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Hiển thị thông tin tài khoản được chọn lên các TextField
                hienThiThongTinTaiKhoan(newSelection);
            }
        });

        MMayCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaMay()));
        BaoHanhCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getBaoHanh()).asObject());
        NgayMuaCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNgayMua()));
        TGDungCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getThoiGianDung()).asObject());
        CoSanCol.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isCoSan()).asObject());
        TrangThaiCol.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isTrangThai()).asObject());
        PhongCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhong()));

        try {
            BangDSMT.getItems().addAll(DSMayTinh);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void ThemMay() {
        String Mamay = MMayTF.getText();
        String BaoHanhText = BaoHanhTF.getText();
        LocalDate ngayMuaValue = NgayMuaTF.getValue();
        String Phong = PhongCB.getValue();

        if (Mamay == null || BaoHanhText.isEmpty() || ngayMuaValue == null || Phong == null) {
            showAlert("Lỗi!!!", "Không được để trống thông tin!!!", Alert.AlertType.ERROR);
            return;
        }
        try {
            int BaoHanh = Integer.parseInt(BaoHanhText);
            Date NgayMua = Date.valueOf(ngayMuaValue);

            // Kiểm tra xem mã máy đã tồn tại hay chưa
            if (CheckMaMayTonTai(Mamay) || CheckMayTrongPhongHopLe(Mamay ,Phong) || Mamay.length() != 6 || !(Mamay.substring(0,3).equals("MAY"))) {
                showAlert("Lỗi!!!", "Lỗi, mã máy đã tồn tại hoặc mã máy phải đúng theo phòng!!!", Alert.AlertType.ERROR);
                return;
            }

            MayTinh mt = new MayTinh(Mamay, Phong, BaoHanh, 0, true, false, NgayMua);

            // Thêm máy vào DBS
            try {
                showAlert("Thông báo", "Đã thêm máy có mã: " + mt.getMaMay(), Alert.AlertType.CONFIRMATION);
                new DanhSachMT().ThemMay(mt);
                CapNhatLaiTableView();

                MainController mainController = MainControllerStatusManagement.getMainController();
                if (mainController != null) {
                    mainController.CapNhatMainStatus();
                }

            } catch (Exception e) {
                System.out.println("Lỗi thêm máy vào DBS");
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Bảo hành phải là một số nguyên!");
            alert.showAndWait();
        }
    }

    public boolean CheckMaMayTonTai(String maMay) {
        for (MayTinh may : DSMayTinh) {
            if (may.getMaMay().equals(maMay)) {
                return true;
            }
        }
        return false;
    }

    public boolean CheckMayTrongPhongHopLe(String maMay, String phong){
        switch (phong) {
            case "T01":
                return kiemTraPhamVi("MAY001", "MAY018", maMay);
            case "T02":
                return kiemTraPhamVi("MAY019", "MAY036", maMay);
            case "VIP01":
                return kiemTraPhamVi("MAY037", "MAY054", maMay);
            default:
                // Nếu phòng không nằm trong các trường hợp trên, coi như là không hợp lệ
                return false;
        }
    }

    private boolean kiemTraPhamVi(String start, String end, String maMay) {
        int startNum = Integer.parseInt(start.substring(3));
        int endNum = Integer.parseInt(end.substring(3));
        int maMayNum = Integer.parseInt(maMay.substring(3));

        return !(maMayNum >= startNum && maMayNum <= endNum);
    }

    public void XoaMay() {
        if (BangDSMT.getSelectionModel().isEmpty()) {
            showAlert("Lỗi!!!", "Hãy chọn máy cần xóa!!!", Alert.AlertType.ERROR);
        } else {
            MayTinh mt = BangDSMT.getSelectionModel().getSelectedItem();
            // Hiển thị hộp thoại xác nhận trước khi xóa
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận xóa");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn muốn xóa máy có mã: " + mt.getMaMay());

            // Sử dụng Optional để xác nhận người dùng chọn OK hoặc Cancel
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        // Xóa máy trên Database
                        new DanhSachMT().XoaMay(mt);
                        CapNhatLaiTableView();

                        MainController mainController = MainControllerStatusManagement.getMainController();
                        if (mainController != null) {
                            mainController.CapNhatMainStatus();
                        }

                    } catch (Exception e) {
                        System.out.println("Xóa máy trên Database thất bại!!!");
                    }
                    BangDSMT.refresh();
                }
            });

        }
    }

    public void CapNhatMay() {
        if (BangDSMT.getSelectionModel().isEmpty()) {
            showAlert("Lỗi!!!", "Hãy chọn máy cần cập nhật!!!", Alert.AlertType.ERROR);
        } else {
            MayTinh mayTinh = BangDSMT.getSelectionModel().getSelectedItem();

            String Mamay = mayTinh.getMaMay();
            String Phong = mayTinh.getPhong();
            int TGdung = mayTinh.getThoiGianDung();

            String BaoHanhText = BaoHanhTF.getText();
            LocalDate ngayMuaValue = NgayMuaTF.getValue();

            if (BaoHanhText.isEmpty() || ngayMuaValue == null) {
                showAlert("Lỗi!!!", "Không được để trống!!!", Alert.AlertType.ERROR);
                return;
            }

            try {
                int BaoHanh = Integer.parseInt(BaoHanhText);
                Date NgayMua = Date.valueOf(ngayMuaValue);

                MayTinh mt = new MayTinh(Mamay, Phong, BaoHanh, TGdung, true, false, NgayMua);
                int selectedRowIndex = BangDSMT.getSelectionModel().getSelectedIndex();

                //Cập nhật máy cho cả hai
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Xác nhận cập nhật");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có chắc muốn cập nhật lại máy không?");

                // Sử dụng Optional để xác nhận người dùng chọn OK hoặc Cancel
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            new DanhSachMT().CapNhatMay(mayTinh);
                            CapNhatLaiTableView();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        BangDSMT.refresh();
                    }
                });
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Bảo hành phải là một số nguyên!");
                alert.showAndWait();
            }

        }
    }

    public void LamMoi() {
        TimKiemTF.setText(null);
        MMayTF.setText(null);
        BaoHanhTF.setText(null);
        NgayMuaTF.setValue(null);
        MMayTF.setText(null);
        PhongCB.setValue(null);
        PhongCB.setPromptText("(Chọn phòng)");
        ThemBT.setDisable(false);
        MMayTF.setDisable(false);
        PhongCB.setDisable(false);
        BangDSMT.refresh();
        BangDSMT.getSelectionModel().clearSelection();
    }

    public void TimKiem(){
        try {
            if (TimKiemTF.getText().isEmpty()) {
                showAlert("Lỗi", "Lỗi, không được bỏ trống!!!", Alert.AlertType.ERROR);
            } else if (TimKiemTF.getText().length() != 6 || !(TimKiemTF.getText().substring(0, 3).equals("MAY")) || !(TimKiemTF.getText().substring(3).matches("\\d+"))) {
                showAlert("Lỗi", "Lỗi, mã máy phải có đủ 6 ký tự và phải là mã máy hợp lệ!!!", Alert.AlertType.ERROR);
            } else {
                Boolean check = true;
                if (CheckMaMayTonTai(TimKiemTF.getText())) {
                    for(MayTinh mt : DSMayTinh){
                        if(mt.getMaMay().equals(TimKiemTF.getText())){
                            check = false;
                            BangDSMT.getSelectionModel().select(mt);
                            BangDSMT.scrollTo(mt);
                            BangDSMT.requestFocus();
                            showAlert("Xác nhận", "Đã tìm thấy máy tính có mã: " + TimKiemTF.getText(), Alert.AlertType.CONFIRMATION);
                            break;
                        }
                    }
                }
                if(check){
                    showAlert("Thông báo", "Không tìm thấy máy tính có mã: " + TimKiemTF.getText() + ". Vui lòng thử lại", Alert.AlertType.CONFIRMATION);
                }
            }
        }catch (NullPointerException exception){
            showAlert("Lỗi", "Lỗi, không được bỏ trống!!!", Alert.AlertType.ERROR);
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
