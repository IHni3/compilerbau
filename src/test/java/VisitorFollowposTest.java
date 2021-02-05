import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import javax.swing.*;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class VisitorFollowposTest {

        /*
        Used Tree:

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

        Excepted Table
        ---------------------------
        |Pos |Symbol  | Followpos |
        ---------------------------
        |1   |A       |1, 2       |
        |2   |B       |1, 2       |
        |3   |C       |4          |
        |4   |#       |{}         |
        ---------------------------

        */

        @TestFactory
        public DynamicTest followposFactoryTest() {

            var testCase = createTestCase();

            return DynamicTest.dynamicTest("test followpos visitor",
                    () -> {
                        var visitor = new VisitorFollowpos();
                        DepthFirstIterator.traverse(testCase.getInput(), visitor);
                        var actual = testCase.getInput();
                        var expected = testCase.getExpected();

                        Assert.assertEquals((Set<FollowPosTableEntry>) actual,expected);
                    });
        }

    private TestCase<Visitable, Set<FollowPosTableEntry>> createTestCase() {

        var table =  Collections.<FollowPosTableEntry>emptySet();

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
        addNodeToTable(EXPaNode, table);

        var EXPbNode = new OperandNode("B");
        EXPbNode.setPosition(2);
        EXPbNode.getFollowpos().add(1);
        EXPbNode.getFollowpos().add(2);
        addNodeToTable(EXPbNode, table);

        var EXPcNode = new OperandNode("C");
        EXPcNode.setPosition(3);
        EXPcNode.getFollowpos().add(4);
        addNodeToTable(EXPcNode, table);

        var EXPzaunNode = new OperandNode("#");
        EXPzaunNode.setPosition(4);
        addNodeToTable(EXPzaunNode, table);


        return new TestCase<Visitable, Set<FollowPosTableEntry>>(rootKon, table);
    }

    private void addNodeToTable (OperandNode node, Collection<FollowPosTableEntry> table) {
        table.add(new FollowPosTableEntry(node.getPosition(), node.getSymbol()));

        for (FollowPosTableEntry tableEntry : table) {
            tableEntry.getFollowpos().addAll(node.getFollowpos());
        }
    }
}
