package arithlang;

import java.io.IOException;

import org.antlr.v4.Tool;
import org.antlr.v4.tool.ErrorType;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.LexerInterpreter;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.tool.Grammar;

import arithlang.Exp.Const;
import arithlang.Exp.DivExp;
import arithlang.Exp.SubExp;
import arithlang.Exp.MultExp;
import arithlang.Exp.AddExp;

public class Interpreter {
    public static void main(String[] args) {
	System.out.println("Type expression to evaluate and press the enter key, e.g. 3 * 100 + 84 / (279 - 277)");
	System.out.println("Press Ctrl + C to exit.");
	Evaluator eval = new Evaluator();
	Printer printer = new Printer();
	Reader reader = new Reader();
	testParserGen();
	try {
	    while (true) { // Read-Eval-Print-Loop (also known as REPL)
		ParseTree ast = reader.read();
		Exp e = new AddExp(new MultExp(new Const(3), new Const(100)),
			new DivExp(new Const(84), new SubExp(new Const(279),
				new Const(277))));
		Value val = eval.valueOf(e);
		printer.print(val);
	    }
	} catch (IOException e) {
	    System.out.println("Error reading input.");
	}
    }

    private static void testParserGen() {
	System.out.println("Working Directory = "
		+ System.getProperty("user.dir"));
	Tool parser = new Tool(new String[] { "./build/arithlang/ArithLang.g" });
	try {
	    parser.processGrammarsOnCommandLine();
	} finally {
	    if (parser.log) {
		try {
		    String logname = parser.logMgr.save();
		    System.out.println("wrote " + logname);
		} catch (IOException ioe) {
		    parser.errMgr.toolError(ErrorType.INTERNAL_ERROR, ioe);
		}
	    }
	}
    }
}
