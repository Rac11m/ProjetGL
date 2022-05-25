package Login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginApp extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginApp.class.getResource("Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1400, 820);
            scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());

            stage.setTitle("Bienvenue!");
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(event -> {
                event.consume();
                    logout(stage);
        });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quitter");
        alert.setHeaderText("Vous êtes sur le point de vous déconnecter!");
        alert.setContentText("Souhaitez-vous enregistrer avant de quitter ? ");

        if (alert.showAndWait().get() == ButtonType.OK) {
//            stage = (Stage) scenePane.getScene().getWindow();
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}