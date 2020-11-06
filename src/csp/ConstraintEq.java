import java.io.BufferedReader;

/**
 * Pour manipuler des contraintes d'égalité
 *
 */
public class ConstraintEq extends Constraint {

	public ConstraintEq(BufferedReader in) throws Exception {
		super(in);
	}

	/**
	 * Teste si une assignation viole la contrainte. La violation "classique" n'est
	 * avérée que si : - toutes les variables de la contrainte ont une valeur
	 * associée dans l'assignation testée - au moin deux variable on une assignation
	 * différente.
	 * 
	 * @param a l'assignation à tester
	 * @return vrai ssi l'assignation viole la contrainte
	 */
	public boolean violation(Assignment a) {
		// - toutes les variables de la contrainte ont une valeur associée dans
		// l'assignation testée
		for (String var : this.varList)
			if (!a.getVars().contains(var))
				return false;

		// - au moin deux variable on une assignation différente.
		for (int i = 0; i < this.varList.size(); i++)
			for (int j = i + 1; j < this.varList.size(); j++)
				if (!a.get(this.varList.get(i)).equals(a.get(this.varList.get(j))))
					return true;

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Constraint#toString()
	 */
	public String toString() {
		return "\n\t Equal " + super.toString();
	}

}
