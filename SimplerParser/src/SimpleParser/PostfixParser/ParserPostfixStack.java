//Dummy code to convert one infix expression to postfix expression. 

package SimpleParser.PostfixParser;

import java.util.Stack;

import SimpleParser.ParserBase;
import SimpleParser.ParserException;

public class ParserPostfixStack extends ParserBase {


    private double evaluatePostfix(String s) throws ParserException
    {
        Stack<Double> stack = new Stack<Double>();
        int strpos = 0;
        char c;
        double x = 0;
        while (strpos < s.length())
        {
            c = s.charAt(strpos);
            x = 0;
            if (c == '+')
            {
                double x1 = Double.valueOf(stack.pop().toString());
                double x2 = Double.valueOf(stack.pop().toString());
                x = x2 + x1;
                stack.push(x);
            }
            else if (c == '-')
            {
                double x1 = Double.valueOf(stack.pop().toString());
                double x2 = Double.valueOf(stack.pop().toString());
                x = x2 - x1;
                stack.push(x);
            }
            else if (c == '*')
            {
                double x1 = Double.valueOf(stack.pop().toString());
                double x2 = Double.valueOf(stack.pop().toString());
                x = x2 * x1;
                stack.push(x);
            }
            else if (c == '/')
            {
                double x1 = Double.valueOf(stack.pop().toString());
                double x2 = Double.valueOf(stack.pop().toString());
                x = x2 / x1;
                stack.push(x);
            }
            else if (c >= '0' && c <= '9')
            { 

                String sub = s.substring(strpos);
                int i;
                for (i = 0; i < sub.length(); i++)
                    if (sub.charAt(i) == ' ')
                        sub = sub.substring(0, i);

                try {
                    x = Double.parseDouble(sub);
                } catch (NumberFormatException ex) {
                    throw new ParserException("String to number parsing exception: " + s);
                }
                stack.push(x);
                strpos += i-1;
            }


            strpos++;
        }

        return x;  
    }

    @Override
    public double evaluate(String s) throws ParserException {
        StringBuffer sBuf = new StringBuffer(s);
        sBuf = deleteSpaces(sBuf);
        String tmpstr = createPostfix(sBuf.toString());
        //System.out.println(tmpstr);
        return evaluatePostfix(tmpstr);
    }
}