package GestionDeClient;

import DBUtil.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class UpdateClients implements Initializable {

    Connection connection;
    Client c = new Client();

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

    public boolean chercherClient(String email) throws  Exception{
        PreparedStatement pr=null;
        ResultSet rs=null;
        String sql="select * from client where email = ?";//On utilise l'email du client patient car c'est la seul donnée de la table qui est obligatoirement unique

        try{
            this.connection = DBConnection.connectionBD();
            pr=this.connection.prepareStatement(sql);
            pr.setString(1,email);
            rs = pr.executeQuery();
                c.setId_client(rs.getInt("id_client"));
                c.setType_client(rs.getString("type_client"));
                c.setNom_client(rs.getString("nom_client"));
                c.setPrenom_client(rs.getString("prenom_client"));
                c.setEmail(rs.getString("email"));
                c.setTel(rs.getString("tel"));
                c.setDesignation(rs.getString("designation"));
                c.setAdresse(rs.getString("adresse"));

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

                if(this.nom.getText().length() != 0)
                    pr.setString(1,this.nom.getText());
                    else
                        pr.setString(1,c.getNom_client());
                if(this.prenom.getText().length() != 0)
                    pr.setString(2,this.prenom.getText());
                    else
                        pr.setString(2,c.getPrenom_client());
                if(this.des.getText().length() != 0)
                    pr.setString(3, this.des.getText());
                    else
                        pr.setString(3,c.getDesignation());
                if(this.adresse.getText().length() != 0)
                    pr.setString(4, this.adresse.getText());
                    else
                        pr.setString(4,c.getAdresse());
                if(this.email.getText().length() != 0)
                    pr.setString(5, this.email.getText());
                    else
                        pr.setString(5,c.getEmail());
                if(this.tel.getText().length() != 0)
                    pr.setString(6,this.tel.getText());
                    else
                        pr.setString(6,c.getTel());

                pr.setString(7,this.searchTF.getText());

                pr.executeUpdate();
                this.statusLabel.setText("Client modifié avec succès !");
            }

        }catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Probleme de Runtime");
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
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
