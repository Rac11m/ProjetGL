package GestionDeProduit;

import DBUtil.DBConnection;
import GestionDeClient.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DeleteProducts implements Initializable {

    Connection conn;
    private Stage stage;
    private Scene scene;
    private Parent root;

    ObservableList<Produit> prod = FXCollections.observableArrayList();

    @FXML
    private Button SearchBtn;

    @FXML
    private TextField SearchTF;

    @FXML
    private Label StautsLabel;
    @FXML
    private Label ChercherLabel;

    @FXML
    private TableView<Produit> SuppTV;

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

    @FXML
    private ImageView imageView,imageView2;
    @FXML
    private Image image1 = new Image(getClass().getResourceAsStream("/Images/milky.png"));
    @FXML
    private Image image2 = new Image(getClass().getResourceAsStream("/Images/back.png"));
    @FXML
    private Button retourBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageView.setImage(image1);
        imageView2.setImage(image2);
        retourBtn.setGraphic(imageView2);
    }

    public boolean chercherProduit(String ref) throws SQLException,Exception{
        PreparedStatement pr=null;
        ResultSet rs=null;
        String sql="select * from Produits where reference = ?";

        try{
            this.conn = DBConnection.connectionBD();
            pr=this.conn.prepareStatement(sql);
            pr.setString(1,ref);
            rs = pr.executeQuery();
            if (rs.next()){
                return true;// dans le cas ou le produit existe dans la BDD on retourne son code
            }
            else{
                return false;// sinon on retourne 0
            }
        }catch (SQLException e)
        {

            System.out.println("Vous avez un probleme dans la classe deleteProduit methode chercher produits");
            return false;
        }catch (Exception e){
          e.printStackTrace();
        } finally {
            assert pr != null;
            pr.close();
            assert rs!= null;
            rs.close();
//            assert this.conn != null;
//            this.conn.close();
        }
    return false;
    }


    public void rechercheBtn() throws SQLException{
        try{
            this.conn = DBConnection.connectionBD();
        } catch (SQLException e){
            e.printStackTrace();
        }

        if(conn != null){
            PreparedStatement pr= null;
            ResultSet rs= null;
            String sql="SELECT * FROM produits where reference = ?";

            try {

                String ref = this.SearchTF.getText();
                if (this.chercherProduit(ref))
                    ChercherLabel.setText("Le Produit existe dans la base de donnees.");
                else
                    ChercherLabel.setText("Le Produit n'existe pas !");

                pr= conn.prepareStatement(sql);
                pr.setString(1,SearchTF.getText());
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
                    SuppTV.setItems(prod);
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

    public String PickTheRightType(int numeroType) throws SQLException {
        PreparedStatement pr = null;
        ResultSet rs=null;
        try {
            this.conn = DBConnection.connectionBD();


            String sql = "select * from typeProduit where id_type = ?";

            pr= conn.prepareStatement(sql);
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

    public void Delete(ActionEvent event) throws SQLException{
        this.conn = DBConnection.connectionBD();
        PreparedStatement pr = null;
        String sql = "Delete from produits where reference = ?";
        try {
            pr = conn.prepareStatement(sql);
            pr.setString(1, this.SearchTF.getText());
            if (this.chercherProduit(this.SearchTF.getText())) {
                pr.executeUpdate();
                this.StautsLabel.setText("Produit supprimé avec succès !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            assert pr != null;
            pr.close();
            assert this.conn != null;
            this.conn.close();
        }
    }


    @FXML
    void switchToPreviousScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/GestionProduits/UIProduits.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }


}
