package GestionDirecteur;

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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BilanDeVente implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    Connection connection;

    XYChart.Series<String, Double> series = new XYChart.Series<String, Double>();
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    @FXML
    private LineChart<String, Double> lineChart;
    @FXML
    private PieChart pieChart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void statLineChart(ActionEvent event) throws SQLException {
        series.setName("Ventes"); //setting series name (appear as legends)

        PreparedStatement pr= null;
        ResultSet rs= null;

        try{
            this.connection = DBConnection.connectionBD();
        } catch (SQLException e){
            e.printStackTrace();
        }

        if(connection != null){
            String sql="SELECT strftime('%m',D_commande) AS Month, sum(prix_total) FROM commande GROUP BY strftime('%m',D_commande) ORDER BY strftime('%m',D_commande)";

            try {
                pr= connection.prepareStatement(sql);
                rs= pr.executeQuery();

                while (rs.next()) {
                    int month_n = rs.getInt("Month");
                    System.out.println(month_n);
                    System.out.println("\t"+getMois(month_n)+"\n");
                    double prix_par_mois = (rs.getDouble("sum(prix_total)"));
                    System.out.println(prix_par_mois);

                    String mois = getMois(month_n);
                    series.getData().add(new XYChart.Data<String, Double>((mois), prix_par_mois));
                }
                lineChart.getData().add(series);
                lineChart.setTitle("Bilan de ventes de l'année 2022");
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

    public void statPieChart(ActionEvent event) throws SQLException {
        PreparedStatement pr= null;
        ResultSet rs= null;

        try{
            this.connection = DBConnection.connectionBD();
        } catch (SQLException e){
            e.printStackTrace();
        }

        if(connection != null){
            String sql="SELECT strftime('%m',D_commande) AS Month, sum(prix_total) FROM commande GROUP BY strftime('%m',D_commande) ORDER BY strftime('%m',D_commande)";

            try {
                pr= connection.prepareStatement(sql);
                rs= pr.executeQuery();

                while (rs.next()) {
                    int month_n = rs.getInt("Month");
                    double prix_par_mois = (rs.getDouble("sum(prix_total)"));
                    String mois = getMois(month_n);

                    pieChartData.add(new PieChart.Data(mois,prix_par_mois));
                }
                pieChart.setData(pieChartData);
                pieChart.setTitle("Bilan de ventes de l'année 2022");
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

    public String getMois(int m) {
        String mois;
        switch (m) {
            case 1:  mois= "January";
                break;
            case 2:  mois= "February";
                break;
            case 3:  mois= "March";
                break;
            case 4:  mois= "April";
                break;
            case 5:  mois= "May";
                break;
            case 6:  mois= "June";
                break;
            case 7:  mois= "July";
                break;
            case 8:  mois= "August";
                break;
            case 9:  mois= "September";
                break;
            case 10: mois= "October";
                break;
            case 11: mois= "November";
                break;
            case 12: mois= "December";
                break;
            default: mois= "Ce mois n'existe pas";
                break;
        }
        return mois;
    }

    public void switchToPreviousScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/Directeur/Directeur.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
