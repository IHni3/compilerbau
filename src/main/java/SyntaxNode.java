import java.util.HashSet;
import java.util.Set;

// Base class that each node will imply.
// Let Nodes get and set Nullable, as well as get firstpos and lastpos.

public abstract class SyntaxNode
{
    private Boolean nullable;
    private final Set<Integer> firstpos;
    private final Set<Integer> lastpos;

    public SyntaxNode()
    {
        firstpos = new HashSet<>();
        lastpos = new HashSet<>();
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