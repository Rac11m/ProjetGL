package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UIClientele implements Initializable {
    Connection conn = null;

    @FXML
    private Button shwBtn;

    @FXML
    private Button ajtBtn;

    @FXML
    private Button mdfBtn;

    @FXML
    private Button sprBtn;
    private Parent root;
    private Scene scene;
    private Stage stage;
    @FXML
    private ImageView imageView,imageView2,imageView3, imageView4,imageView5,imageView6;
    @FXML
    private Image image1 = new Image(getClass().getResourceAsStream("/Images/milky.png"));
    @FXML
    private Image image2 = new Image(getClass().getResourceAsStream("/Images/back.png"));
    @FXML
    private Image image3 = new Image(getClass().getResourceAsStream("/Images/afficherClient.png"));
    @FXML
    private Image image4 = new Image(getClass().getResourceAsStream("/Images/ajouterClient.png"));
    @FXML
    private Image image5 = new Image(getClass().getResourceAsStream("/Images/supprimerClient.png"));
    @FXML
    private Image image6 = new Image(getClass().getResourceAsStream("/Images/modifierClient.png"));
    @FXML
    private Button retourBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageView.setImage(image1);
        imageView2.setImage(image2);
        imageView3.setImage(image3);
        imageView4.setImage(image4);
        imageView5.setImage(image5);
        imageView6.setImage(image6);
        retourBtn.setGraphic(imageView2);
    }
    public void ShowClientScene() throws SQLException {
        Stage stage = (Stage)this.shwBtn.getScene().getWindow();
        stage.close();

        try{
            Stage primaryStage= new Stage();
            Parent root= FXMLLoader.load(getClass().getResource("/GestionClientele/AfficherClient.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch (Exception e){
            System.out.println("Vous avez un probleme avec GestionClientele/AfficherClient.fxml");
            e.printStackTrace();
        }
    }

    public void AddClientScene() throws SQLException{
        Stage stage = (Stage)this.ajtBtn.getScene().getWindow();
        stage.close();

        try{
            Stage primaryStage= new Stage();

            Parent root= FXMLLoader.load(getClass().getResource("/GestionClientele/AjouterClient.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception e){
            System.out.println("Vous avez un probleme avec GestionClientele/AjouterClient.fxml");
            e.printStackTrace();
        }
    }

    public void ModifyClientScene() throws SQLException{
        Stage stage = (Stage)this.mdfBtn.getScene().getWindow();
        stage.close();

        try{
            Stage primaryStage= new Stage();

            Parent root= FXMLLoader.load(getClass().getResource("/GestionClientele/ModifierClient.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception e){
            System.out.println("Vous avez un probleme avec GestionClientele/ModifierClient.fxml");
            e.printStackTrace();
        }
    }

    public void DeleteClientScene() throws SQLException{
        Stage stage = (Stage)this.sprBtn.getScene().getWindow();
        stage.close();

        try{
            Stage primaryStage= new Stage();

            Parent root= FXMLLoader.load(getClass().getResource("/GestionClientele/SupprimerClient.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception e){
            System.out.println("Vous avez un probleme avec GestionClientele/SupprimerClient.fxml");
            e.printStackTrace();
        }
    }

    public void switchToPreviousScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AgentDeVente/AgentDeVente.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        stage.show();
    }
    public void switchToPreviousSceneAC(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AgentCommercial/AgentCommercial.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        stage.show();
    }
}
