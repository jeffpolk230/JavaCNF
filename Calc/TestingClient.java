package Calc;
import java_cup.runtime.*;
import Calc.*;
import Calc.Absyn.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TestingClient{
    public static void main(String args[]) throws Exception
	{
            CnfTablesc tmp = new CnfTablesc();
	    Yylex l = null;
	    PrintWriter out = new PrintWriter("out");
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