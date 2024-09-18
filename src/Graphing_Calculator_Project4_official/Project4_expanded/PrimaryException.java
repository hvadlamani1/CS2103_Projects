
public class PrimaryExpression implements Expression {

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
	
	public static Expression parse(String str) {
        if (str.length() > 0 && str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')') {
            Expression innerExpression;
			try {
				innerExpression = SimpleExpressionParser.newParse(str.substring(1, str.length() - 1));
			} catch (ExpressionParseException e) {
				return null;
			}

            if (innerExpression != null) {
            	//LiteralExpression
                OperationExpression exp = new OperationExpression("()");
                exp.addSubexpression(innerExpression);
                return (Expression) exp;
            }
        }

        return null;
       // return parseL(str);
	}

}
