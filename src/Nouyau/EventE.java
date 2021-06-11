package Nouyau ;
public enum EventE {
    anniversaire,
    obteniondediplom,
    dinerdemariaage,
    dinerdedepartalaretraite;

    public static void affiche() {
        int i = 0;
        for (EventE e : EventE.values()) {
            System.out.println(i + " " + e.toString());
            i++;
        }
    }
}
