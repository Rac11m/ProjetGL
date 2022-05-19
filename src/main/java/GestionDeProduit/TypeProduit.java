package GestionDeProduit;


public enum TypeProduit {
    Lait_Cru,Lait_Pasterise,Lait_Sterilise,Lait_UHT,Lait_Concentre,Fromage,Yaourt,Beurre,CremeFraiche;

    private TypeProduit(){}

    public String value(){
        return name();
    }

    public static TypeProduit fromvalue(String v){
        return valueOf(v);
    }
}
