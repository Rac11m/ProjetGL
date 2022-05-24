package methode;

import GestionDeProduit.Produit;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

public class Commande {


    private int id_commande;
    private int id_agent;
    private int id_client;
    private String date_commande;
    private String heure_commande;
    private double prix_total;

    private CheckBox selectVerif;

    private ObservableList<Produit> prod_commande;



    public Commande() {}

    public CheckBox getSelectVerif() {
        return selectVerif;
    }

    public void setSelectVerif(CheckBox selectVerif) {
        this.selectVerif = selectVerif;
    }


    public double getPrix_total() {
        return prix_total;
    }

    public void setPrix_total(double prix_total) {
        this.prix_total = prix_total;
    }

    public int getId_commande() {
        return id_commande;
    }

    public void setId_commande(int id_commande) {
        this.id_commande = id_commande;
    }

    public int getId_agent() {
        return id_agent;
    }

    public void setId_agent(int id_agent) {
        this.id_agent = id_agent;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getDate_commande() {
        return date_commande;
    }

    public void setDate_commande(String date_commande) {
        this.date_commande = date_commande;
    }

    public String getHeure_commande() {
        return heure_commande;
    }

    public void setHeure_commande(String heure_commande) {
        this.heure_commande = heure_commande;
    }

    public ObservableList<Produit> getProd_commande() {
        return prod_commande;
    }

    public void setProd_commande(ObservableList<Produit> prod_commande) {
        this.prod_commande = prod_commande;
    }
}
