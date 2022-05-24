package GestionDirecteur;

import DBUtil.DBConnection;
import GestionDeClient.TypeClient;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static java.lang.Math.abs;

public class PlanDeVente implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    Connection connection;

    @FXML
    private TextField estimation;
    @FXML
    private ChoiceBox<Mois> typeMchbox;

    @FXML
    private Label chiffreAffaire;
    @FXML
    private Label difference;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.typeMchbox.setItems(FXCollections.observableArrayList(Mois.values()));
    }

    public void VoirPlant() throws SQLException {
        PreparedStatement pr= null;
        ResultSet rs= null;
        String sql="SELECT strftime('%m',D_commande) AS Month, sum(prix_total) FROM commande GROUP BY strftime('%m',D_commande) HAVING Month = ?";
        String mois = typeMchbox.getValue().toString();
        try{
            this.connection = DBConnection.connectionBD();
            pr=this.connection.prepareStatement(sql);
            pr.setString(1,mois);
            rs = pr.executeQuery();

            int month_n = rs.getInt("Month");
            double prix_par_mois = (rs.getDouble("sum(prix_total)"));
            chiffreAffaire.setText(String.valueOf(prix_par_mois));
            double diff = Double.parseDouble(estimation.getText()) - prix_par_mois;
            if (diff < 0 ) {
                difference.setText("Il y-a un gain de "+ abs(diff)+ " DA");
            } else if (diff == 0) {
                difference.setText("Votre estimation est bien correcte ");
            }else {
                difference.setText("Il y-a une difference negative de "+ diff+ " DA");
            }

        }catch (SQLException e)
        {
            System.out.println("Vous avez un probleme dans la classe AddClient methode chercher Client");
        }
        finally {
            assert pr != null;
            pr.close();
            assert rs!= null;
            rs.close();
        }
    }


    public void switchToPreviousScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Directeur/Directeur.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
