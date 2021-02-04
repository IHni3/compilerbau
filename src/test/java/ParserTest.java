import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ParserTest {

    @Before
    public void init() {

    }

    @TestFactory
    public Stream<DynamicTest> parserFactoryTest() {

        var testData = new TestData();

        return testData.getTestCases().stream()
                .map(testcase -> DynamicTest.dynamicTest("Parsing: " + testcase.getInput(),
                        () -> {
                            var parser = new Parser(testcase.getInput());
                            var actual = parser.run();
                            var expected = testcase.getParserExpected();

                            Assert.assertTrue(equals(actual,expected));
                        }));
    }

    @TestFactory
    public Stream<DynamicTest> parserRuntimeExceptionTest() {

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

                            throw new RuntimeException("Parser should throw runtime exception");
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
