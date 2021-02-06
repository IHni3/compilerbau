import org.junit.Assert;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

public class VisitorNFLTest {

    private static Visitable createInput() {
        var aOperandNode = new OperandNode("A");
        var bOperandNode = new OperandNode("B");
        var cOperandNode = new OperandNode("C");
        var endNode = new OperandNode("#");

        var plusNode = new UnaryOpNode("+", aOperandNode);
        var questionNode = new UnaryOpNode("?", bOperandNode);
        var starNode = new UnaryOpNode("*", cOperandNode);

        var andNode = new BinOpNode("°", plusNode, questionNode);
        var orNode = new BinOpNode("|", andNode, starNode);
        var rootNode = new BinOpNode("°", orNode, endNode);

        return rootNode;
    }

    private static Visitable createExpected() {
        var aOperandNode    = Utils.operandNodeFactory("A", 0, false, 0,0);
        var bOperandNode    = Utils.operandNodeFactory("B", 1, false, 1,1);
        var cOperandNode    = Utils.operandNodeFactory("C", 2, false, 2,2);

        var endNode     = Utils.operandNodeFactory("#", 3, false, 3, 3);

        var plusNode    = Utils.unaryNodeFactory("#", aOperandNode, false, 0, 0);
        var questionNode= Utils.unaryNodeFactory("?", bOperandNode, true, 1, 1);
        var starNode    = Utils.unaryNodeFactory("*", cOperandNode, true, 2, 2);

        var andNode     = Utils.binOpNodeFactory("°", plusNode, questionNode, false, new Integer[]{0}, new Integer[]{0,1});
        var orNode      = Utils.binOpNodeFactory("|", andNode, starNode, true, new Integer[]{0,2}, new Integer[]{0,1,2});

        var rootNode = Utils.binOpNodeFactory("°", orNode, endNode, false, new Integer[]{0,2,3}, new Integer[]{3});

        return rootNode;
    }

    private static TestCase<Visitable, Visitable> createTestCase() {
        return new TestCase<>(createInput(), createExpected());
    }
    
    @TestFactory
    public DynamicTest NFLFactoryTest() {

        var testCase = createTestCase();

        return DynamicTest.dynamicTest("Test nullable firstpos lastpos visitor",
                        () -> {
                            var visitor = new VisitorNFL();

                            var tree = testCase.getInput();
                            DepthFirstIterator.traverse(tree, visitor);

                            var actual = tree;
                            var expected = testCase.getExpected();

                            Assert.assertTrue(equals(actual,expected));
                        });
    }


    private boolean equals(Visitable expected, Visitable visited)
    {
        if (expected == null && visited == null) return true;
        if (expected == null || visited == null) return false;
        if (expected.getClass() != visited.getClass()) return false;
        if (expected.getClass() == BinOpNode.class)
        {
            BinOpNode op1 = (BinOpNode) expected;
            BinOpNode op2 = (BinOpNode) visited;
            return op1.getNullable().equals(op2.getNullable()) &&
                    op1.getFirstpos().equals(op2.getFirstpos()) &&
                    op1.getLastpos().equals(op2.getLastpos()) &&
                    equals(op1.getLeft(), op2.getLeft()) &&
                    equals(op1.getRight(), op2.getRight());
        }
        if (expected.getClass() == UnaryOpNode.class)
        {
            UnaryOpNode op1 = (UnaryOpNode) expected;
            UnaryOpNode op2 = (UnaryOpNode) visited;
            return op1.getNullable().equals(op2.getNullable()) &&
                    op1.getFirstpos().equals(op2.getFirstpos()) &&
                    op1.getLastpos().equals(op2.getLastpos()) &&
                    equals(op1.getSubNode(), op2.getSubNode());
        }
        if (expected.getClass() == OperandNode.class)
        {
            OperandNode op1 = (OperandNode) expected;
            OperandNode op2 = (OperandNode) visited;
            return op1.getNullable().equals(op2.getNullable()) &&
                    op1.getFirstpos().equals(op2.getFirstpos()) &&
                    op1.getLastpos().equals(op2.getLastpos());
        }
        throw new IllegalStateException(
                String.format( "Beide Wurzelknoten sind Instanzen der Klasse %1$s !" + " Dies ist nicht erlaubt!", expected.getClass().getSimpleName())
);
    }
}
