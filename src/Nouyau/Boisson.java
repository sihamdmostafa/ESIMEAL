package Nouyau ;
public class Boisson extends Met {
    private BoisonE type ;
    private String marque ;
    private String goute ;
    public Boisson(String nom, double prix, boolean dispo, int nbcalories, BoisonE type, String marque, String goute)
    {
        super(nom,prix,dispo,nbcalories) ;
        this.type=type ;
        this.marque=marque ;
        this.goute=goute ;
    }

    public BoisonE getType() {
        return type;
    }

    public void setType(BoisonE type) {
        this.type = type;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getGoute() {
        return goute;
    }

    public void setGoute(String goute) {
        this.goute = goute;
    }

    public String getDispo() {
        return super.isDispo() ? "Disponible" : "Non disponible";
    }

    public void setDispo(boolean dispo) {
        super.setDispo(dispo);
    }
    public void setUnit(int unit) {
        super.setUnit(unit);
    }

    public int getUnit() {
        return super.getUnit();
    }
}
