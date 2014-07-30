package arithlang;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.LexerInterpreter;
import org.antlr.v4.runtime.ParserInterpreter;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.tool.Grammar;
import org.antlr.v4.tool.LexerGrammar;

import arithlang.AST.*;

public class Reader {
	
	private static final boolean DEBUG = false;
	
	Program read() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("$ ");
		String program = br.readLine();
		return parse(program);
	}
	
	Program parse(String program) {
		final LexerInterpreter lexEngine = lg.createLexerInterpreter(new ANTLRInputStream(program));
		final CommonTokenStream tokens = new CommonTokenStream(lexEngine);
		final ParserInterpreter parser = g.createParserInterpreter(tokens);
		final ParseTree t = parser.parse(g.rules.get(startRule).index);
		if(DEBUG) System.out.println("parse tree: " + t.toStringTree(parser));

		// We know that top-level node is a program, and for this language it
		// contains a single expression, so we just convert the enclosing
		// expression's parse tree to the AST used by this interpreter.
		Exp exp = t.getChild(0).accept(new TreeToExpConverter(parser));
		return new Program(exp);
	}

	public static final LexerGrammar lg = createLexicalGrammar();
	private static LexerGrammar createLexicalGrammar() {
		LexerGrammar lg = null;
		try {
			lg = new LexerGrammar(readFile("build/arithlang/ArithLang.g"));
		} catch (RecognitionException e) {
			System.out.println("Error in Lexical Specification\n" + e);
			System.exit(-1); // These are fatal errors
		}
		return lg;
	}


	//Following are ANTLR constants - Change them if you change the Grammar.
	public static final String startRule = "program";
	public static final int program = 0, exp = 1, varexp = 2, numexp = 3,
			addexp = 4, subexp = 5, multexp = 6, divexp = 7;

	public static final Grammar g = createGrammar();
	private static Grammar createGrammar() {
		Grammar g = null;
		try {
			g = new Grammar(readFile("build/arithlang/ArithLang.g"), Reader.lg);
		} catch (RecognitionException e) {
			System.out.println("Error in Grammar Specification\n" + e);
			System.exit(-1); // These are fatal errors
		}
		return g;
	}

	private static String readFile(String fileName) {
		try {
			try (BufferedReader br = new BufferedReader(
					new FileReader(fileName))) {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					sb.append(System.lineSeparator());
					line = br.readLine();
				}
				return sb.toString();
			}
		} catch (IOException e) {
			System.out.println("Could not open file " + fileName);
			System.exit(-1); // These are fatal errors
		}
		return "";
	}

	/**
	 * This data adapter takes the parse tree provided by ANTLR and converts it
	 * to the abstract syntax tree representation used by the rest of this
	 * interpreter. This class needs to be adapted for each new abstract syntax
	 * tree node.
	 * 
	 * @author hridesh
	 * 
	 */
	class TreeToExpConverter implements ParseTreeVisitor<AST.Exp> {

		ParserInterpreter parser;

		TreeToExpConverter(ParserInterpreter parser) {
			this.parser = parser;
		}

		public AST.Exp visit(ParseTree tree) {
			System.out.println("visit: " + tree.toStringTree(parser));
			return null;
		}

		public AST.Exp visitChildren(RuleNode node) {			
			switch(node.getRuleContext().getRuleIndex()){
				case exp: return visitChildrenHelper(node).get(0); 
				case varexp: System.out.println(visitChildrenHelper(node)); break; 
				case numexp: return visitChildrenHelper(node).get(0);
				case addexp: return new AST.AddExp(visitChildrenHelper(node)); 
				case subexp: return new AST.SubExp(visitChildrenHelper(node)); 
				case multexp: return new AST.MultExp(visitChildrenHelper(node));
				case divexp: return new AST.DivExp(visitChildrenHelper(node));
				default: 
					System.out.println("Conversion error (from parse tree to AST): found unknown/unhandled case " + node.getRuleContext().getRuleIndex());
			}
			return null;
		}

		public AST.Exp visitTerminal(TerminalNode node) {
			String s = node.toStringTree(parser);
			if (isConcreteSyntaxToken(s))
				return null;

			try {
				int v = Integer.parseInt(s);
				return new AST.Const(v);
			} catch (NumberFormatException e) {
			}
			// Error case - generally means a new Token is added in the grammar
			// and not handled in
			// the filterTokens method above, or a new value type is added.
			System.out.println("visitTerminal: Illegal terminal " + s);
			return new AST.Error();
		}

		public AST.Exp visitErrorNode(ErrorNode node) {
			System.out.println("visitErrorNode: " + node.toStringTree(parser));
			return new AST.Error();
		}

		private List<AST.Exp> visitChildrenHelper(RuleNode node) {
			int childCount = node.getChildCount();
			List<AST.Exp> results = new ArrayList<AST.Exp>(); 
			if(DEBUG) System.out.println("visitChildren(RuleNode node), node = "
					+ node.toStringTree(parser) + ", #children = "
					+ childCount);
			for (int i = 0; i < childCount; i++) {
				AST.Exp result = node.getChild(i).accept(this);
				if(result!=null) results.add(result);
			}
			return results;
		}
				
		/**
		 * This method filters out those Tokens that are part of the concrete
		 * syntax and thus are not represented in the abstract syntax.
		 * 
		 * @param s - string representation of the token
		 * @return true if the token is part of concrete syntax.
		 */
		private boolean isConcreteSyntaxToken(String s) {
			if (s.equals("(") || s.equals(")") || s.equals("+")
					|| s.equals("-") || s.equals("*") || s.equals("/"))
				return true;
			return false;
		}
	}
}
