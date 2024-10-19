public class AretePreference extends Arete {

    public AretePreference(Sommet sommet1, Sommet sommet2) {
        super(sommet1, sommet2);
        sommet1.setPreference(sommet2);
        sommet2.setPreference(sommet1);
    }

    @Override
    public String toString() {
        return this.getSommets().get(0).toString() + " -----pref---- " + this.getSommets().get(1).toString();
    }
}
