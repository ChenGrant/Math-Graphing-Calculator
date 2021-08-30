import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TrigPanel extends JPanel implements ActionListener {
	private final String FONT = "Verdana";
	private final int TRIG_VALUE_FONT_SIZE = 15;

	private final int SPIN_BUTTON_X = 700;
	private final int SPIN_BUTTON_Y = 620;
	private final int SPIN_BUTTON_WIDTH = 150;
	private final int SPIN_BUTTON_HEIGHT = 70;

	private final int SPEED_UP_BUTTON_X = 100;
	private final int SPEED_UP_BUTTON_Y = 620;
	private final int SPEED_UP_BUTTON_WIDTH = 250;
	private final int SPEED_UP_BUTTON_HEIGHT = 70;

	private final int SLOW_DOWN_BUTTON_X = 400;
	private final int SLOW_DOWN_BUTTON_Y = 620;
	private final int SLOW_DOWN_BUTTON_WIDTH = 250;
	private final int SLOW_DOWN_BUTTON_HEIGHT = 70;

	private final int RADIANS_BUTTON_X = 500;
	private final int RADIANS_BUTTON_Y = 850;
	private final int RADIANS_BUTTON_WIDTH = 300;
	private final int RADIANS_BUTTON_HEIGHT = 70;

	private final int DEGREES_BUTTON_X = 200;
	private final int DEGREES_BUTTON_Y = 850;
	private final int DEGREES_BUTTON_WIDTH = 300;
	private final int DEGREES_BUTTON_HEIGHT = 70;

	private final int TRIG_VALUES_X = 25;
	private final int TRIG_VALUES_Y = 740;
	private final int TRIG_VALUES_WIDTH = 230;
	private final int TRIG_VALUES_HEIGHT = 30;
	private final int TRIG_VALUES_CHANGE_IN_WIDTH = 240;
	private final int TRIG_VALUES_CHANGE_IN_HEIGHT = 20;

	private final int DEGREES_IN_PI_RADIANS = 180;
	private final int RADIUS = 200;
	private final double FOUR_DECIMAL_PLACES = 10000;
	private final int ARROW_LENGTH = 50;
	private final int ARROW_WIDTH = 20;

	private boolean spin = true;
	private double dSpin = 0.001;
	private int panelWidth;
	private int panelHeight;
	private double angle = 0;
	private JButton spinButton = new JButton();
	private JButton speedUp = new JButton();
	private JButton slowDown = new JButton();
	private JButton inputAngleRad = new JButton();
	private JButton inputAngleDeg = new JButton();
	private JLabel[][] trigValues = new JLabel[2][4];
	private Image backgroundImage = new ImageIcon("src\\Images\\TrigPanel\\background.png").getImage();
	private Image boxImage = new ImageIcon("src\\Images\\TrigPanel\\box.png").getImage();
	private Image spinImage = new ImageIcon("src\\Images\\TrigPanel\\spin.png").getImage();
	private Image slowDownImage = new ImageIcon("src\\Images\\TrigPanel\\slow down.png").getImage();
	private Image speedUpImage = new ImageIcon("src\\Images\\TrigPanel\\speed up.png").getImage();
	private Image radiansImage = new ImageIcon("src\\Images\\TrigPanel\\radians.png").getImage();
	private Image degreesImage = new ImageIcon("src\\Images\\TrigPanel\\degrees.png").getImage();

	public TrigPanel(int w, int h) {
		panelWidth = w;
		panelHeight = h;
		this.setBackground(Color.gray);
		this.setLayout(null);
		this.setSize(w, h);
		spinButton.setBounds(SPIN_BUTTON_X, SPIN_BUTTON_Y, SPIN_BUTTON_WIDTH, SPIN_BUTTON_HEIGHT);
		spinButton.setIcon(new ImageIcon(spinImage.getScaledInstance(SPIN_BUTTON_WIDTH, SPIN_BUTTON_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
		spinButton.addActionListener(this);
		this.add(spinButton);

		speedUp.setIcon(new ImageIcon(speedUpImage.getScaledInstance(SPEED_UP_BUTTON_WIDTH, SPEED_UP_BUTTON_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
		speedUp.setBounds(SPEED_UP_BUTTON_X, SPEED_UP_BUTTON_Y, SPEED_UP_BUTTON_WIDTH, SPEED_UP_BUTTON_HEIGHT);
		speedUp.addActionListener(this);
		this.add(speedUp);

		slowDown.setBounds(SLOW_DOWN_BUTTON_X, SLOW_DOWN_BUTTON_Y, SLOW_DOWN_BUTTON_WIDTH, SLOW_DOWN_BUTTON_HEIGHT);
		slowDown.setIcon(new ImageIcon(slowDownImage.getScaledInstance(SLOW_DOWN_BUTTON_WIDTH, SLOW_DOWN_BUTTON_HEIGHT,	java.awt.Image.SCALE_SMOOTH)));
		slowDown.addActionListener(this);
		this.add(slowDown);

		inputAngleRad.setBounds(RADIANS_BUTTON_X, RADIANS_BUTTON_Y, RADIANS_BUTTON_WIDTH, RADIANS_BUTTON_HEIGHT);
		inputAngleRad.setIcon(new ImageIcon(radiansImage.getScaledInstance(RADIANS_BUTTON_WIDTH, RADIANS_BUTTON_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
		inputAngleRad.addActionListener(this);
		this.add(inputAngleRad);

		inputAngleDeg.setBounds(DEGREES_BUTTON_X, DEGREES_BUTTON_Y, DEGREES_BUTTON_WIDTH, DEGREES_BUTTON_HEIGHT);
		inputAngleDeg.setIcon(new ImageIcon(degreesImage.getScaledInstance(DEGREES_BUTTON_WIDTH, DEGREES_BUTTON_HEIGHT,java.awt.Image.SCALE_SMOOTH)));
		inputAngleDeg.addActionListener(this);
		this.add(inputAngleDeg);

		setUpLabels();
	}

	public void setUpLabels() {
		for (int i = 0; i < trigValues.length; i++) {
			for (int z = 0; z < trigValues[i].length; z++) {
				trigValues[i][z] = new JLabel();
				trigValues[i][z].setBounds(TRIG_VALUES_X + TRIG_VALUES_CHANGE_IN_WIDTH * z, TRIG_VALUES_Y + TRIG_VALUES_CHANGE_IN_HEIGHT * i, TRIG_VALUES_WIDTH, TRIG_VALUES_HEIGHT);
				trigValues[i][z].setBackground(Color.white);
				trigValues[i][z].setForeground(Color.black);
				trigValues[i][z].setFont(new Font(FONT, Font.PLAIN, TRIG_VALUE_FONT_SIZE));
				this.add(trigValues[i][z]);
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(panelWidth / 2, panelHeight / 3);
		if (spin)
			angle += dSpin;
		if (angle > 2 * Math.PI) {
			angle -= 2 * Math.PI;
		}
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Color.black);
		g.drawImage(boxImage, -RADIUS - 2 * ARROW_LENGTH, -RADIUS - 2 * ARROW_LENGTH + 20, 2 * RADIUS + 4 * ARROW_LENGTH, 2 * RADIUS + 4 * ARROW_LENGTH - 40, this);
		g2d.drawOval(-RADIUS, -RADIUS, RADIUS * 2, RADIUS * 2);
		g2d.drawLine(-RADIUS - ARROW_LENGTH, 0, RADIUS + ARROW_LENGTH, 0);
		g2d.drawLine(0, -RADIUS - ARROW_LENGTH, 0, RADIUS + ARROW_LENGTH);
		g2d.setColor(Color.red);
		g2d.drawLine(0, 0, (int) (RADIUS * Math.cos(-angle)), (int) (RADIUS * Math.sin(-angle)));
		g2d.drawLine((int) (RADIUS * Math.cos(-angle)), 0, (int) (RADIUS * Math.cos(-angle)),(int) (RADIUS * Math.sin(-angle)));
		g2d.drawArc(-RADIUS / 4, -RADIUS / 4, RADIUS / 2, RADIUS / 2, 0, (int) radToDeg(angle));
		g2d.setColor(Color.black);
		g2d.drawLine(-RADIUS - ARROW_LENGTH, 0, -RADIUS - ARROW_LENGTH / 2, ARROW_WIDTH);
		g2d.drawLine(-RADIUS - ARROW_LENGTH, 0, -RADIUS - ARROW_LENGTH / 2, -ARROW_WIDTH);
		g2d.drawLine(RADIUS + ARROW_LENGTH, 0, RADIUS + ARROW_LENGTH / 2, ARROW_WIDTH);
		g2d.drawLine(RADIUS + ARROW_LENGTH, 0, RADIUS + ARROW_LENGTH / 2, -ARROW_WIDTH);
		g2d.drawLine(0, RADIUS + ARROW_LENGTH, ARROW_WIDTH, RADIUS + ARROW_LENGTH / 2);
		g2d.drawLine(0, RADIUS + ARROW_LENGTH, -ARROW_WIDTH, RADIUS + ARROW_LENGTH / 2);
		g2d.drawLine(0, -RADIUS - ARROW_LENGTH, ARROW_WIDTH, -RADIUS - ARROW_LENGTH / 2);
		g2d.drawLine(0, -RADIUS - ARROW_LENGTH, -ARROW_WIDTH, -RADIUS - ARROW_LENGTH / 2);
		
		trigValues[0][0].setText("Angle = " + round(angle) + " radians");
		trigValues[1][0].setText("Angle = " + round(radToDeg(angle)) + " degrees");
		trigValues[0][1].setText("sin(" + round(angle) + ") = " + round(Math.sin(angle)));
		trigValues[0][2].setText("cos(" + round(angle) + ") = " + round(Math.cos(angle)));
		trigValues[0][3].setText("tan(" + round(angle) + ") = " + round(Math.tan(angle)));
		trigValues[1][1].setText("csc(" + round(angle) + ") = " + round(1 / Math.sin(angle)));
		trigValues[1][2].setText("sec(" + round(angle) + ") = " + round(1 / Math.cos(angle)));
		trigValues[1][3].setText("cot(" + round(angle) + ") = " + round(1 / Math.tan(angle)));

		repaint();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, panelWidth, panelHeight, this);
	}

	public double radToDeg(double x) {
		return DEGREES_IN_PI_RADIANS * x / Math.PI;
	}

	public double degToRad(double x) {
		return Math.PI * x / DEGREES_IN_PI_RADIANS;
	}

	public double round(double x) {
		return Math.round(x * FOUR_DECIMAL_PLACES) / FOUR_DECIMAL_PLACES;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == spinButton) {
			spin = !spin;
		}
		
		if (e.getSource() == speedUp) {
			dSpin = dSpin * 2;
			spin = true;
		}
		
		if (e.getSource() == slowDown) {
			dSpin = dSpin / 2;
			spin = true;
		}
		
		if (e.getSource() == inputAngleRad) {
			String input = JOptionPane.showInputDialog("Please input the value of the angle in radians.");
			try {
				angle = Double.parseDouble(input);
				spin = false;

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "The input is not an real number.");
			}
		}
		
		if (e.getSource() == inputAngleDeg) {
			String input = JOptionPane.showInputDialog("Please input the value of the angle in degrees.");
			try {
				angle = degToRad(Double.parseDouble(input));
				spin = false;

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "The input is not an real number.");
			}
		}
	}
}
