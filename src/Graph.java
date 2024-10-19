import java.util.ArrayList;

public class Graph {
    private ArrayList<Arete> aretes;
    private ArrayList<Sommet> sommets;

    public Graph() {
        this.aretes = new ArrayList<Arete>();
        this.sommets = new ArrayList<Sommet>();
    }

    public Graph(Graph g){
        this.aretes = new ArrayList<Arete>();
        for(Arete a : g.getAretes()){
            this.aretes.add(a);
        }
        this.sommets = new ArrayList<Sommet>();
        for(Sommet s : g.getSommets()){
            this.sommets.add(s);
        }
    }

    public ArrayList<Arete> getAretes() {
        return this.aretes;
    }

    public ArrayList<Sommet> getSommets() {
        return this.sommets;
    }

    public boolean ajouterArete(Arete arete) {
        return this.aretes.add(arete);
    }

    public boolean ajouterSommet(Sommet sommet) {
        return this.sommets.add(sommet);
    }

    public Sommet trouveSommetTrivial(int k) {

        if (this.aretes.isEmpty()) {
            return this.sommets.get(0);
        }

        for (Arete a : this.aretes) {
            Sommet s1 = a.getSommets().get(0);
            Sommet s2 = a.getSommets().get(1);

            int nbVoisins1 = s1.getnbVoisins();
            int nbVoisins2 = s2.getnbVoisins();

            if (nbVoisins1 < k || nbVoisins2 < k) {
                if (nbVoisins1 < k)
                {
                    System.out.println("Sommet trivial trouvé : " + s1.getNom());
                    return s1;
                }
                else
                {
                    System.out.println("Sommet trivial trouvé : " + s2.getNom());
                    return s2;
                }
            }
        }

        return null;
    }

    public Sommet getSommetNonTrivial(int k) {
        for (Sommet s : this.sommets) {
            if (s.getnbVoisins() >= k) {
                return s;
            }
        }
        return null;
    }

    public boolean supprimerSommet(Sommet s) {

        // Liste temporaire pour stocker les arêtes à supprimer pour eviter la maudite concurrentModificationException
        ArrayList<Arete> aretesToRemove = new ArrayList<>();

        // Rassembler les arêtes qui impliquent le sommet 's'
        for (Arete a : this.aretes) {
            if (a.getSommet(s) != null) {
                aretesToRemove.add(a);
            }
        }

        // Supprimer les arêtes de la liste principale
        this.aretes.removeAll(aretesToRemove);

        // Supprimer le sommet des listes de voisins de ses voisins
        for (Sommet s2 : s.getVoisins()) {
            s2.removeVoisin(s);
        }

        // Supprimer le sommet de la liste des sommets
        return this.sommets.remove(s);
    }




    @Override
    public String toString() {
        String res = "";

        for (Arete a : this.aretes) {
            res += a.toString() + "\n";
        }

        return res;

    }

}
