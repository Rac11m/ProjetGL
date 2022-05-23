module com.example.projectgl {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.sql.rowset;

    opens Login to javafx.fxml;
    exports Login;

    opens Controllers to javafx.fxml;
    exports Controllers;
    opens GestionDeProduit to javafx.fxml;
    exports GestionDeProduit;

    opens GestionDeClient to javafx.fxml;
    exports GestionDeClient;
}