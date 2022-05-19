package GestionDeProduit;

import DBUtil.DBConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddProducts implements Initializable {

    Connection connection;
    @FXML
    private Button ajtBTN;

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
    private TextField refTF;
    @FXML
    private ChoiceBox<TypeProduit> typePchbox;

    @FXML
    private TextField vnTF;



    public void initialize(URL location, ResourceBundle resources){
          this.typePchbox.setItems(FXCollections.observableArrayList(TypeProduit.values()));

    }
    public boolean isDataBaseConnected() {
        try {

            this.connection= DBConnection.connectionBD();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.connection != null;
    }

    public int chercherProduit(String designation) throws  Exception{
        PreparedStatement pr=null;
        ResultSet rs=null;

        String sql="select * from Patient where Designation = ?";//On utilise le numero de telephone du patient car c'est la seul donnée de la table qui est obligatoirement unique

        try{
            pr=this.connection.prepareStatement(sql);
            pr.setString(1,designation);

            rs = pr.executeQuery();


            if (rs.next()){

                return rs.getInt("id_produit");// dans le cas ou le patient existe dans la BDD on retourne son code
            }
            else{

                return 0;// sinon on retourne 0
            }
        }catch (SQLException e)
        {

            System.out.println("Vous avez un probleme dans la classe Secretaire methode chercher patient");
            return 0;
        }
        finally {
            assert pr != null;
            pr.close();
            assert rs!= null;
            rs.close();
        }

    }


   public void Add(ActionEvent event){

        int st = 0;
        try {
            String sql="insert into produits (id_type,reference,designation,\"valeur-nutritionnelle\",\"date-production\",\"date-peremption\",\"poids-net\",\"prix-vente\",quantite,ingredients)values (?,?,?,?,?,?,?,?,?,?)";
            this.connection = DBConnection.connectionBD();
            PreparedStatement pr=null;
            ResultSet rs=null;
            pr = connection.prepareStatement(sql);
            Produit p = new Produit();

            p.setType(this.typePchbox.getValue().toString());
            p.TypeToID();
            p.setReference(this.refTF.getText());
            p.setDesignation(this.desTF.getText());
            p.setValeur_nutritionnelle(this.vnTF.getText());
            p.setDate_production(this.dateProTF.getValue().toString());
            p.setDate_peremption(this.datePrmTF.getValue().toString());
            p.setPoids_net(Float.parseFloat(this.pnTF.getText()));
            p.setPrix_vente(Double.parseDouble(this.prixTF.getText()));
            p.setQuantite(Integer.parseInt(this.qntTF.getText()));
            p.setIngredients(this.ingdTF.getText());

            pr.setInt(1,p.getId_type());
            pr.setString(2,p.getReference());
            pr.setString(3,p.getDesignation());
            pr.setString(4,p.getValeur_nutritionnelle());
            pr.setString(5,p.getDate_production());
            pr.setString(6,p.getDate_peremption());
            pr.setFloat(7,p.getPoids_net());
            pr.setDouble(8,p.getPrix_vente());
            pr.setInt(9,p.getQuantite());
            pr.setString(10,p.getIngredients());
            pr.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
