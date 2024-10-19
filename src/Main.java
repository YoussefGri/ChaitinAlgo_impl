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

    public static void coloration(Graph g, int nbCouleurs) {

        int k = nbCouleurs;
        Sommet s;

        if (g.getAretes().isEmpty()) {
            s = g.trouveSommetTrivial(k);
            if (s != null) {
                s.setColor(getAvailableColor(s, k));
            }

            return;
        }

        // si on trouve un sommet trivial

        s = g.trouveSommetTrivial(k);
        if (s != null) {
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

            s = g.getSommetNonTrivial(k); // on prend le premier sommet (ou peu importe)

            if (s.getNom().equals("y")){System.out.println("Sommet choisi pour etre spilled : " + s.getNom() + " nombre de voisins : " + s.getnbVoisins());}
            if (s.getNom().equals("x")){System.out.println("Sommet choisi pour etre spilled : " + s.getNom() + " nombre de voisins : " + s.getnbVoisins());}

            Graph g2 = new Graph(g);
            if(g2.supprimerSommet(s)) {

                coloration(g2, k);
                s.setSpilled(true);
            }

        }

    }

    /**
     * Trouve la première couleur disponible pour un sommet donné en fonction de ses voisins.
     */

    public static int getAvailableColor(Sommet s, int k) {
        boolean[] usedColors = new boolean[k + 1]; // Available colors from 1 to k
        int preferenceColor = -1; // To store the color of a preference neighbor

        // Iterate over neighbors
        for (Sommet voisin : s.getVoisins()) {
            // If the neighbor has a color assigned
            if (voisin.getColor() != -1) {
                // Check if this is a preference edge
                if (isPreferenceEdge(s, voisin)) {
                    // If it's a preference edge, remember the color
                    preferenceColor = voisin.getColor();
                } else {
                    // Mark the color as used
                    usedColors[voisin.getColor()] = true;
                }
            }
        }

        // If there's a preference color, return it if it's available
        if (preferenceColor != -1 && !usedColors[preferenceColor]) {
            return preferenceColor;
        }

        // Find the first available color that is not used
        for (int i = 1; i <= k; i++) {
            if (!usedColors[i]) {
                return i;
            }
        }

        // Return -1 if no color is available (should not happen under normal conditions)
        return -1;
    }

    // Method to check if there's a preference edge between two vertices
    private static boolean isPreferenceEdge(Sommet s1, Sommet s2) {
        // Implement logic to determine if s1 and s2 are connected by a preference edge
        // This could involve checking a specific property of the edge or maintaining a separate list of preference edges
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
