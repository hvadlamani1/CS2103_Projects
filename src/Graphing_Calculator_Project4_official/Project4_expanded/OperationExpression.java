import java.util.ArrayList;

public class OperationExpression implements CompoundExpression {
	private final String _operation;
	private Expression _parent;
	private final ArrayList<Expression> _children;
	public OperationExpression(String operation) {
		this._operation = operation; 
		this._parent = null;
		this._children = new ArrayList<Expression>();
	}
	
	public void addSubexpression(Expression subexpression) {
		this._children.add(subexpression);
	}

	  public void setParent (Expression parent){
		    this._parent = parent;
	}
	  
	@Override
	public Expression deepCopy() {
		   final OperationExpression copy = new OperationExpression(this._operation);
		    copy.setParent(this._parent);

		    // Copy expression's subexpressions
		    for(Expression exp : this._children){
		      copy.addSubexpression(exp.deepCopy());
		    }

		    return copy;
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
