
public class AdditiveExpression extends CompoundExpression implements Expression {
	Expression left;
	Expression right;
	Boolean plus;
	
	public AdditiveExpression(Expression l, Expression r, Boolean add) {
		left = l;
		right = r;
		plus = add;
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
		if(plus) {
			return "+";
		}
		else {
			return "-";
		}
	}

}
