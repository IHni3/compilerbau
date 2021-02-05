import java.util.TreeMap;


class VisitorFollowpos implements Visitor {

    private TreeMap<Integer, FollowPosTableEntry> table;
    
    public VisitorFollowpos(){
        table = new TreeMap<Integer, FollowPosTableEntry>();
    }

    public void visit(OperandNode node){
        table.put(node.getPosition(), new FollowPosTableEntry(node.getPosition(), node.getSymbol()));
    }
    public void visit(BinOpNode node){
        SyntaxNode c1 = (SyntaxNode)node.getLeft();
        SyntaxNode c2 = (SyntaxNode)node.getRight();
        
        if (node.getOperator().equals("°")){
            for (Integer pos : c1.getLastpos()) {
                table.get(pos).getFollowpos().addAll(c2.getFirstpos());
            }
        } 
    }
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