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
        var aOperandNode = new OperandNode("A");
        aOperandNode.setPosition(0);
        aOperandNode.setNullable(false);
        aOperandNode.getFirstpos().add(0);
        aOperandNode.getLastpos().add(0);

        var bOperandNode = new OperandNode("B");
        bOperandNode.setPosition(1);
        bOperandNode.setNullable(false);
        bOperandNode.getFirstpos().add(1);
        bOperandNode.getLastpos().add(1);

        var cOperandNode = new OperandNode("C");
        cOperandNode.setPosition(2);
        cOperandNode.setNullable(false);
        cOperandNode.getFirstpos().add(2);
        cOperandNode.getLastpos().add(2);

        var endNode = new OperandNode("#");
        endNode.setPosition(3);
        endNode.setNullable(false);
        endNode.getFirstpos().add(3);
        endNode.getLastpos().add(3);


        var plusNode = new UnaryOpNode("+", aOperandNode);
        plusNode.setNullable(false);
        plusNode.getFirstpos().add(0);
        plusNode.getLastpos().add(0);

        var questionNode = new UnaryOpNode("?", bOperandNode);
        questionNode.setNullable(true);
        questionNode.getFirstpos().add(1);
        questionNode.getLastpos().add(1);

        var starNode = new UnaryOpNode("*", cOperandNode);
        starNode.setNullable(true);
        starNode.getFirstpos().add(2);
        starNode.getLastpos().add(2);

        var andNode = new BinOpNode("°", plusNode, questionNode);
        andNode.setNullable(false);
        andNode.getFirstpos().add(0);
        andNode.getLastpos().add(0);
        andNode.getLastpos().add(1);


        var orNode = new BinOpNode("|", andNode, starNode);
        orNode.setNullable(true);
        orNode.getFirstpos().add(0);
        orNode.getFirstpos().add(2);
        orNode.getLastpos().add(0);
        orNode.getLastpos().add(1);
        orNode.getLastpos().add(2);



        var rootNode = new BinOpNode("°", orNode, endNode);
        rootNode.setNullable(false);
        rootNode.getFirstpos().add(0);
        rootNode.getFirstpos().add(2);
        rootNode.getFirstpos().add(3);
        rootNode.getLastpos().add(3);

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
