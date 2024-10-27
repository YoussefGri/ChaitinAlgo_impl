/**
 * HAI705I - TP - Coloration de graphe
 * Authors:
 * - Youssef GRARI (22015973)
 * - Fadel BENOMAR (ELMY03039309)
 */

import java.util.ArrayList;

public class Sommet {
    private String nom;
    private ArrayList<Sommet> voisins;
    private ArrayList<Sommet> preferences;
    private int color = -1;
    private boolean spilled = false;

    public Sommet(String nom) {
        this.nom = nom;
        this.voisins = new ArrayList<Sommet>();
        this.preferences = new ArrayList<Sommet>();
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

    public ArrayList<Sommet> getPreferences() {
        return this.preferences;
    }

    public boolean setVoisin(Sommet voisin) {
        return this.voisins.add(voisin);
    }

    // Method to add a preference edge
    public void setPreference(Sommet voisin) {
        this.preferences.add(voisin);
    }


    public boolean removeVoisin(Sommet voisin) {
        return this.voisins.remove(voisin);
    }

    public int getnbVoisins() {
        return (this.voisins.size() - this.preferences.size());
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
