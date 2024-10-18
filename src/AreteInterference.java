public class AreteInterference extends Arete {

    public AreteInterference(Sommet sommet1, Sommet sommet2) {
        super(sommet1, sommet2);
    }

    @Override
    public String toString() {
        return this.getSommet1().toString() + " ---interf--> " + this.getSommet2().toString();
    }
}
