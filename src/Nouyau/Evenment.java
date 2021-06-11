package Nouyau ;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Evenment extends Commande implements Serializable {
    private EventE decore;
    private List<Met> menu = new ArrayList<Met>();
    private LocalDate date;
    public Evenment()
    {
    }
    public Evenment(Client c,int nbper,int i,EventE type,LocalDate date) throws NonFidelException,MaxPersonne,RetardHeure {
        super(c,nbper,i);
        this.decore = type ;
        this.date = date ;
        //validcommande(null,0);
    }

    public void ajoutmet(ArrayList<Met> list) {
        menu = new ArrayList<Met>() ;
        menu.addAll(list) ;
    }
    public LocalDate getdateEvent()
    {
        return (this.date) ;
    }

    public EventE getDecore() {
        return decore;
    }

    public void setDecore(EventE decore) {
        this.decore = decore;
    }

    public void ajoutmet(Met c) {
        menu.add(c);
    }


    public LocalDate getDate() {
        return date;
    }

    public List<Met> getMenu() {
        return menu;
    }

    public void setMenu(List<Met> menu) {
        this.menu = menu;
    }

    public int getNbperos() {
        return super.getNbperson();
    }

    public Client getClient() {
        return super.getClient();
    }
    public void setClient(Client client) {
        setClient(client);
    }
    public int getNbperson() {
        return super.getNbperson();
    }
    public void setNbperson(int nbperson) {
        super.setNbperson(nbperson);
    }
    //


    public double reduction(){
        double reduction = 0.0;
        if(super.getNbperson() >= 50) reduction += 10;
        if (super.getClient().isEtudiant()) reduction+=8;
        if ((Esimeal.isFidele(super.getClient()))&&(Esimeal.nbCmds(super.getClient())>=2)) reduction += 5;
        return reduction/100;
    }

    public double calcul(int j){
        double prix = 0.0;
        for(Met i : menu){
            prix += i.getPrix();
        }
        return j == 0 ?  prix - prix*reduction() : prix;
    }
    public double calcule1(ArrayList<Met>met)
    {
        double e=0 ;
        double prix = 0.0;
        for(Met i : met){
            prix += i.getPrix();
        }
        return e == 0 ?  prix - prix*reduction() : prix;
    }
    public double reductionAmount(){
        double prix = 0.0;
        for(Met i : menu){
            prix += i.getPrix();
        }
        return prix*reduction();
    }
    public double reductionAmountFidel(){
        double prix = 0.0;
        double reduction = 0.0;
        for(Met i : menu){
            prix += i.getPrix();
        }
        if ((Esimeal.isFidele(super.getClient()))&&(Esimeal.nbCmds(super.getClient())>=2)) reduction += 0.05;
        return prix*reduction;
    }
    public double reductionAmountEtudiant(){
        double prix = 0.0;
        double reduction = 0.0;
        for(Met i : menu){
            prix += i.getPrix();
        }
        if (super.getClient().isEtudiant()) reduction+=0.08;
        return prix*reduction;
    }
    public double reductionAmountGroup(){
        return 0.0;
    }
    public double reductionAmountEvent(){
        double prix = 0.0;
        double reduction = 0.0;
        for(Met i : menu){
            prix += i.getPrix();
        }
        if(super.getNbperson() >= 50) reduction += 0.1;
        return prix*reduction;
    }
    public  void validcommande(LocalTime heure, int dist) throws MaxPersonne,RetardHeure
    {

        if((super.getNbperson() < 5) || (super.getNbperson() > 80))
        {
            throw new  MaxPersonne() ;
        }
        if(LocalDate.now().plusDays(15).isAfter(this.date))
        {
            throw new RetardHeure() ;
        }
    }
   public boolean IsEventtoday(LocalDateTime date)
   {
       if((this.date.getDayOfMonth()==date.getDayOfMonth())&&(this.date.getMonthValue()==date.getMonthValue())&&(this.date.getYear()==date.getYear()))
       {
           return true ;
       }
       return false ;
   }
}
