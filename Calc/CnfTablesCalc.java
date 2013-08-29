package Calc;
import java_cup.runtime.*;
import Calc.*;
import Calc.Absyn.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class CnfTablesCalc{
CatTag[] first, second;

public String getDesc (CatTag c){
	switch(c){
	case CAT_Exp:
		return "Exp";
	case CAT_0:
		return "2-prefix of EAdd (Exp '+')";
	case CAT_1:
		return "2-prefix of ESub (Exp '-')";
	case CAT_Exp1:
		return "Exp1";
	case CAT_2:
		return "2-prefix of EMul (Exp1 '*')";
	case CAT_3:
		return "2-prefix of EDiv (Exp1 '/')";
	case CAT_Exp2:
		return "Exp2";
	case CAT_4:
		return "2-prefix of id ('(' Exp)";
	case CAT_Integer:
		return "Integer";
	case TOK_40:
		return "token (";
	case TOK_41:
		return "token )";
	case TOK_42:
		return "token *";
	case TOK_43:
		return "token +";
	case TOK_45:
		return "token -";
	case TOK_47:
		return "token /";
	default: return " Error, allSyms mismatch";
	}
}
//combine :: Bool -> CATEGORY -> CATEGORY -> Pair [(CATEGORY, Any -> Any -> Any)]
public Pair getCombine (boolean p, CatTag r1,CatTag r2){
	switch(r1){
	case CAT_0:
		switch(r2){
			case CAT_Exp1:
			first = new CatTag[]{ CatTag.CAT_Exp /* \x y -> unsafeCoerce# (unsafeCoerce# (x) (unsafeCoerce# (y)))*/,};second = new CatTag[]{ CatTag.CAT_Exp /* \x y -> unsafeCoerce# (unsafeCoerce# (x) (unsafeCoerce# (y)))*/,};
			return new Pair (new CatTagList(first) , new CatTagList(second));
			default: return new Pair(new CatTagList(new CatTag[]{}), new CatTagList(new CatTag[]{}));
		}
	case CAT_1:
		switch(r2){
			case CAT_Exp1:
			first = new CatTag[]{ CatTag.CAT_Exp /* \x y -> unsafeCoerce# (unsafeCoerce# (x) (unsafeCoerce# (y)))*/,};second = new CatTag[]{ CatTag.CAT_Exp /* \x y -> unsafeCoerce# (unsafeCoerce# (x) (unsafeCoerce# (y)))*/,};
			return new Pair (new CatTagList(first) , new CatTagList(second));
			default: return new Pair(new CatTagList(new CatTag[]{}), new CatTagList(new CatTag[]{}));
		}
	case CAT_2:
		switch(r2){
			case CAT_Exp2:
			first = new CatTag[]{ CatTag.CAT_Exp1 /* \x y -> unsafeCoerce# (unsafeCoerce# (x) (unsafeCoerce# (y)))*/, CatTag.CAT_Exp /* \x y -> unsafeCoerce# (unsafeCoerce# (x) (unsafeCoerce# (y)))*/,};second = new CatTag[]{ CatTag.CAT_Exp1 /* \x y -> unsafeCoerce# (unsafeCoerce# (x) (unsafeCoerce# (y)))*/, CatTag.CAT_Exp /* \x y -> unsafeCoerce# (unsafeCoerce# (x) (unsafeCoerce# (y)))*/,};
			return new Pair (new CatTagList(first) , new CatTagList(second));
			default: return new Pair(new CatTagList(new CatTag[]{}), new CatTagList(new CatTag[]{}));
		}
	case CAT_3:
		switch(r2){
			case CAT_Exp2:
			first = new CatTag[]{ CatTag.CAT_Exp1 /* \x y -> unsafeCoerce# (unsafeCoerce# (x) (unsafeCoerce# (y)))*/, CatTag.CAT_Exp /* \x y -> unsafeCoerce# (unsafeCoerce# (x) (unsafeCoerce# (y)))*/,};second = new CatTag[]{ CatTag.CAT_Exp1 /* \x y -> unsafeCoerce# (unsafeCoerce# (x) (unsafeCoerce# (y)))*/, CatTag.CAT_Exp /* \x y -> unsafeCoerce# (unsafeCoerce# (x) (unsafeCoerce# (y)))*/,};
			return new Pair (new CatTagList(first) , new CatTagList(second));
			default: return new Pair(new CatTagList(new CatTag[]{}), new CatTagList(new CatTag[]{}));
		}
	case CAT_4:
		switch(r2){
			case TOK_41:
			first = new CatTag[]{ CatTag.CAT_Exp2 /* \x y -> unsafeCoerce# (unsafeCoerce# (x))*/, CatTag.CAT_Exp1 /* \x y -> unsafeCoerce# (unsafeCoerce# (x))*/, CatTag.CAT_Exp /* \x y -> unsafeCoerce# (unsafeCoerce# (x))*/,};second = new CatTag[]{ CatTag.CAT_Exp2 /* \x y -> unsafeCoerce# (unsafeCoerce# (x))*/, CatTag.CAT_Exp1 /* \x y -> unsafeCoerce# (unsafeCoerce# (x))*/, CatTag.CAT_Exp /* \x y -> unsafeCoerce# (unsafeCoerce# (x))*/,};
			return new Pair (new CatTagList(first) , new CatTagList(second));
			default: return new Pair(new CatTagList(new CatTag[]{}), new CatTagList(new CatTag[]{}));
		}
	case CAT_Exp:
		switch(r2){
			case TOK_43:
			first = new CatTag[]{ CatTag.CAT_0 /* \x y -> unsafeCoerce# (EAdd (unsafeCoerce# (x)))*/,};second = new CatTag[]{ };
			return new Pair (new CatTagList(first) , new CatTagList(second));
			case TOK_45:
			first = new CatTag[]{ CatTag.CAT_1 /* \x y -> unsafeCoerce# (ESub (unsafeCoerce# (x)))*/,};second = new CatTag[]{ };
			return new Pair (new CatTagList(first) , new CatTagList(second));
			default: return new Pair(new CatTagList(new CatTag[]{}), new CatTagList(new CatTag[]{}));
		}
	case CAT_Exp1:
		switch(r2){
			case TOK_42:
			first = new CatTag[]{ CatTag.CAT_2 /* \x y -> unsafeCoerce# (EMul (unsafeCoerce# (x)))*/,};second = new CatTag[]{ };
			return new Pair (new CatTagList(first) , new CatTagList(second));
			case TOK_47:
			first = new CatTag[]{ CatTag.CAT_3 /* \x y -> unsafeCoerce# (EDiv (unsafeCoerce# (x)))*/,};second = new CatTag[]{ };
			return new Pair (new CatTagList(first) , new CatTagList(second));
			default: return new Pair(new CatTagList(new CatTag[]{}), new CatTagList(new CatTag[]{}));
		}
	case TOK_40:
		switch(r2){
			case CAT_Exp:
			first = new CatTag[]{ CatTag.CAT_4 /* \x y -> unsafeCoerce# (unsafeCoerce# (y))*/,};second = new CatTag[]{ };
			return new Pair (new CatTagList(first) , new CatTagList(second));
			default: return new Pair(new CatTagList(new CatTag[]{}), new CatTagList(new CatTag[]{}));
		}
	default: return new Pair(new CatTagList(new CatTag[]{}), new CatTagList(new CatTag[]{}));
	}
}
public Pair tokenToCats (boolean p, Symbol s){ 
switch (s.sym){ 


case sym._INTEGER_:
			first = new CatTag[]{ CatTag.CAT_Exp2 /* unsafeCoerce# (EInt (readInteger (x)))*/, CatTag.CAT_Exp1 /* unsafeCoerce# (EInt (readInteger (x)))*/, CatTag.CAT_Exp /* unsafeCoerce# (EInt (readInteger (x)))*/,};second = new CatTag[]{ CatTag.CAT_Exp2 /* unsafeCoerce# (EInt (readInteger (x)))*/, CatTag.CAT_Exp1 /* unsafeCoerce# (EInt (readInteger (x)))*/, CatTag.CAT_Exp /* unsafeCoerce# (EInt (readInteger (x)))*/,};
return new Pair (new CatTagList(first) , new CatTagList(second));

 // +
case sym._SYMB_0:
 
			first = new CatTag[]{ };second = new CatTag[]{ CatTag.TOK_43 /* "error"cannot access value of token: +\""*/,};
return new Pair (new CatTagList(first) , new CatTagList(second));
 // -
case sym._SYMB_1:
 
			first = new CatTag[]{ };second = new CatTag[]{ CatTag.TOK_45 /* "error"cannot access value of token: -\""*/,};
return new Pair (new CatTagList(first) , new CatTagList(second));
 // *
case sym._SYMB_2:
 
			first = new CatTag[]{ };second = new CatTag[]{ CatTag.TOK_42 /* "error"cannot access value of token: *\""*/,};
return new Pair (new CatTagList(first) , new CatTagList(second));
 // /
case sym._SYMB_3:
 
			first = new CatTag[]{ };second = new CatTag[]{ CatTag.TOK_47 /* "error"cannot access value of token: /\""*/,};
return new Pair (new CatTagList(first) , new CatTagList(second));
 // (
case sym._SYMB_4:
 
			first = new CatTag[]{ CatTag.TOK_40 /* "error"cannot access value of token: (\""*/,};second = new CatTag[]{ };
return new Pair (new CatTagList(first) , new CatTagList(second));
 // )
case sym._SYMB_5:
 
			first = new CatTag[]{ };second = new CatTag[]{ CatTag.TOK_41 /* "error"cannot access value of token: )\""*/,};
return new Pair (new CatTagList(first) , new CatTagList(second));
default: return null;	}
	}
/*Normalised grammar:
  ($). Exp ::= "0" "Exp1"
EAdd. 0 ::= "Exp" +
($). Exp ::= "1" "Exp1"
ESub. 1 ::= "Exp" -
($). Exp1 ::= "2" "Exp2"
EMul. 2 ::= "Exp1" *
($). Exp1 ::= "3" "Exp2"
EDiv. 3 ::= "Exp1" /
EInt. Exp2 ::= "Integer"
id. Exp ::= "Exp1"
id. Exp1 ::= "Exp2"
($). Exp2 ::= "4" )
id. 4 ::= ( "Exp"

  Unit relation:
  id : CAT_Exp1 --> Exp
  id : CAT_Exp2 --> Exp1
  id : CAT_Exp2 --> Exp
  EInt : CAT_Integer --> Exp2
  EInt : CAT_Integer --> Exp1
  EInt : CAT_Integer --> Exp*/
}
