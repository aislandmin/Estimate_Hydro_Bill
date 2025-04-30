module com.example.xiaomin_final {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.xiaomin_final to javafx.fxml;
    exports com.example.xiaomin_final;
}