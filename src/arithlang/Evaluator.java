package arithlang;
import static arithlang.AST.*;
import static arithlang.Value.*;

import java.util.List;
import java.lang.Math;

public class Evaluator implements Visitor<Value> {
	
	Printer.Formatter ts = new Printer.Formatter();
	
	Value valueOf(Program p) {
		// Value of a program in this language is the value of the expression
		return (Value) p.accept(this);
	}
	
	@Override
	public Value visit(AddExp e) {
		List<Exp> operands = e.all();
		double result = 0;
		for(Exp exp: operands) {
			NumVal intermediate = (NumVal) exp.accept(this); // Dynamic type-checking
			result += intermediate.v(); //Semantics of AddExp in terms of the target language.
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(NumExp e) {
		return new NumVal(e.v());
	}

	@Override
	public Value visit(DivExp e) {
		List<Exp> operands = e.all();
		NumVal lVal = (NumVal) operands.get(0).accept(this);
		double result = lVal.v(); 
		for(int i=1; i<operands.size(); i++) {
			NumVal rVal = (NumVal) operands.get(i).accept(this);
			result = result / rVal.v();
		}
		return new NumVal(result);
	}
	// added exponential exp
	@Override
	public Value visit(ExpExp e) {
		/*These cases felled
		 // Examples:
		 //    (** 3 4)
		 //    [java] $ 64 // [x]
		 //    (** 3 2 4)
		 //    [java] $ 65536 // [ ]
		 //    (** 8 0)
		 //    [java] $ 0   // [x]
		//     expected: 81, 6561, 1		 
		 * 
		 * 
		 */

		List<Exp> operands = e.all();
		NumVal lVal = (NumVal) operands.get(0).accept(this); // get left most value
		double result = lVal.v(); // init result as left most value
		for(int i=1; i<operands.size(); i++) {
			NumVal rVal = (NumVal) operands.get(i).accept(this);
			// value i+1 to the right of result to compute next
			result = Math.pow(result, rVal.v());
		}
		return new NumVal(result);
		// List<Exp> operands = e.all();
		// double result = 1;
		// for(Exp exp: operands) {
		// 	NumVal intermediate = (NumVal) exp.accept(this); // Dynamic type-checking
		// 	result = Math.pow(intermediate.v(), result); //Semantics of ExpExp.
		// }
		// return new NumVal(result);
	}
	@Override
	public Value visit(MultExp e) {
		List<Exp> operands = e.all();
		double result = 1;
		for(Exp exp: operands) {
			NumVal intermediate = (NumVal) exp.accept(this); // Dynamic type-checking
			result *= intermediate.v(); //Semantics of MultExp.
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(Program p) {
		return (Value) p.e().accept(this);
	}

	@Override
	public Value visit(SubExp e) {
		List<Exp> operands = e.all();
		NumVal lVal = (NumVal) operands.get(0).accept(this);
		double result = lVal.v();
		for(int i=1; i<operands.size(); i++) {
			NumVal rVal = (NumVal) operands.get(i).accept(this);
			result = result - rVal.v();
		}
		return new NumVal(result);
	}

}
