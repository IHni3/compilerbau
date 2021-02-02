public class Parser
{
    private int position;
    private final String input;
//...
    public Parser(String input)
    {
        this.input = input;
        this.position = 0;
    }

    private char getCurrentChar()
    {
        return input.charAt(position);
    }
//...
    private void match(char symbol)
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
/*
    Start → '#' {Start.return = new OperandNode('#');}
    Start → {RegExp.parameter = null;} ('RegExp')''#'
    {
        leaf = new OperandNode('#');
        root = new BinOpNode('°', RegExp.return, leaf);
        Start.return = root;
    }
    RegExp → {Term.parameter = null;}
    Term
    {RE'.parameter = Term.return;}
    RE'
    {RegExp.return = RE'.return;}
        RE' → Ɛ {RE'.return = RE'.parameter;}
        RE' → '|'
        {Term.parameter = null;}
        Term
        {
            root = new BinOpNode('|',
                    RE'.parameter,
            Term.return);
            RE1'.parameter = root;
        } RE1'
        {RE'.return = RE1'.return;}


    */

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