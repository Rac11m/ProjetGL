package GestionDeProduit;

import javafx.beans.property.*;

public class Produit {



    private int id_produit;
    private int id_type;

    private String type;
    private String reference;
    private String designation;
    private String valeur_nutritionnelle;
    private String date_production;
    private String date_peremption;
    private float poids_net;
    private double prix_vente;
    private int quantite;
    private String ingredients;



    private double prixCommande;

    public double getPrixCommande() {
        return prixCommande;
    }

    public void setPrixCommande(double prixCommande) {
        this.prixCommande = prixCommande;
    }

    //  private IntegerProperty id_produitP = new SimpleIntegerProperty();
    //  private IntegerProperty id_typeP = new SimpleIntegerProperty();
    //  private StringProperty referenceP = new SimpleStringProperty();
    //  private StringProperty designationP = new SimpleStringProperty();
    //  private StringProperty valeur_nutritionnelleP = new SimpleStringProperty();
    //  private StringProperty date_productionP = new SimpleStringProperty();
    //  private StringProperty date_peremptionP = new SimpleStringProperty();
    //  private FloatProperty poids_netP = new SimpleFloatProperty();
    //  private DoubleProperty prix_venteP = new SimpleDoubleProperty();
    //  private IntegerProperty quantiteP = new SimpleIntegerProperty();
    //  private StringProperty ingredientsP = new SimpleStringProperty();



    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getValeur_nutritionnelle() {
        return valeur_nutritionnelle;
    }

    public void setValeur_nutritionnelle(String valeur_nutritionnelle) {
        this.valeur_nutritionnelle = valeur_nutritionnelle;
    }

    public String getDate_production() {
        return date_production;
    }

    public void setDate_production(String date_production) {
        this.date_production = date_production;
    }

    public String getDate_peremption() {
        return date_peremption;
    }

    public void setDate_peremption(String date_peremption) {
        this.date_peremption = date_peremption;
    }

    public float getPoids_net() {
        return poids_net;
    }

    public void setPoids_net(float poids_net) {
        this.poids_net = poids_net;
    }

    public double getPrix_vente() {
        return prix_vente;
    }

    public void setPrix_vente(double prix_vente) {
        this.prix_vente = prix_vente;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void TypeToID(){
        switch (this.type){
            case "Lait Cru":
                this.id_type=1;
                break;
            case "Lait Pasterisé":
                this.id_type=2;
                break;
            case "Lait Stérilisé":
                this.id_type=3;
                break;
            case "Lait UHT":
                this.id_type=4;
                break;
            case "Lait Concentré":
                this.id_type=5;
                break;
            case "Fromage":
                this.id_type=6;
                break;
            case "Yaourt":
                this.id_type=7;
                break;
            case "Beurre":
                this.id_type=8;
                break;
            case "Crème Fraîche":
                this.id_type=9;
                break;
        }

    }

}
