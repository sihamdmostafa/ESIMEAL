package Nouyau ;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
 public class Commandelivre extends Commande implements Serializable {
    private String adr;
    private double dist;
    private LocalTime heure;
     private ArrayList<ArrayList<Met>> personnes = new ArrayList<ArrayList<Met>>();

   public Commandelivre()
   {

   }
    public Commandelivre(Client c,int nbper,int i,String adr,int dist,LocalTime heure) throws NonFidelException,Tempnonfavorable,EventToday,DistanceException,RetardHeure
    {
        super(c, nbper, i);
        this.adr = adr;
        this.dist = dist ;
        this.heure = heure ;
        super.setHeure(heure);
        validcommande(heure,0);

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

      public String getAdr() {
         return adr;
     }

     public void setAdr(String adr) {
         this.adr = adr;
     }

     public double getDist() {
         return dist;
     }

     public void setDist(double dist) {
         this.dist = dist;
     }
     public void setHeure(LocalTime heure) {
         this.heure = heure;
     }
     public Client getClient() {
         return super.getClient();
     }
     public void setClient(Client client) {
         super.setClient(client);
     }
     public int getNbperson() {
         return super.getNbperson();
     }
     public void setNbperson(int nbperson) {
         super.setNbperson(nbperson);
     }
     public void affichmet(ArrayList<Met> met) {
        int j = 0;
        for (int i=0;i<met.size();i++) {
            if (met.get(i) instanceof Repas) {
                System.out.println(j + " Un repas de type  et de nom  " + met.get(i).getNom()+"et la disponibilité est "+met.get(i).isDispo());
                j++ ;
            } else {
                System.out.println(j + " Un boisson de type  et de nom  " + met.get(i).getNom()+"et la disponibilité est "+met.get(i).isDispo());
                j++ ;

            }
        }
    }
     public double reduction() {
         double reduction = 0.0;
         if (super.getClient().isEtudiant()) reduction += 8;
         if ((Esimeal.isFidele(super.getClient())) && (Esimeal.nbCmds(super.getClient()) >= 2)) reduction += 5;
         if (super.getNbperson() >= 4) reduction += 7;
         return reduction / 100;
     }

     public double calcul(int a) {
         double prix = 0.0;
         for (Met i : personnes.get(a)) {
              prix += i.getPrix();
         }
         return a == 0 ?  prix - prix * reduction() + 20 * dist : prix - prix*reductionAmountGroup();
     }
     public double calcul1(ArrayList<Met> met,int a) {
         double prix = 0.0;
              for (Met j : met) prix += j.getPrix();

         return a == 0 ?  prix - prix * reduction() + 20 * dist : prix - prix*reductionAmountGroup();
     }
     public double reductionAmount(){
         double prix = 0.0;
         for (ArrayList<Met> i : personnes) {
             for (Met j : i) prix += j.getPrix();
         }
         return prix*reduction();
     }
     public double reductionAmountFidel(){
         double prix = 0.0;
         double reduction = 0.0;
         for (ArrayList<Met> i : personnes) {
             for (Met j : i) prix += j.getPrix();
         }
         if ((Esimeal.isFidele(super.getClient())) && (Esimeal.nbCmds(super.getClient()) >= 2)) reduction += 0.05;
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
         double prix = 0.0;
         double reduction = 0.0;
         for (ArrayList<Met> i : personnes) {
             for (Met j : i) prix += j.getPrix();
         }
         if (super.getNbperson() >= 4) reduction += 0.07;
         return prix*reduction;
     }
     public double reductionAmountEvent(){
         return 0.0;
     }
    public  void validcommande(LocalTime heure,int minuit) throws Tempnonfavorable,EventToday,DistanceException,RetardHeure
    {
     if(Esimeal.iseventToday()==true)
     {
         throw new EventToday() ;
     }
     if((0<=heure.getHour()) && (heure.getHour() < 10))
     {
         throw new Tempnonfavorable();
     }
     if(LocalTime.now().plusMinutes(90).isAfter(heure))
        {
            throw new RetardHeure() ;
        }
     if(dist > 20)
     {
         throw new DistanceException() ;
     }
    }

     public  void  SetList(ArrayList<ArrayList<Met>> pers)
     {
         this.personnes = pers ;
     }
}


