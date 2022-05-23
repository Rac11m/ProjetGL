package Login;

import Controllers.AgentCommercialC;
import Controllers.AgentDeVenteC;
import Controllers.ClientC;
import Controllers.DirecteurC;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import methode.Agent;
import methode.Client;
import org.w3c.dom.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    LoginModel loginModel = new LoginModel();

    @FXML
    private AnchorPane anchrPane;

    @FXML
    private Label bienvueLabel;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField emlField;

    /* pour les commandes */

    private static TextField emlFieldStatic = new TextField();

    public static TextField getTextField(){
        return emlFieldStatic;
    }

    @FXML
    private Label emlLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField passwordField;

    @FXML
    private ChoiceBox<option> chcBox;

    @FXML
    private Label LoginStatus;

    @FXML
    private Button switchbtn;

    private String[] profiles = {"AgentVente","Agent commercial", "Client", "Directeur"};

    public void initialize(URL location, ResourceBundle resources){
        // chcBox.getItems().addAll(profiles);
        this.chcBox.setItems(FXCollections.observableArrayList(option.values()));


    }

    @FXML
    public void Login(ActionEvent event) {

        try {
            if (this.loginModel.isLogin(this.emlField.getText(), this.passwordField.getText(), ((option) this.chcBox.getValue()).toString())) {
                Stage stage = (Stage) this.btnLogin.getScene().getWindow();
                stage.close();

                switch (((option) this.chcBox.getValue()).toString()) {
                    case "AgentVente":
                        AgentVenteLogin();
                        break;
                    case "AgentCommercial":
                        AgentCommercialLogin();
                        break;
                    case "Directeur":
                        PDGLogin();
                        break;
                    case "Client":
                        ClientLogin();

                        break;
                }

            } else {
                this.LoginStatus.setText("--- Erreur de connection ---");
            }
        } catch (Exception e) {
            System.out.println("Vous avez un problème avec LoginController/ Login");
            e.printStackTrace();
        }
    }


    public void AgentVenteLogin() throws SQLException{
        AgentDeVenteC AV= new AgentDeVenteC();
        //crée mon objet Agent de vente contenant ses info
        Agent AgentV =loginModel.getInfoAgent(this.emlField.getText(), this.passwordField.getText());
        AV.setAgentDeVente(AgentV);

        //afficher la fenetre principale du/de la agent de vente
        try {

            Stage AgentVStage =new Stage();
            Parent root= FXMLLoader.load(getClass().getResource("/AgentDeVente/AgentDeVente.fxml"));
            Scene scene = new Scene(root);
//            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            AgentVStage.setScene(scene);
            AgentVStage.show();


        } catch (Exception e) {
            System.out.println("Vous avez un probleme avec AgentDeVente/AgentDeVente.fxml");
            e.printStackTrace();
        }
    }
    public void AgentCommercialLogin() throws SQLException{
        AgentCommercialC AC= new AgentCommercialC();

        //crйe mon objet Agent commercial contenant ses info
        Agent AComm =loginModel.getInfoAgent(this.emlField.getText(), this.passwordField.getText());
        AC.setAgentCommercial(AComm);



        //afficher la fenetre principale du/de la agent commercial
        try {

            Stage primaryStage =new Stage();

            Parent root= FXMLLoader.load(getClass().getResource("/AgentCommercial/AgentCommercial.fxml"));
            Scene scene = new Scene(root);
//            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();



        } catch (Exception e) {
            System.out.println("Vous avez un probleme avec AgentCommercial/AgentCommercial.fxml");
            e.printStackTrace();
        }
    }
    public void PDGLogin() throws SQLException{
        DirecteurC DC= new DirecteurC();

        //crée mon objet Agent commercial contenant ses info
        Agent Directeur =loginModel.getInfoAgent(this.emlField.getText(), this.passwordField.getText());
        DC.setDirecteur(Directeur);



        // afficher la fenetre principale du/de la agent commercial
        try {

            Stage primaryStage =new Stage();

            Parent root= FXMLLoader.load(getClass().getResource("/Directeur/Directeur.fxml"));
            Scene scene = new Scene(root);
//            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();



        } catch (Exception e) {
            System.out.println("Vous avez un probleme avec Directeur/Directeur.fxml");
            e.printStackTrace();
        }
    }
    public void ClientLogin() throws SQLException{
        ClientC cl= new ClientC();
        //crée mon objet client contenant ses info
        Client client =loginModel.getInfoClient(this.emlField.getText(), this.passwordField.getText());
        emlFieldStatic.setText(this.emlField.getText());
        System.out.println(emlFieldStatic.getText());
        cl.setClient(client);

        //afficher la fenetre principale du/de la client
        try {

           Stage clientStage =new Stage();
           Parent root= FXMLLoader.load(getClass().getResource("/Client/Client.fxml"));
           Scene scene = new Scene(root);
//         scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
           clientStage.setScene(scene);
           clientStage.show();

        } catch (Exception e) {
            System.out.println("Vous avez un probleme avec Client/Client.fxml");
            e.printStackTrace();
        }

    }



}