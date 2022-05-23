package GestionDeClient;

import DBUtil.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateClients implements Initializable {

    Connection connection;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField des;
    @FXML
    private TextField adresse;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;

    @FXML
    private TextField email;
    @FXML
    private TextField tel;
    @FXML
    private Label ChercherLabel;

    @FXML
    private TextField searchTF;

    @FXML
    private Label statusLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public boolean chercherClient(String email) throws  Exception{
        PreparedStatement pr=null;
        ResultSet rs=null;
        String sql="select * from client where email = ?";//On utilise l'email du client patient car c'est la seul donnée de la table qui est obligatoirement unique

        try{
            this.connection = DBConnection.connectionBD();
            pr=this.connection.prepareStatement(sql);
            pr.setString(1,email);
            rs = pr.executeQuery();
            if (rs.next()){
                return true;// dans le cas ou le client existe dans la BDD on retourne son code
            }
            else{
                return false;// sinon on retourne 0
            }
        }catch (SQLException e)
        {

            System.out.println("Vous avez un probleme dans la classe UpdateClients methode chercher Client");
            return false;
        }
        finally {
            assert pr != null;
            pr.close();
            assert rs!= null;
            rs.close();
        }

    }

    public void rechercheBtn() throws Exception {
        String email = this.searchTF.getText();
        if (this.chercherClient(email)) {
            ChercherLabel.setText("Le client existe dans la base de donnees \n Vous pouvez modifier ses informations.");
        }else
            ChercherLabel.setText("Le client n'existe pas !");
    }


    public void UpdateClient(ActionEvent event) throws  SQLException{

        PreparedStatement pr = null;
        String sql="Update client set nom_client = ?, prenom_client = ?, designation = ?, adresse = ?, email = ?,tel = ? where email = ?";

        try {
            this.connection = DBConnection.connectionBD();
            pr=this.connection.prepareStatement(sql);

            if(this.chercherClient(this.searchTF.getText())){

                if(this.nom.getText().length() != 0) {
                    pr.setString(1,this.nom.getText());
                }
                if(this.prenom.getText().length() != 0) {
                    pr.setString(2,this.prenom.getText());
                }
                if(this.des.getText().length() != 0){
                    pr.setString(3, this.des.getText());
                }
                if(this.adresse.getText().length() != 0){
                    pr.setString(4, this.adresse.getText());
                }
                if(this.email.getText().length() != 0){
                    pr.setString(5, this.email.getText());
                }
                if(this.tel.getText().length() != 0) {
                    pr.setString(6,this.tel.getText());
                }

                pr.setString(7,this.searchTF.getText());

                pr.executeUpdate();
                this.statusLabel.setText("Produit modifié avec succès !");
            }

        }catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("probleme de runtime");
            throw new RuntimeException(e);
        }finally {
            assert pr != null;
            pr.close();
            System.out.println("Fermeture du prepared statement avec succes");
            assert this.connection != null;
            this.connection.close();
            System.out.println("Fermeture du bdd avec succes");
        }
    }

    public void switchToPreviousScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/GestionClientele/UIClientele.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
