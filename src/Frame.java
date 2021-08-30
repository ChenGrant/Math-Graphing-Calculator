import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Frame extends JFrame implements ActionListener {
	private final static String FRAME_NAME = "IB Math Calculator";
	private final int FRAME_WIDTH = 1000;
	private final int FRAME_HEIGHT = 1000;
	private final int SCIENTIFIC_PANEL_HEIGHT = 500;
	private final int STATS_PANEL_HEIGHT = 840;
	private final int ADD_FUNCTIONS_PANEL_HEIGHT = 700;
	private final int MENU_ITEM_SIZE = 5;
	
	private Container c = getContentPane();
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItems[];
	private ScientificPanel scientificPanel = new ScientificPanel(FRAME_WIDTH, SCIENTIFIC_PANEL_HEIGHT);
	private StatisticsPanel statisticsPanel = new StatisticsPanel(FRAME_WIDTH, STATS_PANEL_HEIGHT);
	private TrigPanel trigPanel = new TrigPanel(FRAME_WIDTH, FRAME_HEIGHT);
	private GraphingPanel graphingPanel = new GraphingPanel(FRAME_WIDTH, FRAME_HEIGHT);
	private AddFunctionPanel addFunctionPanel = new AddFunctionPanel(FRAME_WIDTH, ADD_FUNCTIONS_PANEL_HEIGHT);
	private EditFunctionPanel editFunctionPanel = new EditFunctionPanel(FRAME_WIDTH, FRAME_HEIGHT);

	public Frame() {
		super(FRAME_NAME);
		c.add(scientificPanel);
		graphingPanel.addFunction.addActionListener(this);
		graphingPanel.editFunctions.addActionListener(this);
		addFunctionPanel.backButton.addActionListener(this);
		editFunctionPanel.backButton.addActionListener(this);
		setUpFrame();
		this.setSize(FRAME_WIDTH,SCIENTIFIC_PANEL_HEIGHT);
		if (!scientificPanel.notifcationShown) {

			JOptionPane.showMessageDialog(null,
					"Please enter negative numbers with a leading negative as 0 minus their magnitude. Ex: -6-2 can be expressed as 0-6-2");
			scientificPanel.notifcationShown = true;
		}
	}

	public void setUpFrame() {
		this.setJMenuBar(null);
		menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		menuItems = new JMenuItem[MENU_ITEM_SIZE];
		menuItems[0] = new JMenuItem("Scientific Calculator");
		menuItems[1] = new JMenuItem("Statistics");
		menuItems[2] = new JMenuItem("Graphing");
		menuItems[3] = new JMenuItem("Trigonometry");
		menuItems[4] = new JMenuItem("Exit");
		for (int i = 0; i < MENU_ITEM_SIZE; i++) {
			menuItems[i].addActionListener(this);
			menu.add(menuItems[i]);
		}
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setResizable(false);
		this.setVisible(true);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuItems[0]) {
			c.removeAll();
			c.add(scientificPanel);
			setUpFrame();
			this.setSize(FRAME_WIDTH, SCIENTIFIC_PANEL_HEIGHT);
		}
		if (e.getSource() == menuItems[1]) {
			c.removeAll();
			c.add(statisticsPanel);
			setUpFrame();
			this.setSize(FRAME_WIDTH, STATS_PANEL_HEIGHT);
		}
		if (e.getSource() == menuItems[2]) {
			c.removeAll();
			c.add(graphingPanel);
			setUpFrame();
			graphingPanel.setFocusable(true);
			graphingPanel.requestFocusInWindow();
		}
		if (e.getSource() == menuItems[3]) {
			c.removeAll();
			c.add(trigPanel);
			setUpFrame();
		}
		if (e.getSource() == menuItems[4]) {
			System.exit(1);
		}
		if (e.getSource() == graphingPanel.addFunction) {
			c.removeAll();
			addFunctionPanel = new AddFunctionPanel(FRAME_WIDTH, ADD_FUNCTIONS_PANEL_HEIGHT);
			addFunctionPanel.backButton.addActionListener(this);
			c.add(addFunctionPanel);
			setUpFrame();
			this.setSize(FRAME_WIDTH, ADD_FUNCTIONS_PANEL_HEIGHT);
		}
		if (e.getSource() == addFunctionPanel.backButton) {
			c.removeAll();
			c.add(graphingPanel);
			setUpFrame();
			graphingPanel.setFocusable(true);
			graphingPanel.requestFocusInWindow();
		}
		if (e.getSource() == graphingPanel.editFunctions) {
			if (Function.numOfFunctions != 0) {
				c.removeAll();
				editFunctionPanel = new EditFunctionPanel(FRAME_WIDTH, FRAME_HEIGHT);
				editFunctionPanel.backButton.addActionListener(this);
				c.add(editFunctionPanel);
				setUpFrame();
			}
			else {
				JOptionPane.showMessageDialog(null, "There are no functions to edit.");
			}
		}
		if (e.getSource() == editFunctionPanel.backButton) {
			c.removeAll();
			c.add(graphingPanel);
			setUpFrame();
			graphingPanel.setFocusable(true);
			graphingPanel.requestFocusInWindow();
		}
	}
}
