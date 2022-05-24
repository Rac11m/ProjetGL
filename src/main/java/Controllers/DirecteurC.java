package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import methode.Agent;

import java.io.IOException;
import java.sql.SQLException;

public class DirecteurC {

    private static Agent agent;
    @FXML
    private Button GP;
    @FXML private Button GC;
    @FXML private Button GV;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public DirecteurC() throws SQLException {
    }

    public void setDirecteur(Agent agent) {
        this.agent = agent;
    }

    public void EtablirPlanDeVenteBouton() {
        Stage stage =(Stage)this.GP.getScene().getWindow();
        stage.close();

        try {
            Stage primaryStage =new Stage();

            Parent root= FXMLLoader.load(getClass().getResource("/GestionProduits/UIProduits.fxml"));
            Scene scene = new Scene(root);
//            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            System.out.println("Vous avez un probleme avec GestionProduits/UIProduits.fxml");
            e.printStackTrace();
        }
    }

    public void EditePlanDeVenteBouton() {
        Stage stage =(Stage)this.GC.getScene().getWindow();
        stage.close();

        try {
            Stage primaryStage =new Stage();

            Parent root= FXMLLoader.load(getClass().getResource("/GestionClientele/UIClientele.fxml"));
            Scene scene = new Scene(root);
//            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            System.out.println("Vous avez un probleme avec GestionClientele/UIClientele.fxml");
            e.printStackTrace();
        }
    }

    public void ConsulterBilanDeVenteBouton() {
        Stage stage =(Stage)this.GV.getScene().getWindow();
        stage.close();

        try {
            Stage primaryStage =new Stage();

            Parent root= FXMLLoader.load(getClass().getResource("/Directeur/BilanDeVente.fxml"));
            Scene scene = new Scene(root);
//            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            System.out.println("Vous avez un probleme avec Directeur/BilanDeVente.fxml");
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
