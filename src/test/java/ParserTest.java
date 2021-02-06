import org.junit.Assert;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class ParserTest {

    // Test with single exit Token. Should be valid.
    private static TestCase<String, Visitable> createTestCase01() {
        //#
        var parserExpected01 = new OperandNode("#");
        return new TestCase<String,Visitable>("#",parserExpected01);
    }

    private static TestCase<String, Visitable> createTestCase02() {
        //(A)#          Should be valid.
        var parserExpected02 = new BinOpNode("°",
                new OperandNode("A"),
                new OperandNode("#"));
        return new TestCase<String,Visitable>("(A)#",parserExpected02);
    }

    private static TestCase<String, Visitable> createTestCase03() {
        //(A*)#         Should be valid.
        var parserExpected03 = new BinOpNode("°",
                new UnaryOpNode("*", new OperandNode("A")),
                new OperandNode("#"));
        return new TestCase<String,Visitable>("(A*)#",parserExpected03);
    }

    private static TestCase<String, Visitable> createTestCase04() {
        //(A?)#         Should be valid.
        var parserExpected04 = new BinOpNode("°",
                new UnaryOpNode("?", new OperandNode("A")),
                new OperandNode("#"));
        return new TestCase<String,Visitable>("(A?)#",parserExpected04);
    }

    private static TestCase<String, Visitable> createTestCase05() {
        //(A+)#         Should be valid.
        var parserExpected05 = new BinOpNode("°",
                new UnaryOpNode("+", new OperandNode("A")),
                new OperandNode("#"));
        return new TestCase<String,Visitable>("(A+)#",parserExpected05);
    }

    private static TestCase<String, Visitable> createTestCase06() {
        //(AB)#         Should be valid.
        var parserExpected06 = new BinOpNode("°",
                new BinOpNode("°",
                        new OperandNode("A"),
                        new OperandNode("B")),
                new OperandNode("#"));
        return new TestCase<String,Visitable>("(AB)#",parserExpected06);
    }

    private static TestCase<String, Visitable> createTestCase07() {
        //(A*B+)#       Should be valid.
        var parserExpected07 = new BinOpNode("°",
                new BinOpNode("°",
                        new UnaryOpNode("*",
                                new OperandNode("A")),
                        new UnaryOpNode("+",
                                new OperandNode("B"))),
                new OperandNode("#"));
        return new TestCase<String,Visitable>("(A*B+)#",parserExpected07);
    }

    private static TestCase<String, Visitable> createTestCase08() {
        //((AB)*)#      Should be valid.
        var parserExpected08 = new BinOpNode("°",
                new UnaryOpNode("*",
                        new BinOpNode("°",
                                new OperandNode("A"),
                                new OperandNode("B"))),
                new OperandNode("#"));
        return new TestCase<String,Visitable>("((AB)*)#",parserExpected08);
    }

    private static TestCase<String, Visitable> createTestCase09() {
        //(A|B)#        Should be valid.
        var parserExpected09 = new BinOpNode("°",
                new BinOpNode("|",
                        new OperandNode("A"),
                        new OperandNode("B")),
                new OperandNode("#"));
        return new TestCase<String,Visitable>("(A|B)#",parserExpected09);
    }

    private static TestCase<String, Visitable> createTestCase10() {
        //(123)#         Should be valid.
        var parserExpected10 = new BinOpNode("°",
                new BinOpNode("°",
                        new BinOpNode("°",
                                new OperandNode("1"),
                                new OperandNode("2")),
                        new OperandNode("3")),
                new OperandNode("#"));
        return new TestCase<String,Visitable>("(123)#",parserExpected10);
    }

    private static List<TestCase<String, Visitable>> createTestCases() {

            return new ArrayList<>(Arrays.asList(
                    createTestCase01(),
                    createTestCase02(),
                    createTestCase03(),
                    createTestCase04(),
                    createTestCase05(),
                    createTestCase06(),
                    createTestCase07(),
                    createTestCase08(),
                    createTestCase09(),
                    createTestCase10()
            ));
        }


    // Factory Test that runs the Parser through the Expressions
    // and checks if the output is equal to the expected test result.
    @TestFactory
    public Stream<DynamicTest> parserFactoryTest() {

        var testCases = createTestCases();

        return testCases.stream()
                .map(testcase -> DynamicTest.dynamicTest("Parsing: " + testcase.getInput(),
                        () -> {
                            var parser = new Parser(testcase.getInput());
                            var actual = parser.run();
                            var expected = testcase.getExpected();
                            Assert.assertTrue(equals(actual,expected));
                        }));
    }

    // Factory Test that runs the Parser through the invalid expressions
    // and checks if the Parser throws an exception.
    @TestFactory
    public Stream<DynamicTest> parserInvalidInputTest() {

        List<String> inputs = new ArrayList<String>(Arrays.asList("!", "##", "{}$$", "ABAB"));

        return inputs.stream()
                .map(input -> DynamicTest.dynamicTest("Parsing: " + input,
                        () -> {
                            var parser = new Parser(input);

                            try {
                                parser.run();
                            } catch (RuntimeException ex) {
                                return;
                            }

                            throw new RuntimeException("Parser did not throw runtime exception!");
                        }));
    }

    // Checks if both nodes aren't null and equal.
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
        throw new IllegalStateException("Invalid node type!");
    }
}
