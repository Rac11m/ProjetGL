package GestionDeProduit;

import DBUtil.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteProducts {

    Connection conn;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableColumn<?, ?> IDcol;

    @FXML
    private Button SearchBtn;

    @FXML
    private TextField SearchTF;

    @FXML
    private Label StautsLabel;

    @FXML
    private TableView<?> SuppTV;

    @FXML
    private TableColumn<?, ?> desCol;

    @FXML
    private TableColumn<?, ?> refCol;

    @FXML
    private Button retourBtn;

    public boolean chercherProduit(String s) throws SQLException,Exception{
        this.conn = DBConnection.connectionBD();
        PreparedStatement pr=null;
        ResultSet rs=null;
        String sql="select * from Produits where reference = ?";//On utilise le numero de telephone du patient car c'est la seul donnée de la table qui est obligatoirement unique

        try{
            pr=this.conn.prepareStatement(sql);
            pr.setString(1,s);

            rs = pr.executeQuery();

            if (rs.next()){

                return true;// dans le cas ou le patient existe dans la BDD on retourne son code
            }
            else{

                return false;// sinon on retourne 0
            }
        }catch (SQLException e) {
            System.out.println("Vous avez un probleme dans la classe Secretaire methode chercher produits");
            return false;
        }catch (Exception e){
          e.printStackTrace();
        } finally {
            assert pr != null;
            pr.close();
            assert rs!= null;
            rs.close();
            assert this.conn != null;
            this.conn.close();
        }
    return false;
    }


    public void Delete(ActionEvent event) throws SQLException{
        this.conn = DBConnection.connectionBD();
        PreparedStatement pr = null;
        String sql = "Delete from produits where reference = ?";
        try {
            pr = conn.prepareStatement(sql);
            pr.setString(1, this.SearchTF.getText());
            if (this.chercherProduit(this.SearchTF.getText())) {
                pr.executeUpdate();
                this.StautsLabel.setText("Produit supprimé avec succès !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            assert pr != null;
            pr.close();
            assert this.conn != null;
            this.conn.close();
        }
    }


    @FXML
    void switchToPreviousScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/GestionProduits/UIProduits.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
