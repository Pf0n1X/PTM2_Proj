package interpreter;

import java.util.*;
import math_expressions.Expression;
import math_expressions.Variable;
import math_expressions.Number;
import math_expressions.Plus;
import math_expressions.Minus;
import math_expressions.Mul;
import math_expressions.Div;

public class ShuntingYard {

	// This method converts an infix expression to a postfix expression.
	private static String convertInfixToPostfix(List<String> expression, HashMap<String, Variable> varMap) {

		Stack<String> stack = new Stack<String>();
		Queue<String> queue = new LinkedList<String>();
		ArrayList<String> expressionTokens = new ArrayList<String>(expression);

		replaceVars(expressionTokens, varMap);
		deleteOperators(expressionTokens);
		checkMinuses(expressionTokens);

		for (int i = 0; i < expressionTokens.size(); i++) {
			if (isParsableToDouble(expressionTokens.get(i))) {
				queue.add(expressionTokens.get(i));
			} else {
				switch (expressionTokens.get(i)) {
				case "(":
					stack.push("(");
					break;
				case ")":
					while (!stack.isEmpty() && (!stack.peek().equals("("))) {
						queue.add(stack.pop());
					}
					
					if (!stack.isEmpty())
						stack.pop();
					
					break;
				case "/":
					while (!stack.isEmpty() && (!stack.peek().equals("("))
							&& (!stack.peek().equals("+") && (!stack.peek().equals("-")))) {
						queue.add(stack.pop());
					}
					
					stack.push(expressionTokens.get(i));
				case "*":
					while (!stack.isEmpty() && (!stack.peek().equals("("))
							&& (!stack.peek().equals("+") && (!stack.peek().equals("-")))) {
						queue.add(stack.pop());
					}
					
					stack.push(expressionTokens.get(i));
					break;
				case "+":
					while (!stack.isEmpty() && (!stack.peek().equals("("))) {
						queue.add(stack.pop());
					}
					
					stack.push(expressionTokens.get(i));
					break;
				case "-":
					while (!stack.isEmpty() && (!stack.peek().equals("("))) {
						queue.add(stack.pop());
					}
					stack.push(expressionTokens.get(i));
					break;
				}
			}
		}

		while (!stack.isEmpty()) {
			queue.add(stack.pop());
		}

		StringBuilder stringBuilder = new StringBuilder();
		
		for (String str : queue)
			stringBuilder.append(str).append(",");
		
		return stringBuilder.toString();
	}
	
	private static void deleteOperators(ArrayList<String> expressionTokens) {
		
		final String ADD = "+";
		final String SUB = "-";
		final String MUL = "*";
		final String DIV = "/";
		final List<String> ADD_SUB = Arrays.asList(ADD, SUB);
		final List<String> MUL_DIV = Arrays.asList(MUL, DIV);
		
		for (int i = 0; i < expressionTokens.size(); i++) {
			if (ADD_SUB.contains(expressionTokens.get(i))) {
				int counter = 1;
				int startIndex = i;
				boolean flag = !expressionTokens.get(i++).equals(SUB); // True if equals to +

				while (ADD_SUB.contains(expressionTokens.get(i))) {
					if (flag && expressionTokens.get(i).equals(SUB)) {
						flag = false;
					} // +- -+
					else if (!flag && expressionTokens.get(i).equals(SUB)) {
						flag = true;
					} // --
					counter++;
					i++;
				}

				for (int j = 0; j < counter; j++) {
					expressionTokens.remove(startIndex);
				}

				if (startIndex == 0 || expressionTokens.get(startIndex - 1).equals("(")
						|| MUL_DIV.contains(expressionTokens.get(startIndex - 1))) {
					if (!flag)
						expressionTokens.add(startIndex, SUB);
				} else
					expressionTokens.add(startIndex, flag ? ADD : SUB);
			}
		}
		
	}

	// This method deals with minuses appearing in the expressions.
	private static void checkMinuses(ArrayList<String> expressionTokens) {
		
		final List<String> BLOCKING_SYMBOLS = Arrays.asList("*", "/", "(");
		
		for (int i = 0; i < expressionTokens.size(); i++) {
			if (expressionTokens.get(i).equals("-")) {
				
				// Checks if the minus appears in the beginning
				if (i == 0) {
					
					// If it does add a 0 to the calculation
					expressionTokens.remove(i);
					expressionTokens.set(0, "-" + expressionTokens.get(0));
				} else if (BLOCKING_SYMBOLS.contains(expressionTokens.get(i - 1))) {
					
					// Otherwise, check if one of the blocking symbols appear right before it.
					expressionTokens.remove(i);
					expressionTokens.set(i, "-" + expressionTokens.get(i));
				}
			}
		}
	}

	private static void replaceVars(ArrayList<String> expressionTokens, HashMap<String, Variable> varMap) {
		Variable var;
		String key;
		
		for (int i = 0; i < expressionTokens.size(); i++) {
			key = expressionTokens.get(i);
			var = varMap.get(key);
			if (var != null) {
				if (var.calculate() >= 0)
					expressionTokens.set(i, String.valueOf(var.calculate()));
				else {
					expressionTokens.add(i++, "-");
					expressionTokens.set(i, String.valueOf(-var.calculate()));
				}
			}
		}
	}
	
	// This method checks if a string represents a double
	// typed number.
	public static boolean isParsableToDouble(String str) {
		try {
	         Double num = Double.valueOf(str);
	         
	      }catch (NumberFormatException ex) {
	         return false;
	      }
		
		return true;
	}
	
	// This method gets the infix expression, converts it to postfix form
	// creates an expression object from it and executes the code.
	public static double run(ArrayList<String> exp, HashMap<String, Variable> symbolTable) {
		String postFixExpression = convertInfixToPostfix(exp, symbolTable);
		return (runPostfixExpression(postFixExpression));
	}
	
	// Calculate post-fix string expression
	private static double runPostfixExpression(String postfix) {
		Stack<Expression> expressionStack = new Stack<>();
		String[] expressions = postfix.split(",");
		Expression right;
		Expression left;
		
		// Go through all the the expressions after splitting them.
		for (String cur : expressions) {
			
			// If the expression represents a number, push it as a number object back to the stack.
			if (isParsableToDouble(cur)) {
				expressionStack.push(new Number(Double.parseDouble(cur)));
			} else {
				
				// Otherwise, it must be a binary expression which means a conversion
				// to whatever type of expression it is is needed.
				right = expressionStack.pop();
				left = expressionStack.pop();

				switch (cur) {
				case "+":
					expressionStack.add(new Plus(left, right));
					break;
				case "-":
					expressionStack.add(new Minus(left, right));
					break;
				case "*":
					expressionStack.add(new Mul(left, right));
					break;
				case "/":
					expressionStack.add(new Div(left, right));
					break;
				}
			}
		}
		
		return Math.floor(expressionStack.pop().calculate() * 1000) / 1000;
	}
}
