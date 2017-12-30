grammar ArithLang;

 // Grammar of this Programming Language
 //  - grammar rules start with lowercase
 //  - this is a comment. 
 
 // This is an example of a production rule in its simplified form.
 // program : exp ; 
 // 
 //
 // The rule above in its full form, where actions enclosed in { }
 // are used to construct abstract syntax tree (AST) nodes. 
 program returns [Program ast] :   
		e=exp { $ast = new Program($e.ast); }
		;
//
// In the rule above, the form "returns [Program ast]" says that this 
// rule produces an object of type Program and other rules can access that 
// produced object using the name ast. If that rule is the start rule, which
// is the case here, then ast is the object returned by parsing the program.
// In the rule above, the form "e=exp" should be read as let us called this 
// non-terminal "e". Furthermore, the form { } is an action that runs 
// when the parser is successful in demonstrating that a string belonging to
// the language was the input. For instance, in the rule above when the parser
// is successful in demonstrating that it has parsed an expression, the action
// "{ $ast = new Program($e.ast); } runs that creates a new object Program 
// using the object produced by the rule for non-terminal "e".
//

// The following is another example of a production rule in its simplified form.
// exp : 
//     numexp
//     | addexp
//     | subexp
//     | multexp
//     | divexp
//
// The rule above in its full form, where actions are enclosed in { }
//
 exp returns [Exp ast]: 
		n=numexp { $ast = $n.ast; }
        | a=addexp { $ast = $a.ast; }
        | s=subexp { $ast = $s.ast; }
        | m=multexp { $ast = $m.ast; }
        | d=divexp { $ast = $d.ast; }
        ;
 
 // The actions { $ast = $n.ast; } means that this rule passed through 
 // the object that it received from its child rule.
  
// The following is another example of a production rule in its simplified form.
// numexp : 
//         Number 
//       | '-' Number
//       | Number Dot Number 
//       | '-' Number Dot Number 
//       ;
//
// The rule above in its full form, where actions are enclosed in { }
// 
 numexp returns [NumExp ast]:
 		n0=Number { $ast = new NumExp(Integer.parseInt($n0.text)); } 
  		| '-' n0=Number { $ast = new NumExp(-Integer.parseInt($n0.text)); }
  		| n0=Number Dot n1=Number { $ast = new NumExp(Double.parseDouble($n0.text+"."+$n1.text)); }
  		| '-' n0=Number Dot n1=Number { $ast = new NumExp(Double.parseDouble("-" + $n0.text+"."+$n1.text)); }
  		;		

//
// The variable access syntax $n0.text is ANTLR's syntax for obtaining the string
// that is parsed by the rule named n0.
//
  
// The following is another example of a production rule in its simplified form.
// addexp :
//        `('  '+'  
//             exp 
//             ( exp )+ 
//         ')' 
//        ; 
//
// The rule above in its full form, where actions are enclosed in { }
// 
 addexp returns [AddExp ast]
        locals [ArrayList<Exp> list]
 		@init { $list = new ArrayList<Exp>(); } :
 		'(' '+'
 		    e=exp { $list.add($e.ast); } 
 		    ( e=exp { $list.add($e.ast); } )+
 		')' { $ast = new AddExp($list); }
 		;

//
// In the action above, "locals" clause declares variables that will be available
// throughout that production rule, "@init" clause indicates action that will be 
// run before we start parsing this production rule. In summary, this rule creates
// a list before it runs (list), adds expressions to the list as it parses them,
// and finally creates an AddExp object using those collected expressions. 
// 
 
 subexp returns [SubExp ast]  
        locals [ArrayList<Exp> list]
 		@init { $list = new ArrayList<Exp>(); } :
 		'(' '-'
 		    e=exp { $list.add($e.ast); } 
 		    ( e=exp { $list.add($e.ast); } )+ 
 		')' { $ast = new SubExp($list); }
 		;

 multexp returns [MultExp ast] 
        locals [ArrayList<Exp> list]
 		@init { $list = new ArrayList<Exp>(); } :
 		'(' '*'
 		    e=exp { $list.add($e.ast); } 
 		    ( e=exp { $list.add($e.ast); } )+ 
 		')' { $ast = new MultExp($list); }
 		;
 
 divexp returns [DivExp ast] 
        locals [ArrayList<Exp> list]
 		@init { $list = new ArrayList<Exp>(); } :
 		'(' '/'
 		    e=exp { $list.add($e.ast); } 
 		    ( e=exp { $list.add($e.ast); } )+ 
 		')' { $ast = new DivExp($list); }
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
