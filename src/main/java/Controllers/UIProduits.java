package Controllers;

import DBUtil.DBConnection;
import GestionDeProduit.Produit;
import GestionDeProduit.TypeProduit;
import Login.option;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UIProduits implements Initializable {

    Connection conn = null;

    @FXML
    private Button SearchBtn;

    @FXML
    private Button shwBtn;

    @FXML
    private Button ajtBtn;

    @FXML
    private Button mdfBtn;

    @FXML
    private Button sprBtn;

    @FXML
    private TextField SearchTF;

    @FXML
    private TableView<Produit> TableProduit;

    @FXML
    private TableColumn<Produit, String> desTP;

    @FXML
    private TableColumn<Produit, String> dprTP;

    @FXML
    private TableColumn<Produit, String> dptTP;

    @FXML
    private TableColumn<Produit, Integer> idTP;

    @FXML
    private TableColumn<Produit, String> ingdTP;

    @FXML
    private TableColumn<Produit, Float> poidsnTP;

    @FXML
    private TableColumn<Produit, Double> prixTP;

    @FXML
    private TableColumn<Produit, Integer> qntTP;

    @FXML
    private TableColumn<Produit, String> refTP;

    @FXML
    private TableColumn<Produit, String> typeTP;

    @FXML
    private TableColumn<Produit, String> vnTP;



    public void initialize(URL location, ResourceBundle resouces){
    }
    // RECHERCHER UN PRODUIT DANS LA BASE DE DONNEES


    public void ShowProductScene() throws SQLException{
        Stage stage = (Stage)this.shwBtn.getScene().getWindow();
        stage.close();

        try{
            Stage primaryStage= new Stage();
            Parent root= FXMLLoader.load(getClass().getResource("/GestionProduits/AfficherProduit.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch (Exception e){
            System.out.println("Vous avez un probleme avec GestionProduits/AfficherProduit.fxml");
            e.printStackTrace();
        }
    }
    public void AddProductScene() throws SQLException{
        Stage stage = (Stage)this.ajtBtn.getScene().getWindow();
        stage.close();

        try{
            Stage primaryStage= new Stage();

            Parent root= FXMLLoader.load(getClass().getResource("/GestionProduits/AjouterProduit.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception e){
            System.out.println("Vous avez un probleme avec GestionProduits/AjouterProduit.fxml");
            e.printStackTrace();
        }
    }

    public void ModifyProductScene() throws SQLException{
        Stage stage = (Stage)this.mdfBtn.getScene().getWindow();
        stage.close();

        try{
            Stage primaryStage= new Stage();

            Parent root= FXMLLoader.load(getClass().getResource("/GestionProduits/ModifierProduit.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception e){
            System.out.println("Vous avez un probleme avec GestionProduits/ModifierProduit.fxml");
            e.printStackTrace();
        }
    }

    public void DeleteProductScene() throws SQLException{
        Stage stage = (Stage)this.sprBtn.getScene().getWindow();
        stage.close();

        try{
            Stage primaryStage= new Stage();

            Parent root= FXMLLoader.load(getClass().getResource("/GestionProduits/SupprimerProduit.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception e){
            System.out.println("Vous avez un probleme avec GestionProduits/SupprimerProduit.fxml");
            e.printStackTrace();
        }
    }



}
