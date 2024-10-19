public class AretePreference extends Arete {

    public AretePreference(Sommet sommet1, Sommet sommet2) {
        super(sommet1, sommet2);
    }

    @Override
    public String toString() {
        return this.getSommets().get(0).toString() + " ---pref--> " + this.getSommets().get(1).toString();
    }
}
