import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParserTest {

    @Before
    public void init() {

    }

    @TestFactory
    public void parserTest(final String input, final Visitable expected) {
        var parser = new Parser(input);
        var visitable = parser.run();
        Assert.assertTrue(equals(visitable,expected));
    }


    Stream<DynamicTest> dynamicTestsFromStreamInJava8() {

        List<String> inputs = new ArrayList<String>(Arrays.asList("(A*|B?)#", "(A*|B?|(ABBC)*)#"));

        return inputList.stream()
                .map(dom -> DynamicTest.dynamicTest("Resolving: " + dom,
                        () -> {int id = inputList.indexOf(dom);

                            assertEquals(outputList.get(id), resolver.resolveDomain(dom));
                        }));
    }

    @Test
    public void simpleParserTest() {
        Parser parser = new Parser("(A*|B?)#");
        Visitable v = parser.run();
        System.out.printf("\n\n");

        Parser parser2 = new Parser("(A*|B?|(ABBC)*)#");

        /*
                °
               / \
              |   #
             / \
            /   \
           |     \
          / \     *
         *   ?    |
         |   |    °
         A   B   / \
                °   C
               / \
              °   B
             / \
            A   B
         */
        Visitable v2 = parser2.run();
        System.out.printf("\n\n");

        Parser parser3 = new Parser("(HALLO123)#");
        Visitable v3 = parser.run();
    }


    private static boolean equals(Visitable v1, Visitable v2)
    {
        if (v1 == v2)
            return true;
        if (v1 == null)
            return false;
        if (v2 == null)
            return false;
        if (v1.getClass() != v2.getClass())
            return false;
        if (v1.getClass() == OperandNode.class)
        {
            OperandNode op1 = (OperandNode) v1;
            OperandNode op2 = (OperandNode) v2;
            return op1.position == op2.position && op1.symbol.equals(op2.symbol);
        }
        if (v1.getClass() == UnaryOpNode.class)
        {
            UnaryOpNode op1 = (UnaryOpNode) v1;
            UnaryOpNode op2 = (UnaryOpNode) v2;
            return op1.getOperator().equals(op2.getOperator()) && equals(op1.getSubNode(),
                    op2.getSubNode());
        }
        if (v1.getClass() == BinOpNode.class)
        {
            BinOpNode op1 = (BinOpNode) v1;
            BinOpNode op2 = (BinOpNode) v2;
            return op1.getOperator().equals(op2.getOperator()) &&
                    equals(op1.getLeft(), op2.getLeft()) &&
                    equals(op1.getRight(), op2.getRight());
        }
        throw new IllegalStateException("Ungueltiger Knotentyp!");
    }


}
