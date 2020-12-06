package interpreter;

import java.util.*;

public class ShuntingYard {

    private enum Operator
    {
        ADD(1), SUB(2), MUL(3), DIV(4), UNARY(5), BIGGER_THAN(6),
        LESS_THAN(7), BIGGER_EQUAL_THAN(8), LESS_EQUAL_THAN(9), EQ(10), NOT_EQ(11), NOT(12);
        final int precedence;
        Operator(int p) { precedence = p; }
    }

    private static Map<String, Operator> ops = new HashMap<String, Operator>() {{
        put("+", Operator.ADD);
        put("-", Operator.SUB);
        put("*", Operator.MUL);
        put("/", Operator.DIV);
        put("~", Operator.UNARY);
        put(">", Operator.BIGGER_THAN);
        put("<", Operator.LESS_THAN);
        put(">=", Operator.BIGGER_EQUAL_THAN);
        put("<=", Operator.LESS_EQUAL_THAN);
        put("==", Operator.EQ);
        put("!=", Operator.NOT_EQ);
        put("!", Operator.NOT);
    }};

    private static boolean isHigerPrec(String op, String sub)
    {
        return (ops.containsKey(sub) && ops.get(sub).precedence >= ops.get(op).precedence);
    }

    public static String postfix(String infix)
    {
        StringBuilder output = new StringBuilder();
        Deque<String> stack  = new LinkedList<>();

        for (String token : infix.split("\\s")) {
            // operator
            if (ops.containsKey(token)) {
                while ( ! stack.isEmpty() && isHigerPrec(token, stack.peek()))
                    output.append(stack.pop()).append(' ');
                stack.push(token);

            // left parenthesis
            } else if (token.equals("(")) {
                stack.push(token);

            // right parenthesis
            } else if (token.equals(")")) {
                while ( ! stack.peek().equals("("))
                    output.append(stack.pop()).append(' ');
                stack.pop();

            // digit
            } else {
                output.append(token).append(' ');
            }
        }

        while ( ! stack.isEmpty())
            output.append(stack.pop()).append(' ');

        return output.toString();
    }

}
