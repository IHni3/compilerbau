

public class OperandNode extends SyntaxNode implements Visitable
{


    private Integer position;
    private final String symbol;
    public OperandNode(String symbol)
    {
        position = -1; // bedeutet: noch nicht initialisiert
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