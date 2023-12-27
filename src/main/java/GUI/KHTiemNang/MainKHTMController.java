package GUI.KHTiemNang;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import mainscript.quanlytiemnet.ChuyenCanhFXML;

import java.net.URL;
import java.util.ResourceBundle;

public class MainKHTMController implements Initializable {
    @FXML
    private BorderPane ChuyenPhong;

    @FXML
    private JFXComboBox<String> TinhNang;

    @FXML
    public void chonTinhNang(ActionEvent event) {
            if(TinhNang.getValue().equals("Chơi lâu nhất")){
                ChuyenCanhFXML object = new ChuyenCanhFXML();
                Pane view = object.getPage("/KHTiemNang/ChoiLauNhat.fxml");
                ChuyenPhong.setCenter(view);
            }else if (TinhNang.getValue().equals("Nạp nhiều nhất")){
                ChuyenCanhFXML object = new ChuyenCanhFXML();
                Pane view = object.getPage("/KHTiemNang/NapNhieuNhat.fxml");
                ChuyenPhong.setCenter(view);
            }else if (TinhNang.getValue().equals("Không onl cách 3 tháng trước")){
                ChuyenCanhFXML object = new ChuyenCanhFXML();
                Pane view = object.getPage("/KHTiemNang/KhongONL3thang.fxml");
                ChuyenPhong.setCenter(view);
            }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] cacPhong = {"Chơi lâu nhất", "Nạp nhiều nhất", "Không onl cách 3 tháng trước"};
        TinhNang.getItems().addAll(cacPhong);
        TinhNang.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-text-fill: WHITE; -fx-font-weight: bold;");
        ChuyenCanhFXML object = new ChuyenCanhFXML();
        Pane view = object.getPage("/KHTiemNang/ChoiLauNhat.fxml");
        ChuyenPhong.setCenter(view);
        TinhNang.setValue("Chơi lâu nhất");
    }
}
