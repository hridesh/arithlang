package arithlang;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileReader;

import org.antlr.runtime.RecognitionException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.LexerInterpreter;
import org.antlr.v4.runtime.ParserInterpreter;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.tool.Grammar;
import org.antlr.v4.tool.LexerGrammar;

public class Reader {
	ParseTree read() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("$ ");
		String program = br.readLine();
		final LexerInterpreter lexEngine = lg.createLexerInterpreter(new ANTLRInputStream(program));
		final CommonTokenStream tokens = new CommonTokenStream(lexEngine);
		final ParserInterpreter parser = g.createParserInterpreter(tokens);

		//Temp.
		parser.addParseListener(new ParseTreeListener() {
			public void visitTerminal(TerminalNode node) { 
				
			}
			public void visitErrorNode(ErrorNode node) { 
				
			}
			public void enterEveryRule(ParserRuleContext ctx) { 
		    	System.out.println("Listener - start" + ctx.getRuleIndex());
		    }
			public void exitEveryRule(ParserRuleContext ctx) { 
		    	System.out.println("Listener - exit" + ctx.getRuleIndex());
		    }
		});
		final ParseTree t = parser.parse(g.rules.get(startRule).index);
		System.out.println("parse tree: "+t.toStringTree(parser));
		
		t.accept(new ParseTreeVisitor<arithlang.Value>(){
			public Value visit(ParseTree tree) {
				System.out.println("visit: "+tree.toStringTree(parser));
				return new arithlang.Value.Unit();
			}
			public Value visitChildren(RuleNode node) { 
				System.out.println("Rule node: ");
				System.out.println("visitChildren: " + node.toStringTree(parser));
				int childCount = node.getChildCount();
				System.out.println("Num children: " + childCount);
				for(int i = 0; i < childCount; i++) {
					node.getChild(i).accept(this);
				}
				return new arithlang.Value.Unit();
			}
			public Value visitTerminal(TerminalNode node) { 
				System.out.println("visitTerminal: " + node.toStringTree(parser));
				
				return new arithlang.Value.Unit();
			}
			public Value visitErrorNode(ErrorNode node) { 
				System.out.println("visitErrorNode: " + node.toStringTree(parser));
				
				return new arithlang.Value.Unit();
			}
		});
		return t;
	}
	
	public static final LexerGrammar lg = createLexicalGrammar(); 
	private static LexerGrammar createLexicalGrammar(){
		LexerGrammar lg = null;
		try{
			lg = new LexerGrammar(readFile("build/arithlang/LexSpec.g"));
		} catch (RecognitionException e) {
			System.out.println("Error in Lexical Specification\n" + e);
		}		
		return lg;
	}
	
	public static final String startRule = "program";
	public static final Grammar g = createGrammar();
	private static Grammar createGrammar() {
		Grammar g = null;
		try{
			g = new Grammar(readFile("build/arithlang/LangSpec.g"), Reader.lg);
		} catch (RecognitionException e) {
			System.out.println("Error in Grammar Specification\n" + e);
		}
		return g;
	}
	
	private static String readFile(String fileName) {
		try {
			try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
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
		}
		return "";
	}
}
