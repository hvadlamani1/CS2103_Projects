
public class LiteralExpression implements Expression{
	private final double _value;
	private Expression _parent;
	
	public LiteralExpression(double value) {
		this._value = value;
		this._parent = null;
	}

	public void setParent(Expression parent) {
		this._parent = parent;
	}
	@Override
	public Expression deepCopy() {
		final LiteralExpression copy = new LiteralExpression(this._value);
		copy.setParent(this._parent);
		return copy;
	}

	@Override
	public String convertToString(int indentLevel) {
		final StringBuilder stringBuilder = new StringBuilder();
		convertToString(stringBuilder, indentLevel);
		return stringBuilder.toString();
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
	public void convertToString(StringBuilder stringBuilder, int indentLevel) {
	    Expression.indent(stringBuilder, indentLevel);

	    stringBuilder.append(this._value);
	    stringBuilder.append("\n");
		
	}
	
	public static Expression parse(String str) {
		/*
		 try {
	            float floatValue = Float.parseFloat(str);
	            return new LiteralExpression(String.valueOf(floatValue));
	        } catch (NumberFormatException e) {
	            return null;
	        }
	        */
	}
	
}
