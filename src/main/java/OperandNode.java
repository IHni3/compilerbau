
// Class for Operand Nodes (Letters and numbers, as well as the exit token #).

public class OperandNode extends SyntaxNode implements Visitable
{


    private Integer position;
    private final String symbol;

    // Assigns symbol to each node.
    public OperandNode(String symbol)
    {
        position = -1; // isn't initialized yet
        this.symbol = symbol;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getSymbol() {
        return symbol;
    }

    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }
}