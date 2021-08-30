import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class EditFunctionPanel extends JPanel implements ActionListener {
	private final String FONT = "Veranda";
	private final int LABEL_FONT_SIZE = 20;
	private final int TEXT_FIELD_FONT_SIZE = 15;
	private final int ANSWER_TEXT_FIELD_FONT_SIZE = 15;

	private final String LEFT_BUTTON_FILE = "src\\Images\\EditFunctionPanel\\previous.png";
	private final int LEFT_BUTTON_X = 50;
	private final int LEFT_BUTTON_Y = 50;
	private final int LEFT_BUTTON_WIDTH = 150;
	private final int LEFT_BUTTON_HEIGHT = 50;

	private final String RIGHT_BUTTON_FILE = "src\\Images\\EditFunctionPanel\\next.png";
	private final int RIGHT_BUTTON_X = 800;
	private final int RIGHT_BUTTON_Y = 50;
	private final int RIGHT_BUTTON_WIDTH = 150;
	private final int RIGHT_BUTTON_HEIGHT = 50;

	private final String BACK_BUTTON_FILE = "src\\Images\\EditFunctionPanel\\back.png";
	private final int BACK_BUTTON_X = 50;
	private final int BACK_BUTTON_Y = 800;
	private final int BACK_BUTTON_WIDTH = 170;
	private final int BACK_BUTTON_HEIGHT = 50;

	private final String REMOVE_BUTTON_FILE = "src\\Images\\EditFunctionPanel\\remove.png";
	private final int REMOVE_BUTTON_X = 50;
	private final int REMOVE_BUTTON_Y = 700;
	private final int REMOVE_BUTTON_WIDTH = 170;
	private final int REMOVE_BUTTON_HEIGHT = 50;

	private final String SHOW_BUTTON_FILE = "src\\Images\\EditFunctionPanel\\show.png";
	private final String HIDE_BUTTON_FILE = "src\\Images\\EditFunctionPanel\\hide.png";
	private final int SHOW_BUTTON_X = 300;
	private final int SHOW_BUTTON_Y = 700;
	private final int SHOW_BUTTON_WIDTH = 150;
	private final int SHOW_BUTTON_HEIGHT = 50;

	private final String COLOR_BUTTON_FILE = "src\\Images\\EditFunctionPanel\\color.png";
	private final int COLOR_BUTTON_X = 500;
	private final int COLOR_BUTTON_Y = 700;
	private final int COLOR_BUTTON_WIDTH = 120;
	private final int COLOR_BUTTON_HEIGHT = 50;
	
	private final int LABEL_DY = 100;
	private final int LABEL_X = 50;
	private final int LABEL_Y = 100;
	private final int LABEL_WIDTH = 1000;
	private final int LABEL_HEIGHT = 75;

	private final int TEXT_FIELD_0_X = 147;
	private final int TEXT_FIELD_0_Y = 225;
	private final int TEXT_FIELD_0_WIDTH = 120;
	private final int TEXT_FIELD_0_HEIGHT = 25;

	private final int TEXT_FIELD_1_X = 147;
	private final int TEXT_FIELD_1_Y = 327;
	private final int TEXT_FIELD_1_WIDTH = 120;
	private final int TEXT_FIELD_1_HEIGHT = 25;

	private final int TEXT_FIELD_2_X = 170;
	private final int TEXT_FIELD_2_Y = 427;
	private final int TEXT_FIELD_2_WIDTH = 120;
	private final int TEXT_FIELD_2_HEIGHT = 25;

	private final int TEXT_FIELD_3_X = 350;
	private final int TEXT_FIELD_3_Y = 427;
	private final int TEXT_FIELD_3_WIDTH = 120;
	private final int TEXT_FIELD_3_HEIGHT = 25;

	private final int TEXT_FIELD_4_X = 293;
	private final int TEXT_FIELD_4_Y = 527;
	private final int TEXT_FIELD_4_WIDTH = 60;
	private final int TEXT_FIELD_4_HEIGHT = 25;

	private final int TEXT_FIELD_5_X = 525;
	private final int TEXT_FIELD_5_Y = 527;
	private final int TEXT_FIELD_5_WIDTH = 60;
	private final int TEXT_FIELD_5_HEIGHT = 25;
	
	private final int TEXT_FIELD_6_X = 650;
	private final int TEXT_FIELD_6_Y = 527;
	private final int TEXT_FIELD_6_WIDTH = 60;
	private final int TEXT_FIELD_6_HEIGHT = 25;
	
	private final int ANSWER_TEXT_FIELD_1_X = 300;
	private final int ANSWER_TEXT_FIELD_1_Y = 225;
	private final int ANSWER_TEXT_FIELD_1_WIDTH = 120;
	private final int ANSWER_TEXT_FIELD_1_HEIGHT = 25;

	private final int ANSWER_TEXT_FIELD_2_X = 300;
	private final int ANSWER_TEXT_FIELD_2_Y = 327;
	private final int ANSWER_TEXT_FIELD_2_WIDTH = 120;
	private final int ANSWER_TEXT_FIELD_2_HEIGHT = 25;

	private final int ANSWER_TEXT_FIELD_3_X = 507;
	private final int ANSWER_TEXT_FIELD_3_Y = 427;
	private final int ANSWER_TEXT_FIELD_3_WIDTH = 120;
	private final int ANSWER_TEXT_FIELD_3_HEIGHT = 25;

	private final int ANSWER_TEXT_FIELD_4_X = 800;
	private final int ANSWER_TEXT_FIELD_4_Y = 527;
	private final int ANSWER_TEXT_FIELD_4_WIDTH = 120;
	private final int ANSWER_TEXT_FIELD_4_HEIGHT = 25;
	private final String space = "                    ";
	
	private boolean perform = false;
	private boolean valid1 = false;
	private boolean valid2 = false;
	private boolean valid3 = false;
	private int currFunctionNumber = 0;
	private JLabel labels[] = new JLabel[5];
	private JTextField[] textField = new JTextField[7];
	private Image image = new ImageIcon(LEFT_BUTTON_FILE).getImage();
	private boolean showLeftButton = false;
	private boolean showRightButton = true;
	private JButton leftButton = new JButton();
	private JButton rightButton = new JButton();
	public JButton backButton = new JButton();
	private JButton showButton = new JButton();
	private JButton removeButton = new JButton();
	private JButton colorButton = new JButton();
	private JTextField answerTextField1 = new JTextField();
	private JTextField answerTextField2 = new JTextField();
	private JTextField answerTextField3 = new JTextField();
	private JTextField answerTextField4 = new JTextField();

	public EditFunctionPanel(int width, int height) {
		this.setLayout(null);
		this.setSize(width, height);
		leftButton.setBounds(LEFT_BUTTON_X, LEFT_BUTTON_Y, LEFT_BUTTON_WIDTH, LEFT_BUTTON_HEIGHT);
		leftButton.addActionListener(this);
		leftButton.setVisible(showLeftButton);
		leftButton.setIcon(new ImageIcon(
				image.getScaledInstance(LEFT_BUTTON_WIDTH, LEFT_BUTTON_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
		this.add(leftButton);

		rightButton.setBounds(RIGHT_BUTTON_X, RIGHT_BUTTON_Y, RIGHT_BUTTON_WIDTH, RIGHT_BUTTON_HEIGHT);
		rightButton.addActionListener(this);
		rightButton.setVisible(showRightButton);
		image = new ImageIcon(RIGHT_BUTTON_FILE).getImage();
		rightButton.setIcon(new ImageIcon(
				image.getScaledInstance(RIGHT_BUTTON_WIDTH, RIGHT_BUTTON_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
		this.add(rightButton);

		backButton.setBounds(BACK_BUTTON_X, BACK_BUTTON_Y, BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT);
		backButton.addActionListener(this);
		image = new ImageIcon(BACK_BUTTON_FILE).getImage();
		backButton.setIcon(new ImageIcon(
				image.getScaledInstance(BACK_BUTTON_WIDTH, BACK_BUTTON_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
		this.add(backButton);

		removeButton.setBounds(REMOVE_BUTTON_X, REMOVE_BUTTON_Y, REMOVE_BUTTON_WIDTH, REMOVE_BUTTON_HEIGHT);
		removeButton.addActionListener(this);
		image = new ImageIcon(REMOVE_BUTTON_FILE).getImage();
		removeButton.setIcon(new ImageIcon(
				image.getScaledInstance(REMOVE_BUTTON_WIDTH, REMOVE_BUTTON_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
		this.add(removeButton);

		showButton.setBounds(SHOW_BUTTON_X, SHOW_BUTTON_Y, SHOW_BUTTON_WIDTH, SHOW_BUTTON_HEIGHT);
		showButton.addActionListener(this);
		image = new ImageIcon(HIDE_BUTTON_FILE).getImage();
		showButton.setIcon(new ImageIcon(
				image.getScaledInstance(SHOW_BUTTON_WIDTH, SHOW_BUTTON_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
		this.add(showButton);

		
		colorButton.setBounds(COLOR_BUTTON_X, COLOR_BUTTON_Y, COLOR_BUTTON_WIDTH, COLOR_BUTTON_HEIGHT);
		colorButton.addActionListener(this);
		image = new ImageIcon(COLOR_BUTTON_FILE).getImage();
		colorButton.setIcon(new ImageIcon(
				image.getScaledInstance(COLOR_BUTTON_WIDTH, COLOR_BUTTON_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
		this.add(colorButton);
		setUpLabels();
	}
	public void setUpLabels() {
		labels[0] = new JLabel();
		labels[1] = new JLabel("f(x) at x =  " + space + " is:");
		labels[2] = new JLabel("f'(x) at x =  " + space + " is:");
		labels[3] = new JLabel("F(x) from x = " + space + " to x =" + space + "  is:");
		labels[4] = new JLabel("f(x) equals function number            in the interval x =               to x =             at x = ");
		for (int i = 0; i < labels.length; i++) {
			labels[i].setBounds(LABEL_X, LABEL_Y + LABEL_DY * i, LABEL_WIDTH, LABEL_HEIGHT);
			labels[i].setBackground(Color.white);
			labels[i].setForeground(Color.black);
			labels[i].setFont(new Font(FONT, Font.PLAIN, LABEL_FONT_SIZE));
			this.add(labels[i]);
			if (i < textField.length) {
				textField[i] = new JTextField();
				textField[i].setFont(new Font(FONT, Font.PLAIN, TEXT_FIELD_FONT_SIZE));
				textField[i].setForeground(Color.black);
				textField[i].addActionListener(this);
				this.add(textField[i]);
			}

		}
		textField[5] = new JTextField();
		textField[5].setFont(new Font(FONT, Font.PLAIN, TEXT_FIELD_FONT_SIZE));
		textField[5].setForeground(Color.black);
		textField[5].addActionListener(this);
		this.add(textField[5]);
		textField[6] = new JTextField();
		textField[6].setFont(new Font(FONT, Font.PLAIN, TEXT_FIELD_FONT_SIZE));
		textField[6].setForeground(Color.black);
		textField[6].addActionListener(this);
		this.add(textField[6]);
		textField[0].setBounds(TEXT_FIELD_0_X, TEXT_FIELD_0_Y, TEXT_FIELD_0_WIDTH, TEXT_FIELD_0_HEIGHT);
		textField[1].setBounds(TEXT_FIELD_1_X, TEXT_FIELD_1_Y, TEXT_FIELD_1_WIDTH, TEXT_FIELD_1_HEIGHT);
		textField[2].setBounds(TEXT_FIELD_2_X, TEXT_FIELD_2_Y, TEXT_FIELD_2_WIDTH, TEXT_FIELD_2_HEIGHT);
		textField[3].setBounds(TEXT_FIELD_3_X, TEXT_FIELD_3_Y, TEXT_FIELD_3_WIDTH, TEXT_FIELD_3_HEIGHT);
		textField[4].setBounds(TEXT_FIELD_4_X, TEXT_FIELD_4_Y, TEXT_FIELD_4_WIDTH, TEXT_FIELD_4_HEIGHT);
		textField[5].setBounds(TEXT_FIELD_5_X, TEXT_FIELD_5_Y, TEXT_FIELD_5_WIDTH, TEXT_FIELD_5_HEIGHT);
		textField[6].setBounds(TEXT_FIELD_6_X, TEXT_FIELD_6_Y, TEXT_FIELD_6_WIDTH, TEXT_FIELD_6_HEIGHT);
		
		
		answerTextField1.setBounds(ANSWER_TEXT_FIELD_1_X, ANSWER_TEXT_FIELD_1_Y, ANSWER_TEXT_FIELD_1_WIDTH,
				ANSWER_TEXT_FIELD_1_HEIGHT);
		answerTextField2.setBounds(ANSWER_TEXT_FIELD_2_X, ANSWER_TEXT_FIELD_2_Y, ANSWER_TEXT_FIELD_2_WIDTH,
				ANSWER_TEXT_FIELD_2_HEIGHT);
		answerTextField3.setBounds(ANSWER_TEXT_FIELD_3_X, ANSWER_TEXT_FIELD_3_Y, ANSWER_TEXT_FIELD_3_WIDTH,
				ANSWER_TEXT_FIELD_3_HEIGHT);
		answerTextField4.setBounds(ANSWER_TEXT_FIELD_4_X, ANSWER_TEXT_FIELD_4_Y, ANSWER_TEXT_FIELD_4_WIDTH,
				ANSWER_TEXT_FIELD_4_HEIGHT);
		answerTextField1.setFont(new Font(FONT, Font.PLAIN, ANSWER_TEXT_FIELD_FONT_SIZE));
		answerTextField2.setFont(new Font(FONT, Font.PLAIN, ANSWER_TEXT_FIELD_FONT_SIZE));
		answerTextField3.setFont(new Font(FONT, Font.PLAIN, ANSWER_TEXT_FIELD_FONT_SIZE));
		answerTextField4.setFont(new Font(FONT, Font.PLAIN, ANSWER_TEXT_FIELD_FONT_SIZE));
		answerTextField1.setForeground(Color.black);
		answerTextField2.setForeground(Color.black);
		answerTextField3.setForeground(Color.black);
		answerTextField4.setForeground(Color.black);
		this.add(answerTextField1);
		this.add(answerTextField2);
		this.add(answerTextField3);
		this.add(answerTextField4);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (Function.numOfFunctions == 1) {
			showLeftButton = false;
			showRightButton = false;
		} else {
			if (currFunctionNumber == 0) {
				showLeftButton = false;
				showRightButton = true;
			} else if (currFunctionNumber == Function.numOfFunctions - 1) {
				showLeftButton = true;
				showRightButton = false;
			} else {
				showLeftButton = true;
				showRightButton = true;
			}
		}
		leftButton.setVisible(showLeftButton);
		rightButton.setVisible(showRightButton);
		if (GraphingPanel.functions.get(currFunctionNumber).show) {
			image = new ImageIcon(HIDE_BUTTON_FILE).getImage();
			showButton.setIcon(new ImageIcon(
					image.getScaledInstance(SHOW_BUTTON_WIDTH, SHOW_BUTTON_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
		} else {
			image = new ImageIcon(SHOW_BUTTON_FILE).getImage();
			showButton.setIcon(new ImageIcon(
					image.getScaledInstance(SHOW_BUTTON_WIDTH, SHOW_BUTTON_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
		}
		labels[0].setText(GraphingPanel.functions.get(currFunctionNumber).toString());
		if (valid1 && valid2&&valid3&& perform) {
			try {
				int n = Integer.parseInt(textField[4].getText());
				double x1= Double.parseDouble(textField[5].getText());
				double x2= Double.parseDouble(textField[6].getText());
				String s = GraphingPanel.functions.get(currFunctionNumber).equal(GraphingPanel.functions.get(n-1),x1,x2);
				answerTextField4.setText(s);
			}
			catch(Exception ex) {
				answerTextField4.setText("Not Continuous");
			}
			perform = false;
		}
		repaint();
	}

	public double round(double d) {
		return Math.round(d * Math.pow(10, 5)) / Math.pow(10, 5);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == leftButton) {
			currFunctionNumber--;
		}
		if (e.getSource() == rightButton) {
			currFunctionNumber++;
		}
		if (e.getSource() == textField[0]) {
			boolean isDouble = false;
			String s = textField[0].getText();
			try {
				double d = Double.parseDouble(s);
				isDouble = true;
				d = GraphingPanel.functions.get(currFunctionNumber).eval(d);
				if (Double.isNaN(d)) {
					answerTextField1.setText("undefined");
				} else
					answerTextField1.setText(round(d) + "");
			} catch (Exception ex) {
				if (!isDouble)
					JOptionPane.showMessageDialog(null, "The x value is not a real number.");
			}
		}
		if (e.getSource() == textField[1]) {
			boolean isDouble = false;
			String s = textField[1].getText();

			try {
				double d = Double.parseDouble(s);
				isDouble = true;
				s = GraphingPanel.functions.get(currFunctionNumber).derivative(d);
				if (s.equals("NaN")) {
					answerTextField2.setText("DNE");
				} else
					answerTextField2.setText(s);
			} catch (Exception ex) {
				if (!isDouble)
					JOptionPane.showMessageDialog(null, "The x value is not a real number.");
			}

		}
		if (e.getSource()==textField[2]) {
			if (!textField[3].getText().isEmpty()) {
				if (textField[2].getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "The first x bound is empty.");
				} else {
					boolean error = false;
					double x1=0;
					double x2=0;
					try {
						x1 = Double.parseDouble(textField[2].getText());
						

					} catch (Exception ex) {
						error = true;
						JOptionPane.showMessageDialog(null, "The first x bound is not a real number.");
					}
					if (!error) {
						if (textField[2].getText().length() == 0) {
							JOptionPane.showMessageDialog(null, "The second x bound is empty.");
						} else {
							try {
								x2 = Double.parseDouble(textField[3].getText());
								try{
									Double d = GraphingPanel.functions.get(currFunctionNumber).integral(x1,x2);
									answerTextField3.setText(d+"");
								}
								catch(Exception ex) {
									JOptionPane.showMessageDialog(null, "The function is undefined within the x bounds from x = "+x1+" to x = "+x2+".");
								}
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(null, "The second x bound is not a real number.");
							}
						}
					}
				}
			}
		}
		if (e.getSource() == textField[3]) {
			if (textField[2].getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "The first x bound is empty.");
			} else {
				boolean error = false;
				double x1=0;
				double x2=0;
				try {
					x1 = Double.parseDouble(textField[2].getText());
					

				} catch (Exception ex) {
					error = true;
					JOptionPane.showMessageDialog(null, "The first x bound is not a real number.");
				}
				if (!error) {
					if (textField[2].getText().length() == 0) {
						JOptionPane.showMessageDialog(null, "The second x bound is empty.");
					} else {
						try {
							x2 = Double.parseDouble(textField[3].getText());
							try{
								Double d = GraphingPanel.functions.get(currFunctionNumber).integral(x1,x2);
								answerTextField3.setText(d+"");
							}
							catch(Exception ex) {
								JOptionPane.showMessageDialog(null, "The function is undefined within the x bounds from x = "+x1+" to x = "+x2+".");
							}
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "The second x bound is not a real number.");
						}
					}
				}
			}
		}
		if (e.getSource() == removeButton) {
			GraphingPanel.functions.remove(currFunctionNumber);
			
			for (int i =currFunctionNumber; i<Function.numOfFunctions-1; i++ ) {
				GraphingPanel.functions.get(i).num--;
			}
			
			Function.numOfFunctions--;
			backButton.doClick();
		}
		if (e.getSource() == showButton) {
			GraphingPanel.functions
					.get(currFunctionNumber).show = !GraphingPanel.functions.get(currFunctionNumber).show;
		}
		if (e.getSource() == colorButton) {
			GraphingPanel.functions.get(currFunctionNumber).setColor(GraphingPanel.functions.get(currFunctionNumber).randomColor());
		}
		if (e.getSource() == textField[4]) {
			JOptionPane.showMessageDialog(null, "Note: This feature only works if the two functions are continuous.");
			try {
				int n = Integer.parseInt(textField[4].getText());
				if (n<1) {
					valid1 = false;
					JOptionPane.showMessageDialog(null, "The function number of the other function cannot be less than 1.");
				}
				else if (n>Function.numOfFunctions) {
					valid1 = false;
					JOptionPane.showMessageDialog(null, "The function number of the other function cannot be greater than the current total number of functions of "+Function.numOfFunctions+".");
				}
				else {
					valid1 = true;
					perform = true;
				}
			}
			catch(Exception ex) {
				valid1 = false;
				JOptionPane.showMessageDialog(null, "The function number of the other function is not an integer.");
			}
			
		}
		if (e.getSource()==textField[5]) {
			String s = textField[5].getText();
			try {
				double d = Double.parseDouble(s);
				valid2 = true;
				perform = true;
			}
			catch (Exception ex) {
				valid2 = false;
				JOptionPane.showMessageDialog(null, "The x value is not a real number.");
			}
		}
		if (e.getSource()==textField[6]) {
			String s = textField[6].getText();
			try {
				double d = Double.parseDouble(s);
				valid3 = true;
				perform = true;
			}
			catch (Exception ex) {
				valid3 = false;
				JOptionPane.showMessageDialog(null, "The x value is not a real number.");
			}
		}
	}
}