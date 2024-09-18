
public class ExponentialExpression implements Expression{
	Expression base;
	Expression exponent;
	Boolean inc;
	public ExponentialExpression(String left2, String right2, boolean b) {
		left = left2;
		right = right2;
		inc = b;
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
	
	public static Expression parse(String str) {
		/*
	        int powerIndex = str.indexOf("^");
	        int logIndex = str.indexOf("log(");

	        if (powerIndex != -1) {
	            Expression base = PrimaryExpression.parse(str.substring(0, powerIndex));
	            Expression exponent = ExponentialExpression.parse(str.substring(powerIndex + 1));

	            if (base != null && exponent != null) {
	            	//LiteralExpression
	                OperationExpression exp = new OperationExpression("^");
	                exp.addSubexpression(base);
	                exp.addSubexpression(exponent);
	                return exp;
	            }
	        } else if (logIndex != -1) {
	            Expression argument = PrimaryExpression.parse(str.substring(logIndex + 4, str.length() - 1));

	            if (argument != null) {
	            	//LiteralExpression
	                OperationExpression exp = new OperationExpression("log");
	                exp.addSubexpression(argument);
	                return exp;
	            }
	        }
	        return null;
	       // return PrimaryExpression.parse(str);
	        * */
	        
	}
	}


