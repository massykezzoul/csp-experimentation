
import java.util.ArrayList;
import java.util.List;

/**
 * Solveur : permet de résoudre un problème de contrainte par Backtrack : 
 * 	Calcul d'une solution, 
 * 	Calcul de toutes les solutions
 *
 */
public class CSP {

		private Network network;		// le réseau à résoudre
		private List<Assignment> solutions; 	// les solutions du réseau (résultat de searchAllSolutions)
		private Assignment assignment;		// l'assignation courante (résultat de searchSolution)
		int cptr;			       	// le compteur de noeuds explorés

		
		/**
		 * Crée un problème de résolution de contraintes pour un réseau donné
		 * 
		 * @param r le réseau de contraintes à résoudre
		 */
		public CSP(Network r) {
			network = r;
			solutions = new ArrayList<Assignment>();
			assignment = new Assignment();

		}
		
		
		
		/********************** BACKTRACK UNE SOLUTION *******************************************/
		 
		/**
		 * Cherche une solution au réseau de contraintes
		 * 
		 * @return une assignation solution du réseau, ou null si pas de solution
		 */
		
		public Assignment searchSolution() {
			cptr=1; // compteur de noeuds exploré
			
			this.assignment = new Assignment();
			Assignment sol = this.backtrack();
			return sol;
		}

		/* La methode bactrack ci-dessous travaille directement sur l'attribut assignment. 
		 * On peut aussi choisir de ne pas utiliser cet attribut et de créer plutot un objet Assignment local à searchSolution : 
		 * dans ce cas il faut le passer en parametre de backtrack
		 */
		/**
		 * Exécute l'algorithme de backtrack à la recherche d'une solution en étendant l'assignation courante
		 * Utilise l'attribut assignment
		 * @return la prochaine solution ou null si pas de nouvelle solution
		 */
		
		private Assignment backtrack() {
			if (this.assignment.getVars().size() == network.getVarNumber() ) 
				return this.assignment; // solution trouvé

			String x = this.chooseVar();

			for (Object v : tri(network.getDom(x))) { // supposé trié
				cptr += 1;
				this.assignment.put(x,v);
				if (this.consistant(x)){
					Assignment b = backtrack();
					if (b != null) 
						return b;
				}
				this.assignment.remove(x);
			}
			
		    return null;
		}
		
		
		
		/********************** BACKTRACK TOUTES SOLUTIONS *******************************************/
		
		/**
		 * Calcule toutes les solutions au réseau de contraintes
		 * 
		 * @return la liste des assignations solution
		 * 
		 */
		public List<Assignment> searchAllSolutions(){
			cptr=1;
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
				
			}
			else {
				String x = this.chooseVar();
				for(Object v : tri(this.network.getDom(x))) {
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
		 * Retourne la prochaine variable à assigner étant donné assignment (qui doit contenir la solution partielle courante)
		 *  
		 * @return une variable non encore assignée
		 */
		private String chooseVar() {
			for (String var : network.getVars()) {
				if (! this.assignment.getVars().contains(var))
					return var;
			}
		    return null;
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
		 * Teste si l'assignation courante stokée dans l'attribut assignment est consistante, c'est à dire qu'elle
		 * ne viole aucune contrainte.
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
				
}