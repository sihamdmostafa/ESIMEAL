package Nouyau ;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.io.*;
public class Esimeal implements Serializable {
    private static HashMap<Integer,Clientfidel> clientf = new HashMap<Integer, Clientfidel>();
    private static ArrayList<Met> mt = new ArrayList<Met>();
    private static TreeSet<Commande> cmnd = new TreeSet<>();
    private static TreeSet<Commande> cmndvalid = new TreeSet<Commande>();
    private static ArrayList<Table> table = new ArrayList<Table>();
    private static ArrayList<Suplment> suplement = new ArrayList<>();
    public static ArrayList<Suplment> getSuplement() {
        return suplement;
    }

    public static ArrayList<Table> getTable() {
        return table;
    }
    public static TreeSet<Commande> getCmnd() {
        return cmndvalid;
    }
    private static int nbrchaisse = 200;
    private static int Nbrfidel = 19000 ;
    public static int Getnbrfidel()
    {
    	return Nbrfidel ; 
    }
    public static void Setnbrfidel(int a)
    {
    	Nbrfidel = a ; 
    }
    public static ArrayList<Met> getMt() {
        return mt;
    }
    public static TreeSet<Commande> getlistcommande() {
         return cmnd;
     }
    public static HashMap<Integer,Clientfidel> getlistClientf() {
        return clientf;
    }
    public static TreeSet<Commande> getlistCmndnonvalid() {
        return cmndvalid;
    }
    public static ArrayList<Table> getlistTable() {
        return table;
    }
    public static void setMt(ArrayList<Met> met) {
        mt=met ;
     }
    public static  void setlistcommande(TreeSet<Commande> cmnde) {
    cmnd = cmnde ;
    }
    public static void setlistClientf(HashMap<Integer,Clientfidel> clientff) {
        clientf = clientff ;
    }
    public static void setlistCmndnonvalid(TreeSet<Commande> cmnde) {
        cmndvalid = cmnde ;
    }
    private static LocalTime datedernier = LocalTime.now() ;
    public static  void setlistTable(ArrayList<Table>  listtable) {

            table = listtable;

        }

    public static void setSuplement(ArrayList<Suplment> suplement) {
        Esimeal.suplement = suplement;
    }

    public static void ajouttable(Table tabl) {
        table.add(tabl);
    }

    public static void ajoutMet(Met e) {
        mt.add(e);
    }

    public static ArrayList<String> affichMet() {
        ArrayList<String> list = new ArrayList<String>() ;
        int j = 0;
        for (int i = 0; i < mt.size(); i++) {
            if (mt.get(i) instanceof Repas) {
                if(mt.get(i).getUnit() > 0)
                    list.add(i + " Un repas : le nom est "+mt.get(i).getNom()+"  le prix est  " + mt.get(i).getPrix() + " le nombre de calories est "+mt.get(i).getNbcalories());
            } else {
                if(mt.get(i).getUnit() > 0)
                    list.add(i + " Un boisson : le nom est "+mt.get(i).getNom()+"  le prix est  " + mt.get(i).getPrix() + " le nombre de calories est "+mt.get(i).getNbcalories());
            }
        }
        return list ;
    }

    public static Clientfidel ajoutfidel(String nom,String prenom,String num) {
        Clientfidel f;
         int nombreAleatoire = Nbrfidel ;
         Nbrfidel++ ;
         f = new Clientfidel(nom, prenom, num, nombreAleatoire);
         clientf.put(nombreAleatoire,f);
         return f;
    }

    public  void Commander(Commande comnd){
         cmnd.add(comnd);
    }

    public static Clientfidel rechClientfidel(int i) {
        for (Clientfidel c : clientf.values())
        {
            if(c.getCodefidel() == i)
            return (clientf.get(i)) ;
        }
        return null;
    }

    public static boolean isFidele(Client cl) {
        for (Client i : clientf.values()) {
            if (cl.getNom().equals(i.getNom()) && cl.getPrenom().equals(i.getPrenom()) && cl.getNumtelph().equals(i.getNumtelph()))
                return true;
        }
        return false;
    }

    public static int nbCmds(Client c) {
        int s = 0;
        if (isFidele(c)) {
            for (Commande i : cmnd) {
                if (c.equals(i)) s++;
            }
            return s;
        } else return 0;
    }

    public static Table isDispotable(boolean type) {
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).getDispo() == true) {
                if (table.get(i).gettype() == type) {
                    table.get(i).setDispo(false);
                    table.get(i).setheure(LocalDateTime.now().getHour());
                    return table.get(i);
                }
            }
        }
        return null;
    }

    public static int getnbtchaisse() {
        return nbrchaisse;
    }

    public static void setnbchaise(int nbr) {
        nbrchaisse = nbr;
    }

    public static void refrechtab() {
        int hour = LocalDateTime.now().getHour();
        if ((hour >= 0) && (hour < 10)) {
            for (Table tab : table) {
                tab.setheure(0);
                tab.setDispo(true);
            }
        } else {
            for (Table tab : table) {
                if (hour >= (tab.getHeure() + 2)) {
                    tab.setheure(0);
                    tab.setDispo(true);
                }
            }
        }
    }
   public static void mtajouretab()
   {
       LocalTime heure = LocalTime.now() ;
       if(datedernier.plusMinutes(30).isBefore(heure))
       {
           refrechtab();
           datedernier = heure ;
       }
   }
    public static boolean iseventToday() {
        for (Commande c : cmnd) {
            if (c instanceof Evenment) {
                if (((Evenment) c).IsEventtoday(LocalDateTime.now())) {
                    return true;
                }
            }
        }
       return false ;
    }
    public static void mtajoure(String nom, String prenom, String numtelph)
    {
        for (Clientfidel c:clientf.values())
              {
                 if((c.getNom().equals(nom))&&(c.getPrenom().equals(prenom)))
                 {
                     c.setNumtelph(numtelph);
                 }
              }
    }
     public void supmet(Met m)
     {
         mt.remove(m) ;
     }
     public int metajoure(Met met)
     {
         for (Met m:mt) {
             if(m.getNom()==met.getNom())
             {
                 mt.remove(m) ;
                 mt.add(met) ;
                 return 1 ;
             }

         }
         return 0 ;
     }
     public static int nbrcommandeeffectuer(LocalDate date1,LocalDate date2)
     {
         int i=0 ;
         for (Commande c:cmndvalid) {
             if((c.getdate().isBefore(date2))&&(c.getdate().isAfter(date1)))
             {
                 i++ ;
             }

         }
       return i ;
     }
    public static double montantcommandefftc(LocalDate date1, LocalDate date2) {
        double s = 0.0;
        int i;
        for (Commande c : cmndvalid) {
            i = c.getNbperson();
            if ((c.getdate().isBefore(date2)) && (c.getdate().isAfter(date1))) {
                for (int j = 0;j<i;j++)
                    s = s + c.calcul(j);
            }

        }
        return s;
    }
     public static int NbrcmndEffect(LocalDate date1,LocalDate date2,int j) // nombre des commande effectuer selon le type
     {
         int i=0 ;
         for (Commande c:cmndvalid) {
             if((c.getdate().isBefore(date2))&&(c.getdate().isAfter(date1)))
             {
                 if(((c instanceof Commandext)&&(j==0)) ||((c instanceof Commandelivre)&&(j==1))||((c instanceof Evenment)&&(j==2)))
                    i++ ;
             }

         }
         return i ;
     }
    public double MontCmndEfec(LocalDate date1, LocalDate date2, int j) //le prix de la commande selon le type
    {
        double s = 0.0;
        int i;
        for (Commande c : cmndvalid) {
            i = c.getNbperson();
            if ((c.getdate().isBefore(date2)) && (c.getdate().isAfter(date1))) {
                if (((c instanceof Commandext) && (j == 0)) || ((c instanceof Commandelivre) && (j == 1)) || ((c instanceof Evenment) && (j == 2))) {
                    for (int k = 0; k<i ;k++)
                        s = s + c.calcul(k);
                }
            }

        }
        return s;
    }
    public static int Nbrcmndmet(LocalDate date1,LocalDate date2,Met m)
    {
        int i = 0 ;
        for(Commande c : cmndvalid)
        {
            if(c instanceof Commandext) {
                if ((c.getdate().isBefore(date2)) && (c.getdate().isAfter(date1))) {
                    for (ArrayList<Met> list : ((Commandext) c).getList()) {
                        for (Met n : list) {
                            if(n.getNom() == m.getNom()) i++ ;
                        }
                    }
                }
            }
        }
        return i ;
    }
     public static Met Metpluscommander(LocalDate date1,LocalDate date2)
     {
         if(mt.size() !=0) {
             Met m = mt.get(0);
             int i = Nbrcmndmet(date1,date2,m) ;
             for (Met met : mt) {
                 if (Nbrcmndmet(date1,date2,met) >= i) {
                     m = met;
                     i = Nbrcmndmet(date1,date2,met);
                 }
             }
             return m;
         }
         else
         {
             return null ;
         }
     }
     public static Met Metmoincommander(LocalDate date1,LocalDate date2)
     {
         if(mt.size() !=0) {
             Met m = null;
             int i = 0;
             for (Met met : mt) {
                 if (Nbrcmndmet(date1,date2,met) <= i) {
                     m = met;
                     i = Nbrcmndmet(date1,date2,met);
                 }
             }
             return m;
         }
         else
             return null ;
     }
     public static int getNcmndSurplc(LocalDate date1,LocalDate date2,Client cl){
        int i = 0;
        if (cmndvalid.size() != 0) {
             for (Commande c : cmndvalid){
                 if ((c.getdate().isBefore(date2)) && (c.getdate().isAfter(date1)))
                     if(c.getClient().equals(cl))
                         if(c.getType() == 0) i++;
             }
         }
         else{
            i = 0;
         }
         return i;
     }
    public static int getNcmndDmcl(LocalDate date1,LocalDate date2,Client cl){
        int i = 0;
        if (cmndvalid.size() != 0) {
            for (Commande c : cmndvalid){
                if ((c.getdate().isBefore(date2)) && (c.getdate().isAfter(date1)))
                    if(c.getClient().equals(cl))
                        if(c.getType() == 1) i++;
            }
        }
        else{
            i = 0;
        }
        return i;
    }
    public static int getNcmndEvent(LocalDate date1,LocalDate date2,Client cl){
        int i = 0;
        if (cmndvalid.size() != 0) {
            for (Commande c : cmndvalid){
                if ((c.getdate().isBefore(date2)) && (c.getdate().isAfter(date1)))
                    if(c.getClient().equals(cl))
                        if(c.getType() == 2) i++;
            }
        }
        else{
            i = 0;
        }
        return i;
    }
    public static int getnbrcmnd(LocalDate date1,LocalDate date2,Client cl){
        return getNcmndSurplc(date1,date2,cl) + getNcmndDmcl(date1,date2,cl) + getNcmndEvent(date1,date2,cl);
    }
     public static Client PlusCmndClient(LocalDate date1,LocalDate date2) {
         if (cmndvalid.size() != 0) {
             Client client = null ;
             int i =0 ;
             for (Commande c : cmndvalid) {
                 if ((c.getdate().isBefore(date2)) && (c.getdate().isAfter(date1))) {
                     if(c.getClient().getnbrcmnd() >= i)
                     {
                         client=c.getClient() ;
                         i = getnbrcmnd(date1,date2,c.getClient());
                     }
                 }

             }
             return client ;
         }
         else return null ;
     }
     public static Client PlusCmndVarClient(LocalDate date1,LocalDate date2,int j) {
         if (cmndvalid.size() != 0) {
             Client client = null ;
             int i =0 ;
             for (Commande c : cmndvalid) {
                 if ((c.getdate().isBefore(date2)) && (c.getdate().isAfter(date1))) {
                     if(((c.getClient().getNcmndSurplc() >= i)&&(j==0))||((c.getClient().getNcmndDmcl()>= i)&&(j==1))||((c.getClient().getNcmndEvent()>= i)&&(j==2)))
                     {
                         client=c.getClient() ;
                         switch (j){
                             case 0:
                                 i = getNcmndSurplc(date1,date2,c.getClient());
                                 break;
                             case 1:
                                 i = getNcmndDmcl(date1,date2,c.getClient());
                                 break;
                             case 2:
                                 i = getNcmndEvent(date1,date2,c.getClient());
                                 break;
                         }
                     }
                 }

             }
             return client ;
         }
         else return null ;
     }

    public static double montantTotalReduction(LocalDate date1,LocalDate date2){
        double amount = 0.0;
        for(Commande c : cmndvalid){
            if (c.getdate().isAfter(date1) && c.getdate().isBefore(date2))
                amount += c.reductionAmount();
        }
        return amount;
    }
    public static double montantTotalReductionType(LocalDate date1,LocalDate date2,int j){
        double amount = 0.0;
        switch (j){
            case 0:
                for(Commande c : cmndvalid){
                    if (c.getdate().isAfter(date1) && c.getdate().isBefore(date2))
                        amount += c.reductionAmountFidel();
                }
                break;
            case 1:
                for(Commande c : cmndvalid){
                    if (c.getdate().isAfter(date1) && c.getdate().isBefore(date2))
                        amount += c.reductionAmountEtudiant();
                }
                break;
            case 2:
                for(Commande c : cmndvalid){
                    if (c.getdate().isAfter(date1) && c.getdate().isBefore(date2))
                        amount += c.reductionAmountGroup();
                }
                break;
            case 3:
                for(Commande c : cmndvalid){
                    if (c.getdate().isAfter(date1) && c.getdate().isBefore(date2))
                        amount += c.reductionAmountEvent();
                }
                break;
            default:
                return 0.0;
        }
        return amount;
    }
    public static Client plusNbReduction(LocalDate date1,LocalDate date2){
        double amount = 0.0;
        Client client = null;
        for (Commande c : cmndvalid){
            if (c.reductionAmount()>= amount && c.getdate().isAfter(date1) && c.getdate().isBefore(date2)){
                amount = c.reductionAmount();
                client = c.getClient();
            }
        }
        return client;
    }
    public static Client plusNbReductionType(LocalDate date1,LocalDate date2,int j){
        Client client = null;
        double amount = 0.0;
        switch (j){
            case 0:
                for (Commande c : cmndvalid){
                    if (c.reductionAmountFidel()>= amount && c.getdate().isAfter(date1) && c.getdate().isBefore(date2)){
                        amount = c.reductionAmountFidel();
                        client = c.getClient();
                    }
                }
                break;
            case 1:
                for (Commande c : cmndvalid){
                    if (c.reductionAmountEtudiant()>= amount && c.getdate().isAfter(date1) && c.getdate().isBefore(date2)){
                        amount = c.reductionAmountEtudiant();
                        client = c.getClient();
                    }
                }
                break;
            case 2:
                for (Commande c : cmndvalid){
                    if (c.reductionAmountGroup()>= amount && c.getdate().isAfter(date1) && c.getdate().isBefore(date2)){
                        amount = c.reductionAmountGroup();
                        client = c.getClient();
                    }
                }
                break;
            case 3:
                for (Commande c : cmndvalid){
                    if (c.reductionAmountEvent()>= amount && c.getdate().isAfter(date1) && c.getdate().isBefore(date2)){
                        amount = c.reductionAmountEvent();
                        client = c.getClient();
                    }
                }
                break;
            default:
                return null;
        }
        return client;
    }
     public static void  ajoutecmnd(Commande cmnd)
     {
         cmndvalid.add(cmnd) ;
     }
     public static void ajoutCmndEffect()
     {
         for(Commande c : cmndvalid)
         {
             if(c.getType() != 2) {
                 if (LocalTime.now().isAfter(c.getHeure1())) {
                     cmndvalid.remove(c);
                     cmnd.add(c);
                 }
             }
             else
             {
                 if(LocalDate.now().isAfter(((Evenment)c).getdateEvent()))
                 {
                     cmndvalid.remove(c);
                     cmnd.add(c);
                 }
             }
         }
     }

}






