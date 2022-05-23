package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    public void initialize(URL location, ResourceBundle resouces){
    }
    public void ShowClientScene() throws SQLException {
        Stage stage = (Stage)this.shwBtn.getScene().getWindow();
        stage.close();

        try{
            Stage primaryStage= new Stage();
            Parent root= FXMLLoader.load(getClass().getResource("/GestionClientele/AfficherClient.fxml"));
            Scene scene = new Scene(root);
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
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception e){
            System.out.println("Vous avez un probleme avec GestionClientele/SupprimerClient.fxml");
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
