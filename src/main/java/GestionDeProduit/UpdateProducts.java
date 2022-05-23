package GestionDeProduit;

import DBUtil.DBConnection;
import GestionDeClient.Client;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateProducts implements Initializable {

    Connection connection;
    Produit p = new Produit();

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private DatePicker datePrmTF;

    @FXML
    private DatePicker dateProTF;

    @FXML
    private TextField desTF;

    @FXML
    private TextField ingdTF;

    @FXML
    private TextField pnTF;

    @FXML
    private TextField prixTF;

    @FXML
    private TextField qntTF;

    @FXML
    private TextField searchTF;

    @FXML
    private Label statusLabel;

    @FXML
    private Button updateBtn;

    @FXML
    private Button retourBtn;
    @FXML
    private Label ChercherLabel;

    @FXML
    private TextField vnTF;

    public void initialize(URL location, ResourceBundle resources){
    }


    public boolean chercherProduit(String s) throws SQLException,Exception{
        PreparedStatement pr=null;
        ResultSet rs=null;
        String sql="select * from Produits where reference = ?";//On utilise la reference du produit car c'est la seul donnée de la table qui est obligatoirement unique

        try{
            this.connection = DBConnection.connectionBD();
            pr=this.connection.prepareStatement(sql);
            pr.setString(1,s);
            rs = pr.executeQuery();
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
            if (rs.next()){
                return true;// dans le cas ou le produit existe dans la BDD on retourne son code
            }
            else{
                return false;// sinon on retourne 0
            }
        }catch (SQLException e) {
            System.out.println("Vous avez un probleme dans la classe produit methode chercher produits");
            return false;
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            assert pr != null;
            pr.close();
            assert rs!= null;
            rs.close();
            assert this.connection != null;
            this.connection.close();
        }
        return false;
    }

    public String PickTheRightType(int numeroType) throws SQLException {
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            this.connection = DBConnection.connectionBD();
            String sql = "select * from typeProduit where id_type = ?";

            pr = connection.prepareStatement(sql);
            pr.setInt(1, numeroType);
            rs = pr.executeQuery();

            return rs.getString("type");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pr.close();
            rs.close();
            this.connection.close();
        }
        return null;
    }
    public void rechercheBtn() throws Exception {
        String ref = this.searchTF.getText();
        if (this.chercherProduit(ref)) {
            ChercherLabel.setText("Le Produit existe dans la base de donnees \n Vous pouvez modifier ses informations.");
        }else
            ChercherLabel.setText("Le produit n'existe pas !");
    }
    public void Update(ActionEvent event) throws  SQLException{
        PreparedStatement pr = null;
        String sql="Update Produits set designation = ?, \"valeur-nutritionnelle\" = ?, \"date-production\" = ?, \"date-peremption\" = ?,\"poids-net\" = ?,\"prix-vente\" = ?,quantite = ?,ingredients = ? where reference = ?";

        try {
            this.connection = DBConnection.connectionBD();
            pr=this.connection.prepareStatement(sql);

            if(this.chercherProduit(this.searchTF.getText())){

                if(this.desTF.getText().length() != 0) {
                    pr.setString(1,this.desTF.getText());
                }else
                    pr.setString(1,p.getDesignation());

                if(this.vnTF.getText().length() != 0) {
                    pr.setString(2,this.vnTF.getText());
                }else
                    pr.setString(2,p.getValeur_nutritionnelle());

                if(this.dateProTF.getValue() != null){
                    pr.setString(3,this.dateProTF.getValue().toString());
                }else
                    pr.setString(3,p.getDate_production());

                if(this.datePrmTF.getValue() != null){
                    pr.setString(4,this.datePrmTF.getValue().toString());
                }else
                    pr.setString(4,p.getDate_peremption());

                if(this.pnTF.getText().length() != 0){
                    pr.setFloat(5, Float.parseFloat(this.pnTF.getText()));
                }else
                    pr.setFloat(5, p.getPoids_net());

                if(this.prixTF.getText().length() != 0){
                    pr.setDouble(6, Double.parseDouble(this.prixTF.getText()));
                }else
                    pr.setDouble(6, p.getPrix_vente());
                if(this.qntTF.getText().length() != 0){
                    pr.setInt(7, Integer.parseInt(this.qntTF.getText()));
                }else
                    pr.setInt(7, p.getQuantite());

                if(this.ingdTF.getText().length() != 0) {
                    pr.setString(8,this.ingdTF.getText());
                }else
                    pr.setString(8,p.getIngredients());

                pr.setString(9,this.searchTF.getText());

                pr.executeUpdate();
                this.statusLabel.setText("Produit modifié avec succès !");
            }

        }catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            assert pr != null;
            pr.close();
            assert this.connection != null;
            this.connection.close();
        }
    }

    public void switchToPreviousScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/GestionProduits/UIProduits.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
