package Nouyau ;

import java.io.Serializable;

public class Table implements Serializable {
    private boolean ext ; //le type de la table true si interieur false si exterieur
    private boolean dispo ;
    private boolean dispoAff;
    private boolean extAff;
    private int heure ;
    public Table(boolean ext)
    {
        this.ext = ext ;
        this.dispo = true ;
        this.dispoAff = true;
        this.extAff = ext;
    }

    public String getDispoAff() {
        return dispoAff ? "Disponible" : "Non disponible";
    }

    public String getExtAff() {
        return extAff ? "Interieure" : "Exterieure";
    }
    public void setDispo(boolean dispo) {
        this.dispo = dispo;
    }

    public boolean getDispo()
    {
        return this.dispo ;
    }

    public boolean gettype()
    {
        return ext ;
    }
    public void setheure(int heure)
    {
        this.heure =heure ;
    }
    public int getHeure()
    {
        return heure ;
    }

}
