package Controllers;

import DBUtil.DBConnection;
import GestionDeProduit.Produit;
import GestionDeProduit.TypeProduit;
import Login.option;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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




    public void SearchBtn() throws SQLException{
        ObservableList<Produit> prod = null;


        try{
            this.conn = DBConnection.connectionBD();
        } catch (SQLException e){
            e.printStackTrace();
        }

        if(conn != null){
            PreparedStatement pr= null;
            PreparedStatement pr2= null;
            ResultSet rs= null;
            ResultSet rs2= null;
            String sql="SELECT * FROM produits where reference = ? or designation = ?";
            String sql2="SELECT type FROM typeProduit where id_type in (select id_type from produits)";

            try {
                prod = FXCollections.observableArrayList();
                pr= conn.prepareStatement(sql);
                pr2=conn.prepareStatement(sql2);
                pr.setString(1,SearchTF.getText());
                pr.setString(2,SearchTF.getText());


                rs= pr.executeQuery();
                rs2=pr2.executeQuery();
                while (rs.next() && rs2.next()) {
                    Produit p = new Produit();
                    p.setId_produit(rs.getInt("id_produit"));
                    p.setId_type(rs.getInt("id_type"));
                    p.setType(rs2.getString("type"));
                    p.setReference(rs.getString("reference"));
                    p.setDesignation(rs.getString("designation"));
                    p.setValeur_nutritionnelle(rs.getString("valeur-nutritionnelle"));
                    p.setDate_production(rs.getString("date-production"));
                    p.setDate_peremption(rs.getString("date-peremption"));
                    p.setPoids_net(rs.getFloat("poids-net"));
                    p.setPrix_vente(rs.getFloat("prix-vente"));
                    p.setQuantite(rs.getInt("quantite"));
                    p.setIngredients(rs.getString("ingredients"));

                    prod.add(p);
                    idTP.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("id_produit"));
                    //typeTP.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("id_type"));
                    typeTP.setCellValueFactory(new PropertyValueFactory<Produit, String>("type"));
                    desTP.setCellValueFactory(new PropertyValueFactory<Produit, String>("designation"));
                    dprTP.setCellValueFactory(new PropertyValueFactory<Produit, String>("date_peremption"));
                    dptTP.setCellValueFactory(new PropertyValueFactory<Produit, String>("date_production"));
                    ingdTP.setCellValueFactory(new PropertyValueFactory<Produit, String>("ingredients"));
                    poidsnTP.setCellValueFactory(new PropertyValueFactory<Produit, Float>("poids_net"));
                    qntTP.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("quantite"));
                    vnTP.setCellValueFactory(new PropertyValueFactory<Produit, String>("valeur_nutritionnelle"));
                    prixTP.setCellValueFactory(new PropertyValueFactory<Produit, Double>("prix_vente"));
                    refTP.setCellValueFactory(new PropertyValueFactory<Produit, String>("reference"));
                    TableProduit.setItems(prod);
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                assert pr != null;
                pr.close();
                assert rs != null;
                rs.close();
                assert pr2 != null;
                pr2.close();
                assert  rs2 != null;
                rs2.close();
                conn.close();
            }
        }
    }
    // AFFICHER TOUS LES PRODUITS DE LA BASE DE DONNEES

    public void LoadAll() throws SQLException{
        ObservableList<Produit> prod = null;


        try{
            this.conn = DBConnection.connectionBD();
        } catch (SQLException e){
            e.printStackTrace();
        }

        if(conn != null){
            PreparedStatement pr= null;
            PreparedStatement pr2= null;
            ResultSet rs= null;
            ResultSet rs2= null;
            String sql="SELECT * FROM produits";
            String sql2="SELECT type FROM typeProduit where id_type in (select id_type from produits)";

            try {
                prod = FXCollections.observableArrayList();
                pr= conn.prepareStatement(sql);
                pr2=conn.prepareStatement(sql2);



                rs= pr.executeQuery();
                rs2=pr2.executeQuery();
                while (rs.next() && rs2.next()) {
                    Produit p = new Produit();
                    p.setId_produit(rs.getInt("id_produit"));
                    p.setId_type(rs.getInt("id_type"));
                    p.setType(rs2.getString("type"));
                    p.setReference(rs.getString("reference"));
                    p.setDesignation(rs.getString("designation"));
                    p.setValeur_nutritionnelle(rs.getString("valeur-nutritionnelle"));
                    p.setDate_production(rs.getString("date-production"));
                    p.setDate_peremption(rs.getString("date-peremption"));
                    p.setPoids_net(rs.getFloat("poids-net"));
                    p.setPrix_vente(rs.getFloat("prix-vente"));
                    p.setQuantite(rs.getInt("quantite"));
                    p.setIngredients(rs.getString("ingredients"));

                    prod.add(p);
                    idTP.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("id_produit"));
                    //typeTP.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("id_type"));
                    typeTP.setCellValueFactory(new PropertyValueFactory<Produit, String>("type"));
                    desTP.setCellValueFactory(new PropertyValueFactory<Produit, String>("designation"));
                    dprTP.setCellValueFactory(new PropertyValueFactory<Produit, String>("date_peremption"));
                    dptTP.setCellValueFactory(new PropertyValueFactory<Produit, String>("date_production"));
                    ingdTP.setCellValueFactory(new PropertyValueFactory<Produit, String>("ingredients"));
                    poidsnTP.setCellValueFactory(new PropertyValueFactory<Produit, Float>("poids_net"));
                    qntTP.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("quantite"));
                    vnTP.setCellValueFactory(new PropertyValueFactory<Produit, String>("valeur_nutritionnelle"));
                    prixTP.setCellValueFactory(new PropertyValueFactory<Produit, Double>("prix_vente"));
                    refTP.setCellValueFactory(new PropertyValueFactory<Produit, String>("reference"));
                    TableProduit.setItems(prod);
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                assert pr != null;
                pr.close();
                assert rs != null;
                rs.close();
                assert pr2 != null;
                pr2.close();
                assert  rs2 != null;
                rs2.close();
                conn.close();
            }
        }
    }


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
