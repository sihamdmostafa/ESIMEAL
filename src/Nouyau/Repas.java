package Nouyau ;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Repas extends Met implements Serializable {
    private RepasE type;
    private List<Suplment> supplm = new ArrayList<Suplment>();
    private List<String> ingrid = new ArrayList<String>();
     public Repas(String nom, double prix, boolean dispo, int nbcalories, RepasE type) {
        super(nom, prix, dispo, nbcalories);
        this.type = type;

    }

    public RepasE getType() {
        return type;
    }
    public List<Suplment> getSuppl()
    {
        return supplm ;
    }
    public void setlist(List<Suplment> list1)
    {
        supplm = new ArrayList<Suplment>();
        supplm.addAll(list1) ;
    }
    public Suplment getsup(int i)
    {
        return (supplm.get(i)) ;
    }
    public void setType(RepasE type) {
        this.type = type;
    }

    public void setSupplm(List<Suplment> supplm) {
        this.supplm = supplm;
    }

    public void ajoutsupplment(Suplment s) {
        supplm.add(s);
    }

    ////////////////////////////
    public String getNom() {
        return super.getNom();
    }

    public void setNom(String nom) {
        super.setNom(nom);
    }

    public double getPrix() {
        double s = 0.0;
        for(Suplment i : supplm) s+= i.getPrix();
         return super.getPrix()+s;
    }

    public void setPrix(double prix) {
        super.setPrix(prix);
    }

    public String getDispo() {
        return super.isDispo() ? "Disponible" : "Non disponible";
    }

    public void setDispo(boolean dispo) {
        super.setDispo(dispo);
    }

    public int getNbcalories() {
        return super.getNbcalories();
    }

    public void setNbcalories(int nbcalories) {
        super.setNbcalories(nbcalories);
    }

    public List<Suplment> getSupplm1(){ return  this.supplm; }
    public String getSupplm() {
        String s = "";
        for (Suplment i : this.supplm){
            s += i.getNom()+", ";
        }
        return s;
    }

    public void setUnit(int unit) {
        super.setUnit(unit);
    }

    public int getUnit() {
        return super.getUnit();
    }
    public String getIngrid() {
        String s = "";
        for (String i : this.ingrid){
            s += i+", ";
        }
        return s;
    }

    public void setIngrid(List<String> ingrid) {
        this.ingrid = ingrid;
    }

}

