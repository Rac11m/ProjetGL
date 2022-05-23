package Controllers;

import DBUtil.DBConnection;
import GestionDeProduit.Produit;
import Login.LoginController;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UICommandes implements Initializable {

    ObservableList<Produit> prod = FXCollections.observableArrayList();
    ObservableList<Commande> comm = FXCollections.observableArrayList();
    Connection connection;

    private double prixTotal=0;
    @FXML
    private Button ajtprdBtn;

    @FXML
    private Button commandeBtn;

    @FXML
    private Label prixtotalTF;

    @FXML
    private TextField qntTF;

    @FXML
    private TextField refTF;

    @FXML
    private Label statusLabel;

    @FXML
    private TableView<Produit> tableView;

    @FXML
    private TableColumn<Produit, String> produitCol;

    @FXML
    private TableColumn<Produit, Integer> qntCol;

    @FXML
    private TableColumn<Produit, Double> prixuCol;

    @FXML
    private TableColumn<Produit, Double> prixCol;

    public void initialize(URL location, ResourceBundle resources){

    }

    public boolean chercherProduit(String s) throws  Exception{
        PreparedStatement pr=null;
        ResultSet rs=null;
        String sql="select * from produits where reference = ?";//On utilise le numero de telephone du patient car c'est la seul donnée de la table qui est obligatoirement unique

        try{
            this.connection = DBConnection.connectionBD();
            pr=this.connection.prepareStatement(sql);
            pr.setString(1,s);

            rs = pr.executeQuery();
            if (rs.next()){
                return true;// dans le cas ou le patient existe dans la BDD on retourne son code
            }
            else{
                return false;// sinon on retourne 0
            }
        }catch (SQLException e)
        {

            System.out.println("Vous avez un probleme dans la classe Secretaire methode chercher produits");
            return false;
        }
        finally {
            assert pr != null;
            pr.close();
            assert rs!= null;
            rs.close();
        }

    }

    public String PickTheRightType(int numeroType) throws SQLException {
        PreparedStatement pr = null;
        ResultSet rs=null;
        try {
            this.connection = DBConnection.connectionBD();


            String sql = "select * from typeProduit where id_type = ?";

            pr=connection.prepareStatement(sql);
            pr.setInt(1,numeroType);
            rs=pr.executeQuery();

            return rs.getString("type");


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            pr.close();
            rs.close();
            this.connection.close();
        }


        return null;
    }

    public void AddCommande(ActionEvent event) throws SQLException {

        try{
            this.connection = DBConnection.connectionBD();
        } catch (SQLException e){
            e.printStackTrace();
        }

        if(connection != null){

            PreparedStatement pr= null;
            ResultSet rs= null;
            try {


                if(this.chercherProduit(this.refTF.getText())) {
                    String sql="SELECT * FROM produits where reference = ?";
                    pr = connection.prepareStatement(sql);
                    pr.setString(1, this.refTF.getText());
                    rs = pr.executeQuery();


                   // p.setQuantite(rs.getInt("quantite"));

                    if(Integer.parseInt(this.qntTF.getText()) <= rs.getInt("quantite")) {
                        Produit p = new Produit();
                        p.setId_produit(rs.getInt("id_produit"));
                        p.setId_type(rs.getInt("id_type"));
                        p.setType(this.PickTheRightType(p.getId_type()));
                        //p.setType(rs2.getString("type"));
                        p.setReference(rs.getString("reference"));
                        p.setDesignation(rs.getString("designation"));
                        p.setValeur_nutritionnelle(rs.getString("valeur-nutritionnelle"));
                        p.setDate_production(rs.getString("date-production"));
                        p.setDate_peremption(rs.getString("date-peremption"));
                        p.setPoids_net(rs.getFloat("poids-net"));
                        p.setPrix_vente(rs.getDouble("prix-vente"));
                        p.setQuantite(Integer.parseInt(this.qntTF.getText()));
                        p.setIngredients(rs.getString("ingredients"));
                        p.setPrixCommande((p.getPrix_vente()*p.getQuantite()));
                        prixTotal += p.getPrixCommande();
                        prod.add(p);

                        this.produitCol.setCellValueFactory(new PropertyValueFactory<Produit, String>("designation"));
                        this.prixuCol.setCellValueFactory(new PropertyValueFactory<Produit, Double>("prix_vente"));
                        this.prixCol.setCellValueFactory(new PropertyValueFactory<Produit, Double>("prixCommande"));

                        this.qntCol.setCellValueFactory(new PropertyValueFactory<Produit,Integer>("quantite"));
                        tableView.setItems(prod);

                        this.prixtotalTF.setText(""+prixTotal);
                    } else {
                        this.statusLabel.setText("quantite insuffisante !");
                    }

                    }else {
                    System.out.println("Produit n'existe pas");
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

    public void Commander(ActionEvent event ) throws SQLException{
        try{
            this.connection = DBConnection.connectionBD();
        } catch (SQLException e){
            e.printStackTrace();
        }
        PreparedStatement pr=null;
        PreparedStatement prC= null;
        ResultSet rsC=null;
        String sql="insert into commande (id_commande,id_client,id_agent,D_commande,Heure_commande)values (?,?,?,?,?)";
        String sqlC="select id_client from client where email = ?";
        try{
            prC= this.connection.prepareStatement(sqlC);
            prC.setString(1, LoginController.getTextField().getText());
            rsC= prC.executeQuery();

            DateTimeFormatter currentDate = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            DateTimeFormatter currentTime = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            pr= connection.prepareStatement(sql);
            Commande c = new Commande();

            c.setId_client(rsC.getInt("id_client"));
            
            c.setId_agent(1);
            c.setDate_commande(currentDate.format(now));
            c.setHeure_commande(currentTime.format(now));
            c.setProd_commande(prod);
            comm.add(c);
            pr.setInt(2,c.getId_client());
            pr.setInt(3,c.getId_agent());
            pr.setString(4,c.getDate_commande());
            pr.setString(5,c.getHeure_commande());

            pr.executeUpdate();
            this.statusLabel.setText("Commande effectuée avec succès !");
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            assert pr != null;
            pr.close();
            assert prC != null;
            prC.close();
            assert rsC != null;
            rsC.close();
            this.connection.close();
        }

    }
}
