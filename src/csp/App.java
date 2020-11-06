import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Comparator;

public class App {
    static ThreadMXBean thread = ManagementFactory.getThreadMXBean();

    private static ArrayList<Object> run(String fileName) {
        Network myNetwork;
        
        long startCpuTime;

        try {
            BufferedReader readFile = new BufferedReader(new FileReader(fileName));
            myNetwork = new Network(readFile);
            readFile.close();
        } catch(Exception e) {
            System.err.println("Fichier "+fileName+" n'existe pas.");
            myNetwork = null;
            System.exit(1);
        }
        CSP csp = new CSP(myNetwork);
        int nb_test = 5;
        ArrayList<Long> temps = new ArrayList<Long>();
        boolean result = false;
        //échauffemetn de la JVM
        for (int i = 0; i < 10; i++) {
            csp.searchSolutionFC();
        }


        for (int i = 0; i < nb_test; i++) {
            startCpuTime = thread.getCurrentThreadCpuTime();
            result = (csp.searchSolutionFC() != null);
            temps.add(thread.getCurrentThreadCpuTime() - startCpuTime);
        }
        double avg = temps.stream()
            .sorted(Comparator.reverseOrder()) // sort the stream from the highest to the smallest
            .skip(1)                           // discards 1 element from the beginning
            .sorted()                          // sort the stream from the smallest to the highest
            .skip(1)                           // discards 1 element from the beginning
            .mapToLong(num -> Long.valueOf(num))
            .average()
            .orElse(0);
        //System.out.print(result+":");
        ArrayList<Object> res = new ArrayList<Object>();
        res.add(avg / 100000);
        res.add(result?1:0);
        return res;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Utilisation : java App benchmark-path");
            System.exit(1);
        }
        String benchmark = args[0];
        if (!benchmark.substring(benchmark.length() - 1).equals("/"))
            benchmark += "/";
        
        int durete_min = 0, durete_max = 99;
        int instance_min = 1, instance_max = 10;

        System.out.println("durete temps taux_vrai");
        for (int d = durete_min; d <= durete_max; d++) {
            double somme = 0;
            int nb_true = 0;
            for(int i = instance_min; i <= instance_max; i++) {
                String file_name = benchmark + "d"+ d +"-i"+ i + ".txt";
                ArrayList<Object> r = run(file_name);
                somme += (double)r.get(0);
                nb_true += ((int)r.get(1))==0?0:1;
            }
            System.out.println(d+" "+somme/instance_max+" "+nb_true/instance_max*100);
        }
    }
}   