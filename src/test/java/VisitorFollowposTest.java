import org.junit.Assert;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import javax.swing.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class VisitorFollowposTest {


        private final Set<FollowPosTableEntry> table = Collections.<FollowPosTableEntry>emptySet();

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

Verwendete Tabelle
---------------------------
|Pos |Zeichen | Followpos |
---------------------------
|1   |A       |1, 2       |
|2   |B       |1, 2       |
|3   |C       |4          |
|4   |#       |{}         |
---------------------------

 */




        @TestFactory
        public DynamicTest FollowposFactoryTest() {

            var testCase = createTestCase();

            return DynamicTest.dynamicTest("Followpos Visitor überprüfen",
                    () -> {
                        var visitor = new VisitorFollowpos();
                        DepthFirstIterator.traverse(testCase.getInput(), visitor);
                        var actual = testCase.getInput();
                        var expected = testCase.getExpected();

                        Assert.assertTrue(equals((Set<FollowPosTableEntry>) actual,expected));
                    });
        }

    private TestCase<Visitable, Set<FollowPosTableEntry>> createTestCase() {
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
        EXPaNode.getFollowpos().add(1);
        EXPaNode.getFollowpos().add(2);
        addTable(EXPaNode);

        var EXPbNode = new OperandNode("B");
        EXPbNode.setPosition(2);
        EXPbNode.getFollowpos().add(1);
        EXPbNode.getFollowpos().add(2);
        addTable(EXPbNode);

        var EXPcNode = new OperandNode("C");
        EXPcNode.setPosition(3);
        EXPcNode.getFollowpos().add(4);
        addTable(EXPcNode);

        var EXPzaunNode = new OperandNode("#");
        EXPzaunNode.setPosition(4);
        addTable(EXPzaunNode);


        return new TestCase<Visitable, Set<FollowPosTableEntry>>(rootKon, table);
    }

    private void addTable (OperandNode node) {
        table.add(new FollowPosTableEntry(node.getPosition(), node.getSymbol()));
        for (FollowPosTableEntry tableEntry : table) {
            tableEntry.getFollowpos().addAll(node.getFollowpos());
        }
    }

    private boolean equals(Set<FollowPosTableEntry> expected, Set<FollowPosTableEntry> visited)
    {
        if (expected == null && visited == null) return true;
        if (expected == null || visited == null) return false;
        if (expected.getClass() != visited.getClass()) return false;


        throw new IllegalStateException("Ungueltige Followpos!");
    }
}
