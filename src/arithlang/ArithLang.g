grammar ArithLang;
 
 // Grammar of this Programming Language
 //  - grammar rules start with lowercase
 program : 
		exp
		;

 exp : 
		varexp 
		| numexp 
        | addexp 
        | subexp 
        | multexp 
        | divexp 
        ;
 
 varexp  : 
 		Identifier 
 		;
 
 numexp :
 		Number 
  		| '-' Number
  		| Number Dot Number
  		| '-' Number Dot Number
  		;		
  
 addexp :
 		'(' '+'
 		    exp 
 		    (exp)+ 
 		    ')' 
 		;
 
 subexp :  
 		'(' '-' 
 		    exp 
 		    (exp)+ 
 		    ')' 
 		;

 multexp : 
 		'(' '*' 
 		    exp 
 		    (exp)+ 
 		    ')' 
 		;
 
 divexp  : 
 		'(' '/' 
 		    exp 
 		    (exp)+ 
 		    ')' 
 		;


 // Lexical Specification of this Programming Language
 //  - lexical specification rules start with uppercase
 
 Dot : '.' ;

 Number : DIGIT+ ;

 Identifier :   Letter LetterOrDigit*;

 Letter :   [a-zA-Z$_]
	|   ~[\u0000-\u00FF\uD800-\uDBFF] 
		{Character.isJavaIdentifierStart(_input.LA(-1))}?
	|   [\uD800-\uDBFF] [\uDC00-\uDFFF] 
		{Character.isJavaIdentifierStart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}? ;

 LetterOrDigit: [a-zA-Z0-9$_]
	|   ~[\u0000-\u00FF\uD800-\uDBFF] 
		{Character.isJavaIdentifierPart(_input.LA(-1))}?
	|    [\uD800-\uDBFF] [\uDC00-\uDFFF] 
		{Character.isJavaIdentifierPart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?;

 fragment DIGIT: ('0'..'9');

 AT : '@';
 ELLIPSIS : '...';
 WS  :  [ \t\r\n\u000C]+ -> skip;
 Comment :   '/*' .*? '*/' -> skip;
 Line_Comment :   '//' ~[\r\n]* -> skip;