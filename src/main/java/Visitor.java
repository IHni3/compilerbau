interface Visitor
{
    // Interface for the Visitors to check each Node.

    public void visit(OperandNode node);
    public void visit(BinOpNode node);
    public void visit(UnaryOpNode node);
}