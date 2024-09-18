
public class LogarithmicExpression extends CompoundExpression implements Expression {
	String argument;
	
	public LogarithmicExpression (String a) {
		argument = a;
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
	


}
