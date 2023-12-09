package mainscript.quanlytiemnet;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import mainscript.quanlytiemnet.ChuyenCanhFXML;

public class MainController implements Initializable{
    public BorderPane MainSwitching;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    @FXML
    public void ChonDSKH(ActionEvent event) throws IOException {
        ChuyenCanhFXML object = new ChuyenCanhFXML();
        Pane view = object.getPage("/DanhSachTK/MainDSTK.fxml");
        MainSwitching.setCenter(view);
    }
    public void ChonTK(ActionEvent event) throws IOException {
        ChuyenCanhFXML object = new ChuyenCanhFXML();
        Pane view = object.getPage("/ThongKe/MainTK.fxml");
        MainSwitching.setCenter(view);
    }

    public void ChonTrangThaiMT(ActionEvent event) throws IOException {
        ChuyenCanhFXML object = new ChuyenCanhFXML();
        Pane view = object.getPage("/TrangThaiMayTinh/MainTrangThaiMT.fxml");
        MainSwitching.setCenter(view);
    }
}
