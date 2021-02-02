import org.junit.Test;

public class ParserTest {

    @Test
    public void simpleParserTest() {
        Parser parser = new Parser("(A*|B?)#");
        Visitable v = parser.run();
        var str = v.toString();
    }
}
