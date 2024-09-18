
public class MultiplicativeExpression implements Expression{
	Expression left;
	Expression right;
	Boolean inc;
	
	public MultiplicativeExpression(Expression l, Expression r, Boolean add) {
		left = l;
		right = r;
		inc = add;
	}
	@Override
	public Expression deepCopy() {
		// TODO Auto-generated method stub
		return null;
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
	
	
	@Override
	public void convertToString(int indentLevel) {
		// TODO Auto-generated method stub
		
	}
	
	public String operationCharacter() {
		if(inc) {
			return "*";
		}
		else {
			return "/";
		}
	}
}
