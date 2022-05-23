package GestionDeProduit;

import DBUtil.DBConnection;
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
    private TextField vnTF;

    public void initialize(URL location, ResourceBundle resources){
    }


    public boolean chercherProduit(String s) throws SQLException,Exception{
        this.connection = DBConnection.connectionBD();
        PreparedStatement pr=null;
        ResultSet rs=null;
        String sql="select * from Produits where reference = ?";//On utilise la reference du produit car c'est la seul donnée de la table qui est obligatoirement unique

        try{
            pr=this.connection.prepareStatement(sql);
            pr.setString(1,s);

            rs = pr.executeQuery();

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

    public void Update(ActionEvent event) throws  SQLException{
        ////////////////// :::::::::::::::: NOTE IMPORTANTE :::::::::::::: ///////////////////
        /*
        Essayer:
         String sql = "Update produits set "
        apres if (.....) {sql+"......"}
              .
              .
              .
              .
              if(true) {sql+ " where reference = "+ this.searchTF.getText())};

         */
        PreparedStatement pr = null;
        String sql="Update Produits set designation = ?, \"valeur-nutritionnelle\" = ?, \"date-production\" = ?, \"date-peremption\" = ?,\"poids-net\" = ?,\"prix-vente\" = ?,quantite = ?,ingredients = ? where reference = ?";

        try {
            this.connection = DBConnection.connectionBD();
            pr=this.connection.prepareStatement(sql);

            if(this.chercherProduit(this.searchTF.getText())){

                if(this.desTF.getText().length() != 0) {
                    pr.setString(1,this.desTF.getText());
                }
                if(this.vnTF.getText().length() != 0) {
                    pr.setString(2,this.vnTF.getText());
                }
                if(this.dateProTF.getValue().toString().length() != 0){
                    pr.setString(3,this.dateProTF.getValue().toString());
                }
                if(this.datePrmTF.getValue().toString().length() != 0){
                    pr.setString(4,this.datePrmTF.getValue().toString());
                }
                if(this.pnTF.getText().length() != 0){
                    pr.setFloat(5, Float.parseFloat(this.pnTF.getText()));
                }
                if(this.prixTF.getText().length() != 0){
                    pr.setDouble(6, Double.parseDouble(this.prixTF.getText()));
                }
                if(this.qntTF.getText().length() != 0){
                    pr.setInt(7, Integer.parseInt(this.qntTF.getText()));
                }
                if(this.ingdTF.getText().length() != 0) {
                    pr.setString(8,this.ingdTF.getText());
                }

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
