import java.util.ArrayList;
import java.util.Map;

public class Graph {
    private ArrayList<Arete> aretes;

    public Graph() {
        this.aretes = new ArrayList<Arete>();
    }

    public void ajouterArete(Arete arete) {
        this.aretes.add(arete);
    }

    public ArrayList<Arete> getAretes() {
        return this.aretes;
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
