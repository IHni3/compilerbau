import org.junit.Assert;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

/*

Verwendeter Baum:

                °
               / \
              /   #
             |
            / \
           °   \
          / \   *
         +   ?  |
         |   |  C
         A   B
 */

public class VisitorNFLTest {

    private TestCase<Visitable, Visitable> createTestCase() {
        var aNode = new OperandNode("A");
        var bNode = new OperandNode("B");
        var cNode = new OperandNode("C");
        var zaunNode = new OperandNode("#");

        var posNode = new UnaryOpNode("+", aNode);
        var frageNode = new UnaryOpNode("?", bNode);
        var klienNode = new UnaryOpNode("*", cNode);

        var innerKon = new BinOpNode("°", posNode, frageNode);
        var orNode = new BinOpNode("|", innerKon, klienNode);
        var rootKon = new BinOpNode("°", orNode, zaunNode);

        var EXPaNode = new OperandNode("A");
        EXPaNode.setPosition(1);
        EXPaNode.setNullable(false);
        EXPaNode.getFirstpos().add(1);
        EXPaNode.getLastpos().add(1);
        
        var EXPbNode = new OperandNode("B");
        EXPbNode.setPosition(2);
        EXPbNode.setNullable(false);
        EXPbNode.getFirstpos().add(2);
        EXPbNode.getLastpos().add(2);
        
        var EXPcNode = new OperandNode("C");
        EXPcNode.setPosition(3);
        EXPcNode.setNullable(false);
        EXPcNode.getFirstpos().add(3);
        EXPcNode.getLastpos().add(3);
        
        var EXPzaunNode = new OperandNode("#");
        EXPzaunNode.setPosition(4);
        EXPzaunNode.setNullable(false);
        EXPzaunNode.getFirstpos().add(4);
        EXPzaunNode.getLastpos().add(4);


        var EXPposNode = new UnaryOpNode("+", aNode);
        EXPposNode.setNullable(true);
        EXPposNode.getFirstpos().add(1);
        EXPposNode.getLastpos().add(1);
        
        var EXPfrageNode = new UnaryOpNode("?", bNode);
        EXPfrageNode.setNullable(true);
        EXPfrageNode.getFirstpos().add(2);
        EXPfrageNode.getLastpos().add(2);
        
        var EXPklienNode = new UnaryOpNode("*", cNode);
        EXPklienNode.setNullable(true);
        EXPklienNode.getFirstpos().add(3);
        EXPklienNode.getLastpos().add(3);

        var EXPinnerKon = new BinOpNode("°", posNode, frageNode);
        EXPinnerKon.setNullable(true);
        EXPinnerKon.getFirstpos().add(1);
        EXPinnerKon.getFirstpos().add(2);
        EXPinnerKon.getLastpos().add(1);
        EXPinnerKon.getLastpos().add(2);


        var EXPorNode = new BinOpNode("|", innerKon, klienNode);
        EXPorNode.setNullable(true);
        EXPorNode.getFirstpos().add(1);
        EXPorNode.getFirstpos().add(2);
        EXPorNode.getFirstpos().add(3);
        EXPorNode.getLastpos().add(1);
        EXPorNode.getLastpos().add(2);
        EXPorNode.getLastpos().add(3);



        var EXProotKon = new BinOpNode("°", orNode, zaunNode);
        EXProotKon.setNullable(false);
        EXProotKon.getFirstpos().add(1);
        EXProotKon.getFirstpos().add(2);
        EXProotKon.getFirstpos().add(3);
        EXProotKon.getFirstpos().add(4);
        EXProotKon.getLastpos().add(4);

        return new TestCase<Visitable, Visitable>(rootKon, EXProotKon);
    }
    
    
    @TestFactory
    public DynamicTest NFLFactoryTest() {

        var testCase = createTestCase();

        return DynamicTest.dynamicTest("Nullable Firstpos Lastpos Visitor überprüfen",
                        () -> {
                            var visitor = new VisitorNFL();
                            DepthFirstIterator.traverse(testCase.getInput(), visitor);
                            var actual = testCase.getInput();
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
