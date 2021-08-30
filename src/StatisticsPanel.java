import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class StatisticsPanel extends JPanel implements ActionListener {
	private final String FONT = "Verdana";
	private final int INPUT_LABEL_FONT_SIZE = 15;
	private final int TEXT_FIELD_FONT_SIZE = 15;
	private final int STAT_LABEL_FONT_SIZE = 20;
	private final int TEXT_AREA_FONT_SIZE = 15;
	
	private final int BINOM_DIS_SIZE = 18;
	private final int BINOM_DIS_TEXT_SIZE = 13;
	
	private final int INPUT_LABEL_X = 50;
	private final int INPUT_LABEL_Y = 25;
	private final int INPUT_LABEL_WIDTH = 300;
	private final int INPUT_LABEL_HEIGHT = 20;

	private final int TEXT_FIELD_X = 50;
	private final int TEXT_FIELD_Y = 60;
	private final int TEXT_FIELD_WIDTH = 300;
	private final int TEXT_FIELD_HEIGHT = 20;

	private final int SCROLL_PANE_X = 50;
	private final int SCROLL_PANE_Y = 100;
	private final int SCROLL_PANE_WIDTH = 300;
	private final int SCROLL_PANE_HEIGHT = 200;

	private final int TEXT_AREA_ROWS = 150;
	private final int TEXT_AREA_COLUMNS = 300;

	private final int SORT_BUTTON_X = 100;
	private final int SORT_BUTTON_Y = 310;
	private final int SORT_BUTTON_WIDTH = 200;
	private final int SORT_BUTTON_HEIGHT = 50;

	private final int REMOVE_BUTTON_X = 100;
	private final int REMOVE_BUTTON_Y = 370;
	private final int REMOVE_BUTTON_WIDTH = 200;
	private final int REMOVE_BUTTON_HEIGHT = 50;

	private final int REMOVE_ALL_BUTTON_X = 100;
	private final int REMOVE_ALL_BUTTON_Y = 430;
	private final int REMOVE_ALL_BUTTON_WIDTH = 200;
	private final int REMOVE_ALL_BUTTON_HEIGHT = 50;

	private final int STAT_LABEL_X = 50;
	private final int STAT_LABEL_Y = 500;
	private final int STAT_LABEL_WIDTH = 600;
	private final int STAT_LABEL_HEIGHT = 50;

	private final int BINOM_DIS_X = 450;
	private final int BINOM_DIS_Y = 75;
	private final int BINOM_DIS_WIDTH = 500;
	private final int BINOM_DIS_HEIGHT = 20;
	private final int BINOM_DIS_CHANGE_IN_HEIGHT = 50;

	private final int BINOM_DIS_TEXT_X = 650;
	private final int BINOM_DIS_TEXT_Y = 125;
	private final int BINOM_DIS_TEXT_WIDTH = 100;
	private final int BINOM_DIS_TEXT_HEIGHT = 20;
	private final int BINOM_DIS_TEXT_CHANGE_IN_HEIGHT = 50;

	private final String SPACE = "            ";
	private final int DX = 5;
	
	private final int NUM_OF_DECIMAL_ACCURACY = 9;
	private final double FOUR_DECIMAL_ACCURACY = 10000.0;
	private final double TEN = 10.0;
	private final int SHIFT_BINOM_LEFT=80;
	private final int SHIFT_BINOM_UP=-40;

	private final String FILE = "src\\StatsText.txt";

	private ArrayList<Double> dataValues = new ArrayList<Double>();
	private JLabel inputLabel = new JLabel("Enter To Add A Data Value:");
	private JTextField textField = new JTextField();
	private String textAreaString = "";
	private JTextArea textArea = new JTextArea(textAreaString, TEXT_AREA_ROWS, TEXT_AREA_COLUMNS);
	private JScrollPane scrollPane;
	private JButton sortButton = new JButton();
	private JButton removeButton = new JButton();
	private JButton removeAll = new JButton();
	private JLabel statLabels[] = new JLabel[5];
	private JLabel binomDis[] = new JLabel[6];
	private JTextField binomDisText[] = new JTextField[5];
	private boolean validNumberOfTrials = false;
	private boolean validProbabilityOfSuccess = false;
	private boolean validRandomVariable = false;
	private long numberOfTrials = 0;
	private double probabilityOfSuccess = 0;
	private long randomVariable = 0;
	private int panelWidth;
	private int panelHeight;
	private Image backgroundImage = new ImageIcon("src\\Images\\StatisticsPanel\\background.png").getImage();
	private Image sortButtonImage = new ImageIcon("src\\Images\\StatisticsPanel\\sortButton.png").getImage();
	private Image removeButtonImage = new ImageIcon("src\\Images\\StatisticsPanel\\removeItem.png").getImage();
	private Image removeAllButtonImage = new ImageIcon("src\\Images\\StatisticsPanel\\removeAll.png").getImage();

	public StatisticsPanel(int w, int h) {
		panelWidth = w;
		panelHeight = h;
		this.setBackground(Color.gray);
		this.setLayout(null);
		this.setSize(w, h);

		inputLabel.setBounds(INPUT_LABEL_X, INPUT_LABEL_Y, INPUT_LABEL_WIDTH, INPUT_LABEL_HEIGHT);
		inputLabel.setBackground(Color.white);
		inputLabel.setForeground(Color.black);
		inputLabel.setFont(new Font(FONT, Font.PLAIN, INPUT_LABEL_FONT_SIZE));
		this.add(inputLabel);

		textField.setBounds(TEXT_FIELD_X, TEXT_FIELD_Y, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		textField.setFont(new Font(FONT, Font.PLAIN, TEXT_FIELD_FONT_SIZE));
		textField.addActionListener(this);
		this.add(textField);

		textArea.setLineWrap(true);
		textAreaString = "";
		setArrayFromText();
		for (int i = 0; i < dataValues.size(); i++) {
			textAreaString += ("Data item " + (i + 1) + " is: " + dataValues.get(i) + "\n\n");
		}
		textArea.setText(textAreaString);
		textArea.setFont(new Font(FONT, Font.PLAIN, TEXT_AREA_FONT_SIZE));
		writeArrayInText();
		this.add(textArea);
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(SCROLL_PANE_X, SCROLL_PANE_Y, SCROLL_PANE_WIDTH, SCROLL_PANE_HEIGHT);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.add(scrollPane);

		sortButton.setBounds(SORT_BUTTON_X, SORT_BUTTON_Y, SORT_BUTTON_WIDTH, SORT_BUTTON_HEIGHT);
		sortButton.setIcon(new ImageIcon(sortButtonImage.getScaledInstance(SORT_BUTTON_WIDTH, SORT_BUTTON_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
		sortButton.addActionListener(this);
		this.add(sortButton);

		removeButton.setBounds(REMOVE_BUTTON_X, REMOVE_BUTTON_Y, REMOVE_BUTTON_WIDTH, REMOVE_BUTTON_HEIGHT);
		removeButton.setIcon(new ImageIcon(removeButtonImage.getScaledInstance(REMOVE_BUTTON_WIDTH, REMOVE_BUTTON_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
		removeButton.addActionListener(this);
		this.add(removeButton);

		removeAll.setBounds(REMOVE_ALL_BUTTON_X, REMOVE_ALL_BUTTON_Y, REMOVE_ALL_BUTTON_WIDTH, REMOVE_ALL_BUTTON_HEIGHT);
		removeAll.setIcon(new ImageIcon(removeAllButtonImage.getScaledInstance(REMOVE_ALL_BUTTON_WIDTH, REMOVE_ALL_BUTTON_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
		removeAll.addActionListener(this);
		this.add(removeAll);

		statLabels[0] = new JLabel("Mean: ");
		statLabels[1] = new JLabel("Range: ");
		statLabels[2] = new JLabel("Standard Deviation: ");
		statLabels[3] = new JLabel("Variance: ");
		statLabels[4] = new JLabel("Median: ");
		for (int i = 0; i < statLabels.length; i++) {
			statLabels[i].setBounds(STAT_LABEL_X, STAT_LABEL_Y + STAT_LABEL_HEIGHT * i, STAT_LABEL_WIDTH,STAT_LABEL_HEIGHT);
			statLabels[i].setBackground(Color.white);
			statLabels[i].setForeground(Color.black);
			statLabels[i].setFont(new Font(FONT, Font.PLAIN, STAT_LABEL_FONT_SIZE));
			this.add(statLabels[i]);
		}

		binomDis[0] = new JLabel("Binomial Distribution Functions");
		binomDis[1] = new JLabel("Number Of Trials:             " + SPACE + isValid(validNumberOfTrials));
		binomDis[2] = new JLabel("Probability of Sucess:        " + SPACE + isValid(validProbabilityOfSuccess));
		binomDis[3] = new JLabel("Random Variable:             " + SPACE + isValid(validRandomVariable));
		binomDis[4] = new JLabel("PDF: ");
		binomDis[5] = new JLabel("CDF: ");
		for (int i = 0; i < binomDis.length; i++) {
			binomDis[i].setBounds(BINOM_DIS_X, BINOM_DIS_Y + BINOM_DIS_CHANGE_IN_HEIGHT * i, BINOM_DIS_WIDTH,
					BINOM_DIS_HEIGHT);
			binomDis[i].setBackground(Color.white);
			binomDis[i].setForeground(Color.black);
			
			binomDis[i].setFont(new Font(FONT, Font.PLAIN, BINOM_DIS_SIZE));
			this.add(binomDis[i]);
		}
		binomDis[0].setBounds(BINOM_DIS_X+SHIFT_BINOM_LEFT, BINOM_DIS_Y+SHIFT_BINOM_UP, BINOM_DIS_WIDTH,BINOM_DIS_HEIGHT);
		
		for (int i = 0; i < binomDisText.length; i++) {
			binomDisText[i] = new JTextField();
			binomDisText[i].setBounds(BINOM_DIS_TEXT_X, BINOM_DIS_TEXT_Y + BINOM_DIS_TEXT_CHANGE_IN_HEIGHT * i,
					BINOM_DIS_TEXT_WIDTH, BINOM_DIS_TEXT_HEIGHT);
			binomDisText[i].setFont(new Font(FONT, Font.PLAIN, BINOM_DIS_TEXT_SIZE));
			binomDisText[i].addActionListener(this);
			this.add(binomDisText[i]);
		}

		setUpStats();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, panelWidth, panelHeight, this);
		g.setColor(Color.black);
		g.fillRect(SCROLL_PANE_X-DX, SCROLL_PANE_Y-DX, SCROLL_PANE_WIDTH+2*DX, SCROLL_PANE_HEIGHT+2*DX);
		g.fillRect(TEXT_FIELD_X-DX, TEXT_FIELD_Y-DX, TEXT_FIELD_WIDTH+2*DX, TEXT_FIELD_HEIGHT+2*DX);
		for (int i = 0; i < binomDisText.length; i++) {
			g.fillRect(BINOM_DIS_TEXT_X-DX, BINOM_DIS_TEXT_Y + BINOM_DIS_TEXT_CHANGE_IN_HEIGHT * i-DX,BINOM_DIS_TEXT_WIDTH+2*DX, BINOM_DIS_TEXT_HEIGHT+2*DX);
		}
		repaint();
	}

	public void trialsSetValidLabel(boolean b) {
		binomDis[1].setText("Number Of Trials:             " + SPACE + isValid(validNumberOfTrials));

	}

	public void pSetValidLabel(boolean b) {
		binomDis[2].setText("Probability of Sucess:        " + SPACE+isValid(validProbabilityOfSuccess));
	}

	public void randomVariableSetValidLabel(boolean b) {
		binomDis[3].setText("Random Variable:             " + SPACE + isValid(validRandomVariable));
	}

	public String isValid(boolean b) {
		if (b)
			return "Input Is Entered";
		else
			return "Input Is Not Entered";
	}

	public void updateBinom() {
		double pdf = pdf(numberOfTrials, probabilityOfSuccess, randomVariable);
		binomDisText[3].setText(round(pdf, NUM_OF_DECIMAL_ACCURACY) + "");
		double cdf = 0;
		for (int i = 0; i <= randomVariable; i++) {
			cdf += pdf(numberOfTrials, probabilityOfSuccess, i);
		}
		binomDisText[4].setText(round(cdf, NUM_OF_DECIMAL_ACCURACY) + "");
	}

	public double pdf(long trials, double p, long randomVariable) {
		double pdf = (factorial(trials) / ((factorial(randomVariable) * factorial(trials - randomVariable))))
				* Math.pow(p, randomVariable) * Math.pow(1 - p, trials - randomVariable);
		return pdf;
	}

	public double round(double x) {
		return Math.round(x * FOUR_DECIMAL_ACCURACY) / FOUR_DECIMAL_ACCURACY;
	}

	public double round(double x, int n) {
		return Math.round(x * Math.pow(TEN, n)) / Math.pow(TEN, n);
	}

	public void setUpStats() {
		if (dataValues.isEmpty()) {
			statLabels[0].setText("Mean: ");
			statLabels[1].setText("Range: ");
			statLabels[2].setText("Standard Deviation: ");
			statLabels[3].setText("Variance: ");
			statLabels[4].setText("Median: ");
		} else {
			double maxData = dataValues.get(0);
			double minData = dataValues.get(0);
			double mean = 0;
			for (int i = 0; i < dataValues.size(); i++) {
				mean += dataValues.get(i);
				maxData = Math.max(maxData, dataValues.get(i));
				minData = Math.min(minData, dataValues.get(i));
			}
			mean = mean / dataValues.size();
			statLabels[0].setText("Mean: " + round(mean));
			statLabels[1].setText("Range: " + (maxData - minData));
			double standardDeviation = 0;
			for (int i = 0; i < dataValues.size(); i++) {
				standardDeviation += Math.pow(dataValues.get(i) - mean, 2);
			}
			standardDeviation = Math.sqrt(standardDeviation / dataValues.size());
			statLabels[2].setText("Standard Deviation: " + round(standardDeviation));
			statLabels[3].setText("Variance: " + round(Math.pow(standardDeviation, 2)));

			ArrayList<Double> a = new ArrayList<Double>();
			for (int i = 0; i < dataValues.size(); i++) {
				a.add(dataValues.get(i));
			}
			bubbleSort(a);
			double median = 0;
			if (a.size() == 1)
				median = a.get(0);
			else if (a.size() % 2 == 0) {
				median = (a.get(a.size() / 2) + a.get(a.size() / 2 - 1)) / 2;
			} else if (a.size() % 2 == 1) {
				median = a.get(a.size() / 2);
			}
			statLabels[4].setText("Median: " + median);
		}
	}

	public void bubbleSort(ArrayList<Double> data) {
		boolean swapped = true;
		while (swapped) {
			swapped = false;
			for (int i = 1; i < data.size(); i++) {
				if (data.get(i - 1) > data.get(i)) {
					double temp = data.get(i);
					data.set(i, data.get(i - 1));
					data.set(i - 1, temp);
					swapped = true;
				}
			}
		}
	}

	public double factorial(long x) {
		if (x == 0)
			return 1;
		else
			return x * factorial(x - 1);
	}

	public void writeArrayInText() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(FILE));
			for (int i = 0; i < dataValues.size(); i++) {
				bw.write(("Data Item " + (i + 1) + " is: " + dataValues.get(i) + "\n"));
			}
			bw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void setArrayFromText() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(FILE));
			String s;
			dataValues.clear();
			while ((s = br.readLine()) != null) {
				int index = s.indexOf(':');
				s = s.substring(index + 1).trim();
				dataValues.add(Double.parseDouble(s));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {

		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == textField) {
			try {
				dataValues.add(Double.parseDouble(textField.getText()));
				textAreaString = "";
				for (int i = 0; i < dataValues.size(); i++) {
					textAreaString += ("Data Item " + (i + 1) + " is: " + dataValues.get(i) + "\n\n");
				}
				textArea.setText(textAreaString);
				writeArrayInText();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "The input is not a real number.");
			}
			textField.setText("");
			setUpStats();
		}
		if (e.getSource() == removeButton) {
			String input = JOptionPane.showInputDialog("Remove The Data Item With Index Number Of: ");
			try {
				int t = Integer.parseInt(input);
				if (t < 0)
					JOptionPane.showMessageDialog(null, "The input is not a positive integer.");
				else if (t > dataValues.size())
					JOptionPane.showMessageDialog(null, "There is no term number with an index as large as " + t + ".");
				else {
					dataValues.remove(t - 1);
					setUpStats();
					textAreaString = "";
					for (int i = 0; i < dataValues.size(); i++) {
						textAreaString += ("Data Item " + (i + 1) + " is: " + dataValues.get(i) + "\n\n");
					}
					textArea.setText(textAreaString);
				}
			} catch (Exception ex) {
			}
			writeArrayInText();
		}
		if (e.getSource() == removeAll) {
			dataValues.clear();
			setUpStats();
			textAreaString = "";
			textArea.setText(textAreaString);
			writeArrayInText();
		}
		if (e.getSource() == sortButton) {
			bubbleSort(dataValues);
			textAreaString = "";
			for (int i = 0; i < dataValues.size(); i++) {
				textAreaString += ("Data item " + (i + 1) + " is: " + dataValues.get(i) + "\n\n");
			}
			textArea.setText(textAreaString);
			writeArrayInText();
		}
		if (e.getSource() == binomDisText[0]) {
			try {
				int t = Integer.parseInt(binomDisText[0].getText());
				if (t < 0) {
					JOptionPane.showMessageDialog(null, "The input is not a positive integer.");
					validNumberOfTrials = false;
					trialsSetValidLabel(validNumberOfTrials);
				} else {
					numberOfTrials = t;
					validNumberOfTrials = true;
					trialsSetValidLabel(validNumberOfTrials);
					if (validNumberOfTrials && validProbabilityOfSuccess && validRandomVariable) {
						updateBinom();
					}
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "The input is not a positive integer.");
				validNumberOfTrials = false;
				trialsSetValidLabel(validNumberOfTrials);
			}
		}
		if (e.getSource() == binomDisText[1]) {
			try {
				double prob = Double.parseDouble(binomDisText[1].getText());
				if (prob < 0 || prob > 1) {
					JOptionPane.showMessageDialog(null, "The input is within the range from 0 to 1 inclusive.");
					validProbabilityOfSuccess = false;
					pSetValidLabel(validProbabilityOfSuccess);
				} else {
					probabilityOfSuccess = prob;
					validProbabilityOfSuccess = true;
					pSetValidLabel(validProbabilityOfSuccess);
					if (validNumberOfTrials && validProbabilityOfSuccess && validRandomVariable) {
						updateBinom();
					}
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "The input is not a real number.");
				validProbabilityOfSuccess = false;
				pSetValidLabel(validProbabilityOfSuccess);
			}
		}
		if (e.getSource() == binomDisText[2]) {
			try {
				int t = Integer.parseInt(binomDisText[2].getText());
				if (!validNumberOfTrials) {
					JOptionPane.showMessageDialog(null, "Input the number of trials first.");
					validRandomVariable = false;
					randomVariableSetValidLabel(validRandomVariable);
				} else {
					if (t < 0) {
						JOptionPane.showMessageDialog(null, "The input is not a positive integer.");
						validRandomVariable = false;
						randomVariableSetValidLabel(validRandomVariable);
					} else if (t > numberOfTrials) {
						JOptionPane.showMessageDialog(null, "The input cannot be greater than the number of trials.");
						validRandomVariable = false;
						randomVariableSetValidLabel(validRandomVariable);
					} else {
						randomVariable = t;
						validRandomVariable = true;
						randomVariableSetValidLabel(validRandomVariable);
						if (validNumberOfTrials && validProbabilityOfSuccess && validRandomVariable) {
							updateBinom();
						}
					}
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "The input is not a positive integer.");
				validRandomVariable = false;
				randomVariableSetValidLabel(validRandomVariable);
			}
		}
	}
}
