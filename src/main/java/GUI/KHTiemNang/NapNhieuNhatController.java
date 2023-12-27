package GUI.KHTiemNang;

import BLL.InFoTaiKhoan.TaiKhoan;
import BLL.KHTiemNang;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import mainscript.quanlytiemnet.LabelAnimation;

import java.net.URL;
import java.util.ResourceBundle;

public class NapNhieuNhatController implements Initializable{
    @FXML
    private TableView<TaiKhoan> BangDSKH;

    @FXML
    private TableColumn<TaiKhoan, Double> STConLaiCol;

    @FXML
    private TableColumn<TaiKhoan, Double> STTichLuyCol;

    @FXML
    private TableColumn<TaiKhoan, String> SdtCol;

    @FXML
    private Label TuaDeLB;

    @FXML
    private TableColumn<TaiKhoan, String> UsernameCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LabelAnimation.startRainbowAnimation(TuaDeLB);
        SdtCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSdt()));
        STTichLuyCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getSotienTichLuy()).asObject());
        STConLaiCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getSoTienConLai()).asObject());
        UsernameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        BangDSKH.getItems().addAll(new KHTiemNang().top10STN());
    }
}
