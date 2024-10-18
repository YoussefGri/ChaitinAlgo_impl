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

    public void ajouterVoisin(Sommet voisin) {
        this.voisins.add(voisin);
    }

    public String getNom() {
        return this.nom;
    }

    public ArrayList<Sommet> getVoisins() {
        return this.voisins;
    }

    public int nbVoisins() {
        return this.voisins.size();
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }

    public void setSpilled(boolean spilled) {
        this.spilled = spilled;
    }

    public boolean isSpilled() {
        return this.spilled;
    }

    public String toString() {
        if (!this.spilled) {
            return this.nom + " (" + this.color + ")";
        }
        return this.nom + " ( spilled )";
    }

}
