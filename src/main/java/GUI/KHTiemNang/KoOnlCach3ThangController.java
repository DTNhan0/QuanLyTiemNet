package GUI.KHTiemNang;

import BLL.InFoTaiKhoan.TaiKhoan;
import BLL.InFoThongTinSD.DSThongTinSD;
import BLL.InFoThongTinSD.ThongTinSuDung;
import BLL.KHTiemNang;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import mainscript.quanlytiemnet.LabelAnimation;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class KoOnlCach3ThangController implements Initializable{
    @FXML
    private TableView<ThongTinSuDung> BangDSKH;

    @FXML
    private TableColumn<ThongTinSuDung, LocalDateTime> DateCol;

    @FXML
    private TableColumn<ThongTinSuDung, String> SdtCol;

    @FXML
    private Label TuaDeLB;

    @FXML
    private TableColumn<ThongTinSuDung, String> UsernameCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TuaDeLB.setTextFill(Color.RED);
        SdtCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSdt()));
        DateCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTgKetThuc()));
        UsernameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        List <ThongTinSuDung> thongTinSuDungs = new ArrayList<>();
        for(TaiKhoan tk : new KHTiemNang().LietKeTKkoSD3thang()){
            List <LocalDateTime> thoigianGN = new ArrayList<>();
            for(ThongTinSuDung thongTinSuDung : new DSThongTinSD().getDSThongTinSD()){
                if(tk.getSdt().equals(thongTinSuDung.getSdt())){
                    thoigianGN.add(thongTinSuDung.getTgKetThuc());
                }
            }
            LocalDateTime thoigian = new KHTiemNang().TimDateGanNhat(thoigianGN);
            ThongTinSuDung ttsd = new ThongTinSuDung(tk.getUsername(), tk.getSdt(), thoigian);
            thongTinSuDungs.add(ttsd);
        }
        BangDSKH.getItems().addAll(thongTinSuDungs);
    }
}
