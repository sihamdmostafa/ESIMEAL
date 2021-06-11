package Nouyau ;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.String ;
public abstract class Commande implements Serializable,Comparable {
    private Client client;
    private int nbperson;
    private static int n=0 ;
    private int type ;
    private int id ;
    private LocalTime heure ;
     private LocalDate temp ;
  public Commande()
    {

    }
  public Commande(Client c,int nbper,int i) throws NonFidelException //Donc si un client est fidel alors il suffit de donner i sans nom,prenom,numtemph
  {
       if(i==0)
      {
          client =c ;
      }
       else {
          try {
              if (Esimeal.rechClientfidel(i) == null) {
                  throw new NonFidelException();
              }
              else
              {
                  client = Esimeal.rechClientfidel(i) ;
              }
          }
          catch (NonFidelException e)
          {

          }
       }
          this.nbperson = nbper ;

  }
     public void setClient(Client client) {
        this.client = client;
    }
    public LocalTime getHeure1() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public LocalDate getdate()
     {
         return temp ;
     }
    public void setDate()
    {
        this.temp = LocalDate.now() ;
    }
    public Client getClient() {
        return client;
    }
   public int getid()
   {
       return id ;
   }
   public int Getn()
   {
       return n ;
   }
   public void Setid()
   {
       this.id=n ;
       n++ ;
   }
   public void setType(int i)
   {
       this.type = i ;
   }
    public int getType()
    {
        return(type) ;
    }
    public int getNbperson() {
        return nbperson;
    }
    public abstract double calcul(int j);

    public abstract double reduction();

    public abstract double reductionAmount();
    public abstract double reductionAmountFidel();
    public abstract double reductionAmountEtudiant();
    public abstract double reductionAmountGroup();
    public abstract double reductionAmountEvent();
    public void setNbperson(int nbperson) {
        this.nbperson = nbperson;
    }
    public abstract void validcommande(LocalTime heur, int minuit) throws Tempnonfavorable,Exceptionchaisse,EventToday,DistanceException,MaxPersonne,RetardHeure ;

     public boolean equals(Object obj) {
         if (((Commande) obj).getid() == this.getid()) return true;
         else return false ;
         }
     public int compareTo(Object o)
     {
         if(this.type != 2) {
             if (((Commande) o).getHeure1().isBefore(this.getHeure1())) return 1;
             else if (((Commande) o).getHeure1().isAfter(this.getHeure1())) return -1;
             else {
                 if ((((Commande) o).type == 1) && (this.type == 0)) {
                     return 1;
                 } else if ((((Commande) o).type == 0) && (this.type == 1)) {
                     return -1;
                 } else if ((((Commande) o).type == this.type)) {
                     if (((Commande) o).getid() > this.getid()) {
                         return 1;
                     } else {
                         return -1;
                     }
                 }
             }
         }
         else
         {
             return -1 ;
         }
         return 0;
     }
}
