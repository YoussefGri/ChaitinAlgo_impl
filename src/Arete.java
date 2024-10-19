import java.util.ArrayList;

public class Arete {
    private Sommet sommet1;
    private Sommet sommet2;

    public Arete(Sommet sommet1, Sommet sommet2) {
        this.sommet1 = sommet1;
        this.sommet2 = sommet2;

        if (!sommet1.getVoisins().contains(sommet2)) {
            sommet1.setVoisin(sommet2);
        }
        if (!sommet2.getVoisins().contains(sommet1)) {
            sommet2.setVoisin(sommet1);
        }
    }


    public Sommet getSommet(Sommet s) {
        if (sommet1.equals(s)) {
            return sommet2; // Retourne l'autre sommet
        }
        if (sommet2.equals(s)) {
            return sommet1; // Retourne l'autre sommet
        }
        return null;
    }


    public ArrayList<Sommet> getSommets() {
        ArrayList<Sommet> sommets = new ArrayList<Sommet>();
        sommets.add(sommet1);
        sommets.add(sommet2);
        return sommets;
    }



}
