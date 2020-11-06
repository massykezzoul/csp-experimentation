import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Application {
    public static void main(String[] args) throws Exception {
        // lecture du fichier
        if (args.length != 1) {
            System.err.println("Utilisation : java Application file-path");
            System.exit(1);
        }
        String fileName = args[0];
        Network myNetwork;
        
        System.out.println("Chargement du fichier : "+ new java.io.File(".").getCanonicalPath()+"/"+fileName);

        // Création du Network à partir du fichier 
        try {
            BufferedReader readFile = new BufferedReader(new FileReader(fileName));
            myNetwork = new Network(readFile);
            readFile.close();
            
            System.out.println(myNetwork);

        } catch(FileNotFoundException e) {
            System.err.println("Fichier "+fileName+" n'existe pas.");
            myNetwork = null;
            System.exit(1);
        }

        CSP csp = new CSP(myNetwork);
        System.out.println("Searching solution...");
        Assignment sol = csp.searchSolution();
        if (sol == null) 
            System.out.println("Aucune solution trouvé.");
        else {
            System.out.println("Solution : " + sol);
            //System.out.println("Toutes les solutions : " + csp.searchAllSolutions());
        }

        
    }
}   