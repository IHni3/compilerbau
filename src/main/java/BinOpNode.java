public class BinOpNode extends SyntaxNode implements Visitable
{

    private String operator;
    private Visitable left;
    private Visitable right;
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