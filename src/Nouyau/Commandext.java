package Nouyau ;
import javafx.scene.control.Tab;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Commandext extends Commande implements Serializable {
    private boolean ext;
    private LocalTime heure;
    private ArrayList<ArrayList<Met>> mtnonvalid = new ArrayList<ArrayList<Met>>();
    private ArrayList<ArrayList<Met>> personnes = new ArrayList<ArrayList<Met>>();
     public Commandext()
    {

    }
    public Commandext(Client c,int nbper,int i,LocalTime heure,boolean ext) throws NonFidelException,Tempnonfavorable,Exceptionchaisse,EventToday,RetardHeure{
        super(c,nbper,i);
        this.heure = heure;
        this.ext = ext ;
        super.setHeure(heure);
         validcommande(heure,0);
    }


    public boolean isExt() {
        return ext;
    }
  public int CalculeCalories(int i)
  {
      int k=0;
      for(int j=0;j<personnes.get(i).size();j++)
      {
          k+=personnes.get(i).get(j).getNbcalories() ;
      }
      return k ;
  }
    public String getExt() {
        return ext ? "Interieur" : "Exterieure";
    }

    public void setExt(boolean ext) {
        this.ext = ext;
    }

    public String getHeure() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH : mm : ss");
        return heure.format(formatter);
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public LocalDate getdate() {
        return super.getdate();
    }

    public int getNbperson() {
        return super.getNbperson();
    }

    public void setClient(Client client) {
        super.setClient(client);
    }
    public double reduction(){
        double reduction = 0.0;
        if (super.getClient().isEtudiant()) reduction+=8;
        if ((Esimeal.isFidele(super.getClient()))&&(Esimeal.nbCmds(super.getClient())>=2)) reduction += 5;
        return reduction/100;
    }
    public double calcul(int a){
        double prix = 0.0,exter = 0.0;

            for (Met j : personnes.get(a)) prix += j.getPrix();

        exter += ext ? prix*0.05 : 0;
        return a == 0 ? prix - prix*reduction() + exter : prix - reductionAmountGroup()*prix;
    }
    public double calcul1(ArrayList<Met> met,int a){
        double prix = 0.0,exter = 0.0;

        for (Met j : met) prix += j.getPrix();

        exter += ext ? prix*0.05 : 0;
        System.out.println(reduction()+" "+super.getClient().isEtudiant());
        return a == 0 ? prix - prix*reduction() + exter : prix - reductionAmountGroup()*prix;
    }
    public double reductionAmount(){
        double prix = 0.0;
        for(ArrayList<Met> i : personnes){
            for (Met j : i) prix += j.getPrix();
        }
        return prix*reduction();
    }
    public double reductionAmountFidel(){
        double prix = 0.0;
        double reduction = 0.0;
        for(ArrayList<Met> i : personnes){
            for (Met j : i) prix += j.getPrix();
        }
        if ((Esimeal.isFidele(super.getClient()))&&(Esimeal.nbCmds(super.getClient())>=2)) reduction += 0.05;
        return prix*reduction;
    }
    public double reductionAmountEtudiant(){
        double prix = 0.0;
        double reduction = 0.0;
        for (ArrayList<Met> i : personnes) {
            for (Met j : i) prix += j.getPrix();
        }
        if (super.getClient().isEtudiant()) reduction += 0.08;
        return prix*reduction;
    }
    public double reductionAmountGroup(){
        return 0.0;
    }
    public double reductionAmountEvent(){
        return 0.0;
    }
public String AffichPrix(int i)
{
    return ("Votre Prix de Menu est "+ this.calcul(i) + "le nombre de calories est "+this.CalculeCalories(i));}

    public  void validcommande(LocalTime heure,int minuit) throws Tempnonfavorable,Exceptionchaisse,EventToday,RetardHeure
    {

        if(Esimeal.iseventToday()==true)
        {
            throw new EventToday() ;
        }
      if(((22<=heure.getHour())) || ((0<=heure.getHour()) && (heure.getHour() < 10)))
      {
          throw new Tempnonfavorable() ;
      }
      if(LocalTime.now().plusMinutes(60).isAfter(heure))
        {
            throw new RetardHeure() ;
        }
     Table tabel = Esimeal.isDispotable(ext)  ;
     if(tabel == null)
     {
         throw new Exceptionchaisse() ;
     }
     else if(super.getNbperson() > Esimeal.getnbtchaisse())
     {
          throw new Exceptionchaisse() ;
     }
     else
     {
       Esimeal.setnbchaise(Esimeal.getnbtchaisse()-super.getNbperson());

     }

    }

    public  void  SetList(ArrayList<ArrayList<Met>> pers)
    {
        this.personnes = pers ;
    }
    public ArrayList<ArrayList<Met>> getList()
    {
        return personnes ;
    }
}
