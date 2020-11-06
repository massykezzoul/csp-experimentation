import java.io.BufferedReader;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Pour manipuler des contraintes en intension
 *
 */
public class ConstraintExp extends Constraint {
    protected String expression;
    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

    private static void enginePut(ScriptEngine e, String var, Object value) {
        try {
            e.put(var, Integer.parseInt(value.toString()));
        } catch (NumberFormatException nfe) {
            try {
                e.put(var, Float.parseFloat(value.toString()));
            } catch (NumberFormatException nfe2) {
                if (value.toString().equalsIgnoreCase("false") || value.toString().equalsIgnoreCase("true")) {
                    e.put(var, Boolean.parseBoolean(value.toString()));
                } else {
                    e.put(var, value);
                }
            }
        }
    }

    public ConstraintExp(BufferedReader in) throws Exception {
        super(in);
        this.expression = in.readLine(); // lecture de l'exepression booléenne
    }

    /**
     * Teste si une assignation viole la contrainte. La violation "classique" n'est
     * avérée que si : - toutes les variables de la contrainte ont une valeur
     * associée dans l'assignation testée - si l'assignation ne satisfait pas
     * l'expression booléenne
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

        boolean result = false;
        try {
            for (String var : this.varList)
                enginePut(engine, var, a.get(var));
            result = (boolean) engine.eval(this.expression);
        } catch (ScriptException e) {
            System.err.println("probleme dans: " + this.expression);
        }

        return !result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see Constraint#toString()
     */
    public String toString() {
        return "\n\t Exp " + super.toString() + " : " + this.expression;
    }
}