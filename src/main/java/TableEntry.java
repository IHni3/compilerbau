import java.util.HashSet;
import java.util.Set;

public class TableEntry {
    private Integer pos;
    private String operand;
    private Set<Integer> followpos;

    public TableEntry(Integer pos, String  operand, Set<Integer> followpos){
        this.pos = pos;
        this.operand = operand;
        this.followpos = followpos;
    }

    public Integer getPos() {
        return pos;
    }

    public String getOperand() {
        return operand;
    }

    public Set<Integer> getFollowpos() {
        return followpos;
    }

}