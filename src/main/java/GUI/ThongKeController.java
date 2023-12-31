package GUI;

import BLL.InFoLichSuNap.DSLichSuNap;
import BLL.InFoLichSuNap.LichSuNap;
import BLL.InFoThongTinSD.DSThongTinSD;
import BLL.InFoThongTinSD.ThongTinSuDung;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class ThongKeController implements Initializable {

    List<LichSuNap> dsLichSuNap = new DSLichSuNap().getDsLichSuNap();

    @FXML
    private TableColumn<LichSuNap, Integer> ID1Col;

    @FXML
    private TableColumn<ThongTinSuDung, Integer> ID2Col;

    @FXML
    private RadioButton KhoangThoiGianRB;

    @FXML
    private Button LamMoiBtn;

    @FXML
    private DatePicker NgayDP;

    @FXML
    private RadioButton NgayRB;

    @FXML
    private DatePicker TGBatDauDP;

    @FXML
    private DatePicker TGKetThucDP;

    @FXML
    private ToggleGroup TimeRB;

    @FXML
    private Button XacNhanBtn;

    @FXML
    private TableView<LichSuNap> bangLichSuNap;

    @FXML
    private TableView<ThongTinSuDung> bangThongTinSuDung;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private TextField doanhThuTF;

    @FXML
    private TableColumn<ThongTinSuDung, String> mayCol;

    @FXML
    private TextField ngayDoanhThuTF;

    @FXML
    private TextField doanhThuMaxTF;

    @FXML
    private TableColumn<LichSuNap, String> sdt1Col;

    @FXML
    private TableColumn<ThongTinSuDung, String> sdt2Col;

    @FXML
    private TableColumn<LichSuNap, Double> soTienCol;

    @FXML
    private TableColumn<LichSuNap, String> taiKhoan1Col;

    @FXML
    private TableColumn<ThongTinSuDung, String> taiKhoan2Col;

    @FXML
    private TableColumn<ThongTinSuDung, LocalDateTime> tgBatDauCol;

    @FXML
    private TableColumn<ThongTinSuDung, LocalDateTime> tgKetThucCol;

    @FXML
    private TableColumn<LichSuNap, LocalDateTime> thoiGianNapCol;

    @FXML
    private TextField tkNapTF;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshThongTin();
    }

    @FXML
    void khoangThoiGianRBCLicked(ActionEvent event) {
        NgayRB.setDisable(true);
        NgayDP.setDisable(true);
        NgayDP.setValue(null);
        TGBatDauDP.setDisable(false);
        TGKetThucDP.setDisable(false);
    }

    @FXML
    void ngayRBCLicked(ActionEvent event) {
        NgayDP.setDisable(false);
        KhoangThoiGianRB.setDisable(true);
        TGBatDauDP.setDisable(true);
        TGBatDauDP.setValue(null);
        TGKetThucDP.setDisable(true);
        TGKetThucDP.setValue(null);
    }

    @FXML
    void refreshThongTin() {
        NgayRB.setSelected(false);
        NgayRB.setDisable(false);
        KhoangThoiGianRB.setSelected(false);
        KhoangThoiGianRB.setDisable(false);
        NgayDP.setDisable(true);
        NgayDP.setValue(null);
        TGBatDauDP.setDisable(true);
        TGBatDauDP.setValue(null);
        TGKetThucDP.setDisable(true);
        TGKetThucDP.setValue(null);
        doanhThuTF.setText("");
        tkNapTF.setText("");
        ngayDoanhThuTF.setText("");
        doanhThuMaxTF.setText("");
        bangLichSuNap.getItems().clear();
        bangThongTinSuDung.getItems().clear();
        barChart.getData().clear();
    }

    @FXML
    private void displayDataForDate(LocalDate ngay) {
        try {
            Double doanhThuTheoNgay = new DSLichSuNap().getDoanhThuTheoNgay(ngay);
            doanhThuTF.setText(String.valueOf(doanhThuTheoNgay));

            LichSuNap doanhThuLonNhatTheoNgay = new DSLichSuNap().getDoanhThuLonNhatTheoNgay(ngay);
            tkNapTF.setText(doanhThuLonNhatTheoNgay != null ? String.valueOf(doanhThuLonNhatTheoNgay.getTaiKhoan()) : "Không có dữ liệu");

            ngayDoanhThuTF.clear();
            doanhThuMaxTF.clear();

            displayLichSuNapTable(new DSLichSuNap().getDsLichSuNapTheoNgay(ngay));

            LocalDate nextDay = ngay.plusDays(1);
            displayThongTinSuDungTable(new DSThongTinSD().getThongTinSuDungThongKe(ngay, nextDay));

            Integer soPhut = new DSThongTinSD().getTongSoPhut(ngay, nextDay);
            displayBarChart(doanhThuTheoNgay, soPhut);
        } catch (Exception e) {
            showAlert("Lỗi", "Lỗi, phải điền ngày tháng năm hợp lệ và không được bỏ trống!!!", Alert.AlertType.ERROR);
        }
    }

    private void displayDataForDateRange(LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        try {
            Double doanhThuTheoTime = new DSLichSuNap().getDoanhThuTheoTime(ngayBatDau, ngayKetThuc);
            doanhThuTF.setText(String.valueOf(doanhThuTheoTime));

            LichSuNap doanhThuLonNhatTheoTime = new DSLichSuNap().getDoanhThuLonNhatTheoTime(ngayBatDau, ngayKetThuc);
            tkNapTF.setText(doanhThuLonNhatTheoTime != null ? String.valueOf(doanhThuLonNhatTheoTime.getTaiKhoan()) : "Không có dữ liệu");

            LichSuNap doanhThuMax = new DSLichSuNap().getDoanhThuMax(ngayBatDau, ngayKetThuc);
            ngayDoanhThuTF.setText(String.valueOf(doanhThuMax.getTgNap().toLocalDate()));
            doanhThuMaxTF.setText(String.valueOf(doanhThuMax.getSoTien()));

            displayLichSuNapTable(new DSLichSuNap().getDsLichSuNapTheoTime(ngayBatDau, ngayKetThuc));
            displayThongTinSuDungTable(new DSThongTinSD().getThongTinSuDungThongKe(ngayBatDau, ngayKetThuc));

            Integer soPhut = new DSThongTinSD().getTongSoPhut(ngayBatDau, ngayKetThuc);
            displayBarChart(doanhThuTheoTime, soPhut);
        } catch (Exception e) {
            showAlert("Lỗi", "Lỗi, phải điền ngày tháng năm hợp lệ và không được bỏ trống!!!", Alert.AlertType.ERROR);
        }
    }

    private void displayLichSuNapTable(List<LichSuNap> dsLichSuNap) {
        bangLichSuNap.getItems().clear();
        ID1Col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        taiKhoan1Col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTaiKhoan()));
        sdt1Col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSdt()));
        soTienCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getSoTien()).asObject());
        thoiGianNapCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTgNap()));
        bangLichSuNap.getItems().addAll(dsLichSuNap);
    }

    private void displayThongTinSuDungTable(List<ThongTinSuDung> dsTTSD) {
        bangThongTinSuDung.getItems().clear();
        ID2Col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        taiKhoan2Col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        sdt2Col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSdt()));
        mayCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaMay()));
        tgBatDauCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTgBatDau()));
        tgKetThucCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTgKetThuc()));
        bangThongTinSuDung.getItems().addAll(dsTTSD);
    }

    private void displayBarChart(Double doanhThu, Integer soPhut) {
        barChart.getData().clear();

        Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Doanh thu");
        series1.getData().add(new XYChart.Data<>("", doanhThu / 1000));

        Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Số phút");
        series2.getData().add(new XYChart.Data<>("", soPhut));

        barChart.getData().addAll(series1, series2);
    }

    @FXML
    public void xacNhan() throws Exception {
        barChart.getData().clear();
        if (NgayRB.isSelected()) {
            LocalDate ngay = NgayDP.getValue();
            displayDataForDate(ngay);
        } else if (KhoangThoiGianRB.isSelected()) {
            LocalDate ngayBatDau = TGBatDauDP.getValue();
            LocalDate ngayKetThuc = TGKetThucDP.getValue();
            displayDataForDateRange(ngayBatDau, ngayKetThuc);
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
