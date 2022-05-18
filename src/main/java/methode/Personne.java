package methode;

import DBUtil.DBConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.SQLException;

public class Personne {
    private StringProperty nom;
    private StringProperty Prenom;
    Connection connection;

    public Personne() {
        this.nom = new SimpleStringProperty();
        this.Prenom = new SimpleStringProperty();;
        try {

            this.connection= DBConnection.connectionBD();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(this.connection==null)
        {
            System.out.println("Vous avez un probleme avec la classe Personne");
            System.exit(1);
        }
    }

    public String getNom() {
        return nom.get();
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getPrenom() {
        return Prenom.get();
    }

    public StringProperty prenomProperty() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        this.Prenom.set(prenom);
    }


}
