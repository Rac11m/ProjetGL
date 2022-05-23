package GestionDeClient;

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

public class AddClients implements Initializable {

    Connection connection;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button ajtBTN;
    @FXML
    private TextField Prenom;

    @FXML
    private TextField adr;

    @FXML
    private TextField tel;

    @FXML
    private TextField mdp;

    @FXML
    private TextField des;

    @FXML
    private TextField Nom;
    @FXML
    private ChoiceBox<TypeClient> typePchbox;

    @FXML
    private TextField email;

    @FXML
    private Button retourBtn;

    @FXML
    private Label statusLabel;

    public void initialize(URL location, ResourceBundle resources){
        this.typePchbox.setItems(FXCollections.observableArrayList(TypeClient.values()));

    }
    public boolean isDataBaseConnected() {
        try {

            this.connection= DBConnection.connectionBD();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.connection != null;
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

            System.out.println("Vous avez un probleme dans la classe AddClient methode chercher Client");
            return false;
        }
        finally {
            assert pr != null;
            pr.close();
            assert rs!= null;
            rs.close();
        }

    }


    public void AddClient(ActionEvent event) throws SQLException {
        PreparedStatement pr=null;
        String sql="insert into client (type_client,nom_client,prenom_client,email,mdp,tel,designation,adresse)values (?,?,?,?,?,?,?,?)";
        try {
            this.connection = DBConnection.connectionBD();
            pr = connection.prepareStatement(sql);
            Client c = new Client();
            if (!this.chercherClient(this.email.getText())) {
                c.setType_client(this.typePchbox.getValue().toString());
                c.setNom_client(this.Nom.getText());
                c.setPrenom_client(this.Prenom.getText());
                c.setEmail(this.email.getText());
                c.setMdp(this.mdp.getText());
                c.setTel(this.tel.getText());
                c.setDesignation(this.des.getText());
                c.setAdresse(this.adr.getText());

                pr.setString(1, c.getType_client());
                pr.setString(2, c.getNom_client());
                pr.setString(3, c.getPrenom_client());
                pr.setString(4, c.getEmail());
                pr.setString(5, c.getMdp());
                pr.setString(6, c.getTel());
                pr.setString(7, c.getDesignation());
                pr.setString(8, c.getAdresse());

                pr.executeUpdate();
                this.statusLabel.setText("Client créé avec succès !");
            }else {
                this.statusLabel.setText("Client existe déja !");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            assert pr != null;
            pr.close();
            this.connection.close();
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
