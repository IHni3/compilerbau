public class BinOpNode extends SyntaxNode implements Visitable
{

    private final String operator;
    private final Visitable left;
    private final Visitable right;
    public BinOpNode(String operator, Visitable left, Visitable
            right)
    {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public String getOperator() {
        return operator;
    }

    public Visitable getLeft() {
        return left;
    }

    public Visitable getRight() {
        return right;
    }


    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }
}