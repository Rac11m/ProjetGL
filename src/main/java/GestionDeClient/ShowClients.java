package GestionDeClient;

import DBUtil.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowClients {
    Connection connection;
    ObservableList<Client> client = FXCollections.observableArrayList();

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField SearchTF;
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


    public boolean chercherClient(String email) throws  Exception{
        PreparedStatement pr=null;
        ResultSet rs=null;
        String sql="select * from client where email = ?";//On utilise l'email du client car c'est la seul donnée de la table qui est obligatoirement unique

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
                    ChercherLabel.setText("Le client existe dans la base de donnees.");
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

    public void LoadAll(ActionEvent event) throws SQLException {
        PreparedStatement pr= null;
        ResultSet rs= null;

        ChercherLabel.setText("");

        try{
            this.connection = DBConnection.connectionBD();
        } catch (SQLException e){
            e.printStackTrace();
        }

        if(connection != null){
            String sql="SELECT * FROM client";

            try {
                pr= connection.prepareStatement(sql);
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
                connection.close();
            }
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
