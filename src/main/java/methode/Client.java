package methode;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client extends Personne{
    private StringProperty id_client;

    private StringProperty type_client = new SimpleStringProperty();

    private InformationsPerso IP;
    private StringProperty designation;
    private Commande commande;
    public Client(){
        super();
        id_client = new SimpleStringProperty();
        IP = new InformationsPerso();
        commande = new Commande();
    }

    public String getId_client() {
        return id_client.get();
    }

    public StringProperty id_clientProperty() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client.set(id_client);
    }

    public String getType_client() {
        return type_client.get();
    }

    public StringProperty type_clientProperty() {
        return type_client;
    }

    public void setType_client(String type_client) {
        this.type_client.setValue(type_client);
    }

    public InformationsPerso getIP() {
        return IP;
    }

    public void setIP(InformationsPerso IP) {
        this.IP = IP;
    }

    public String getDesignation() {
        return designation.get();
    }

    public StringProperty designationProperty() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation.set(designation);
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }
}
