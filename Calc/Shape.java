package Calc;
public class Shape {
	public Shape left, right = null;
	public Shape(Shape l, Shape r)
	{
		this.left = l;
		this.right = r;
	}
	public Shape getLeft() 
	{
		return this.left; 
	}
	public Shape getRight()
	{
		return this.right;
	}

	public static int getHeight(Shape s)
	{
		/*return number of nodes*/
		if (s == null)
			return 1;
		else 
			return getHeight(s.right) + getHeight(s.left);
	}
	public static Shape makeShape(int n)
	{
		// makeShpae (n) produce shape with size n+1 
		if (n < 1) return null;
		else if (n == 1) 
			return new Shape(null, null);
		else if (n == 2) 
			return new Shape(new Shape(null, null),null);
		else 
		{
			return new Shape (makeShape(n/2), makeShape(n - n/2 - 1));
		}
	}
	

	/*Test Client*/
	public static void main(String args[])
	{
		Shape s1 = makeShape(5);
		System.out.println(getHeight(s1));
		System.out.println(s1.getHeight(s1.left));
		System.out.println(s1.getHeight(s1.right));
		
		
	}
}
