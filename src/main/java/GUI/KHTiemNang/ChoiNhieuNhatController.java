package GUI.KHTiemNang;

import BLL.InFoTaiKhoan.TaiKhoan;
import BLL.KHTiemNang;
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

public class ChoiNhieuNhatController implements Initializable {

    @FXML
    private Label TuaDeLB;

    @FXML
    private TableView<TaiKhoan> BangDSKH;

    @FXML
    private TableColumn<TaiKhoan, String> SdtCol;

    @FXML
    private TableColumn<TaiKhoan, Integer> SoPhutSDCol;

    @FXML
    private TableColumn<TaiKhoan, String> UsernameCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LabelAnimation.startRainbowAnimation(TuaDeLB);
        SdtCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSdt()));
        SoPhutSDCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSophutdadung() / 60).asObject());
        UsernameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        BangDSKH.getItems().addAll(new KHTiemNang().top10SD());
    }
}
