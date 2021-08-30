import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ScientificPanel extends JPanel implements ActionListener {
	private final String DISPLAY_LABEL_FONT = "Verdana";
	private final int DISPLAY_LABEL_FONT_SIZE = 20;

	private final int DISPLAY_LABEL_X = 155;
	private final int DISPLAY_LABEL_Y = 30;
	private final int DISPLAY_LABEL_WIDTH = 685;
	private final int DISPLAY_LABEL_HEIGHT = 130;

	private final int BACKGROUND_RECTANGLE_X = 135;
	private final int BACKGROUND_RECTANGLE_Y = 30;
	private final int BACKGROUND_RECTANGLE_WIDTH = 715;
	private final int BACKGROUND_RECTANGLE_HEIGHT = 130;

	private final int CALCULATOR_BUTTONS_TOP_LEFT_X = 135;
	private final int CALCULATOR_BUTTONS_TOP_LEFT_Y = 200;
	private final int CALCULATOR_BUTTONS_WIDTH = 80;
	private final int CALCULATOR_BUTTONS_HEIGHT = 50;
	private final int CALCULATOR_BUTTONS_COLUMN = 9;
	private final int CALCULATOR_BUTTONS_ROW = 4;

	public boolean notifcationShown = false;
	private int panelWidth;
	private int panelHeight;
	private boolean lastButtonWasEqual = false;
	private String processText = "";
	private String displayText = "";
	private StringStack input = new StringStack();
	private StringStack processInput = new StringStack();
	private JLabel displayLabel = new JLabel(displayText);
	private JButton[][] calculatorButtons = new JButton[CALCULATOR_BUTTONS_COLUMN][CALCULATOR_BUTTONS_ROW];
	private String[][] addToDisplayText = { { "(", ")", "^(", "e^(", "7", "8", "9", "del", "ac" },
			{ "arcsin(", "sin(", "log(", "ln(", "4", "5", "6", "*", "/" },
			{ "arcos(", "cos(", "abs(", "factorial(", "1", "2", "3", "+", "-" },
			{ "arctan(", "tan(", "pi", "e", "0", ".", "factor(", "%", "=", } };
	private String[][] addToProcessText = { { "(", ")", "^(", "a(", "7", "8", "9", "b", "c" },
			{ "d(", "e(", "f(", "g(", "4", "5", "6", "*", "/" }, { "h(", "i(", "j(", "k(", "1", "2", "3", "+", "@" },
			{ "l(", "m(", "n", "o", "0", ".", "p(", "%", "q" }, };

	private Image calculatorButtonImages;
	private Image backgroundImage;

	public ScientificPanel(int panelWidth, int panelHeight) {
		this.setLayout(null);
		this.setSize(panelWidth, panelHeight);
		setUpButtons();
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		displayLabel.setForeground(Color.white);
		displayLabel.setBackground(Color.black);
		displayLabel.setFont(new Font(DISPLAY_LABEL_FONT, Font.PLAIN, DISPLAY_LABEL_FONT_SIZE));
		displayLabel.setBounds(DISPLAY_LABEL_X, DISPLAY_LABEL_Y, DISPLAY_LABEL_WIDTH, DISPLAY_LABEL_HEIGHT);
		this.add(displayLabel);

	}

	public void setUpButtons() {
		for (int i = 0; i < CALCULATOR_BUTTONS_COLUMN; i++) {
			for (int j = 0; j < CALCULATOR_BUTTONS_ROW; j++) {
				calculatorButtons[i][j] = new JButton();
				calculatorButtons[i][j].setBounds(CALCULATOR_BUTTONS_TOP_LEFT_X + i * CALCULATOR_BUTTONS_WIDTH,
						CALCULATOR_BUTTONS_TOP_LEFT_Y + j * CALCULATOR_BUTTONS_HEIGHT, CALCULATOR_BUTTONS_WIDTH,
						CALCULATOR_BUTTONS_HEIGHT);
				calculatorButtons[i][j].addActionListener(this);
				calculatorButtonImages = new ImageIcon("src\\Images\\ScientificPanel\\" + i + "_" + j + ".png")
						.getImage();
				calculatorButtons[i][j]
						.setIcon(new ImageIcon(calculatorButtonImages.getScaledInstance(CALCULATOR_BUTTONS_WIDTH,
								CALCULATOR_BUTTONS_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
				this.add(calculatorButtons[i][j]);
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		backgroundImage = new ImageIcon("src\\Images\\ScientificPanel\\background.png").getImage();
		g.drawImage(backgroundImage, 0, 0, panelWidth, panelHeight, this);
		Graphics2D g2d = (Graphics2D) g;
		((Graphics2D) g2d).setStroke(new BasicStroke(3));
		g.setColor(Color.black);
		g.fillRect(BACKGROUND_RECTANGLE_X, BACKGROUND_RECTANGLE_Y, BACKGROUND_RECTANGLE_WIDTH,
				BACKGROUND_RECTANGLE_HEIGHT);
		displayLabel.setText(displayText);
		repaint();

	}

	public String factor(long n) {
		if (n == 0)
			return "0=0";
		if (n == 1)
			return "1=1";
		String s = n + "=";
		long count = 2;
		while (n != 1) {
			if (n % count == 0) {
				n = n / count;
				s += count + "*";
				count--;
			}
			count++;
		}
		s = s.substring(0, s.length() - 1);
		return s;
	}

	public double factorial(long n) {
		if (n == 0)
			return 1;
		else
			return n * factorial(n - 1);
	}

	public void actionPerformed(ActionEvent e) {
		try {
			if (lastButtonWasEqual) {
				displayText = "";
				processText = "";
				input.clear();
				processInput.clear();
				lastButtonWasEqual = false;
			}
			if (e.getSource() == calculatorButtons[CALCULATOR_BUTTONS_COLUMN - 1][CALCULATOR_BUTTONS_ROW - 1]) {
				try {
					if (processText.charAt(0) == 'p') {
						String s = (eval(processText)) + "";
						int indexOfDecimal = s.indexOf(".");
						boolean isInteger = true;
						for (int i = indexOfDecimal + 1; i < s.length(); i++) {
							if (s.charAt(i) != '0') {
								isInteger = false;
								break;
							}
						}
						if (isInteger)
							s = (int) (eval(processText)) + "";
						int n = Integer.parseInt(s);
						displayText = factor(n);
					} else {
						displayText = eval(processText) + "";
						int indexOfDecimal = displayText.indexOf(".");
						boolean isInteger = true;
						for (int i = indexOfDecimal + 1; i < displayText.length(); i++) {
							if (displayText.charAt(i) != '0') {
								isInteger = false;
								break;
							}
						}
						if (isInteger)
							displayText = (int) (eval(processText)) + "";
					}
				} catch (Exception ex) {
					displayText = "Invalid Input";
				}
				lastButtonWasEqual = true;
			} else if (e.getSource() == calculatorButtons[8][0]) {
				displayText = "";
				input.clear();
				processText = "";
				processInput.clear();
			} else if (e.getSource() == calculatorButtons[7][0]) {
				if (!displayText.isEmpty()) {
					int lastItemLength = input.pop().length();
					displayText = displayText.substring(0, displayText.length() - lastItemLength);
					lastItemLength = processInput.pop().length();
					processText = processText.substring(0, processText.length() - lastItemLength);
				}
			} else {
				for (int i = 0; i < CALCULATOR_BUTTONS_COLUMN; i++) {
					for (int j = 0; j < CALCULATOR_BUTTONS_ROW; j++) {
						if (e.getSource() == calculatorButtons[i][j]) {
							displayText += addToDisplayText[j][i];
							input.add(addToDisplayText[j][i]);
							processText += addToProcessText[j][i];
							processInput.add(addToProcessText[j][i]);
						}
					}
				}
			}
		} catch (Exception exception) {
			displayText = "Invalid Input";
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
				} else if (s.charAt(i) == 'k') {
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
					try {
						double value = eval(s.substring(i + 1, endIndex + 1));
						if (value == Math.floor(value) && !Double.isInfinite(value)) {
							String p = factorial((long) value) + "";
							term.add(Double.parseDouble(p));
							i = endIndex;
						} else {
							return (Double) (null);
						}
					} catch (Exception ex) {
						return (Double) null;
					}
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
