package Nouyau;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class Fichier {
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    public static void Sauvgarde() throws ClassNotFoundException {
        try {
            out = new ObjectOutputStream(
                    new BufferedOutputStream(new FileOutputStream(
                            new File("fichier.dat"))));
            out.writeObject(Esimeal.getMt());
            out.writeObject(Esimeal.getnbtchaisse());
            out.writeObject(Esimeal.getlistcommande());
            out.writeObject(Esimeal.getlistCmndnonvalid());
            out.writeObject(Esimeal.getlistTable());
            out.writeObject(Esimeal.getlistClientf()) ;
            out.writeObject(Esimeal.getSuplement());
            out.writeInt(Esimeal.Getnbrfidel());
            out.close();

        } catch (FileNotFoundException e)
        {

         } catch (IOException e)

        {
            e.printStackTrace();

        }
     }
    public static void Recupere() throws ClassNotFoundException {
        try {
            in = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(
                                    new File("fichier.dat"))));


            Esimeal.setMt((ArrayList<Met>)in.readObject()) ;
            Esimeal.setnbchaise((Integer)in.readObject());
            Esimeal.setlistcommande((TreeSet<Commande>)in.readObject());
            Esimeal.setlistCmndnonvalid((TreeSet<Commande>)in.readObject());
            Esimeal.setlistTable((ArrayList<Table>)in.readObject()); ;
            Esimeal.setlistClientf((HashMap<Integer,Clientfidel>)in.readObject());
            Esimeal.setSuplement((ArrayList<Suplment>)in.readObject());
            Esimeal.Setnbrfidel((int)in.readInt());
            in.close();

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)

        {
            e.printStackTrace();
        }
    }
}

