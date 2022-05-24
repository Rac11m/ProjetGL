package GestionDeVente;

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
import methode.Commande;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerifCommande {

    ObservableList<Commande> comm = FXCollections.observableArrayList();

    Connection connection;

    private Parent root;
    private Scene scene;
    private Stage stage;

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
    private Button retourBtn;

    @FXML
    private Button VerifBtn;

    @FXML
    private Label statusLabel;

    @FXML
    private TableView<Commande> tableComm;

    public void switchToPreviousScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/GestionVentes/UIVentes.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
        String sql="Delete from commande where id_commande = ?";

        try {
            int i=-1;
            while (!comm.isEmpty()){
                i++;
            pr=connection.prepareStatement(sql);
            pr.setInt(1,this.comm.get(i).getId_commande());
            Commande c = comm.get(i);
            if(!c.getSelectVerif().isSelected()){
                pr.executeUpdate();
                comm.remove(i);
                this.statusLabel.setText("Commandes Vérifiés !");
            }

            }
            this.statusLabel.setText("Commandes Vérifiés!");
            }catch (IndexOutOfBoundsException e){
           e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {
           // assert pr != null;
           // pr.close();
            this.connection.close();
        }




    }

}
