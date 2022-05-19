package GestionDeProduit;


public enum TypeProduit {
    Lait_Cru{
        public String toString(){
            return "Lait Cru";
        }

    },
    Lait_Pasterise{
        public String toString(){
            return "Lait Pasterisé";
        }
    },
    Lait_Sterilise{
        public String toString(){
           return  "Lait Stérilisé";
        }
    },
    Lait_UHT{
        @Override
        public String toString() {
            return "Lait UHT";
        }
    },
    Lait_Concentre{
        @Override
        public String toString() {
            return "Lait Concentré";
        }
    },
    Fromage{
        @Override
        public String toString() {
            return super.toString();
        }
    },
    Beurre{
        @Override
        public String toString() {
            return super.toString();
        }
    },
    CremeFraiche{
        @Override
        public String toString() {
            return "Crème Fraîche";
        }
    };
    /*  Lait_Cru("Lait Cru"),
    Lait_Pasterise("Lait Pasterisé"),
    Lait_Sterilise("Lait Stérilisé"),
    Lait_UHT("Lait UHT"),
    Lait_Concentre("Lait Concentré"),
    Fromage("Fromage"),
    Yaourt("Yaourt"),
    Beurre("Beurre"),
    CremeFraiche("Crème Fraîche");

    private final String text;

    private TypeProduit(final String text){
        this.text = text;
    }
   // private TypeProduit(){}


    public String toString(){
        return text;

    }

    public String value(){
        return name();}

    public static TypeProduit fromvalue(String v){
        return valueOf(v);
    }*/
}
