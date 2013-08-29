package Calc;
public class Pair <T extends Value>  implements Value {
	T first, second;
	//Dangerous to leave it like this !
	public Pair (T r1, T r2){this.first = r1; this.second = r2;}
	public String show(){
		String out = "";
		if (first instanceof Value && second instanceof Value)
			out = out.concat(((Value) first).show() ).concat(" :/: ").concat(((Value) second).show());
		else  
			out = out.concat(first.show()).concat(second.show());
		return out;
	}
	public void setFirst(T first){this.first = first;}
	public void setSecond(T second){this.second = second;}

	public Value addition(Value b){
		Pair input = (Pair) b;
		return new Pair (input.first.addition(this.first),input.second.addition(this.second));
	}
	public Value multiplication(boolean p, Value b) {
		Value frt = this.first.multiplication(p, ((Pair) b).first);
		Value snd = this.second.multiplication(p, ((Pair) b).second);
		return new Pair(frt, snd);
	}
}