import java.io.*;

class Parser
{
  static int lookahead;

  public Parser () throws IOException
  {lookahead = System.in.read ();}

  void expr () throws IOException
  {
    term ();
    while (true)
    {
      if (lookahead == '+')
      {
        this.match ('+');
        term ();
        System.out.write ('+');
      }
      else if (lookahead == '-')
      {
        this.match ('-');
        term ();
        System.out.write ('-');
      }
      else
        return;
    }
  }

  void term () throws IOException
  {
    if (Character.isDigit ((char) lookahead))
    {
      System.out.write ((char) lookahead);
      this.match (lookahead);
    }
    else
      throw new Error ("syntax error");
  }

  void match (int t) throws IOException
  {
    if (lookahead == t)
      lookahead = System.in.read ();
    else
      throw new Error ("syntax error");
  }
}