package GestionDeProduit;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddProducts implements Initializable {

    Connection connection;
    private Stage stage;
    private Scene scene;
    private Parent root;
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

    public void initialize(URL location, ResourceBundle resources){
        this.typePchbox.setItems(FXCollections.observableArrayList(TypeProduit.values()));
        imageView.setImage(image1);
        imageView2.setImage(image2);
        retourBtn.setGraphic(imageView2);
    }
    public boolean isDataBaseConnected() {
        try {

            this.connection= DBConnection.connectionBD();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.connection != null;
    }

    public boolean chercherProduit(String designation,String reference) throws  Exception{
        PreparedStatement pr=null;
        ResultSet rs=null;
        String sql="select * from Produits where designation = ? and reference = ?";//On utilise le numero de telephone du patient car c'est la seul donnée de la table qui est obligatoirement unique

        try{
            this.connection = DBConnection.connectionBD();
            pr=this.connection.prepareStatement(sql);
            pr.setString(1,designation);
            pr.setString(2,reference);
            rs = pr.executeQuery();
            if (rs.next()){
                return true;// dans le cas ou le produit existe dans la BDD on retourne son code
            }
            else{
                return false;// sinon on retourne 0
            }
        }catch (SQLException e)
        {

            System.out.println("Vous avez un probleme dans la classe AddProduit methode chercher produits");
            return false;
        }
        finally {
            assert pr != null;
            pr.close();
            assert rs!= null;
            rs.close();
        }

    }


   public void Add(ActionEvent event) throws SQLException {
       PreparedStatement pr=null;
       String sql="insert into produits (id_type,reference,designation,\"valeur-nutritionnelle\",\"date-production\",\"date-peremption\",\"poids-net\",\"prix-vente\",quantite,ingredients)values (?,?,?,?,?,?,?,?,?,?)";
        try {
            this.connection = DBConnection.connectionBD();
            pr = connection.prepareStatement(sql);
            Produit p = new Produit();
            if (!this.chercherProduit(this.desTF.getText(),this.refTF.getText())) {
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

                pr.setInt(1, p.getId_type());
                pr.setString(2, p.getReference());
                pr.setString(3, p.getDesignation());
                pr.setString(4, p.getValeur_nutritionnelle());
                pr.setString(5, p.getDate_production());
                pr.setString(6, p.getDate_peremption());
                pr.setFloat(7, p.getPoids_net());
                pr.setDouble(8, p.getPrix_vente());
                pr.setInt(9, p.getQuantite());
                pr.setString(10, p.getIngredients());
                pr.executeUpdate();
                this.statusLabel.setText("Produit créé avec succès !");
            }else {
                this.statusLabel.setText("Produit existe déja !");
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
        root = FXMLLoader.load(getClass().getResource("/GestionProduits/UIProduits.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

}
