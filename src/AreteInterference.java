/**
 * HAI705I - TP - Coloration de graphe
 * Authors:
 * - Youssef GRARI (22015973)
 * - Fadel BENOMAR (ELMY03039309)
 */


public class AreteInterference extends Arete {

    public AreteInterference(Sommet sommet1, Sommet sommet2) {
        super(sommet1, sommet2);
    }

    @Override
    public String toString() {
        return this.getSommets().get(0).toString() + " -----interf---- " + this.getSommets().get(1).toString();
    }
}
