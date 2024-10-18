public class Arete {
    private Sommet sommet1;
    private Sommet sommet2;

    public Arete(Sommet sommet1, Sommet sommet2) {
        this.sommet1 = sommet1;
        this.sommet2 = sommet2;

        sommet1.ajouterVoisin(sommet2);
        sommet2.ajouterVoisin(sommet1);
    }

    public Sommet getSommet1() {
        return this.sommet1;
    }

    public Sommet getSommet2() {
        return this.sommet2;
    }

}
