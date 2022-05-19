/*package GestionDeProduit;

import DBUtil.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SearchProduitController implements Initializable {
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

    public void initialize(URL location, ResourceBundle resouces){

    }

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
            String sql="SELECT * FROM produits";
            String sql2="SELECT type FROM typeProduit where id_type in (select id_type from produits)";

            try {
                prod = FXCollections.observableArrayList();
                pr= conn.prepareStatement(sql);
                pr2=conn.prepareStatement(sql2);
               // pr.setString(1,SearchTF.getText());

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
            }
        }
    }
}
*/