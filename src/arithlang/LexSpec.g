lexer grammar LexicalSpecification;

Number : 
	DIGIT 
	| (DIGIT_NOT_ZERO DIGIT+); 

// Tokens for operators	
Plus : '+' ; 
Minus : '-' ;
Mult : '*' ;
Div : '/' ;
OpenParen : '(' ;
CloseParen : ')' ;

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