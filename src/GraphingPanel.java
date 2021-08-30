import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class GraphingPanel extends JPanel implements KeyListener, ActionListener {
	private final int DEFAULT_DX = 0;
	private final int DEFAULT_DY = 0;
	private final int ORIGIN_X = 50;
	private final int ORIGIN_Y = 50;
	private final int ORIGIN_WIDTH = 200;
	private final int ORIGIN_HEIGHT = 50;
	
	private final int ZOOM_IN_X = 50;
	private final int ZOOM_IN_Y = 125;
	private final int ZOOM_IN_WIDTH = 200;
	private final int ZOOM_IN_HEIGHT = 50;
	
	private final int ZOOM_OUT_X = 50;
	private final int ZOOM_OUT_Y = 200;
	private final int ZOOM_OUT_WIDTH = 200;
	private final int ZOOM_OUT_HEIGHT = 50;
	
	private final int EDIT_FUNCTIONS_X = 50;
	private final int EDIT_FUNCTIONS_Y = 275;
	private final int EDIT_FUNCTIONS_WIDTH = 200;
	private final int EDIT_FUNCTIONS_HEIGHT = 50;
	
	private final int ADD_FUNCTIONS_X = 50;
	private final int ADD_FUNCTIONS_Y = 350;
	private final int ADD_FUNCTIONS_WIDTH = 200;
	private final int ADD_FUNCTIONS_HEIGHT = 50;
	
	private final int SPEED = 150;
	private final int TWO_FIFTY_SIX = 256;
	private final double F_CHANGE = 1.3;
	private final double MIN_F = 3;
	private final double DEFAULT_F = 50;
	
	
	
	private double f = DEFAULT_F;
	private int width;
	private int height;
	private int dx = 0;
	private int dy = 0;
	private boolean up = false;
	private boolean down = false;
	private boolean right = false;
	private boolean left = false;
	private JButton zoomIn = new JButton();
	private JButton zoomOut = new JButton();
	public JButton editFunctions = new JButton();
	private JButton origin = new JButton();
	public JButton addFunction = new JButton();
	
	public static SinglyLinkedList functions = new SinglyLinkedList();
	private Image originImage = new ImageIcon("src\\Images\\Calculus\\origin.png").getImage();
	private Image zoomInImage = new ImageIcon("src\\Images\\Calculus\\zoom in.png").getImage();
	private Image zoomOutImage = new ImageIcon("src\\Images\\Calculus\\zoom out.png").getImage();
	private Image editImage = new ImageIcon("src\\Images\\Calculus\\edit.png").getImage();
	private Image addFunctionImage = new ImageIcon("src\\Images\\Calculus\\add function.png").getImage();
	
	public GraphingPanel(int w, int h) {
		width = w;
		height = h;
		this.setSize(w, h);
		this.setLayout(null);
		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();
		
		zoomIn.setBounds(ZOOM_IN_X, ZOOM_IN_Y, ZOOM_IN_WIDTH, ZOOM_IN_HEIGHT);
		zoomIn.addActionListener(this);
		zoomIn.setIcon(new ImageIcon(zoomInImage.getScaledInstance(ZOOM_IN_WIDTH, ZOOM_IN_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
		this.add(zoomIn);
		
		
		zoomOut.setBounds(ZOOM_OUT_X, ZOOM_OUT_Y, ZOOM_OUT_WIDTH, ZOOM_OUT_HEIGHT);
		zoomOut.addActionListener(this);
		zoomOut.setIcon(new ImageIcon(zoomOutImage.getScaledInstance(ZOOM_OUT_WIDTH, ZOOM_OUT_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
		this.add(zoomOut);
		
		
		editFunctions.setBounds(EDIT_FUNCTIONS_X, EDIT_FUNCTIONS_Y, EDIT_FUNCTIONS_WIDTH, EDIT_FUNCTIONS_HEIGHT);
		editFunctions.addActionListener(this);
		editFunctions.setIcon(new ImageIcon(editImage.getScaledInstance(EDIT_FUNCTIONS_WIDTH, EDIT_FUNCTIONS_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
		this.add(editFunctions);

		origin.setBounds(ORIGIN_X, ORIGIN_Y, ORIGIN_WIDTH, ORIGIN_HEIGHT);
		origin.setIcon(new ImageIcon(originImage.getScaledInstance(ORIGIN_WIDTH, ORIGIN_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
		origin.addActionListener(this);
		this.add(origin);
		
		addFunction.setBounds(ADD_FUNCTIONS_X,ADD_FUNCTIONS_Y,ADD_FUNCTIONS_WIDTH,ADD_FUNCTIONS_HEIGHT);
		addFunction.addActionListener(this);
		addFunction.setIcon(new ImageIcon(addFunctionImage.getScaledInstance(ADD_FUNCTIONS_WIDTH, ADD_FUNCTIONS_HEIGHT, java.awt.Image.SCALE_SMOOTH)));
		this.add(addFunction);
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(width / 2 - dx, height / 2 + dy);
		g2d.scale(1, -1);
		setUpGrid(g2d);
		for (int i = 0; i < functions.size(); i++) {
			if (functions.get(i).show)
				functions.get(i).drawFunction(g2d, -width / 2 + dx, width / 2 + dx, f);
		}
		if (left) {
			dx -= SPEED;
		}
		if (right) {
			dx += SPEED;
		}
		if (up) {
			dy += SPEED;
		}
		if (down) {
			dy -= SPEED;
		}
		repaint();
	}
	public Color randomColor() {
		int R = (int) (Math.random() * TWO_FIFTY_SIX);
		int G = (int) (Math.random() * TWO_FIFTY_SIX);
		int B = (int) (Math.random() * TWO_FIFTY_SIX);
		Color color = new Color(R, G, B);
		return color;
	}

	public void setUpGrid(Graphics2D g2d) {
		g2d.setColor(Color.gray);
		g2d.setStroke(new BasicStroke(1));
		for (int i = 0; i < width / 2 + dx; i += f) {
			g2d.drawLine(i, height / 2 + dy, i, -height / 2 + dy);
		}
		for (int i = 0; i > -width / 2 + dx; i -= f) {
			g2d.drawLine(i, height / 2 + dy, i, -height / 2 + dy);
		}
		for (int i = 0; i < height / 2 + dy; i += f) {
			g2d.drawLine(width / 2 + dx, i, -width / 2 + dx, i);
		}
		for (int i = 0; i > -height / 2 + dy; i -= f) {
			g2d.drawLine(width / 2 + dx, i, -width / 2 + dx, i);
		}
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Color.black);
		g2d.drawLine(0, height / 2 + dy, 0, -height / 2 + dy);
		g2d.drawLine(width / 2 + dx, 0, -width / 2 + dx, 0);
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT: {
			left = true;
			break;
		}
		case KeyEvent.VK_RIGHT: {
			right = true;
			break;
		}
		case KeyEvent.VK_DOWN: {
			down = true;
			break;
		}
		case KeyEvent.VK_UP: {
			up = true;
			break;
		}

		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT: {
			left = false;
			break;
		}
		case KeyEvent.VK_RIGHT: {
			right = false;
			break;
		}
		case KeyEvent.VK_DOWN: {
			down = false;
			break;
		}
		case KeyEvent.VK_UP: {
			up = false;
			break;
		}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == zoomIn) {
			f = f * F_CHANGE;
		}
		if (e.getSource() == zoomOut) {
			f = f / F_CHANGE;
			if (f <= MIN_F)
				f = MIN_F;
		}
		if (e.getSource()==origin) {
			dx = DEFAULT_DX;
			dy = DEFAULT_DY;
			f = DEFAULT_F;
		}
		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();

	}

}
