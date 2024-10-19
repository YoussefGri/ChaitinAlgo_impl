import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    /***
     * Fonction de coloration
     * @param g
     * @param nbCouleurs
     * @return void
     * si il existe un sommet s trivialement colorable alors :
     *    colorer g \ s (g privé de s) avec nbCouleurs couleurs
     *    colorer s avec une couleur disponible
     *
     * sinon
     *   choisir un sommet s et colorer g \ s avec nbCouleurs couleurs
     *   spiller s
     */
/*
    public static void coloration(Graph g, int nbCouleurs) {

        int k = nbCouleurs;
        Sommet s;

        if(k==0){
            for (Sommet sommet : g.getSommets()){
                sommet.setSpilled(true);
            }
            return;
        }

        if (g.getSommets().size() == 1) {
            s = g.getSommets().get(0);
            if (s != null) {
                s.setColor(getAvailableColor(s, k));
                System.out.println("Sommet traité (dernier sommet): " + s.getNom());
            }

            return;
        }

        // si on trouve un sommet trivial

        s = g.trouveSommetTrivial(k);
        if (s != null) {
            System.out.println("Sommet traité (trivial): " + s.getNom());
            Graph g2 = new Graph(g);
            if(g2.supprimerSommet(s)){

                // on colorie g \ s avec k couleurs
                coloration(g2, k);
                // et on colorie s avec une couleur disponible
                s.setColor(getAvailableColor(s, k));

            }

        }

        // Sinon, on spill un sommet
        else {

            s = g.getSommetNonTrivial(k);

            System.out.println("Sommet traité(non trivial) : " + s.getNom());

            Graph g2 = new Graph(g);
            if(g2.supprimerSommet(s)) {

                coloration(g2, k);
                s.setSpilled(true);
            }

        }

    }

 */

    public static void coloration(Graph g, int nbCouleurs) {
        int k = nbCouleurs;
        Sommet s;

        if (k == 0) {
            for (Sommet sommet : g.getSommets()) {
                sommet.setSpilled(true);
            }
            return;
        }

        if (g.getSommets().size() == 1) {
            s = g.getSommets().get(0);
            if (s != null) {
                s.setColor(getAvailableColor(s, k));
                System.out.println("Sommet traité (dernier sommet): " + s.getNom());
            }
            return;
        }

        // si on trouve un sommet trivial
        s = g.trouveSommetTrivial(k);
        if (s != null) {
            System.out.println("Sommet traité (trivial): " + s.getNom());
            Graph g2 = new Graph(g);
            if (g2.supprimerSommet(s)) {
                // on colorie g \ s avec k couleurs
                coloration(g2, k);
                // et on colorie s avec une couleur disponible
                s.setColor(getAvailableColor(s, k));
            }
        } else {
            // Sinon, on spill potentiellement un sommet non trivial
            s = g.getSommetNonTrivial(k);
            System.out.println("Sommet traité (non trivial) : " + s.getNom());

            Graph g2 = new Graph(g);
            if (g2.supprimerSommet(s)) {
                coloration(g2, k);

                // Phase de remontée : on essaie de colorier s
                int distinctNeighborColors = getDistinctNeighborColors(s);
                if (distinctNeighborColors < k) {
                    // Il y a moins de k couleurs distinctes chez les voisins de s, on peut colorier s
                    s.setColor(getAvailableColor(s, k));
                    System.out.println("Sommet colorié après remontée: " + s.getNom());
                } else {
                    // Sinon, on spill le sommet
                    s.setSpilled(true);
                    System.out.println("Sommet spillé après remontée: " + s.getNom());
                }
            }
        }
    }

    /**
     * Compte le nombre de couleurs distinctes utilisées par les voisins d'un sommet.
     */
    public static int getDistinctNeighborColors(Sommet s) {
        ArrayList<Integer> distinctColors = new ArrayList<>();
        for (Sommet voisin : s.getVoisins()) {
            int color = voisin.getColor();
            if (color != -1 && !distinctColors.contains(color)) {
                distinctColors.add(color);
            }
        }
        return distinctColors.size();
    }

    /**
     * Trouve la première couleur disponible pour un sommet donné en fonction de ses voisins.
     */

    public static int getAvailableColor(Sommet s, int k) {
        boolean[] usedColors = new boolean[k + 1]; // de 1 à k
        int preferenceColor = -1; // stockera la couleur du voisin pref

        for (Sommet voisin : s.getVoisins()) {

            if (voisin.getColor() != -1) {

                if (isPreferenceEdge(s, voisin)) {

                    preferenceColor = voisin.getColor();

                } else {
                    // Marquer la couleur comme utilisée
                    usedColors[voisin.getColor()] = true;
                }
            }
        }

        // si la couleur est disponible on la retourne
        if (preferenceColor != -1 && !usedColors[preferenceColor]) {
            return preferenceColor;
        }

        // sinon on va chercher la première couleur disponible

        for (int i = 1; i <= k; i++) {
            if (!usedColors[i]) {
                return i;
            }
        }

        // Return -1 if no color is available (should not happen under normal conditions)
        System.out.println("No color available for sommet " + s.getNom());
        return -1;
    }

    /**
     *     on verifie si on a une arete de preference entre s1 et s2
     */

    private static boolean isPreferenceEdge(Sommet s1, Sommet s2) {
        return s1.getPreferences().contains(s2); // Example implementation
    }



    public static void main(String[] args){

        int nbCouleurs;
        int graphe;



        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le nombre de couleurs : ");
        nbCouleurs = sc.nextInt();

        while (true){
            Scanner sc2 = new Scanner(System.in);
            System.out.println("Choisissez un graph (1 pour le graph à 6 sommets, 2 pour le graph en diamant) : ");
            graphe = sc2.nextInt();

            if (graphe == 1 || graphe == 2){
                break;
            }
        }

/***
 * ------------------- GRAPH 1 ---------------------------- *
 */

        if(graphe==1){

            Sommet x = new Sommet("x");
            Sommet y = new Sommet("y");
            Sommet z = new Sommet("z");
            Sommet t = new Sommet("t");
            Sommet u = new Sommet("u");
            Sommet v = new Sommet("v");

            Arete xy = new AreteInterference(x, y);
            Arete xv = new AreteInterference(x, v);
            Arete xu = new AreteInterference(x, u);

            Arete yt = new AreteInterference(y, t);
            Arete yu = new AreteInterference(y, u);

            Arete tv = new AreteInterference(t, v);
            Arete tu = new AretePreference(t,u);

            Arete zv = new AreteInterference(z, v);

            Graph g1 = new Graph();

            g1.ajouterSommet(x);
            g1.ajouterSommet(y);
            g1.ajouterSommet(z);
            g1.ajouterSommet(t);
            g1.ajouterSommet(u);
            g1.ajouterSommet(v);


            g1.ajouterArete(xy);
            g1.ajouterArete(xv);
            g1.ajouterArete(xu);

            g1.ajouterArete(yt);
            g1.ajouterArete(yu);

            g1.ajouterArete(tv);
            g1.ajouterArete(zv);

            g1.ajouterArete(tu);

            try {
                g1.getAretes().isEmpty();
            }
            catch (Exception e){
                System.out.println("Le graphe est vide");
            }

            try {
                g1.getSommets().isEmpty();
            }
            catch (Exception e){
                System.out.println("Le graphe est vide");
            }

            coloration(g1, nbCouleurs);

            String affiche = g1.toString();

            System.out.println(affiche);

    }
/***
 * ------------------- GRAPH 2 ---------------------------- *
 */
    else if (graphe == 2){

        Sommet x = new Sommet("x");
        Sommet y = new Sommet("y");
        Sommet z = new Sommet("z");
        Sommet t = new Sommet("t");

        Arete xy = new AreteInterference(x, y);
        Arete xz = new AreteInterference(x, z);

        Arete yt = new AreteInterference(y, t);
        Arete zt = new AreteInterference(z, t);

        Graph g2 = new Graph();

        g2.ajouterSommet(x);
        g2.ajouterSommet(y);

        g2.ajouterSommet(z);
        g2.ajouterSommet(t);

        g2.ajouterArete(xy);
        g2.ajouterArete(xz);

        g2.ajouterArete(yt);
        g2.ajouterArete(zt);



        coloration(g2, nbCouleurs);

        String affiche = g2.toString();

        System.out.println(affiche);

        }

    }

}
