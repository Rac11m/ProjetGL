package GestionDeProduit;

import DBUtil.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ShowProducts {

    ObservableList<Produit> prod = FXCollections.observableArrayList();

    Connection conn = null;

    @FXML
    private Button SearchBtn;

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

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void initialize(URL location, ResourceBundle resouces){
    }
    // RECHERCHER UN PRODUIT DANS LA BASE DE DONNEES


    public void SearchBtn() throws SQLException{
       try{
            this.conn = DBConnection.connectionBD();
        } catch (SQLException e){
            e.printStackTrace();
        }

        if(conn != null){
            PreparedStatement pr= null;
            ResultSet rs= null;
            String sql="SELECT * FROM produits where reference = ? or designation = ?";

            try {
                pr= conn.prepareStatement(sql);
                pr.setString(1,SearchTF.getText());
                pr.setString(2,SearchTF.getText());
                rs= pr.executeQuery();
                if(!prod.isEmpty()) prod.clear();
                while (rs.next()) {
                    Produit p = new Produit();
                    p.setId_produit(rs.getInt("id_produit"));
                    p.setId_type(rs.getInt("id_type"));
                    p.setType(this.PickTheRightType(p.getId_type()));
                    //p.setType(rs2.getString("type"));
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
                this.conn.close();
            }
        }
    }
    // AFFICHER TOUS LES PRODUITS DE LA BASE DE DONNEES


    public String PickTheRightType(int numeroType) throws SQLException {
        PreparedStatement pr = null;
        ResultSet rs=null;
        try {
            this.conn = DBConnection.connectionBD();


            String sql = "select * from typeProduit where id_type = ?";

            pr=conn.prepareStatement(sql);
            pr.setInt(1,numeroType);
            rs=pr.executeQuery();

            return rs.getString("type");


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
          pr.close();
          rs.close();
          this.conn.close();
        }


        return null;
    }


    public void LoadAll(ActionEvent event) throws SQLException{
       // prod.clear();
        PreparedStatement pr= null;
        ResultSet rs= null;


        try{
            this.conn = DBConnection.connectionBD();
        } catch (SQLException e){
            e.printStackTrace();
        }

        if(conn != null){
            String sql="SELECT * FROM produits";

            try {
                pr= conn.prepareStatement(sql);
                rs= pr.executeQuery();
                if(!prod.isEmpty()) prod.clear();
                while (rs.next()) {
                    Produit p = new Produit();
                    p.setId_produit(rs.getInt("id_produit"));
                    p.setId_type(rs.getInt("id_type"));
                    p.setType(this.PickTheRightType(p.getId_type()));

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
                conn.close();
            }
        }
    }

    public void switchToPreviousScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/GestionProduits/UIProduits.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToPreviousSceneClient(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Client/Client.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
