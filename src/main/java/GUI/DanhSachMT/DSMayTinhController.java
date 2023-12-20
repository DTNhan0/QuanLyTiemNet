package GUI.DanhSachMT;

import BLL.InFoMayTinh.DanhSachMT;
import BLL.InFoMayTinh.MayTinh;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class DSMayTinhController implements Initializable {
        DanhSachMT DBSMayTinh;
        {
            try {
                DBSMayTinh = new DanhSachMT();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
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

        if(mt.getPhong().equals("T01")){
            PhongCB.setValue("T01");
        }else if(mt.getPhong().equals("T02")){
            PhongCB.setValue("T02");
        }else if (mt.getPhong().equals("VIP01")){
            PhongCB.setValue("VIP01");
        }
        ThemBT.setDisable(true);
        MMayTF.setDisable(true);
        PhongCB.setDisable(true);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] cacPhong = {"T01", "T02", "VIP01"};
        PhongCB.getItems().addAll(cacPhong);
        PhongCB.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12px; -fx-text-fill: WHITE;");

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
            BangDSMT.getItems().addAll(new DanhSachMT().getDanhSachMay());
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Không được để trống!");
            alert.showAndWait();
            return;
        }

        try {
            int BaoHanh = Integer.parseInt(BaoHanhText);
            Date NgayMua = Date.valueOf(ngayMuaValue);

            // Kiểm tra xem mã máy đã tồn tại trong phòng hay chưa
            if (CheckMayTonTaiTrongPhong(Mamay, Phong)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Mã máy đã tồn tại trong phòng!");
                alert.showAndWait();
                return;
            }

            MayTinh mt = new MayTinh(Mamay, Phong, BaoHanh, 0, true, false, NgayMua);

            // Thêm máy vào DSMT
            BangDSMT.getItems().add(mt);

            // Thêm máy vào DBS
            try {
                DBSMayTinh.ThemMay(mt);
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

    private boolean CheckMayTonTaiTrongPhong(String maMay, String phong) {
        List<MayTinh> danhSachMayTrongPhong;
        try {
            danhSachMayTrongPhong = DBSMayTinh.LayDSMayTheoPhong(phong);
        } catch (Exception e) {
            System.out.println("Lỗi truy vấn danh sách máy trong phòng");
            return false;
        }

        for (MayTinh may : danhSachMayTrongPhong) {
            if (may.getMaMay().equals(maMay)) {
                return true; // Mã máy đã tồn tại trong phòng
            }
        }
        return false; // Mã máy chưa tồn tại trong phòng
    }

    public void XoaMay() {
        if (BangDSMT.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Hãy chọn máy cần xóa!!!");
            alert.showAndWait();
        } else {
            MayTinh mt = BangDSMT.getSelectionModel().getSelectedItem();
            try {
                // Xóa máy trên Database
                DBSMayTinh.XoaMay(mt);

                // Xóa máy khỏi TableView
                BangDSMT.getItems().remove(mt);
            } catch (Exception e) {
                System.out.println("Xóa máy trên Database thất bại!!!");
            }
            BangDSMT.refresh();
        }
    }

    public void CapNhatMay(){
        if (BangDSMT.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Hãy chọn máy cần cập nhật!!!");
            alert.showAndWait();
        } else {
            MayTinh mayTinh = BangDSMT.getSelectionModel().getSelectedItem();

            String Mamay = mayTinh.getMaMay();
            String Phong = mayTinh.getPhong();
            int TGdung = mayTinh.getThoiGianDung();

            String BaoHanhText = BaoHanhTF.getText();
            LocalDate ngayMuaValue = NgayMuaTF.getValue();

            if (BaoHanhText.isEmpty() || ngayMuaValue == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Không được để trống!");
                alert.showAndWait();
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
                            DBSMayTinh.CapNhatMay(mt);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        BangDSMT.getItems().set(selectedRowIndex, mt);
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


    public void LamMoi(){
        MMayTF.setText(null);
        BaoHanhTF.setText(null);
        NgayMuaTF.setValue(null);
        MMayTF.setText(null);
        PhongCB.setValue("(Chọn phòng)");
        ThemBT.setDisable(false);
        MMayTF.setDisable(false);
        PhongCB.setDisable(false);
        BangDSMT.refresh();
        BangDSMT.getSelectionModel().clearSelection();
    }
}
