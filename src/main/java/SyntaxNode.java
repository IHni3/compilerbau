import java.util.HashSet;
import java.util.Set;

public abstract class SyntaxNode
{
    private Boolean nullable;
    private final Set<Integer> firstpos;
    private final Set<Integer> lastpos;
    private  final Set<Integer> followpos;

    public SyntaxNode()
    {
        firstpos = new HashSet<>();
        lastpos = new HashSet<>();
        followpos = new HashSet<>();
    }

    public Boolean getNullable() {
        return nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public Set<Integer> getFirstpos() {
        return firstpos;
    }

    public Set<Integer> getLastpos() {
        return lastpos;
    }

    public Set<Integer> getFollowpos() { return followpos;}
}