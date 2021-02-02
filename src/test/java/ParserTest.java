import org.junit.Test;

import java.util.List;

public class ParserTest {

    @Test
    public void simpleParserTest() {
        Parser parser = new Parser("(A*|B?)#");
        Visitable v = parser.run();
        printTree(v);
        System.out.printf("\n\n");

        Parser parser2 = new Parser("(A*|B?|(ABBC)*)#");
        Visitable v2 = parser2.run();
        printTree(v2);
        System.out.printf("\n\n");

        Parser parser3 = new Parser("(HALLO123)#");
        Visitable v3 = parser.run();
        printTree(v3);

    }

    public void printTree(Visitable v)
    {
        if(v instanceof BinOpNode)
        {
            BinOpNode node = (BinOpNode) v;
            System.out.println(node.getOperator());

            printTree(node.getLeft());

            printTree(node.getRight());
        }
        else if(v instanceof OperandNode)
        {
            OperandNode node = (OperandNode) v;
            System.out.printf("%s\n",node.symbol);
        }
        else if(v instanceof  UnaryOpNode)
        {
            UnaryOpNode node = (UnaryOpNode) v;

            System.out.printf("%s\n",node.getOperator());
            System.out.printf("|\n");
            printTree(node.getSubNode());

        }

    }
}
