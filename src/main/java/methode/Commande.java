package methode;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Commande {
    private IntegerProperty id_commande;
    private StringProperty Date_commande;
    private  StringProperty Heure_commande;

    public Commande() {
        this.id_commande = new SimpleIntegerProperty();;
        this.Date_commande = new SimpleStringProperty();
        this.Heure_commande = new SimpleStringProperty();
    }

    public int getId_commande() {
        return id_commande.get();
    }

    public IntegerProperty id_commandeProperty() {
        return id_commande;
    }

    public void setId_commande(int id_commande) {
        this.id_commande.set(id_commande);
    }

    public String getDate_commande() {
        return Date_commande.get();
    }

    public StringProperty date_commandeProperty() {
        return Date_commande;
    }

    public void setDate_commande(String date_commande) {
        this.Date_commande.set(date_commande);
    }

    public String getHeure_commande() {
        return Heure_commande.get();
    }

    public StringProperty heure_commandeProperty() {
        return Heure_commande;
    }

    public void setHeure_commande(String heure_commande) {
        this.Heure_commande.set(heure_commande);
    }
}
