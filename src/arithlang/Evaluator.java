package arithlang;
import static arithlang.AST.*;
import static arithlang.Value.*;

public class Evaluator {
	Value valueOf(Program p) {
		// Value of a program in this language is the value of the expression
		return valueOf(p.e());
	}
	Value valueOf(Const exp) {
		return new Int(exp.v());
	}
	Value valueOf(AddExp exp) {
		Int lVal = (Int) valueOf(exp.fst()); // Dynamic type-checking
		Int rVal = (Int) valueOf(exp.snd()); // Dynamic type-checking
		return new Int(lVal.v() + rVal.v());
	}
	Value valueOf(SubExp exp) {
		Int lVal = (Int) valueOf(exp.fst());
		Int rVal = (Int) valueOf(exp.snd());
		return new Int(lVal.v() - rVal.v());
	}
	Value valueOf(DivExp exp) {
		Int lVal = (Int) valueOf(exp.fst());
		Int rVal = (Int) valueOf(exp.snd());
		return new Int(lVal.v() / rVal.v());
	}
	Value valueOf(MultExp exp) {
		Int lVal = (Int) valueOf(exp.fst());
		Int rVal = (Int) valueOf(exp.snd());
		return new Int(lVal.v() * rVal.v());
	}
	Value valueOf(ArithExp exp) {
		if (exp instanceof Const)
			return valueOf((Const) exp);
		else if (exp instanceof AddExp)
			return valueOf((AddExp) exp);
		else if (exp instanceof SubExp)
			return valueOf((SubExp) exp);
		else if (exp instanceof DivExp)
			return valueOf((DivExp) exp);
		else if (exp instanceof MultExp)
			return valueOf((MultExp) exp);
		return new DynamicError();
	}
	Value valueOf(Exp exp) {
		if (exp instanceof ArithExp)
			return valueOf((ArithExp) exp);
		return new DynamicError();
	}
}
