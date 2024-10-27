/**
 * HAI705I - TP - Coloration de graphe
 * Authors:
 * - Youssef GRARI (22015973)
 * - Fadel BENOMAR (ELMY03039309)
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";

    private static int lireEntierValide(Scanner scanner, String message, int min, int max) {
        int valeur;
        while (true) {
            System.out.print(ANSI_BLUE + message + ANSI_RESET);
            try {
                String input = scanner.nextLine().trim();
                valeur = Integer.parseInt(input);
                if (valeur >= min && valeur <= max) {
                    break;
                }
                System.out.println(ANSI_RED + "Erreur: Veuillez entrer un nombre entre " + min + " et " + max + ANSI_RESET);
            } catch (NumberFormatException e) {
                System.out.println(ANSI_RED + "Erreur: Veuillez entrer un nombre valide" + ANSI_RESET);
            }
        }
        return valeur;
    }

    private static void afficherMenu() {
        System.out.println("\n" + ANSI_GREEN + "=== Coloration de Graphe ===" + ANSI_RESET);
        System.out.println("1. Graph à 6 sommets");
        System.out.println("2. Graph en diamant");
        System.out.println("0. Quitter");
        System.out.println(ANSI_GREEN + "=========================" + ANSI_RESET + "\n");
    }

    private static void afficherDescriptionGraphe(int choix) {
        System.out.println(ANSI_BLUE + "\nDescription du graphe choisi:" + ANSI_RESET);
        switch (choix) {
            case 1:
                System.out.println("Graph à 6 sommets (x, y, z, t, u, v) avec:");
                System.out.println("- Arêtes d'interférence: xy, xv, xu, yt, yu, tv, zv");
                System.out.println("- Arête de préférence: tu");
                break;
            case 2:
                System.out.println("Graph en diamant à 4 sommets (x, y, z, t) avec:");
                System.out.println("- Arêtes d'interférence: xy, xz, yt, zt");
                break;
        }
    }

    /***
     * Fonction de coloration
     * @param g
     * @param nbCouleurs
     * @return void
     * ALGO CHAITIN(g, nbCouleurs) :
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
     * Compte le nombre de couleurs distinctes utilisées par les voisins d'un sommet pour le coalescing.
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

        System.out.println("pas de couleur pour le sommet " + s.getNom());
        return -1;
    }

    /**
     *     on verifie si on a une arete de preference entre s1 et s2
     */

    private static boolean isPreferenceEdge(Sommet s1, Sommet s2) {
        return s1.getPreferences().contains(s2);
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(ANSI_GREEN + "\nBienvenue dans le programme de coloration de graphe!" + ANSI_RESET);

        while (true) {
            afficherMenu();
            int choixGraphe = lireEntierValide(scanner, "Choisissez un graphe ou qittez (0-2): ", 0, 2);

            if (choixGraphe == 0) {
                System.out.println(ANSI_GREEN + "\nMerci d'avoir utilisé le programme. Au revoir!" + ANSI_RESET);
                break;
            }

            afficherDescriptionGraphe(choixGraphe);

            int nbCouleurs = lireEntierValide(scanner, "\nEntrez le nombre de couleurs (0-n): ", 0, 1000000000);

            Graph graphe;
            if (choixGraphe == 1) {
                graphe = creerGraphe1();
            } else {
                graphe = creerGraphe2();
            }

            System.out.println(ANSI_BLUE + "\nDébut de la coloration..." + ANSI_RESET);
            coloration(graphe, nbCouleurs);

            System.out.println(ANSI_GREEN + "\nRésultat de la coloration:" + ANSI_RESET);
            System.out.println(graphe.toString());

            System.out.println(ANSI_BLUE + "\nAppuyez sur Entrée pour continuer..." + ANSI_RESET);
            scanner.nextLine();
        }
        scanner.close();
    }

    private static Graph creerGraphe1() {
        Graph g1 = new Graph();
        Sommet x = new Sommet("x");
        Sommet y = new Sommet("y");
        Sommet z = new Sommet("z");
        Sommet t = new Sommet("t");
        Sommet u = new Sommet("u");
        Sommet v = new Sommet("v");

        // Ajout des sommets
        g1.ajouterSommet(x);
        g1.ajouterSommet(y);
        g1.ajouterSommet(z);
        g1.ajouterSommet(t);
        g1.ajouterSommet(u);
        g1.ajouterSommet(v);

        // Ajout des arêtes
        g1.ajouterArete(new AreteInterference(x, y));
        g1.ajouterArete(new AreteInterference(x, v));
        g1.ajouterArete(new AreteInterference(x, u));
        g1.ajouterArete(new AreteInterference(y, t));
        g1.ajouterArete(new AreteInterference(y, u));
        g1.ajouterArete(new AreteInterference(t, v));
        g1.ajouterArete(new AreteInterference(z, v));
        g1.ajouterArete(new AretePreference(t, u));

        return g1;
    }

    private static Graph creerGraphe2() {
        Graph g2 = new Graph();
        Sommet x = new Sommet("x");
        Sommet y = new Sommet("y");
        Sommet z = new Sommet("z");
        Sommet t = new Sommet("t");

        // Ajout des sommets
        g2.ajouterSommet(x);
        g2.ajouterSommet(y);
        g2.ajouterSommet(z);
        g2.ajouterSommet(t);

        // Ajout des arêtes
        g2.ajouterArete(new AreteInterference(x, y));
        g2.ajouterArete(new AreteInterference(x, z));
        g2.ajouterArete(new AreteInterference(y, t));
        g2.ajouterArete(new AreteInterference(z, t));

        return g2;
    }
}
