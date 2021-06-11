package Nouyau ;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Met implements Serializable {
    private String nom;
    private double prix;
    private boolean dispo;
    private int nbcalories;
    private int i =0;
    private int unit;
    public Met(){}
    public Met(String nom, double prix, boolean dispo, int nbcalories) {
        this.nom = nom;
        this.prix = prix;
        this.dispo = dispo;
        this.nbcalories = nbcalories;
    }
    public String getNom() {
        return nom;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getUnit() {
        if(unit > 0 )
            return unit;
        else
            return 0 ;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public boolean isDispo() {
        return dispo;
    }

    public void setDispo(boolean dispo) {
        this.dispo = dispo;
    }

    public int getNbcalories() {
        return nbcalories;
    }

    public void setNbcalories(int nbcalories) {
        this.nbcalories = nbcalories;
    }
    public void incrimentnbr()
    {
        i++ ;
    }
    public int geti(){return i ;}
    public int getnbrcommend()
    {
        return i ;
    }



    @Override
    public String toString() {
        return this.nom;
    }
}
 
