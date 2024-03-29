import java.util.HashSet;
import java.util.Set;

// Class for the Table, generated by the second Visitor (VisitorFollowpos).

class FollowPosTableEntry {

    private final Integer position;
    private final String symbol;
    private final Set<Integer> followpos;

    public FollowPosTableEntry(Integer position, String symbol) {
        this.followpos = new HashSet<Integer>();
        this.position = position;
        this.symbol = symbol;
    }

    public Integer getPosition() {
        return position;
    }

    public String getSymbol() {
        return symbol;
    }

    public Set<Integer> getFollowpos() {
        return followpos;
    }

    // Checks if the given object equals to the Followpos Table.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FollowPosTableEntry)) {
            return false;
        }
        FollowPosTableEntry other = (FollowPosTableEntry) obj;
        return this.position.equals(other.position) &&
                this.symbol.equals(other.symbol) &&
                this.followpos.equals(other.followpos);
    }

    @Override
    public int hashCode() {
        int hashCode = this.position;
        hashCode = 31 * hashCode + this.symbol.hashCode();
        hashCode = 31 * hashCode + this.followpos.hashCode();
        return hashCode;
    }
}