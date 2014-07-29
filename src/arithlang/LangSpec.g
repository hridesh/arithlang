parser grammar T;

program : 
		expression;

expression : 
			Identifier { System.out.println("Identifier"); }
			| Number 
        	| OpenParen expression CloseParen
          	| expression Plus expression 
          	| expression Minus expression 
          	| expression Mult expression
          	| expression Div expression
          	;
          