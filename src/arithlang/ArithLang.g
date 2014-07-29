grammar ArithLang;
 
 // Grammar of this Programming Language
 //  - grammar rules start with lowercase
 program returns [ Program p ] : 
		exp
 		{ p = new Program($exp.e); } 
		;

 exp returns [ Exp e ] : 
		varexp { e = $varexp.e; }
		| numexp { e = $numexp.e; }
        | addexp { e = $addexp.e; }
        | subexp { e = $subexp.e; }
        | multexp { e = $multexp.e; }
        | divexp { e = $divexp.e; }
        ;
 
 varexp returns [ Exp.VarExp e ] : 
 		Identifier 
 		{ e = new Exp.VarExp($Identifier.text); } 
 		;
 
 numexp returns [ Exp.ConstExp e ] :
 		Number 
 		{ e = new Exp.Const(Integer.parseInt(s)); } ;
  
 addexp returns [ Exp.AddExp e ]  
		@init {e = new Exp.AddExp();} :
 		'(' '+' 
 		    fst=exp { e.add($fst.e); }
 		    (rest=exp { e.add($rest.e); } )+ 
 		    ')' 
 		;
 
 subexp returns [ Exp.SubExp e ]  
		@init {e = new Exp.SubExp();} :
 		'(' '-' 
 		    fst=exp { e.add($fst.e); }
 		    (rest=exp { e.add($rest.e); } )+ 
 		    ')' 
 		;

 multexp returns [ Exp.MultExp e ] 
		@init {e = new Exp.MultExp();} :
 		'(' '*' 
 		    fst=exp { e.add($fst.e); }
 		    (rest=exp { e.add($rest.e); } )+ 
 		    ')' 
 		;
 
 divexp returns [ Exp.DivExp e ] 
		@init {e = new Exp.DivExp();} :
 		'(' '/' 
 		    fst=exp { e.add($fst.e); }
 		    (rest=exp { e.add($rest.e); } )+ 
 		    ')' 
 		;


 // Lexical Specification of this Programming Language
 //  - lexical specification rules start with uppercase
 
 Number : 
	DIGIT 
	| (DIGIT_NOT_ZERO DIGIT+); 

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
 fragment DIGIT_NOT_ZERO: ('1'..'9');

 AT : '@';
 ELLIPSIS : '...';
 WS  :  [ \t\r\n\u000C]+ -> skip;
 Comment :   '/*' .*? '*/' -> skip;
 Line_Comment :   '//' ~[\r\n]* -> skip;