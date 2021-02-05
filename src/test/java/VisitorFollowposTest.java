import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import javax.swing.*;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Stream;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

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

                        var tree = testCase.getInput();
                        DepthFirstIterator.traverse(tree, visitor);

                        var actual = visitor.getTable();
                        var expected = testCase.getExpected();

                        Assert.assertEquals(actual,expected);
                    });
        }

    private TestCase<Visitable, List<FollowPosTableEntry>> createTestCase() {

        List<FollowPosTableEntry> table = new ArrayList<FollowPosTableEntry>();

        var aNode = new OperandNode("A");
        aNode.setPosition(0);
        aNode.setNullable(false);
        aNode.getFirstpos().add(0);
        aNode.getLastpos().add(0);
        
        var bNode = new OperandNode("B");
        bNode.setPosition(1);
        bNode.setNullable(false);
        bNode.getFirstpos().add(1);
        bNode.getLastpos().add(1);
        
        var cNode = new OperandNode("C");
        cNode.setPosition(2);
        cNode.setNullable(false);
        cNode.getFirstpos().add(2);
        cNode.getLastpos().add(2);
        
        var zaunNode = new OperandNode("#");
        zaunNode.setPosition(3);
        zaunNode.setNullable(false);
        zaunNode.getFirstpos().add(3);
        zaunNode.getLastpos().add(3);


        var posNode = new UnaryOpNode("+", aNode);
        posNode.setNullable(false);
        posNode.getFirstpos().add(0);
        posNode.getLastpos().add(0);
        
        var frageNode = new UnaryOpNode("?", bNode);
        frageNode.setNullable(true);
        frageNode.getFirstpos().add(1);
        frageNode.getLastpos().add(1);
        
        var klienNode = new UnaryOpNode("*", cNode);
        klienNode.setNullable(true);
        klienNode.getFirstpos().add(2);
        klienNode.getLastpos().add(2);

        var innerKon = new BinOpNode("°", posNode, frageNode);
        innerKon.setNullable(false);
        innerKon.getFirstpos().add(0);
        innerKon.getLastpos().add(0);
        innerKon.getLastpos().add(1);


        var orNode = new BinOpNode("|", innerKon, klienNode);
        orNode.setNullable(true);
        orNode.getFirstpos().add(0);
        orNode.getFirstpos().add(2);
        orNode.getLastpos().add(0);
        orNode.getLastpos().add(1);
        orNode.getLastpos().add(2);



        var rootKon = new BinOpNode("°", orNode, zaunNode);
        rootKon.setNullable(false);
        rootKon.getFirstpos().add(0);
        rootKon.getFirstpos().add(2);
        rootKon.getFirstpos().add(3);
        rootKon.getLastpos().add(3);

        var tableEntry1 = tableEntryFactory(0, "A", new Integer[]{0, 1, 3});

        var tableEntry2 =tableEntryFactory(1, "B", new Integer [] {3});

        var tableEntry3 = tableEntryFactory(2, "C", new Integer[] {2, 3});

        var tableEntry4 = tableEntryFactory(3, "#", new Integer[] {});

        table.add(tableEntry1);
        table.add(tableEntry2);
        table.add(tableEntry3);
        table.add(tableEntry4);

        return new TestCase<Visitable, List<FollowPosTableEntry>>(rootKon, table);
    }

    public FollowPosTableEntry tableEntryFactory(Integer pos, String operand, Integer[] followpos)
    {
        var tableEntry = new FollowPosTableEntry(pos, operand);

            tableEntry.getFollowpos().addAll(Arrays.asList(followpos));
            return  tableEntry;
    }
}
