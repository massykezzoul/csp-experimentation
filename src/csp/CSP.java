
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

/**
 * Solveur : permet de résoudre un problème de contrainte par Backtrack : Calcul
 * d'une solution, Calcul de toutes les solutions
 *
 */
public class CSP {

	private Network network; // le réseau à résoudre
	private List<Assignment> solutions; // les solutions du réseau (résultat de searchAllSolutions)
	private Assignment assignment; // l'assignation courante (résultat de searchSolution)
	private Map<String,Integer> nbOccu;
	int cptr; // le compteur de noeuds explorés

	/**
	 * Crée un problème de résolution de contraintes pour un réseau donné
	 * 
	 * @param r le réseau de contraintes à résoudre
	 */
	public CSP(Network r) {
		network = r;
		solutions = new ArrayList<Assignment>();
		assignment = new Assignment();
		nbOccu = new HashMap<String,Integer>();
		for (Map.Entry mapentry : network.get().entrySet()) {
			nbOccu.put((String) mapentry.getKey(),0);
		 }
		for (Constraint c : network.getConstraints()) {
			for (String  s: c.getVars()) {
				if (nbOccu.containsKey(s)) {
					nbOccu.put(s, nbOccu.get(s)+1);
				}
				else
				{
					nbOccu.put(s, 1);
				}
			}
		}
		nbOccu = sortByComparator(nbOccu, false);
	}

	/**********************
	 * BACKTRACK UNE SOLUTION
	 *******************************************/

	/**
	 * Cherche une solution au réseau de contraintes
	 * 
	 * @return une assignation solution du réseau, ou null si pas de solution
	 */

	public Assignment searchSolution() {
		cptr = 1; // compteur de noeuds exploré

		this.assignment = new Assignment();
		Assignment sol = this.backtrack();
		return sol;
	}

	public Assignment searchSolutionFC() {
		cptr = 1; // compteur de noeuds exploré

		this.assignment = new Assignment();
		if (forwardCheking())
			return this.assignment;
		else
			return null;
	}

	/*
	 * La methode bactrack ci-dessous travaille directement sur l'attribut
	 * assignment. On peut aussi choisir de ne pas utiliser cet attribut et de créer
	 * plutot un objet Assignment local à searchSolution : dans ce cas il faut le
	 * passer en parametre de backtrack
	 */
	/**
	 * Exécute l'algorithme de backtrack à la recherche d'une solution en étendant
	 * l'assignation courante Utilise l'attribut assignment
	 * 
	 * @return la prochaine solution ou null si pas de nouvelle solution
	 */

	private Assignment backtrack() {
		if (this.assignment.getVars().size() == network.getVarNumber())
			return this.assignment; // solution trouvé

		String x = this.chooseVarMaxDegre();

		for (Object v : tri(network.getDom(x))) { // supposé trié
			cptr += 1;
			this.assignment.put(x, v);
			if (this.consistant(x)) {
				Assignment b = backtrack();
				if (b != null)
					return b;
			}
			this.assignment.remove(x);
		}

		return null;
	}

	private boolean forwardCheking() {
		if (this.assignment.getVars().size() == network.getVarNumber()) {
			//System.out.println(assignment);
			return true;
		} // solution trouvé
		String x = this.chooseVarMaxDegre();
		Map<String, List<Object>> oldDom = new HashMap<String, List<Object>>(network.get());
		for (int i = 0 ; i < network.getDom(x).size(); i++) { // supposé trié
			Object v = network.getDom(x).get(i);
			cptr += 1;
			if (propage(x, v)) {
				this.assignment.put(x, v);
				if (forwardCheking()) {
					return true;
				}
			}
			this.assignment.remove(x);
			network.get().clear();
			network.set(oldDom);

		}
		return false;
	}

	/**********************
	 * BACKTRACK TOUTES SOLUTIONS
	 *******************************************/

	/**
	 * Calcule toutes les solutions au réseau de contraintes
	 * 
	 * @return la liste des assignations solution
	 * 
	 */
	public List<Assignment> searchAllSolutions() {
		cptr = 1;
		solutions.clear();
		this.assignment.clear();
		backtrackAll();
		return solutions;
	}

	/**
	 * Exécute l'algorithme de backtrack à la recherche de toutes les solutions
	 * étendant l'assignation courante
	 * 
	 */
	private void backtrackAll() {
		if (this.assignment.getVars().size() == network.getVarNumber()) {
			this.solutions.add(this.assignment.clone());

		} else {
			String x = this.chooseVar();
			for (Object v : tri(this.network.getDom(x))) {
				this.cptr += 1;
				this.assignment.put(x, v);
				if (this.consistant(x)) {
					backtrackAll();
				}
				this.assignment.remove(x);
			}
		}
		return;
	}

	/**
	 * Retourne la prochaine variable à assigner étant donné assignment (qui doit
	 * contenir la solution partielle courante)
	 * 
	 * @return une variable non encore assignée
	 */
	private String chooseVar() {
		for (String var : network.getVars()) {
			if (!this.assignment.getVars().contains(var))
				return var;
		}
		return null;
	}

	private String chooseVarMaxDegre(){
		for (String var : nbOccu.keySet()) {
			if (!this.assignment.getVars().contains(var))
				return var;
		}
		return null;
	}

	private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
    {

        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Integer>>()
        {
            public int compare(Entry<String, Integer> o1,
                    Entry<String, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

	/**
	 * Fixe un ordre de prise en compte des valeurs d'un domaine
	 * 
	 * @param values une liste de valeurs
	 * @return une liste de valeurs
	 */
	private List<Object> tri(List<Object> values) {
		return values; // donc en l'état n'est pas d'une grande utilité !
	}

	/**
	 * Teste si l'assignation courante stokée dans l'attribut assignment est
	 * consistante, c'est à dire qu'elle ne viole aucune contrainte.
	 * 
	 * @param lastAssignedVar la variable que l'on vient d'assigner à cette étape
	 * @return vrai ssi l'assignment courante ne viole aucune contrainte
	 */
	private boolean consistant(String lastAssignedVar) {
		for (Constraint c : this.network.getConstraints()) {
			if (c.violation(this.assignment))
				return false;
		}
		return true;
	}

	private boolean propage(String x, Object v) {
		for (Constraint c : network.getConstraints()) {
			if (c.getVars().contains(x)) {
				List<String> tmp = new ArrayList<String>(c.getVars());
				tmp.remove(x);
				if (tmp.size() == 1) {
					List<Object> tuple1 = new ArrayList<Object>();

					List<Object> dom = new ArrayList<Object> (network.getDom(tmp.get(0)));
					for (Object o : dom) {
						if (c.getVars().indexOf(x)==0) {
							tuple1.add(v);
							tuple1.add(o);
						} else {
							tuple1.add(o);
							tuple1.add(v);
						}
						if (!c.exist(tuple1)) {
							network.getDom(tmp.get(0)).remove(o);
						}
						tuple1.clear();
					}
					if (network.getDom(tmp.get(0)).size() == 0)
						return false;
				}
				
			}

		}
		return true;
	}

}
