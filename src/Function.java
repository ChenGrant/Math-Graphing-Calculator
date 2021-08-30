import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Function extends AbstractFunctions {
	public Function(String s, String d) {
		super(s);
		readExp = d;
		numOfFunctions++;
		num = numOfFunctions;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color c) {
		color = c;
	}

	public String getExpr() {
		return expression;
	}

	public void setExpr(String s) {
		expression = s;
	}

	public double eval(double value) {
		String input = value + "";
		String s = expression;
		if (value < 0) {
			input = "0@" + input.substring(1);
		}
		s = s.replaceAll("x", input);
		return eval(s);
	}
	public double round(double d) {
		return Math.round(d * Math.pow(10, 5)) / Math.pow(10, 5);
	}
	public String derivative(double x) {
		try {
			double leftLimit;
			leftLimit = (eval(x + DELTA_H) - eval(x)) / DELTA_H;
			double rightLimit;
			rightLimit = (eval(x - DELTA_H) - eval(x)) / (-1 * DELTA_H);
			if (Math.abs(leftLimit - rightLimit) > ACCEPTABLE_ERROR_LIMIT) {
				return "DNE";
			} else
				return round(leftLimit) + "";
		} catch (Exception e) {
			return "DNE";
		}
	}

	public double integral(double upper, double lower) {
		double integral = 0;
		double max = Math.max(upper, lower);
		double min = Math.min(upper, lower);
		for (double x = min; x < max; x += DX) {
			integral += (DX * (eval(x) + eval(x + DX)) / 2);
		}
		if (upper < lower)
			return round(integral);
		else
			return round(-integral);
	}

	public String equal(Function f, double leftX, double rightX) {
		Function g = new Function("(" + expression + ")@(" + f.expression + ")", "");
		numOfFunctions--;
		double maxX = Math.max(leftX, rightX);
		double minX = Math.min(rightX, leftX);
		double midX = (maxX + minX) / 2;
		double counter = 0;
		while (true) {
			if (Math.abs(g.eval(midX)) < ACCEPTABLE_ERROR) {
				return round(midX) + "";
			} else if (g.eval(midX) < 0) {
				minX = midX;
				midX = (minX + maxX) / 2;
			} else if (g.eval(midX) > 0) {
				maxX = midX;
				midX = (minX + maxX) / 2;
			}
			counter++;
			if (counter > Math.log(Math.abs(rightX-leftX)*(1/(Math.pow(10, -5))))/Math.log(2)) {
				break;
			}
		}
		maxX = Math.max(leftX, rightX);
		minX = Math.min(rightX, leftX);
		midX = (maxX + minX) / 2;
		counter = 0;
		while (true) {
			if (Math.abs(g.eval(midX)) < ACCEPTABLE_ERROR) {
				return round(midX) + "";
			} else if (g.eval(midX) > 0) {
				minX = midX;
				midX = (minX + maxX) / 2;
			} else if (g.eval(midX) < 0) {
				maxX = midX;
				midX = (minX + maxX) / 2;
			}
			counter++;
			if (counter > Math.log(Math.abs(rightX-leftX)*(1/(Math.pow(10, -5))))/Math.log(2)) {
				break;
			}
		}
		return "No Intersection";
	}

	public String equal(double x, double leftX, double rightX) {
		Function f = new Function(expression + "@" + x, "");
		numOfFunctions--;
		return equal(f, leftX, rightX);
	}

	public String toString() {
		if (numOfFunctions == 1)
			return "The function f(x) = " + readExp + " is function number " + num + " out of " + numOfFunctions
					+ " function.";
		else
			return "The function f(x) = " + readExp + " is function number " + num + " out of " + numOfFunctions
					+ " functions.";
	}

	public void drawFunction(Graphics2D g2d, int minX, int maxX, double f) {
		g2d.setColor(color);
		((Graphics2D) g2d).setStroke(new BasicStroke(3));
		for (double x = minX; x < maxX; x += INCREMENT) {
			double n = x / f;
			try {
				if (!Double.isNaN(f * eval(n))) {
					int d = (int) (f * eval(n));
					g2d.drawLine((int) x, d, (int) (x + 1), (d));
				}
			} catch (Exception ex) {

			}
		}
	}

	public double eval(String s) {
		s += "*1";
		String num = "1234567890.no";
		String operations = "+@,*/%,abcdefghijklmpq^(),";
		StringStack operator = new StringStack();
		DoubleStack term = new DoubleStack();
		for (int i = 0; i < s.length(); i++) {
			if (num.contains(s.charAt(i) + "")) {
				if (s.charAt(i) == 'n') {
					term.add(Math.PI);
				} else if (s.charAt(i) == 'o') {
					term.add(Math.E);
				} else {
					for (int j = i; j < s.length(); j++) {
						if (!num.contains(s.charAt(j) + "")) {
							double d = Double.parseDouble(s.substring(i, j));
							term.add(d);
							i = j;
							break;
						} else if (j == s.length() - 1) {
							double d = Double.parseDouble(s.substring(i));
							term.add(d);
							i = j;
							break;
						}
					}
				}
			}
			if (operations.contains(s.charAt(i) + "")) {
				String op = "";
				if (s.charAt(i) == '(') {
					int endIndex = i;
					int counter = 0;
					for (int z = i + 1; z < s.length(); z++) {
						if (s.charAt(z) == ')') {
							if (counter == 0) {
								endIndex = z;
								break;
							} else {
								counter--;
							}

						} else if (s.charAt(z) == '(')
							counter++;
					}
					String p = eval(s.substring(i + 1, endIndex)) + "";
					term.add(Double.parseDouble(p));
					i = endIndex;
				} else if (s.charAt(i) == 'i') {
					int endIndex = i;
					int counter = 0;
					for (int z = i + 2; z < s.length(); z++) {
						if (s.charAt(z) == ')') {
							if (counter == 0) {
								endIndex = z;
								break;
							} else {
								counter--;
							}

						} else if (s.charAt(z) == '(')
							counter++;
					}
					String p = Math.cos(eval(s.substring(i + 1, endIndex + 1))) + "";
					term.add(Double.parseDouble(p));
					i = endIndex;
				} else if (s.charAt(i) == 'e') {
					int endIndex = i;
					int counter = 0;
					for (int z = i + 2; z < s.length(); z++) {
						if (s.charAt(z) == ')') {
							if (counter == 0) {
								endIndex = z;
								break;
							} else {
								counter--;
							}

						} else if (s.charAt(z) == '(')
							counter++;
					}
					String p = Math.sin(eval(s.substring(i + 1, endIndex + 1))) + "";
					term.add(Double.parseDouble(p));
					i = endIndex;
				} else if (s.charAt(i) == 'm') {
					int endIndex = i;
					int counter = 0;
					for (int z = i + 2; z < s.length(); z++) {
						if (s.charAt(z) == ')') {
							if (counter == 0) {
								endIndex = z;
								break;
							} else {
								counter--;
							}

						} else if (s.charAt(z) == '(')
							counter++;
					}
					String p = Math.tan(eval(s.substring(i + 1, endIndex + 1))) + "";
					term.add(Double.parseDouble(p));
					i = endIndex;
				} else if (s.charAt(i) == 'j') {
					int endIndex = i;
					int counter = 0;
					for (int z = i + 2; z < s.length(); z++) {
						if (s.charAt(z) == ')') {
							if (counter == 0) {
								endIndex = z;
								break;
							} else {
								counter--;
							}

						} else if (s.charAt(z) == '(')
							counter++;
					}
					String p = Math.abs(eval(s.substring(i + 1, endIndex + 1))) + "";
					term.add(Double.parseDouble(p));
					i = endIndex;

				} else if (s.charAt(i) == 'f') {
					int endIndex = i;
					int counter = 0;
					for (int z = i + 2; z < s.length(); z++) {
						if (s.charAt(z) == ')') {
							if (counter == 0) {
								endIndex = z;
								break;
							} else {
								counter--;
							}

						} else if (s.charAt(z) == '(')
							counter++;
					}
					String p = Math.log10(eval(s.substring(i + 1, endIndex + 1))) + "";
					term.add(Double.parseDouble(p));
					i = endIndex;
				} else if (s.charAt(i) == 'd') {
					int endIndex = i;
					int counter = 0;
					for (int z = i + 2; z < s.length(); z++) {
						if (s.charAt(z) == ')') {
							if (counter == 0) {
								endIndex = z;
								break;
							} else {
								counter--;
							}

						} else if (s.charAt(z) == '(')
							counter++;
					}
					String p = Math.asin(eval(s.substring(i + 1, endIndex + 1))) + "";
					term.add(Double.parseDouble(p));
					i = endIndex;
				} else if (s.charAt(i) == 'h') {
					int endIndex = i;
					int counter = 0;
					for (int z = i + 2; z < s.length(); z++) {
						if (s.charAt(z) == ')') {
							if (counter == 0) {
								endIndex = z;
								break;
							} else {
								counter--;
							}

						} else if (s.charAt(z) == '(')
							counter++;
					}
					String p = Math.acos(eval(s.substring(i + 1, endIndex + 1))) + "";
					term.add(Double.parseDouble(p));
					i = endIndex;
				} else if (s.charAt(i) == 'l') {
					int endIndex = i;
					int counter = 0;
					for (int z = i + 2; z < s.length(); z++) {
						if (s.charAt(z) == ')') {
							if (counter == 0) {
								endIndex = z;
								break;
							} else {
								counter--;
							}

						} else if (s.charAt(z) == '(')
							counter++;
					}
					String p = Math.atan(eval(s.substring(i + 1, endIndex + 1))) + "";
					term.add(Double.parseDouble(p));
					i = endIndex;
				} else if (s.charAt(i) == 'a') {
					int endIndex = i;
					int counter = 0;
					for (int z = i + 2; z < s.length(); z++) {
						if (s.charAt(z) == ')') {
							if (counter == 0) {
								endIndex = z;
								break;
							} else {
								counter--;
							}

						} else if (s.charAt(z) == '(')
							counter++;
					}
					String p = Math.exp(eval(s.substring(i + 1, endIndex + 1))) + "";
					term.add(Double.parseDouble(p));
					i = endIndex;
				} else if (s.charAt(i) == 'g') {
					int endIndex = i;
					int counter = 0;
					for (int z = i + 2; z < s.length(); z++) {
						if (s.charAt(z) == ')') {
							if (counter == 0) {
								endIndex = z;
								break;
							} else {
								counter--;
							}

						} else if (s.charAt(z) == '(')
							counter++;
					}
					String p = Math.log(eval(s.substring(i + 1, endIndex + 1))) + "";
					term.add(Double.parseDouble(p));
					i = endIndex;
				} else {
					op = s.charAt(i) + "";
					if (operator.isEmpty()) {
						operator.add(op);
					} else {
						if (operations.indexOf(",", operations.indexOf(operator.peek())) >= operations.indexOf(",",
								operations.indexOf(op))) {
							if (operator.peek().equals("(")) {

							} else if (operator.peek().equals("*")) {
								double term1 = term.pop();
								double term2 = term.pop();
								term.add(term1 * term2);
								operator.pop();
								i--;
							} else if (operator.peek().equals("/")) {
								double term1 = term.pop();
								double term2 = term.pop();
								term.add(term2 / term1);
								operator.pop();
								i--;
							} else if (operator.peek().equals("%")) {
								double term1 = term.pop();
								double term2 = term.pop();
								term.add(term2 % term1);
								operator.pop();
								i--;
							} else if (operator.peek().equals("+")) {
								double term1 = term.pop();
								double term2 = term.pop();
								term.add(term2 + term1);
								operator.pop();
								i--;
							} else if (operator.peek().equals("@")) {
								double term1 = term.pop();
								double term2 = term.pop();
								term.add(term2 - term1);
								operator.pop();
								i--;
							} else if (operator.peek().equals("^")) {
								double term1 = term.pop();
								double term2 = term.pop();
								term.add(Math.pow(term2, term1));
								operator.pop();
								i--;
							}

						} else {
							operator.add(op);
						}
					}
				}
			}
		}
		term.pop();
		operator.pop();
		if (operator.isEmpty()) {
			if (term.size() == 1)
				return term.pop();
			else {
				return (Double) null;
			}
		}

		else {
			if (operator.peek().equals("+")) {
				return term.pop() + term.pop();
			} else {
				double term1 = term.pop();
				double term2 = term.pop();
				return term2 - term1;
			}
		}

	}
}
