module mainscript.quanlytiemnet {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires com.microsoft.sqlserver.jdbc;
    requires java.sql;
    requires java.naming;
    requires javafx.base;


    opens mainscript.quanlytiemnet to javafx.fxml;
    opens Controller to javafx.fxml;
    exports Controller;
    exports mainscript.quanlytiemnet;
}