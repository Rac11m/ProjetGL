package Login;

public enum option {
    AgentVente,AgentCommercial,Directeur,Client;

    private option(){}

    public String value(){
        return name();
    }

    public static option fromvalue(String v){
        return valueOf(v);
    }
}
