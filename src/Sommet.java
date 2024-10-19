import java.util.ArrayList;

public class Sommet {
    private String nom;
    private ArrayList<Sommet> voisins;
    private int color = -1;
    private boolean spilled = false;

    public Sommet(String nom) {
        this.nom = nom;
        this.voisins = new ArrayList<Sommet>();
    }



    public String getNom() {
        return this.nom;
    }

    public ArrayList<Sommet> getVoisins() {
        return this.voisins;
    }

    public int getColor() {
        return this.color;
    }

    public boolean setVoisin(Sommet voisin) {
        return this.voisins.add(voisin);
    }

    public boolean removeVoisin(Sommet voisin) {
        return this.voisins.remove(voisin);
    }

    public int getnbVoisins() {
        return this.voisins.size();
    }

    public void setColor(int color) {
        this.color = color;
    }


    public void setSpilled(boolean spilled) {
        this.spilled = spilled;
    }



    public String toString() {
        if (!this.spilled) {
            return this.nom + " (" + this.color + ")";
        }
        return this.nom + " ( spilled )";
    }

}
