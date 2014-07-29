package arithlang;

/**
 * This class hierarchy represents programs in the abstract syntax tree
 * manipulated by this interpreter.
 * 
 * @author hridesh
 * 
 */
public class Program {
    Exp _e;

    public Program(Exp e) {
	_e = e;
    }

    public Exp e() {
	return _e;
    }
}
