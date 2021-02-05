import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import javax.swing.*;
import java.security.InvalidParameterException;
import java.util.*;
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
        |0   |A       |0, 1       |
        |1   |B       |1          |
        |2   |C       |2          |
        |3   |#       |{}         |
        ---------------------------

        */

        @TestFactory
        public DynamicTest followposFactoryTest() {

            var testCase = createTestCase();

            return DynamicTest.dynamicTest("test followpos visitor",
                    () -> {
                        var visitor = new VisitorFollowpos();
                        DepthFirstIterator.traverse(testCase.getInput(), visitor);
                        var actual = visitor.getTable();
                        var expected = testCase.getExpected();

                        Assert.assertEquals(actual,expected);
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



        var tableEntry1 = tableEntryFactory(0, "A", new Integer[]{0, 1, 3});

        var tableEntry2 =tableEntryFactory(1, "B", new Integer [] {3});

        var tableEntry3 = tableEntryFactory(2, "C", new Integer[] {2, 3});

        var tableEntry4 = tableEntryFactory(3, "#", new Integer[] {});

        return new TestCase<Visitable, Set<FollowPosTableEntry>>(rootKon, table);
    }

    public FollowPosTableEntry tableEntryFactory(Integer pos, String operand, Integer[] followpos)
    {
        var tableEntry = new FollowPosTableEntry(pos, operand);

            tableEntry.getFollowpos().addAll(Arrays.asList(followpos));
            return  tableEntry;
    }
}
