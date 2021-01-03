//Dummy Code acts as a base for the parser, to generate a parse tree.

package SimpleParser;

import java.util.Stack;

public abstract class ParserBase {

    public abstract double evaluate(String s) throws ParserException;

    public StringBuffer deleteSpaces(StringBuffer sbuf)
    {
        for (int i = 0; i < sbuf.length(); i++)
        {
            if (sbuf.charAt(i) == ' ')
            {
                    sbuf.deleteCharAt(i);
                    i--;
            }
        }
        return sbuf;
    }

    public String createPostfix(String s) throws ParserException
    {
        Stack<Character> stack = new Stack<Character>();
        StringBuffer resStr = new StringBuffer();

        char c;
        int strpos = 0;
        while (strpos < s.length())
        {
            c = s.charAt(strpos);
            if (c == ')')
            {

                while (!stack.empty() && !stack.peek().equals('('))
                {
                    resStr.append(stack.pop());
                }
                if (!stack.empty())
                    stack.pop();
            }
            else if (c == '+')
            {
                if (!stack.empty() && (stack.peek().equals('+') || stack.peek().equals('-') ||
                                       stack.peek().equals('*') || stack.peek().equals('/')))
                {
                    resStr.append(stack.pop());
                }
                stack.push(c);
            }
            else if (c == '-')
            {
                if (!stack.empty() && (stack.peek().equals('+') || stack.peek().equals('-') ||
                                       stack.peek().equals('*') || stack.peek().equals('/')))
                {
                    resStr.append(stack.pop());
                }
                stack.push(c);
            }
            else if (c == '*')
            {
                if (!stack.empty() && (stack.peek().equals('*') || stack.peek().equals('/')))
                {
                    resStr.append(stack.pop());
                }
                stack.push(c);
            }
            else if (c == '/')
            {
                if (!stack.empty() && (stack.peek().equals('*') || stack.peek().equals('/')))
                {
                    resStr.append(stack.pop());
                }
                stack.push(c);
            }
            else if (c == '(')
            {
                
                stack.push(c);
            }
            else if (c >= '0' && c <= '9')
            {
                while ( (c >= '0' && c <= '9') || c == '.')
                {
                    resStr.append(c);
                    if (strpos+1 < s.length())
                        c = s.charAt(++strpos);
                    else
                    {
                        c = 0;
                        strpos = s.length();
                    }
                }

                strpos--;
            }
            else
            {
                throw new ParserException("Invalid symbol: " + c);
            }

            strpos++;
 
            resStr.append(" ");
        }

        while (!stack.empty())
        {
            resStr.append(stack.pop());
            resStr.append(" ");
        }

        resStr.deleteCharAt(resStr.length()-1);

        for (int i = 0; i < resStr.length() - 1; i++)
        {
            if (resStr.charAt(i) == ' ' && resStr.charAt(i+1) == ' ')
            {
                resStr.deleteCharAt(i+1);
                i--;
            }
        }

        return resStr.toString();
    }
}
