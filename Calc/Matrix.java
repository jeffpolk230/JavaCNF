package Calc;
import java_cup.runtime.*;
import Calc.*;
import Calc.Absyn.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Matrix <V extends Value> implements Value{
	
	/*Subclasses we want*/
	public static class Zero<V> extends Matrix {

		@Override
		public Value multiplication(boolean p, Value b) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Value addition(Value b) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "";
		}}
	public static class One<V extends Value> extends Matrix<V>
	{
		public V value;
		public One (V value)
		{
			this.value = value;
		}
		public Matrix plus(One A) 
		{
			return new One(this.value.addition(A.value)); 
		}
		public Matrix times(One A) 
		{
			return new One(this.value.multiplication(true, A.value));
		}
		public Value multiplication(boolean p, Value b) {
			return new One(this.value.multiplication(true, ((One) b).value));
		}
		public Value addition(Value b) {
			return new One(this.value.addition(((One) b).value)); 
		}
		public String show() {
			return "ONE";
		}
	}
	public static class Row<V> extends Matrix
	{
		public Matrix left, right;
		public Row (Matrix left, Matrix right)
		{
			this.left = left;
			this.right = right;
		}
		@Override
		public Value multiplication(boolean p, Value b) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Value addition(Value b) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	public static class Col<V> extends Matrix
	{
		public Matrix upper, lower;
		public Col (Matrix upper, Matrix lower)
		{
			this.upper = upper;
			this.lower = lower;
		}
		@Override
		public Value multiplication(boolean p, Value b) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Value addition(Value b) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	public static class Quad<V> extends Matrix
	{
		public Matrix ul, ur, ll, lr; //upper left; upper right; lower left; lower right
		public Quad(Matrix ul, Matrix ur, Matrix ll, Matrix lr)
		{
			this.ul = ul;
			this.ur = ur;
			this.ll = ll;
			this.lr = lr;
		}
		@Override
		public Value multiplication(boolean p, Value b) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Value addition(Value b) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	/*Smart Constructor for creating Zero*/
	public static Matrix zero = new Zero();
	public static Matrix row (Matrix A, Matrix B){
		if (A == zero && B == zero)
			return zero;
		else return new Row(A, B);
	}
	public static Matrix col (Matrix A,Matrix B){
		if (A == zero && B == zero)
			return zero;
		else return new Col(A, B);
	}
	public static Matrix one(Matrix A)
	{
		if(A == zero)
			return zero;
		else return new One(((One) A).value);
	}
	public static Matrix quad (Matrix A,Matrix B, Matrix C, Matrix D){
		if (A == zero && B == zero && C == zero && D == zero )
			return zero;
		else return new Quad(A, B, C, D);
	}
	
	/*Add and Mul for all subclasses*/
	public static Matrix add(Matrix A, Matrix B) throws Exception
	{
		if(A instanceof Zero) return B;
		if(B instanceof Zero) return A;
		if(A instanceof One && B instanceof One)
			return one(((One) A).plus(((One)B)));
		if(A instanceof Row && B instanceof Row)
			return row( add(((Row) A).left,((Row) B).left) ,add(((Row) A).right, ((Row) B).right) );
		if(A instanceof Col && B instanceof Col)
			return col( add(((Col) A).upper,((Col) B).upper) ,add(((Col) A).lower, ((Col) B).lower) );
		if(A instanceof Quad && B instanceof Quad)
			return quad( add(((Quad) A).ul,((Quad) B).ul), add(((Quad) A).ur,((Quad) B).ur) ,add(((Quad) A).ll,((Quad) B).ll) ,add(((Quad) A).lr,((Quad) B).lr)  );
		
		else throw new Exception("Mismatching while adding; you shouldn't be here!");
	}
	public static Matrix mul(boolean p, Matrix A, Matrix B) throws Exception
	{
		//System.out.println("MUL is called");
		//System.out.println(((One)A).value.show());
		//System.out.println(((One)B).value.show());
//		System.out.println(A);
//		System.out.println(B);
		if (A instanceof Zero || B instanceof Zero) 
			return zero;
		
		if (A instanceof One && B instanceof One) 
		{	
			return one(((One) A).times(((One) B)));
		}
		if (A instanceof One && B instanceof Row)
			return row(mul(p, A,(((Row) B).left)),mul(p, A,(((Row) B).right)));
		
		if (A instanceof Col && B instanceof One)
			return col(mul(p, ((Col) A).upper,B), mul(p, ((Col) A).lower,B));
		
		if (A instanceof Row && B instanceof Col)
			return add(( mul(p, (((Row) A).left),(((Col) B).upper ))),(( mul(p, (((Row) A).right),(((Col) B).lower )))));
		
		if (A instanceof Col && B instanceof Row)
			return quad(mul(p, ((Col) A).upper,((Row) B).left),mul(p, ((Col) A).upper,((Row) B).right),mul(p, ((Col) A).lower,((Row) B).left),mul(p, ((Col) A).lower,((Row) B).right));
		
		if (A instanceof Row && B instanceof Quad)
			return row( add( mul(p, ((Row) A).left,((Quad) B).ul),mul(p, ((Row) A).right,((Quad) B).ll) ), add( mul(p,  ((Row) A).left,((Quad) B).ur ) ,mul(p, ((Row) A).right,((Quad) B).lr)) );
			//row (a & a' + b & c') (a & b' + b & d')
		
		if (A instanceof Quad && B instanceof Col)
			return col( add( mul(p,  ((Quad) A).ul, ((Col) B).upper) , mul(p, ((Quad) A).ur,((Col) B).lower )), add( mul(p, ((Quad) A).ll,((Col) B).upper), mul(p, ((Quad) A).lr,((Col) B).lower )) );
			//Quad a b c d & Col a' c' = col (a & a' + b & c') (c & a' + d & c')
		
		if(A instanceof Quad && B instanceof Quad)
			return quad( mul(p, ((Quad) A).ul, ((Quad) B).ul),mul(p, ((Quad) A).ur, ((Quad) B).ur) ,mul(p, ((Quad) A).ll, ((Quad) B).ll) ,mul(p, ((Quad) A).lr, ((Quad) B).lr)  );
		
		else throw new Exception("Mismatching while multipling; you shouldn't reach here");		
	}
	
	
	public static Pair<Matrix> closeDisjointP(boolean p, Matrix a, Matrix b, Matrix c) throws Exception
	{
		Pair result = close(p, a, b, c);
		return result;
	}
	public static Pair close(boolean p , Matrix a,Matrix b,Matrix c) throws Exception
	{
		if (b instanceof Zero)
			return new Pair(zero, zero);
		else if (a instanceof Zero && c instanceof Zero)
			return trav(b);
		else if (a instanceof Quad && b instanceof Quad && c instanceof Quad)
		{
			Pair x21 = close (p, ((Quad) a).lr, ((Quad) b).ll, ((Quad) c).ul);
			Pair x11 = close (p, ((Quad) a).ul, add(mul( p,((Quad) a).ur , (Matrix) x21.second) , ((Quad) b).ul) , ((Quad) c).ul);
			Pair x22 = close (p, ((Quad) a).lr, add(mul( p, (Matrix) x21.first, ((Quad) c).ur ), ((Quad) b).lr) , ((Quad) c).lr);
			Pair x12 = close (p, ((Quad) a).ul, add(mul( p,((Quad) a).ur , (Matrix) x22.second) ,add(mul( p, (Matrix) x11.first, ((Quad) c).ur ), ((Quad) b).ur)) , ((Quad) c).lr);
			Matrix fst = quad ((Matrix) x11.first,(Matrix) x12.first,(Matrix) x21.first,(Matrix) x22.first);
			Matrix snd = quad ((Matrix) x11.second,(Matrix) x12.second,(Matrix) x21.second,(Matrix) x22.second);
			return new Pair(fst,snd);
		}
		else if (a instanceof Zero && b instanceof Quad && c instanceof Quad)
			return close(p, new Quad(new Zero(),new Zero(),new Zero(),new Zero()), b, new Quad( ((Quad) c).ul, ((Quad) c).ur, new Zero(), ((Quad) c).lr  ) );
		else if (a instanceof Quad && ((Quad) a).ll instanceof Zero && b instanceof Quad && c instanceof Zero)
			return close(p, new Quad( ((Quad) a).ul, ((Quad) a).ur, new Zero(), ((Quad) a).lr  ), b, new Quad(new Zero(), new Zero(),new Zero(),new Zero()) );
		else if (a instanceof Quad && ((Quad) a).ll instanceof Zero && b instanceof Col && c instanceof Zero)
		{
			Matrix one, two;
			Pair x2 = close (p, ((Quad) a).lr, ((Col) b).lower, new Zero() );
			Pair x1 = close (p, ((Quad) a).ul, add(mul( p,((Quad) a).ur , (Matrix) x2.second), ((Col) b).upper), new Zero());
			one = col((Matrix) x1.first,(Matrix) x2.first);
			two = col((Matrix) x1.second,(Matrix) x2.second);
			return new Pair(one, two);
		}
		else if (a instanceof Zero && b instanceof Row && c instanceof Quad && ((Quad) c).ll instanceof Zero)
		{
			Matrix one, two;
			Pair x1 = close (p, new Zero(), ((Row) b).left, ((Quad) c).ul);
			Pair x2 = close (p, new Zero(), add(mul( p, (Matrix) x1.first, ((Quad) c).ur ), ((Row) b).right), ((Quad) c).lr);
			one = row((Matrix) x1.first,(Matrix) x2.first);
			two = row((Matrix) x1.second,(Matrix) x2.second);
			return new Pair(one, two);
		}
		else 
			throw new Exception("closeDisjoint mismatching");
	}
	
	public static Pair trav(Matrix<Pair> x) throws Exception
	{
		if (x instanceof Zero)
			return new Pair(new Zero(), new Zero());
		if (x instanceof One)
		{
			return new Pair(new One( ((Pair) ((One) x).value ).first ), new One( ((Pair) ((One) x).value ).second ));
		}
		if (x instanceof Row)
		{
			Matrix one = row( (Matrix) trav (((Row) x).left).first,(Matrix) trav (((Row) x).right).first);
			Matrix two = row( (Matrix) trav (((Row) x).left).second,(Matrix) trav (((Row) x).right).second);
			return new Pair (one, two);
		}
		if (x instanceof Col)
		{
			Matrix one = col( (Matrix) trav (((Col) x).upper).first,(Matrix) trav (((Col) x).lower).first);
			Matrix two = col( (Matrix) trav (((Col) x).upper).second,(Matrix) trav (((Col) x).lower).second);
			return new Pair (one, two);
		}
		if (x instanceof Quad)
		{  
			Matrix one = quad( (Matrix) trav (((Quad) x).ul).first,(Matrix) trav (((Quad) x).ur).first,(Matrix) trav (((Quad) x).lr).first,(Matrix) trav (((Quad) x).ll).first  );
			Matrix two = quad( (Matrix) trav (((Quad) x).ul).second,(Matrix) trav (((Quad) x).ur).second,(Matrix) trav (((Quad) x).lr).second,(Matrix) trav (((Quad) x).ll).second  );
			return new Pair (one, two);
		}
		else throw new Exception("traverse mismatching, shouldn't happen");
	}
	
	public static <T extends Value> Matrix mkSing (Shape x, Shape y, T a)
	{
		if (x == null && y == null)
			return new One(a);
		else if (x == null && y != null)
			return col(new Zero(), mkSing(null, y.right, a));
		else if (x != null && y == null)
			return row(mkSing(x.left, null, a), new Zero());
		else 
			return quad(new Zero(), new Zero(), mkSing(x.left, y.right, a), new Zero());
	}
	
	public static Matrix mkUpDiag(List<Value> a, Shape s)
	{
		if (a.size() == 0 || s == null)
			return new Zero();
		else
		{
			Shape l = s.left;
			Shape r = s.right;
			List<Value> leftPart =  a.subList(0, s.getHeight(l)-1);
			List<Value> rightPart =  a.subList(s.getHeight(l), s.getHeight(s)-1);
			
			return new Quad(mkUpDiag(leftPart, l), mkSing(s.right, s.left, a.get(s.getHeight(l)-1)), zero, mkUpDiag(rightPart, s.right));
		}
	}
	
	public static Pair<Matrix> anotherClose(boolean p ,Matrix x) throws Exception
	{
		if (x instanceof Zero)
			return new Pair(zero , zero);
		if (x instanceof One)
			return new Pair( new One((Value) ((Pair)((One) x).value).first), new One((Value) ((Pair)((One) x).value).second));
		if (x instanceof Quad)
		{
			Pair x11 = anotherClose(!p, ((Quad) x).ul);
			Pair x22 = anotherClose(!p, ((Quad) x).lr);

			Pair pair = closeDisjointP(p, (Matrix) x11.first, ((Quad) x).ur ,(Matrix) x22.second);
			Quad a, b;
			a = (Quad) quad( ((Matrix) x11.first) , ((Matrix) (pair.first)) , zero ,((Matrix) x22.first));
			b = (Quad) quad( ((Matrix)(x11.second)) , ((Matrix) (pair.second)) , zero ,((Matrix) x22.second) );
			return new Pair(a,b);
		}
		else 
			throw new Exception("Exception: mismatching in AnotherClose");
	}
	// dataType SomeTri consists of a shape, and a pair of Matrix. 
	public class Tree
	{
		public Shape shape;
		public Pair<Matrix> pair;
		public Tree(Shape s, Pair<Matrix> p)
		{
			shape = s;
			pair = p;
		}
	}
	public Tree mkTree(List<Value> a) throws Exception
	{
		Shape s = null;
		s = s.makeShape(a.size());
		return new Tree(s, anotherClose(true, mkUpDiag(a, s)));
	}
	
	public static Value access(Shape s1, Shape s2, Matrix a, int x, int y)throws Exception
	{
		if (a instanceof Zero) return new Pair(zero, zero);
		if (a instanceof One) return ((One) a).value;
		if (a instanceof Row)
		{
			if ( x < s1.getHeight(s1.left) )
				return access (s1.left, s2, ((Row) a).left, x, y);
			else
				return access (s1.right, s2, ((Row) a).right, x - s1.getHeight(s1.left), y);
		}
		if (a instanceof Col)
		{
			if ( y< s2.getHeight(s2.left) )
				return access (s1, s2.left, ((Col) a).upper, x, y);
			else
				return access (s1, s2.right, ((Col) a).lower, x, y - s2.getHeight(s2.left));
		}
		if (a instanceof Quad)
		{
			if (x < s1.getHeight(s1.left))
			{
				if (y < s2.getHeight(s2.left))
					return access(s1.left, s2.left, ((Quad) a).ul, x, y);
				else
					return access(s1.left, s2.right, ((Quad) a).ll, x, y-s2.getHeight(s2.left));
			}
			else
			{
				if (y < s2.getHeight(s2.left))
					return access(s1.right, s2.left, ((Quad) a).ur, x-s1.getHeight(s1.left), y);
				else
					return access(s1.right, s2.right, ((Quad) a).lr, x-s1.getHeight(s1.left), y-s2.getHeight(s2.left));
			}	
		}
		else 
		{
			throw new Exception("Exception during access");
		}
		
	}
	
	/*Test Client*/
	public static void main(String args[]) throws Exception
	{
            CnfTablesCalc tmp = new CnfTablesCalc();
	    Yylex l = null;
	    PrintWriter out = new PrintWriter("ResultMatrix");
	    //BufferedWriter out = new BufferedWriter(fstream);
		    try
		    {
		      if (args.length == 0) l = new Yylex(System.in);
		      else l = new Yylex(new FileReader(args[0]));
		    }
		    catch(FileNotFoundException e)
		    {
		     System.err.println("Error: File not found: " + args[0]);
		     System.exit(1);
		    }

		    List<Symbol> inputs = new ArrayList<Symbol>();
		    Symbol s = l.next_token();
		    while (s != null)
		    {
			inputs.add(s);
			s = l.next_token();
		    }
		    System.out.println(inputs.size());

		List<Value> testList = new ArrayList<Value>();
		Shape s1 = new Shape(null,null);
		for(Symbol sy: inputs){
			System.out.println(((Value)tmp.tokenToCats(true, sy)).show());
			testList.add(tmp.tokenToCats(true, sy));
		}
		Matrix mat = mkUpDiag(testList, s1.makeShape(testList.size()));
		System.out.println(s1.getHeight(s1.makeShape(testList.size())));
		System.out.println(testList.size());
		try{
			for (int y = 0 ; y <= testList.size() ; y ++){
				for (int x = 0 ; x <= testList.size() ; x ++)
				{
					Value val = access (s1.makeShape(testList.size()),s1.makeShape(testList.size()), mat, x, y);
					out.printf("%30s",val.show());
				}
				out.write("\n");
			}
			out.write("\n");
			out.write("result matrix");			
			out.write("\n");
			Pair resultfFromClose = anotherClose(true, mat);
			for (int y = 0 ; y <= testList.size() ; y ++){
				for (int x = 0 ; x <= testList.size() ; x ++)
				{
					Value val = access (s1.makeShape(testList.size()),s1.makeShape(testList.size()), (Matrix) resultfFromClose.first, x, y);
					out.printf("%30s",val.show());
				}
				out.write("\n");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	        out.close();
	}
}
