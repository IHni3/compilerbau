import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

class VisitorFollowpos implements Visitor {

    private List<FollowPosTableEntry> table;
    
    public VisitorFollowpos(){
        table = new ArrayList<FollowPosTableEntry>();
    }

    public void visit(OperandNode node){
        table.add(new FollowPosTableEntry(node.getPosition(), node.getSymbol()));
    }
    public void visit(BinOpNode node){
        SyntaxNode c1 = (SyntaxNode)node.getLeft();
        SyntaxNode c2 = (SyntaxNode)node.getRight();
        
        if (node.getOperator().equals("°")){
            for (Integer pos : c1.getLastpos()) {
                for (FollowPosTableEntry tableEntry : table){
                    if (tableEntry.getPosition().equals(pos)){
                        tableEntry.getFollowpos().addAll(c2.getFirstpos());
                    }
                }
            }
        } 
    }
    public void visit(UnaryOpNode node){
        if (node.getOperator().equals("*") || node.getOperator().equals("+")){
            for (Integer pos : node.getLastpos()) {
                for (FollowPosTableEntry tableEntry : table){
                    if (tableEntry.getPosition().equals(pos)){
                        tableEntry.getFollowpos().addAll(node.getFirstpos());
                    }
                }
            }
        }
    }

    public List<FollowPosTableEntry> getTable(){
        return table;
    }
}