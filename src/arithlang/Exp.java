package arithlang;

import java.util.ArrayList;
import java.util.List;

/**
 * This class hierarchy represents expressions in the abstract syntax tree
 * manipulated by this interpreter.
 * 
 * @author hridesh
 * 
 */
public interface Exp {
    static class Error implements Exp {
    }

    static abstract class ArithExp implements Exp {
    }

    static class VarExp implements Exp {
	String _name;

	public VarExp(String name) {
	    _name = name;
	}

	public String name() {
	    return _name;
	}
    }

    static class Const extends ArithExp {
	int _val;

	public Const(int v) {
	    _val = v;
	}

	public int v() {
	    return _val;
	}
    }

    static abstract class CompoundArithExp extends ArithExp {
	List<ArithExp> _rest;
	
	public CompoundArithExp() {
	    _rest = new ArrayList<ArithExp>();
	}

	public CompoundArithExp(ArithExp fst) {
	    _rest = new ArrayList<ArithExp>();
	    _rest.add(fst);
	}

	public CompoundArithExp(ArithExp fst, List<ArithExp> rest) {
	    _rest = new ArrayList<ArithExp>();
	    _rest.add(fst);
	    _rest.addAll(rest);
	}

	public CompoundArithExp(ArithExp fst, ArithExp second) {
	    _rest = new ArrayList<ArithExp>();
	    _rest.add(fst);
	    _rest.add(second);
	}

	public ArithExp fst() {
	    return _rest.get(0);
	}

	public ArithExp snd() {
	    return _rest.get(1);
	}

	public List<ArithExp> all() {
	    return _rest;
	}
	
	public void add(ArithExp e) {
	    _rest.add(e);
	}
    }

    static class AddExp extends CompoundArithExp {
	public AddExp(ArithExp fst) {
	    super(fst);
	}

	public AddExp(ArithExp fst, List<ArithExp> rest) {
	    super(fst, rest);
	}

	public AddExp(ArithExp left, ArithExp right) {
	    super(left, right);
	}
    }

    static class SubExp extends CompoundArithExp {

	public SubExp(ArithExp fst) {
	    super(fst);
	}

	public SubExp(ArithExp fst, List<ArithExp> rest) {
	    super(fst, rest);
	}

	public SubExp(ArithExp left, ArithExp right) {
	    super(left, right);
	}
    }

    static class DivExp extends CompoundArithExp {
	public DivExp(ArithExp fst) {
	    super(fst);
	}

	public DivExp(ArithExp fst, List<ArithExp> rest) {
	    super(fst, rest);
	}

	public DivExp(ArithExp left, ArithExp right) {
	    super(left, right);
	}
    }

    static class MultExp extends CompoundArithExp {
	public MultExp(ArithExp fst) {
	    super(fst);
	}

	public MultExp(ArithExp fst, List<ArithExp> rest) {
	    super(fst, rest);
	}

	public MultExp(ArithExp left, ArithExp right) {
	    super(left, right);
	}
    }
}
