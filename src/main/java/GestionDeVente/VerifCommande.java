package GestionDeVente;

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
import methode.Client;
import methode.Commande;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.Message;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class VerifCommande implements Initializable {

    ObservableList<Commande> comm = FXCollections.observableArrayList();

    Connection connection;

    private Parent root;
    private Scene scene;
    private Stage stage;

    @FXML
    private Button FactCliBtn;

    @FXML
    private TableColumn<Commande, String> DateCommCol;

    @FXML
    private TableColumn<Commande, String> HeurreCommCol;

    @FXML
    private TableColumn<Commande, Double> PrixCommCol;

    @FXML
    private Button ShowCommBtn;

    @FXML
    private TableColumn<Commande, Integer> idAgentCol;

    @FXML
    private TableColumn<Commande, Integer> idCliCol;

    @FXML
    private TableColumn<Commande, Integer> idCommCol;

    @FXML
    private TableColumn<Commande, CheckBox> VerifCol;

    @FXML
    private Button VerifBtn;

    @FXML
    private Label statusLabel;

    @FXML
    private TableView<Commande> tableComm;

    private Client [] emailC = new Client[50];

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

    public void FactCliScene() throws SQLException {
        Stage stage = (Stage)this.FactCliBtn.getScene().getWindow();
        stage.close();

        try{
            Stage primaryStage= new Stage();
            Parent root= FXMLLoader.load(getClass().getResource("/GestionVentes/FacturerClient.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch (Exception e){
            System.out.println("Vous avez un probleme avec GestionVentes/FacturerClient.fxml");
            e.printStackTrace();
        }
    }


    public void ShowComm(ActionEvent event) throws SQLException{
        PreparedStatement pr=null;
        ResultSet rs=null;

        try{
            this.connection = DBConnection.connectionBD();
        }catch (SQLException e){
            e.printStackTrace();
        }

        if(connection != null){
            String sql ="SELECT * FROM commande";

            try {
                pr= connection.prepareStatement(sql);
                rs= pr.executeQuery();

                if(!comm.isEmpty()) comm.clear();
                while (rs.next()){
                    Commande c = new Commande();
                    c.setId_commande(rs.getInt("id_commande"));
                    c.setDate_commande(rs.getString("D_commande"));
                    c.setHeure_commande(rs.getString("Heure_commande"));
                    c.setId_agent(rs.getInt("id_agent"));
                    c.setId_client(rs.getInt("id_client"));
                    c.setPrix_total(rs.getDouble("prix_total"));
                    CheckBox cb = new CheckBox();
                    cb.setSelected(false);
                    c.setSelectVerif(cb);
                    comm.add(c);

                    this.idCommCol.setCellValueFactory(new PropertyValueFactory<Commande, Integer>("id_commande"));
                    this.DateCommCol.setCellValueFactory(new PropertyValueFactory<Commande, String>("date_commande"));
                    this.HeurreCommCol.setCellValueFactory(new PropertyValueFactory<Commande, String>("heure_commande"));
                    this.idAgentCol.setCellValueFactory(new PropertyValueFactory<Commande, Integer>("id_agent"));
                    this.idCliCol.setCellValueFactory(new PropertyValueFactory<Commande, Integer>("id_client"));
                    this.PrixCommCol.setCellValueFactory(new PropertyValueFactory<Commande, Double>("prix_total"));
                    this.VerifCol.setCellValueFactory(new PropertyValueFactory<Commande, CheckBox>("selectVerif"));
                    tableComm.setItems(comm);
                }
            } catch (Exception e){
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

    public void CheckComm(ActionEvent event) throws SQLException{
        try{
            this.connection = DBConnection.connectionBD();
        }catch (SQLException e){
            e.printStackTrace();
        }

        PreparedStatement pr=null;
        PreparedStatement prC=null;
        ResultSet rsC=null;
        String sql="Delete from commande where id_commande = ?";
        String sqlC="select email from client where id_client = ?";
        try {
            int i=-1;
            while (!comm.isEmpty()){
                i++;
            pr=connection.prepareStatement(sql);
            prC=connection.prepareStatement(sqlC);
            pr.setInt(1,this.comm.get(i).getId_commande());
            Commande c = comm.get(i);
            if(c.getSelectVerif().isSelected()){
                pr.executeUpdate();

                prC=connection.prepareStatement(sqlC);
                prC.setInt(1,this.comm.get(i).getId_client());
                rsC=prC.executeQuery();
//                String recepient = rsC.getString("email");
                String recepient = "boiteprojet2022@gmail.com";
                sendMail(recepient,comm.get(i).getPrix_total());

                }

                comm.remove(i);
                this.statusLabel.setText("Commandes Vérifiés !");
            }



            this.statusLabel.setText("Commandes Vérifiés!");
            }catch (IndexOutOfBoundsException e){
            System.out.println("IndexOutOfBoundsException");
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {
            assert pr != null;
           pr.close();
            this.connection.close();
        }

    }
    private static void sendMail(String recepient,double PrixFact){
        Properties properties = new Properties();
        properties.put("mail.debug","true");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String myAccountEmail = "boiteprojet2022@gmail.com";
        String password= "leMotDePasseEstpassword111";

        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myAccountEmail,password);
            }
        });

       // Message message = prepareMessage(session,myAccountEmail,"boiteprojet2022@gmail.com");
        try {
            //Transport.send(message,myAccountEmail,password);
            //
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Facture de votre commande");
            message.setText("Bonjour,\nVotre Commande a été acceptée\nLe montant de votre commande est de : "+PrixFact+" DA\nCordialement");
            Transport.send(message);
            System.out.println("Message sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public void switchToPreviousScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/AgentDeVente/AgentDeVente.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
