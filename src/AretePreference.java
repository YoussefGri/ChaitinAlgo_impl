public class AretePreference extends Arete {

    public AretePreference(Sommet sommet1, Sommet sommet2) {
        super(sommet1, sommet2);
    }

    @Override
    public String toString() {
        return this.getSommet1().getNom() + " ---pref--> " + this.getSommet2().getNom();
    }
}
