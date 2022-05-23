package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import methode.Client;

import java.io.IOException;
import java.sql.SQLException;

public class ClientC {

    private static Client client;
    @FXML private Button VP;
    @FXML private Button PC;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public ClientC() throws SQLException {
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void VoirListeProduits() {
        Stage stage =(Stage)this.VP.getScene().getWindow();
        stage.close();

        try {
            Stage primaryStage =new Stage();
        //TODO
            Parent root= FXMLLoader.load(getClass().getResource("/Client/AfficherProduit.fxml"));
            Scene scene = new Scene(root);
//            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            System.out.println("Vous avez un probleme avec GestionProduits/AfficherProduit.fxml");
            e.printStackTrace();
        }
    }

    public void PasserCommande() {
        Stage stage =(Stage)this.PC.getScene().getWindow();
        stage.close();

        try {
            Stage primaryStage =new Stage();

            Parent root= FXMLLoader.load(getClass().getResource("/GestionCommandes/UICommandes.fxml"));
            Scene scene = new Scene(root);
//            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            System.out.println("Vous avez un probleme avec GestionCommandes/UICommandes.fxml");
            e.printStackTrace();
        }
    }

    public void switchToPreviousScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
