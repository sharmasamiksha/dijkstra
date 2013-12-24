package com.uw.hw6b.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.uw.hw6b.Dijkstra;

public class DijkstraGUI extends JFrame {

	private static final long serialVersionUID = 1877822708861508837L;
	private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	private JPanel myPanel;

	private JTextField start = new JTextField();
	private JTextField end = new JTextField();
	private JLabel startPoint = new JLabel("Start: ");
	private JLabel endPoint = new JLabel("End: ");
	private BrowseAction browseAction = new BrowseAction(this, start, end);
	private JButton browseButton = new JButton(browseAction);
	private Action entryAction = new DataEntryAction(this, start, end);
	private JButton entryButton = new JButton(entryAction);
	
	private static Dijkstra dijkstraGraph;
	
	/**
	 * The constructor of the game.
	 */
	public DijkstraGUI() {
		super("Dijkstras Algorithm Put to Good Use");
		myPanel = new DijkstraPanel();
		setup();
		myPanel.repaint();
	}
	
	public static Dijkstra getDijkstraGraph() {
		return dijkstraGraph;
	}

	public static void setDijkstraGraph(Dijkstra dijkstraGraph) {
		DijkstraGUI.dijkstraGraph = dijkstraGraph;
	}
	
	public void repaintPanel() {
		myPanel.repaint();
	}
	
	/**
	 * Enables/Disables user-input controls.
	 */
	public void enableControls(boolean bEnabled) {
	  start.setEnabled(bEnabled);
    end.setEnabled(bEnabled);
    entryButton.setEnabled(bEnabled);
	}
	
	/**
	 * The method that is used to setup the game.
	 */
	private void setup() {
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setLayout(new BorderLayout());
		this.add(myPanel, BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel(new FlowLayout());
		
		southPanel.setBackground(DijkstraPanel.HUSKY_PURPLE);
		southPanel.add(browseButton);
		southPanel.add(entryButton);
		this.add(southPanel, BorderLayout.SOUTH);
		
		JPanel northPanel = new JPanel(new FlowLayout());
		northPanel.setBackground(DijkstraPanel.HUSKY_PURPLE);
		start.setColumns(10);
		end.setColumns(10);
		startPoint.setForeground(DijkstraPanel.HUSKY_GOLD);
		endPoint.setForeground(DijkstraPanel.HUSKY_GOLD);
		northPanel.add(startPoint);
		northPanel.add(start);
		northPanel.add(endPoint);
		northPanel.add(end);
		
		//Disable input until user selects input file.
		start.setEnabled(false);
        end.setEnabled(false);
        entryButton.setEnabled(false);
    
		this.add(northPanel, BorderLayout.NORTH);
		
		this.setSize(400, 700);
		this.setLocation((SCREEN_SIZE.width / 2) - (this.getSize().width / 2),
				(SCREEN_SIZE.height / 2) - (this.getSize().height / 2));
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
