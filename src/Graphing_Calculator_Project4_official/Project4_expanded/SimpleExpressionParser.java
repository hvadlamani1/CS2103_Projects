import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

public class SimpleExpressionParser implements ExpressionParser{
        /*
         * Attempts to create an expression tree from the specified String.
         * Throws a ExpressionParseException if the specified string cannot be parsed.
	 * Grammar:
	 * S -> A | P
	 * A -> A+M | A-M | M
	 * M -> M*E | M/E | E
	 * E -> P^E | P | log(P)
	 * P -> (S) | L | V
	 * L -> <float>
	 * V -> x
	 * */
	

		/*
         * @param str the string to parse into an expression tree
         * @return the Expression object representing the parsed expression tree
         */
	public Expression parse (String str) throws ExpressionParseException {
		str = str.replaceAll(" ", "");
		Expression expression = parseAdditiveExpression(str);
		if (expression == null) {
			throw new ExpressionParseException("Cannot parse expression: " + str);
		}

		return expression;
	}
	
	

	protected Expression parseAdditiveExpression (String str) {
		for(int i = str.length(); i >= 0; i--) {
			if(str.substring(i, i+1).equals("+")) {
				Expression left = parseAdditiveExpression(str.substring(0,i));
				Expression right = parseAdditiveExpression(str.substring(i+1));
				//additiveExpression.add(new AdditiveExpression(left, right, true));
				return (Expression) new AdditiveExpression(left, right, true);
			}
			else if(str.substring(i, i+1).equals("-")) {
				Expression left = parseAdditiveExpression(str.substring(0,i));
				Expression right = parseAdditiveExpression(str.substring(i+1));
				//additiveExpression.add(new AdditiveExpression(left, right, false));
				return (Expression) new AdditiveExpression(left, right, false);
			}
			
		}	
		return parseMultiplicativeExpression(str);
		
    }

    protected Expression parseMultiplicativeExpression(String str) {
    	for(int i = str.length(); i >= 0; i--) {
			if(str.substring(i, i+1).equals("*")) {
				Expression left = parseMultiplicativeExpression(str.substring(0,i));
				Expression right = parseMultiplicativeExpression(str.substring(i+1));
				return new MultiplicativeExpression(left, right, true);
			}
			else if(str.substring(i, i+1).equals("/")) {
				Expression left = parseMultiplicativeExpression(str.substring(0,i));
				Expression right = parseMultiplicativeExpression(str.substring(i+1));
				return new MultiplicativeExpression(left, right, false);
			}
			
		}
		return parseExponentialExpression(str);
    	 
    }
    
    protected Expression parseExponentialExpression(String str) {
    	for(int i = str.length(); i >= 0; i--) {
			if(str.substring(i, i+1).equals("^")) {
				String base = str.substring(0,i);
				String exponent = str.substring(i+1);
				return new ExponentialExpression(base, exponent, true);
			}
			
			parseLogarithmicExpression(str);
			
    	}
		return null;
    }

    protected Expression parseLogarithmicExpression(String str) {
    	for(int i = str.length(); i >= 0; i++) {
			if(str.substring(i, i+1).equals("log")) {
				String argument = "";
			
				for(int j = i+1; j < str.length(); j++) {
					if(str.substring(j, j+1).equals(")")) {
						break;
					}
					argument = argument + str.substring(j,j+1);
				}
				return new LogarithmicExpression(argument);
			}
    	}
    	
    	
    	return parseParentheticalExpression(str);
    }

	protected Expression parseParentheticalExpression(String str) {
    	//int indexLeft = str.indexOf("(");
		//int indexRight = str.indexOf(")");
		ArrayList returnArray = new ArrayList<String>();
		int indexLeft = str.indexOf("(");
		int indexRight = str.indexOf(")");
		String insideString = "";
		while( indexLeft >= 0 && indexRight >=0 ) {
			insideString = str.substring(indexLeft + 1, indexRight);
			str = str.substring(indexRight);
			indexLeft = str.indexOf("(");
			indexRight = str.indexOf(")");
			
			Expression p = parseParentheticalExpression(str.substring(indexLeft + 1, indexRight));
			return new ParentheticalExpression(p);
		}
		
    	
		return parseLiteralExpression(str);
    }
    

	//implements the different production rules in here
        // TODO: once you implement a VariableExpression class, fix the return-type below.
        protected static /*Variable*/Expression parseVariableExpression (String str) {
        	for(int i = 0; i < str.length(); i++) {
        		if(str.equals("x")) {
        			return new VariableExpression("x");
        		}
        		
        		parseLiteralExpression(str);
        	}
                if (str.equals("x")) {
                        // TODO implement the VariableExpression class and uncomment line below
                         return new VariableExpression(str);
                	
                }
                return null;
        }

        // TODO: once you implement a LiteralExpression class, fix the return-type below.
	protected static /*Literal*/Expression parseLiteralExpression (String str) {
		// From https://stackoverflow.com/questions/3543729/how-to-check-that-a-string-is-parseable-to-a-double/22936891:
		final String Digits     = "(\\p{Digit}+)";
		final String HexDigits  = "(\\p{XDigit}+)";
		// an exponent is 'e' or 'E' followed by an optionally 
		// signed decimal integer.
		final String Exp        = "[eE][+-]?"+Digits;
		final String fpRegex    =
		    ("[\\x00-\\x20]*"+ // Optional leading "whitespace"
		    "[+-]?(" +         // Optional sign character
		    "NaN|" +           // "NaN" string
		    "Infinity|" +      // "Infinity" string

		    // A decimal floating-point string representing a finite positive
		    // number without a leading sign has at most five basic pieces:
		    // Digits . Digits ExponentPart FloatTypeSuffix
		    // 
		    // Since this method allows integer-only strings as input
		    // in addition to strings of floating-point literals, the
		    // two sub-patterns below are simplifications of the grammar
		    // productions from the Java Language Specification, 2nd 
		    // edition, section 3.10.2.

		    // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
		    "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

		    // . Digits ExponentPart_opt FloatTypeSuffix_opt
		    "(\\.("+Digits+")("+Exp+")?)|"+

		    // Hexadecimal strings
		    "((" +
		    // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
		    "(0[xX]" + HexDigits + "(\\.)?)|" +

		    // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
		    "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

		    ")[pP][+-]?" + Digits + "))" +
		    "[fFdD]?))" +
		    "[\\x00-\\x20]*");// Optional trailing "whitespace"

		if (str.matches(fpRegex)) {
			//return null;
			// TODO: Once you implement LiteralExpression, replace the line above with the line below:
			double literal = Double.parseDouble(str);
			return new LiteralExpression(literal);
		}
		return null;
	}

	public static void main (String[] args) throws ExpressionParseException {
		final ExpressionParser parser = new SimpleExpressionParser();
		//System.out.println(parser.parse("10*2+12-4.").convertToString(0));
	}
}
