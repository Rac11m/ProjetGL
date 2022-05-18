package methode;

import DBUtil.DBConnection;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.SQLException;

public class Agent extends Personne{

    private IntegerProperty id_agent; //identifiant de l'agent
    private IntegerProperty type_agent; // son type (Directeur, agent de vente ou agent commerciale)
    private StringProperty Email;
    private StringProperty Tel;

    public Agent() throws SQLException {
        super();
        id_agent =new SimpleIntegerProperty();
        type_agent = new SimpleIntegerProperty();
        Email = new SimpleStringProperty();
        Tel = new SimpleStringProperty();
    }

    public int getId_agent() {
        return id_agent.get();
    }

    public IntegerProperty id_agentProperty() {
        return id_agent;
    }

    public void setId_agent(int id_agent) {
        this.id_agent.set(id_agent);
    }

    public int getType_agent() {
        return type_agent.get();
    }

    public IntegerProperty type_agentProperty() {
        return type_agent;
    }

    public void setType_agent(int type_agent) {
        this.type_agent.set(type_agent);
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

    Connection conn = DBConnection.connectionBD();

}
