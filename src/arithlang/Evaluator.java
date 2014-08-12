package arithlang;
import static arithlang.AST.*;
import static arithlang.Value.*;

import java.util.List;

import arithlang.AST.AddExp;
import arithlang.AST.Const;
import arithlang.AST.DivExp;
import arithlang.AST.ErrorExp;
import arithlang.AST.MultExp;
import arithlang.AST.Program;
import arithlang.AST.SubExp;
import arithlang.AST.VarExp;
import arithlang.AST.Visitor;

public class Evaluator implements Visitor<Value> {
	
	Value valueOf(Program p) {
		// Value of a program in this language is the value of the expression
		return (Value) p.accept(this);
	}
	
	@Override
	public Value visit(AddExp e) {
		List<Exp> operands = e.all();
		int result = 0;
		for(Exp exp: operands) {
			Int intermediate = (Int) exp.accept(this); // Dynamic type-checking
			result += intermediate.v(); //Semantics of AddExp in terms of the target language.
		}
		return new Int(result);
	}

	@Override
	public Value visit(Const e) {
		return new Int(e.v());
	}

	@Override
	public Value visit(DivExp e) {
		List<Exp> operands = e.all();
		Int lVal = (Int) operands.get(0).accept(this);
		int result = lVal.v(); 
		for(int i=1; i<operands.size(); i++) {
			Int rVal = (Int) operands.get(i).accept(this);
			result = result / rVal.v();
		}
		return new Int(result);
	}

	@Override
	public Value visit(ErrorExp e) {
		return new Value.DynamicError("Encountered an error expression");
	}

	@Override
	public Value visit(MultExp e) {
		List<Exp> operands = e.all();
		Int lVal = (Int) operands.get(0).accept(this);
		Int rVal = (Int) operands.get(1).accept(this);
		return new Int(lVal.v() * rVal.v());
	}

	@Override
	public Value visit(Program p) {
		return (Value) p.e().accept(this);
	}

	@Override
	public Value visit(SubExp e) {
		List<Exp> operands = e.all();
		Int lVal = (Int) operands.get(0).accept(this);
		int result = lVal.v();
		for(int i=1; i<operands.size(); i++) {
			Int rVal = (Int) operands.get(i).accept(this);
			result = result - rVal.v();
		}
		return new Int(result);
	}

	@Override
	public Value visit(VarExp e) {
		return new Int(42); // All variables have value 42 in this language.
	}	

}
