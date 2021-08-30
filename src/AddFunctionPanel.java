import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AddFunctionPanel extends JPanel implements ActionListener {
	private final String FONT = "Verdana";
	private final int FUNCTION_EQUALS_FONT_SIZE = 30;
	private final int DISPLAY_LABEL_FONT_SIZE = 30;
	private final int CALCULATOR_BUTTONS_COLUMN = 8;
	private final int CALCULATOR_BUTTONS_ROW = 4;

	private final int DISPLAY_LABEL_X = 240;
	private final int DISPLAY_LABEL_Y = 150;
	private final int DISPLAY_LABEL_WIDTH = 685;
	private final int DISPLAY_LABEL_HEIGHT = 130;

	private final int CALCULATOR_BUTTONS_TOP_LEFT_X = 150;
	private final int CALCULATOR_BUTTONS_TOP_LEFT_Y = 350;
	private final int CALCULATOR_BUTTONS_WIDTH = 80;
	private final int CALCULATOR_BUTTONS_HEIGHT = 50;

	private final int BACKGROUND_RECTANGLE_X = 115;
	private final int BACKGROUND_RECTANGLE_Y = 150;
	private final int BACKGROUND_RECTANGLE_WIDTH = 715;
	private final int BACKGROUND_RECTANGLE_HEIGHT = 130;

	private final int BACK_BUTTONS_TOP_LEFT_X = 135;
	private final int BACK_BUTTONS_TOP_LEFT_Y = 25;
	private final int BACK_BUTTONS_WIDTH = 180;
	private final int BACK_BUTTONS_HEIGHT = 50;

	private final int FUNCTION_EQUALS_X = 130;
	private final int FUNCTION_EQUALS_Y = 190;
	private final int FUNCTION_EQUALS_WIDTH = 150;
	private final int FUNCTION_EQUALS_HEIGHT = 50;

	private final double X_BOUND_FOR_CHECKING = 10000;
	private final double X_INCREMENT_FOR_CHECKING = 100;

	public boolean show = true;
	private boolean lastButtonWasEqual = false;
	private JLabel functionEquals = new JLabel("f(x) = ");
	private Image calculatorButtonImages;
	private JButton[][] calculatorButtons = new JButton[CALCULATOR_BUTTONS_COLUMN][CALCULATOR_BUTTONS_ROW];
	public JButton backButton = new JButton();
	private String processText = "";
	private String displayText = "";
	private StringStack input = new StringStack();
	private StringStack processInput = new StringStack();
	private JLabel displayLabel = new JLabel(displayText);
	private String[][] addToDisplayText = { { "(", ")", "^(", "7", "8", "9", "del", "ac" },
			{ "arcsin(", "sin(", "(x)", "4", "5", "6", "*", "/" }, { "arcos(", "cos(", "ln(", "1", "2", "3", "+", "-" },
			{ "arctan(", "tan(", "abs(", "0", ".", "e", "pi", "=" } };
	private String[][] addToProcessText = { { "(", ")", "^(", "7", "8", "9", "b", "c" },
			{ "d(", "e(", "(x)", "4", "5", "6", "*", "/" }, { "h(", "i(", "g(", "1", "2", "3", "+", "@" },
			{ "l(", "m(", "j(", "0", ".", "o", "n", "q" },

	};

	private Image backImage = new ImageIcon("zImages\\AddFunction\\back.png").getImage();

	public AddFunctionPanel(int w, int h) {
		this.setLayout(null);
		this.setSize(w, h);
		setUpButtons();
		backButton.setBounds(BACK_BUTTONS_TOP_LEFT_X, BACK_BUTTONS_TOP_LEFT_Y, BACK_BUTTONS_WIDTH, BACK_BUTTONS_HEIGHT);
		backButton.setIcon(new ImageIcon(
				backImage.getScaledInstance(BACK_BUTTONS_WIDTH, BACK_BUTTONS_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
		backButton.addActionListener(this);
		this.add(backButton);

		functionEquals.setBackground(Color.white);
		functionEquals.setForeground(Color.black);
		functionEquals.setFont(new Font(FONT, Font.PLAIN, FUNCTION_EQUALS_FONT_SIZE));
		functionEquals.setBounds(FUNCTION_EQUALS_X, FUNCTION_EQUALS_Y, FUNCTION_EQUALS_WIDTH, FUNCTION_EQUALS_HEIGHT);
		this.add(functionEquals);

		displayLabel.setBackground(Color.white);
		displayLabel.setForeground(Color.black);
		displayLabel.setFont(new Font(FONT, Font.PLAIN, DISPLAY_LABEL_FONT_SIZE));
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
				calculatorButtonImages = new ImageIcon("src\\Images\\AddFunction\\" + i + "_" + j + ".png").getImage();
				calculatorButtons[i][j]
						.setIcon(new ImageIcon(calculatorButtonImages.getScaledInstance(CALCULATOR_BUTTONS_WIDTH,
								CALCULATOR_BUTTONS_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
				this.add(calculatorButtons[i][j]);
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		((Graphics2D) g2d).setStroke(new BasicStroke(3));
		g.setColor(Color.black);
		g.drawRect(BACKGROUND_RECTANGLE_X, BACKGROUND_RECTANGLE_Y, BACKGROUND_RECTANGLE_WIDTH,
				BACKGROUND_RECTANGLE_HEIGHT);
		displayLabel.setText(displayText);
		repaint();
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
				if (!processText.isEmpty()) {
					boolean isValid = false;
					String s;
					for (double z = -X_BOUND_FOR_CHECKING; z < 0; z+=X_INCREMENT_FOR_CHECKING) {
						s = processText.replaceAll("x", "0@"+z);
						try {
							eval(s);
							isValid = true;
							break;
						} catch (Exception ex) {

						}
					}
					if (isValid) {
						Function f = new Function(processText, displayText);
						GraphingPanel.functions.add(f); backButton.doClick();
					}
					else {
						for (double z = 0; z < X_BOUND_FOR_CHECKING; z+=X_INCREMENT_FOR_CHECKING) {
							s = processText.replaceAll("x", z+"");
							try {
								eval(s);
								isValid = true;
								break;
							} catch (Exception ex) {

							}
						}
						if (isValid) {
							Function f = new Function(processText,displayText);
							GraphingPanel.functions.add(f); backButton.doClick();
						}
						else {
							displayText = "Invalid Input";
						}
					}

				} else {
					displayText = "Invalid Input";
				}
				lastButtonWasEqual = true;
			} else if (e.getSource() == calculatorButtons[7][0]) {
				displayText = "";
				input.clear();
				processText = "";
				processInput.clear();
			}
			// If the user clicks del button
			else if (e.getSource() == calculatorButtons[6][0]) {
				if (!displayText.isEmpty()) {
					int lastItemLength = input.pop().length();
					displayText = displayText.substring(0, displayText.length() - lastItemLength);
					lastItemLength = processInput.pop().length();
					processText = processText.substring(0, processText.length() - lastItemLength);
				}
			}

			else {
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
		} catch (

		Exception exception) {
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
