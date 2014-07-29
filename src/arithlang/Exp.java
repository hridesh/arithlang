package arithlang;

public interface Exp {
	static abstract class ArithExp implements Exp {}
	static class Const extends ArithExp {
	    int _val; 
	    public Const(int v) { _val = v; } 
	    public int v() { return _val; }
	}
	static abstract class BinArithExp extends ArithExp {
	    ArithExp left, right;
	    public BinArithExp(ArithExp left, ArithExp right) { this.left = left; this.right = right; }
	    public ArithExp left() { return left; }
	    public ArithExp right() { return right; }
	}
	static class Plus extends BinArithExp {
	    public Plus(ArithExp left, ArithExp right) { super(left,right); }
	}
	static class Minus extends BinArithExp {
	    public Minus(ArithExp left, ArithExp right) { super(left,right); }
	}
	static class Div extends BinArithExp {
	    public Div(ArithExp left, ArithExp right) { super(left,right); }
	}
	static class Mult extends BinArithExp {
	    public Mult(ArithExp left, ArithExp right) { super(left,right); }
	}
}
