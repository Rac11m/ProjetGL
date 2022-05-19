package GestionDeProduit;

import DBUtil.DBConnection;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class AddProducts implements Initializable {
    @FXML
    private ChoiceBox<TypeProduit> typePchbox;

    public void initialize(URL location, ResourceBundle resources){
          this.typePchbox.setItems(FXCollections.observableArrayList(TypeProduit.values()));

    }
    public static int Add(Produit p){
        int st = 0;
        try {
            String sql="insert into produits (id_type,reference,designation,valeur-nutritionnelle,date-production,date-peremption,poids-net,prix-vente,quantite,ingredients)values (?,?,?,?,?,?,?,?,?,?)";
            Connection connection = DBConnection.connectionBD();
            PreparedStatement pr=null;
            pr = connection.prepareStatement(sql);

        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
