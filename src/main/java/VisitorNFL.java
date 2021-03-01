
// Class for the first Visitor, which checks for Nullable, firstpos and lastpos of given node.
// Done by 1724537

public class VisitorNFL implements Visitor {

    // Sets starting position as 0;
    private int pos = 0;

    // Determines firstpos and lastpos for an OperandNode and set's Nullable false.
    // Increases the pos counter for the next OperandNode.
    public void visit(OperandNode node) {
        node.setNullable(false);
        node.setPosition(pos);
        node.getFirstpos().add(pos);
        node.getLastpos().add(pos);
        pos++;
    }

    // Determines nullable, firstpos and lastpos based on child nodes.
    public void visit(BinOpNode node){

        // Initiates childnodes.
        SyntaxNode c1 = (SyntaxNode)node.getLeft();
        SyntaxNode c2 = (SyntaxNode)node.getRight();

        if (node.getOperator().equals("°")) {
            node.setNullable(c1.getNullable() && c2.getNullable());

            if (c1.getNullable()) {
                node.getFirstpos().addAll(c1.getFirstpos());
                node.getFirstpos().addAll(c2.getFirstpos());
            } else {
                node.getFirstpos().addAll(c1.getFirstpos());
            }

            if (c2.getNullable()) {
                node.getLastpos().addAll(c1.getLastpos());
                node.getLastpos().addAll(c2.getLastpos());
            } else {
                node.getLastpos().addAll(c2.getLastpos());
            }
        } else

        if (node.getOperator().equals("|")) {
            node.setNullable(c1.getNullable() || c2.getNullable());
            node.getFirstpos().addAll(c1.getFirstpos());
            node.getFirstpos().addAll(c2.getFirstpos());
            node.getLastpos().addAll(c1.getLastpos());
            node.getLastpos().addAll(c2.getLastpos());
        }

    }

    // Determines nullable, firstpos and lastpos based on child node.
    public void visit(UnaryOpNode node){

        //Initiates child node.
        SyntaxNode c1 = (SyntaxNode)node.getSubNode();

        if (node.getOperator().equals("+")) {
            node.setNullable(c1.getNullable());
        }else
            if (node.getOperator().equals("*") || node.getOperator().equals("?")) {
            node.setNullable(true);
        }
            node.getFirstpos().addAll(c1.getFirstpos());
            node.getLastpos().addAll(c1.getLastpos());
    }


}
