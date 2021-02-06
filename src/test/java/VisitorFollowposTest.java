import org.junit.Assert;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.*;


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
        |0   |A       |0, 1, 3    |
        |1   |B       |3          |
        |2   |C       |2, 3       |
        |3   |#       |{}         |
        ---------------------------

        */

    @TestFactory
    public DynamicTest followposFactoryTest() {

        var testCase = createTestCase();

        return DynamicTest.dynamicTest("test followpos visitor",
                () -> {
                    var visitor = new VisitorFollowpos();

                    var tree = testCase.getInput();
                    DepthFirstIterator.traverse(tree, visitor);

                    var actual = visitor.getTable();
                    var expected = testCase.getExpected();

                    Assert.assertEquals(actual,expected);
                });
    }

    private static TestCase<Visitable, TreeMap<Integer, FollowPosTableEntry>> createTestCase() {

        var aOperandNode    = Utils.operandNodeFactory("A", 0, false, 0,0);
        var bOperandNode    = Utils.operandNodeFactory("B", 1, false, 1, 1);
        var cOperandNode    = Utils.operandNodeFactory("C", 2, false, 2 ,2);
        var endNode         = Utils.operandNodeFactory("#", 3, false, 3,3);

        var plusNode        = Utils.unaryNodeFactory("+", aOperandNode, false, 0, 0);
        var questionNode    = Utils.unaryNodeFactory("?", bOperandNode, true, 1, 1);
        var starNode        = Utils.unaryNodeFactory("*", cOperandNode, true, 2, 2);

        var andNode = Utils.binOpNodeFactory("°", plusNode, questionNode, false, new Integer[]{0}, new Integer[]{0,1});
        var orNode  = Utils.binOpNodeFactory("|", andNode, starNode, true, new Integer[]{0,2}, new Integer[]{0,1,2});


        var rootNode = Utils.binOpNodeFactory("°", orNode, endNode, false, new Integer[]{0,2,3}, new Integer[]{3});

        var tableEntry1 = Utils.tableEntryFactory(0, "A", new Integer[] {0, 1, 3});
        var tableEntry2 = Utils.tableEntryFactory(1, "B", new Integer[] {3});
        var tableEntry3 = Utils.tableEntryFactory(2, "C", new Integer[] {2, 3});
        var tableEntry4 = Utils.tableEntryFactory(3, "#", new Integer[] {});

        var table = new TreeMap<Integer, FollowPosTableEntry>();

        table.put(0, tableEntry1);
        table.put(1, tableEntry2);
        table.put(2, tableEntry3);
        table.put(3, tableEntry4);

        return new TestCase<>(rootNode, table);
    }


}
