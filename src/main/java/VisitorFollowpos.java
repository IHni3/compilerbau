import java.util.TreeMap;

// Class for the second Visitor, which checks for the Followpos and enters it in a table
// alongside position and the symbol of given Node.

class VisitorFollowpos implements Visitor {

    //Treemap which represents the Table.
    private TreeMap<Integer, FollowPosTableEntry> table;
    
    public VisitorFollowpos(){
        table = new TreeMap<Integer, FollowPosTableEntry>();
    }

    // Enters the position and symbol of given OperandNode.
    public void visit(OperandNode node){
        table.put(node.getPosition(), new FollowPosTableEntry(node.getPosition(), node.getSymbol()));
    }
    // Visits child nodes of given BinOpNode.
    public void visit(BinOpNode node){
        SyntaxNode c1 = (SyntaxNode)node.getLeft();
        SyntaxNode c2 = (SyntaxNode)node.getRight();

        //Determines Followpos for Child if it's a concatenation.
        if (node.getOperator().equals("°")){
            for (Integer pos : c1.getLastpos()) {
                table.get(pos).getFollowpos().addAll(c2.getFirstpos());
            }
        } 
    }
    // Visits child node of given UnaryOpNode and assigns its Followpos.
    public void visit(UnaryOpNode node){
        if (node.getOperator().equals("*") || node.getOperator().equals("+")){
            for (Integer pos : node.getLastpos()) {
                table.get(pos).getFollowpos().addAll(node.getFirstpos());
            }
        }
    }

    public TreeMap<Integer, FollowPosTableEntry> getTable(){
        return table;
    }
}