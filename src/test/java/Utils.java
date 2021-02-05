import java.util.Arrays;

public class Utils {

    public static OperandNode operandNodeFactory(final String symbol,
                                          final Integer position,
                                          final Boolean nullable,
                                          final Integer firstpos,
                                          final Integer lastpos)
    {
        return operandNodeFactory(symbol,
                position,
                nullable,
                new Integer[]{firstpos},
                new Integer[]{lastpos});
    }
    public static OperandNode operandNodeFactory(final String symbol,
                                          final Integer position,
                                          final Boolean nullable,
                                          final Integer[] firstpos,
                                          final Integer[] lastpos)
    {
        var node = new OperandNode(symbol);
        node.setPosition(position);
        node.setNullable(nullable);
        node.getFirstpos().addAll(Arrays.asList(firstpos));
        node.getLastpos().addAll(Arrays.asList(lastpos));

        return node;
    }

    public static UnaryOpNode unaryNodeFactory(final String operator,
                                        final Visitable subNode,
                                        final Boolean nullable,
                                        final Integer firstpos,
                                        final Integer lastpos) {
        return unaryNodeFactory(operator,
                subNode, nullable,
                new Integer[]{firstpos},
                new Integer[]{lastpos});
    }

    public static UnaryOpNode unaryNodeFactory(final String operator,
                                        final Visitable subNode,
                                        final Boolean nullable,
                                        final Integer[] firstpos,
                                        final Integer[] lastpos) {

        var node = new UnaryOpNode(operator, subNode);
        node.setNullable(nullable);
        node.getFirstpos().addAll(Arrays.asList(firstpos));
        node.getLastpos().addAll(Arrays.asList(lastpos));

        return node;
    }

    public static BinOpNode binOpNodeFactory(final String operator,
                                      final Visitable left,
                                      final Visitable right,
                                      final Boolean nullable,
                                      final Integer firstpos,
                                      final Integer lastpos) {



        return binOpNodeFactory(operator,
                left,
                right,
                nullable,
                new Integer[]{firstpos},
                new Integer[]{lastpos});
    }

    public static BinOpNode binOpNodeFactory(final String operator,
                                      final Visitable left,
                                      final Visitable right,
                                      final Boolean nullable,
                                      final Integer[] firstpos,
                                      final Integer[] lastpos) {

        var node = new BinOpNode(operator, left, right);
        node.setNullable(nullable);
        node.getFirstpos().addAll(Arrays.asList(firstpos));
        node.getLastpos().addAll(Arrays.asList(lastpos));

        return node;
    }

    public static FollowPosTableEntry tableEntryFactory(Integer pos, String operand, Integer[] followpos)
    {
        var tableEntry = new FollowPosTableEntry(pos, operand);

        tableEntry.getFollowpos().addAll(Arrays.asList(followpos));
        return  tableEntry;
    }
}
