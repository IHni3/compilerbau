import java.util.HashSet;
import java.util.Set;

public abstract class SyntaxNode
{
    private Boolean nullable;
    private final Set<Integer> firstpos;
    private final Set<Integer> lastpos;

    public SyntaxNode()
    {
        firstpos = new HashSet<Integer>();
        lastpos = new HashSet<Integer>();
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
}