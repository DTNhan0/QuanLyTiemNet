module mainscript.quanlytiemnet {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires com.microsoft.sqlserver.jdbc;
    requires java.sql;
    requires java.naming;
    requires javafx.base;
//    requires jdk.internal.le;


    opens mainscript.quanlytiemnet to javafx.fxml;
    opens GUI to javafx.fxml;
    exports GUI;
    exports mainscript.quanlytiemnet;
    exports GUI.TrangThaiMT;
    opens GUI.TrangThaiMT to javafx.fxml;
    exports GUI.DSTaiKhoan;
    opens GUI.DSTaiKhoan to javafx.fxml;
    exports GUI.DanhSachMT;
    opens GUI.DanhSachMT to javafx.fxml;
    exports GUI.KHTiemNang;
    opens GUI.KHTiemNang to javafx.fxml;
}