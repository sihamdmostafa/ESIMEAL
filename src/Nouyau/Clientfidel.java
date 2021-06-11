package Nouyau ;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Clientfidel extends Client implements Serializable {
    private int codefidel;
    private ArrayList<String> adr = new ArrayList<String>();
    private LocalDate temp ;

    public Clientfidel(String nom,String  prenom,String  numtelph, int codefidel) {
        super(nom, prenom, numtelph);
        this.codefidel = codefidel;
        temp = LocalDate.now() ;

    }

    public int getCodefidel() {
        return codefidel;
    }

    public void setCodefidel(int codefidel) {
        this.codefidel = codefidel;
    }

    public ArrayList<String> getAdr() {
        return adr;
    }

    public String getAdr(int i) {
        return adr.get(i);
    }

    public void setAdr(ArrayList<String> adr) {
        this.adr = adr;
    }
   public void removadr(String s)
   {
       adr.remove(s) ;
   }
    public void ajouteadr(String s) {
        adr.add(s)
         ;
    }

    public void suprimadr(String s) {
        adr.remove(s);
    }
    public LocalDate gettemp()
    {
        return temp ;
    }
    public void affichadr() {
        for (int i = 0; i < adr.size(); i++) {
            System.out.println(i + " " + adr.get(i));
        }
    }
    public int hashCode()
    {
        return(this.codefidel) ;
    }
    public  boolean equals(Object o)
    {
        if(((Clientfidel)o).getCodefidel()==this.codefidel) return true ;
        else  return false ;
    }

}