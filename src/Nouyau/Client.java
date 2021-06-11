package Nouyau ;

import java.io.Serializable;

public class Client implements Serializable {
    private String nom;
    private String prenom;
    private String numtelph;
    private boolean etudiant;
    private int NcmndSurplc ;
    private int NcmndDmcl ;
    private int NcmndEvent ;
    public Client()   {
    }

    public Client(String nom, String prenom, String numtelph)  {
        this.nom = nom;
        this.prenom = prenom;
        this.numtelph = numtelph;
        this.NcmndDmcl = 0 ;
        this.NcmndEvent = 0;
        this.NcmndSurplc = 0 ;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumtelph() {
        return numtelph;
    }

    public void setNumtelph(String numtelph) {
        this.numtelph = numtelph;
    }

    public boolean isEtudiant() {
        return etudiant;
    }

    public void setEtudiant(boolean etudiant) {
        this.etudiant = etudiant;
    }

    public void affich() {
        System.out.println("le nom est " + nom + " le prenom est " + prenom + " et le numero de telephone est " + this.numtelph);
    }
    public boolean equals(Client c){
        if (c.getNom().equals(nom)&&c.getPrenom().equals(prenom)&&c.getNumtelph().equals(numtelph)) return true;
        else return false;
    }
    public void setNcmndDmcl(int cmnd)
    {
        this.NcmndDmcl = cmnd ;
    }
    public int getNcmndDmcl()
    {
        return(this.NcmndDmcl) ;
    }
    public void setNcmndSurplc(int cmnd)
    {
        this.NcmndSurplc = cmnd ;
    }
    public int getNcmndSurplc()
    {
        return(this.NcmndDmcl) ;
    }
    public void  setNcmndEvent(int cmnd)
    {
        this.NcmndEvent = cmnd ;
    }
    public int  getNcmndEvent()
    {
        return(this.NcmndEvent) ;
    }
    public int getnbrcmnd()
    {
        return (NcmndDmcl+NcmndEvent+NcmndSurplc) ;
    }

    @Override
    public String toString() {
        return nom+" "+prenom;
    }
}
