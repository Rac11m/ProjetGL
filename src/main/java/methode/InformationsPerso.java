package methode;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InformationsPerso {
    private StringProperty Email;
    private StringProperty Tel;
    private StringProperty Adresse;

    public InformationsPerso() {
        Email = new SimpleStringProperty();
        Tel = new SimpleStringProperty();
        Adresse= new SimpleStringProperty();
    }

    public String getEmail() {
        return Email.get();
    }

    public StringProperty emailProperty() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email.set(email);
    }

    public String getTel() {
        return Tel.get();
    }

    public StringProperty telProperty() {
        return Tel;
    }

    public void setTel(String tel) {
        this.Tel.set(tel);
    }

    public String getAdresse() {
        return Adresse.get();
    }

    public StringProperty adresseProperty() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        this.Adresse.set(adresse);
    }
}
