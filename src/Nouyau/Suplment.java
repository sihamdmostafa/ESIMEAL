package Nouyau ;

import java.io.Serializable;

public class Suplment implements Serializable {
    private String nom;
    private double prix;
    private int nbcalories;

    public Suplment(String nom, double prix, int nbcalories) {
        this.nom = nom;
        this.prix = prix;
        this.nbcalories = nbcalories;
    }

    public String getNom() {
        return nom;
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

    public int getNbcalories() {
        return nbcalories;
    }

    public void setNbcalories(int nbcalories) {
        this.nbcalories = nbcalories;
    }
}
