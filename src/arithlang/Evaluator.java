package arithlang;
import static arithlang.Exp.*;
import static arithlang.Value.*;

public class Evaluator {
	Value valueOf(Const exp) {
		return new Int(exp.v());
	}
	Value valueOf(Plus exp) {
		Int lVal = (Int) valueOf(exp.left()); // Dynamic type-checking
		Int rVal = (Int) valueOf(exp.right()); // Dynamic type-checking
		return new Int(lVal.v() + rVal.v());
	}
	Value valueOf(Minus exp) {
		Int lVal = (Int) valueOf(exp.left());
		Int rVal = (Int) valueOf(exp.right());
		return new Int(lVal.v() - rVal.v());
	}
	Value valueOf(Div exp) {
		Int lVal = (Int) valueOf(exp.left());
		Int rVal = (Int) valueOf(exp.right());
		return new Int(lVal.v() / rVal.v());
	}
	Value valueOf(Mult exp) {
		Int lVal = (Int) valueOf(exp.left());
		Int rVal = (Int) valueOf(exp.right());
		return new Int(lVal.v() * rVal.v());
	}
	Value valueOf(ArithExp exp) {
		if (exp instanceof Const)
			return valueOf((Const) exp);
		else if (exp instanceof Plus)
			return valueOf((Plus) exp);
		else if (exp instanceof Minus)
			return valueOf((Minus) exp);
		else if (exp instanceof Div)
			return valueOf((Div) exp);
		else if (exp instanceof Mult)
			return valueOf((Mult) exp);
		return new DynamicError();
	}
	Value valueOf(Exp exp) {
		if (exp instanceof ArithExp)
			return valueOf((ArithExp) exp);
		return new DynamicError();
	}
}
