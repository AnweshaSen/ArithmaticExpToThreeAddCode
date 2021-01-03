//Dummy code for the parser. 

package SimpleParser.RecursiveParser;

import SimpleParser.ParserBase;
import SimpleParser.ParserException;

public class ParserRecursive extends ParserBase {


    private int find(String s, char c)
    {
        int count = 0;
        for (int i = s.length() - 1; i >= 0; i--)
        {
            if (s.charAt(i) == '(') count++;
            if (s.charAt(i) == ')') count--;
            if (s.charAt(i) == c && count == 0) return i;
        }
        return -1;
    }
        
    @Override
    public double evaluate(String s) throws ParserException
    {
        StringBuffer sBuf = new StringBuffer(s);
        sBuf = deleteSpaces(sBuf);
        return evaluateIntern(sBuf.toString());
    }

    private double evaluateIntern(String s) throws ParserException
    {
        int index;

        if (s.isEmpty())
            throw new ParserException("Empty string");

        if (s.charAt(0) == '-' || s.charAt(0) == '+')
            s = '0' + s;

        if ((index = find(s, '+')) >= 0)
        {
            return (evaluateIntern(s.substring(0, index)) +
                    evaluateIntern(s.substring(index+1, s.length())));
        }
        else if ((index = find(s, '-')) >= 0)
        {
            return (evaluateIntern(s.substring(0, index)) -
                    evaluateIntern(s.substring(index+1, s.length())));
        }
        else if ((index = find(s, '*')) >= 0)
        {
            return (evaluateIntern(s.substring(0, index)) *
                    evaluateIntern(s.substring(index+1, s.length())));
        }
        else if ((index = find(s, '/')) >= 0)
        {
            return (evaluateIntern(s.substring(0, index)) /
                    evaluateIntern(s.substring(index+1, s.length())));
        }

        if (s.charAt(0) == '(')
        {
            if (s.charAt(s.length()-1) == ')')
                return (evaluate(s.substring(1, s.length()-1)));
            else
                throw new ParserException("Invalid brackets: " + s);
        }


        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            throw new ParserException("String to number parsing exception: " + s);
        }

    }

}
