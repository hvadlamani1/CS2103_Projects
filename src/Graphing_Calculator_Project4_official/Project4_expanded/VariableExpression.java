import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/* Grammar:
* S -> A | P
* A -> A+M | A-M | M
* M -> M*E | M/E | E
* E -> P^E | P | log(P)
* P -> (S) | L | V
* L -> <float>
* V -> x
* */
public class VariableExpression implements Expression{
	
	public static Expression parse(String str) {
		 return "x".equals(str) ? (Expression) new VariableExpression() : null;
	}
	
	
	

	@Override
	public Expression deepCopy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void convertToString(StringBuilder stringBuilder, int indentLevel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double evaluate(double x) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Expression differentiate() {
		// TODO Auto-generated method stub
		return null;
	}
}
