import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class ParserTest {

    @Before
    public void init() {

    }

    @TestFactory
    Stream<DynamicTest> parserFactoryTest() {
        var inputList = new ArrayList<String>(Arrays.asList("#",
                "(A)#",
                "(A*)#",
                "(A?)#",
                "(A+)#",
                "(AB)#",
                "(A*B+)#",
                "((AB)*)#",
                "(A|B)#",
                "(123)#"
                ));


        //#
        var case01 = new OperandNode("#");

        //(A)#
        var case02 = new BinOpNode("°",
                new OperandNode("A"),
                new OperandNode("#"));

        //(A*)#
        var case03 = new BinOpNode("°",
                new UnaryOpNode("*", new OperandNode("A")),
                new OperandNode("#"));

        //(A?)#
        var case04 = new BinOpNode("°",
                new UnaryOpNode("?", new OperandNode("A")),
                new OperandNode("#"));

        //(A+)#
        var case05 = new BinOpNode("°",
                new UnaryOpNode("+", new OperandNode("A")),
                new OperandNode("#"));

        //(AB)#
        var case06 = new BinOpNode("°",
                new BinOpNode("°",
                        new OperandNode("A"),
                        new OperandNode("B")),
                new OperandNode("#"));

        //(A*B+)#
        var case07 = new BinOpNode("°",
                new BinOpNode("°",
                        new UnaryOpNode("*",
                                new OperandNode("A")),
                        new UnaryOpNode("+",
                                new OperandNode("B"))),
                new OperandNode("#"));

        //((AB)*)#
        var case08 = new BinOpNode("°",
                        new UnaryOpNode("*",
                                new BinOpNode("°",
                                    new OperandNode("A"),
                                    new OperandNode("B"))),
                    new OperandNode("#"));

        //(A|B)#
        var case09 = new BinOpNode("°",
                new BinOpNode("|",
                    new OperandNode("A"),
                    new OperandNode("B")),
                new OperandNode("#"));

        //(123)#
        var case10 = new BinOpNode("°",
                new BinOpNode("°",
                        new BinOpNode("°",
                                new OperandNode("1"),
                                new OperandNode("2")),
                        new OperandNode("3")),
                new OperandNode("#"));

        var expectedList = new ArrayList<Visitable>(Arrays.asList(case01,
                case02,
                case03,
                case04,
                case05,
                case06,
                case07,
                case08,
                case09,
                case10));


        return inputList.stream()
                .map(input -> DynamicTest.dynamicTest("Parsing: " + input,
                        () -> {
                            int id = inputList.indexOf(input);

                            var parser = new Parser(input);
                            var actual = parser.run();
                            var expected = expectedList.get(id);

                            Assert.assertTrue(equals(actual,expected));
                        }));
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
            return op1.getPosition() == op2.getPosition() && op1.getSymbol().equals(op2.getSymbol());
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
