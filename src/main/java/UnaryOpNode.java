
// Class for Unary Operation Nodes (+, * and ?).

public class UnaryOpNode extends SyntaxNode implements Visitable
{
    private final String operator;
    private final Visitable subNode;

    // Sets one visitable child Node.
    public UnaryOpNode(String operator, Visitable subNode)
    {
        this.operator = operator;
        this.subNode = subNode;
    }

    public String getOperator() {
        return operator;
    }

    public Visitable getSubNode() {
        return subNode;
    }

    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }
}