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

public class DeleteClients implements Initializable {

    Connection connection;
    private Stage stage;
    private Scene scene;
    private Parent root;
    ObservableList<Client> client = FXCollections.observableArrayList();


    @FXML
    private Button searchTF;

    @FXML
    private TextField SearchTF;

    @FXML
    private Label StautsLabel;

    @FXML
    private Label ChercherLabel;

    @FXML
    private TableView<Client> TableClient;

    @FXML
    private TableColumn<Client, Integer> IDcol;
    @FXML
    private TableColumn<Client, String> typeCol;
    @FXML
    private TableColumn<Client, String> nomCol;
    @FXML
    private TableColumn<Client, String> prenomCol;
    @FXML
    private TableColumn<Client, String> emailCol;
    @FXML
    private TableColumn<Client, String> telCol;
    @FXML
    private TableColumn<Client, String> desCol;
    @FXML
    private TableColumn<Client, String> AdrCol;

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
            if (rs.next()){
                return true;// dans le cas ou le client existe dans la BDD on retourne son code
            }
            else{
                return false;// sinon on retourne 0
            }
        }catch (SQLException e)
        {

            System.out.println("Vous avez un probleme dans la classe DeleteClients methode chercher Client");
            return false;
        }
        finally {
            assert pr != null;
            pr.close();
            assert rs!= null;
            rs.close();
        }

    }
//    public void rechercheBtn() throws Exception {
//        String email = this.searchTF.getText();
//        if (this.chercherClient(email))
//            ChercherLabel.setText("Le client existe dans la base de donnees \n Vous pouvez le supprimer.");
//        else

//            ChercherLabel.setText("Le client n'existe pas !");
//    }

    public void rechercheBtn() throws SQLException{
        try{
            this.connection = DBConnection.connectionBD();
        } catch (SQLException e){
            e.printStackTrace();
        }

        if(connection != null){
            PreparedStatement pr= null;
            ResultSet rs= null;
            String sql="SELECT * FROM client where email = ?";

            try {

                String email = this.SearchTF.getText();
                if (this.chercherClient(email))
                    ChercherLabel.setText("Le client existe dans la base de donnees \n Vous pouvez le supprimer.");
                else
                    ChercherLabel.setText("Le client n'existe pas !");

                pr= connection.prepareStatement(sql);
                pr.setString(1,SearchTF.getText());
                rs= pr.executeQuery();
                if(!client.isEmpty()) client.clear();
                while (rs.next()) {
                    Client c = new Client();
                    c.setId_client(rs.getInt("id_client"));
                    c.setType_client(rs.getString("type_client"));
                    c.setNom_client(rs.getString("nom_client"));
                    c.setPrenom_client(rs.getString("prenom_client"));
                    c.setEmail(rs.getString("email"));
                    c.setTel(rs.getString("tel"));
                    c.setDesignation(rs.getString("designation"));
                    c.setAdresse(rs.getString("adresse"));

                    client.add(c);
                    IDcol.setCellValueFactory(new PropertyValueFactory<Client, Integer>("id_client"));
                    typeCol.setCellValueFactory(new PropertyValueFactory<Client, String>("type_client"));
                    nomCol.setCellValueFactory(new PropertyValueFactory<Client, String>("nom_client"));
                    prenomCol.setCellValueFactory(new PropertyValueFactory<Client, String>("prenom_client"));
                    emailCol.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
                    telCol.setCellValueFactory(new PropertyValueFactory<Client, String>("tel"));
                    desCol.setCellValueFactory(new PropertyValueFactory<Client, String>("designation"));
                    AdrCol.setCellValueFactory(new PropertyValueFactory<Client, String>("adresse"));

                    TableClient.setItems(client);
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                assert pr != null;
                pr.close();
                assert rs != null;
                rs.close();
                this.connection.close();
            }
        }
    }

    public void Delete(ActionEvent event) throws SQLException{
        this.connection = DBConnection.connectionBD();
        PreparedStatement pr = null;
        String sql = "Delete from client where email = ?";
        try {
            pr = connection.prepareStatement(sql);
            pr.setString(1, this.SearchTF.getText());
            if (this.chercherClient(this.SearchTF.getText())) {
                pr.executeUpdate();
                this.StautsLabel.setText("Client supprimé avec succès !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            assert pr != null;
            pr.close();
            assert this.connection != null;
            this.connection.close();
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
