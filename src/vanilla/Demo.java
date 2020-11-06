import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Demo {
    private static void resolveurExt(Network n, String nom_reseau) {
        System.out.println("Réseau : " + nom_reseau);
        System.out.println("searchSol : ");
        CSP csp = new CSP(n);
        Assignment sol = csp.searchSolution();
        if (sol != null) {
            System.out.println("\tPremière solution trouvée : " + sol);
            System.out.println("\tNombre de noeuds explorés : " + csp.cptr);
            System.out.println("\tNombre d'appels à violation : " + ConstraintExt.appel);
            System.out.println("\tNombre de verification de tuples : " + ConstraintExt.verif);

            System.out.println("searchAllSol : ");
            List<Assignment> solutions = csp.searchAllSolutions();
            if (solutions != null) {
                System.out.println("\tNombre de solutions trouvées : " + solutions.size());
                System.out.println("\tNombre de noeuds explorés : " + csp.cptr);
                System.out.println("\tToutes les solutions trouvées : " + solutions);
            }
        }
    }

    private static void resolveur2(Network n, String nom_reseau) {
        System.out.println("Réseau : " + nom_reseau);
        System.out.println("searchSol : ");
        CSP csp = new CSP(n);
        Assignment sol = csp.searchSolution();
        if (sol != null) {
            System.out.println("\tPremière solution trouvée : " + sol);
            System.out.println("\tNombre de noeuds explorés : " + csp.cptr);
            System.out.println("searchAllSol : ");
            List<Assignment> solutions = csp.searchAllSolutions();
            if (solutions != null) {
                System.out.println("\tNombre de solutions trouvées : " + solutions.size());
                System.out.println("\tNombre de noeuds explorés : " + csp.cptr);
                System.out.println("\tToutes les solutions trouvées : " + solutions);
            }
        }
    }

    private static void resolveur3(Network n, String nom_reseau) {
        System.out.println("Réseau : " + nom_reseau);
        System.out.println("searchSol : ");
        CSP csp = new CSP(n);
        Assignment sol = csp.searchSolution();
        if (sol != null) {
            System.out.println("\tPremière solution trouvée : " + sol);
            System.out.println("\tNombre de noeuds explorés : " + csp.cptr);
            System.out.println("searchAllSol : ");
            List<Assignment> solutions = csp.searchAllSolutions();
            if (solutions != null) {
                System.out.println("\tNombre de solutions trouvées : " + solutions.size());
                System.out.println("\tNombre de noeuds explorés : " + csp.cptr);
            }
        }
    }

    private static void resolveur4(Network n, String nom_reseau) {
        System.out.println("Réseau : " + nom_reseau);
        System.out.println("searchSol : ");
        CSP csp = new CSP(n);
        Assignment sol = csp.searchSolution();
        if (sol != null) {
            System.out.println("\tPremière solution trouvée : " + sol);
            System.out.println("\tNombre de noeuds explorés : " + csp.cptr);
        }
    }

    private static Network readFile(String r) {
        Network current_network;
        String fileName = "../EvalTP1/" + r + ".txt";
        try {
            BufferedReader readFile = new BufferedReader(new FileReader(fileName));
            current_network = new Network(readFile);
            readFile.close();
        } catch (Exception e) {
            System.err.println("Fichier " + fileName + " n'existe pas.");
            current_network = null;
            System.exit(1);
        }
        return current_network;
    }

    public static void main(String[] args) {
        String[] reseaux = {"csp1", "csp2", "coloration"};
        String[] reseaux2 = {"colore", "zebre"};
        String[] reseaux3 = {"10reines_Ext", "10reines_Exp"};
        String[] reseaux4 = {"20reines_Ext"};
        String[] reseaux5 = {"cryptoMoney"/* , "cryptoMoneyIntension" */};


        for (String r : reseaux) {
            resolveurExt(readFile(r), r);
        }

        for (String r : reseaux2) {
            resolveur2(readFile(r), r);
        }

        for (String r : reseaux3) {
            resolveur3(readFile(r), r);
        }

        for (String r : reseaux4) {
            resolveur4(readFile(r), r);
        }

        for (String r : reseaux5) {
            resolveur2(readFile(r), r);
        }

    }
}
