public class Parser
{
    private int position;
    private final String input;
//...
    public Parser(final String input)
    {
        this.input = input;
        this.position = 0;
    }

    private char getCurrentChar()
    {
        return input.charAt(position);
    }
    public String getInput()
    {
        return input;
    }

    private void match(final char symbol)
    {
        if ((input == null) || ("".equals(input)))
        {
            throw new RuntimeException("Syntax error !");
        }
        if (position >= input.length())
        {
            throw new RuntimeException("End of input reached !");
        }
        if (getCurrentChar() != symbol)
        {
            throw new RuntimeException("Syntax error !");
        }

        position++;
    }

    public Visitable run() {
        return startState();
    }

    private Visitable startState() {
        if(getCurrentChar() == '#') {
            match('#');
            
            assertEndOfInput();

            return new OperandNode("#");
        }
        else if(getCurrentChar() == '(') {
            match('(');

            Visitable leafLeft = regExpState();

            match(')');

            match('#');
            OperandNode leafRight = new OperandNode("#");

            assertEndOfInput();

            return new BinOpNode("°", leafLeft, leafRight);
        }
        else
        {
            throw new RuntimeException("Syntax error !");
        }
    };
    private Visitable regExpState() {
            Visitable termNode = termState();
            return regExp1State(termNode);
    };
    private Visitable termState() {
        return termState(null);
    }
    private Visitable termState(Visitable node) {
        //first
        if(Character.isLetterOrDigit(getCurrentChar()) || getCurrentChar() == '(')
        {
            Visitable factorNode = factorState();
            Visitable termParamNode = null;
            if(node != null)
            {
                termParamNode = new BinOpNode("°", node, factorNode);
            }
            else
            {
                termParamNode = factorNode;
            }
            return termState(termParamNode);
        }
        else
        {
            return node;
        }
    };
    private Visitable regExp1State(Visitable node) {
        if(getCurrentChar() == '|')
        {
            match('|');
            Visitable termNode = termState();
            BinOpNode rootNode = new BinOpNode("|", node, termNode);

            return regExp1State(rootNode);
        }
        else {
            return node;
        }
    };
    private Visitable factorState() {
        Visitable node = elemState();
        return h0pState(node);
    };
    private Visitable h0pState(Visitable child) {
        if(getCurrentChar() == '*') {
            match('*');
            return new UnaryOpNode("*", child);
        }
        else if(getCurrentChar() == '+') {
            match('+');
            return new UnaryOpNode("+", child);

        }
        else if(getCurrentChar() == '?') {
            match('?');
            return new UnaryOpNode("?", child);

        }
        else {
            return child;
        }
    };
    private Visitable elemState() {
        if(Character.isLetterOrDigit(getCurrentChar()))
        {
            OperandNode node = alphaNumState();
            return node;
        }
        else if(getCurrentChar() == '(') {
            match('(');
            Visitable node = regExpState();
            match(')');
            return node;
        }
        else {
            throw new RuntimeException("Syntax error !");
        }
    };
    private OperandNode alphaNumState() {
        if(Character.isLetterOrDigit(getCurrentChar()))
        {
            OperandNode node = new OperandNode(String.valueOf(getCurrentChar()));
            match(getCurrentChar());
            return node;
        }
        else
        {
            throw new RuntimeException("Syntax error !");
        }
    };


//------------------------------------------------------------------
// 1. wird benoetigt bei der Regel Start -> '(' RegExp ')''#'
// 2. wird benoetigt bei der Regel Start -> '#'
// 3. wird sonst bei keiner anderen Regel benoetigt
//------------------------------------------------------------------
    private void assertEndOfInput()
    {
        if (position < input.length())
        {
            throw new RuntimeException(" No end of input reached !");
        }
    }
}