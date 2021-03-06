package pt.maisis.search.interpreter;

import bsh.EvalError;

import java.util.Map;
import java.util.Iterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Interpretador que permite fazer a evaluation de expressões.
 * Esta classe simplesmente delega os pedidos de evaluation
 * para a framework beanshell.
 * Anteriormente era utilizada a framework instantj, no entanto,
 * por questões de flexibilidade optou-se por mudar para a
 * framework beanshell.
 *
 * @version 1.0
 */
public class Interpreter {

    private static Log log = LogFactory.getLog(Interpreter.class);
    
    private Interpreter() {
    }

    /**
     * Faz a evaluation dos dados statements contidos
     * na string.
     */
    public static Object eval(String statements) {
        return Interpreter.eval(statements, null);
    }

    /**
     * Faz a evaluation dos dados statements contidos
     * na string, permitindo a utilizacão da informacão
     * do dado contexto.
     */
    public static Object eval(String statements, Map context) {
        bsh.Interpreter bsh = new bsh.Interpreter();

        try {
            if (context != null) {
                Iterator entries = context.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry entry = (Map.Entry) entries.next();
                    bsh.set((String) entry.getKey(), entry.getValue());
                }
            }

            return bsh.eval(statements);
        } catch (EvalError e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
