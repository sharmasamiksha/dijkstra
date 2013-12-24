package com.uw.hw6b.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.JPanel;

import com.uw.hw6b.DijkstraHeapNode;
import com.uw.hw6b.Edge;
import com.uw.hw6b.Vertex;

public class DijkstraPanel extends JPanel {

	private static final long serialVersionUID = -4012847448902492687L;
	private int x = 20;
	private int y = 30;
	private static final int CIRCLE_SIZE = 20;
	private static final int STRING_OFFSET_X = 17;
	private static final int STRING_OFFSET_Y = 5;
	private String TOTAL_DISTANCE = "";
	protected static final Color HUSKY_GOLD = new Color(232, 211, 182);
	protected static final Color HUSKY_PURPLE = new Color(54, 60, 116);

	/**
	 * The constructor of this panel.
	 */
	public DijkstraPanel() {
		super();
		this.setBackground(HUSKY_GOLD);
	}

	/**
	 * The overloaded repaint method.
	 */
	public final void repaint() {
		super.repaint();
	}

	/**
	 * The method used to draw on the board.
	 * 
	 * @param the_graphics
	 *            used to draw on the board.
	 */
	@Override
	public final void paintComponent(final Graphics the_graphics) {
		super.paintComponent(the_graphics);
		final Graphics2D g2d = (Graphics2D) the_graphics;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		if(DijkstraGUI.getDijkstraGraph() != null)
		{
			if (DijkstraGUI.getDijkstraGraph().getCalculatedPath() == null)
			{
				drawCityNames(g2d);
			}
			else
			{
				drawGraphWithPath(g2d);
				g2d.drawString("Total Distance: " + TOTAL_DISTANCE, 20, this.getHeight() - 10 - g2d.getFontMetrics().getHeight());
			}
		}
	}

	private void drawCityNames(final Graphics2D g2d) {
		
		g2d.drawString(DijkstraGUI.getDijkstraGraph().getCurrentFilePath(), 20, this.getHeight() - 10);
		g2d.drawString("Current Graph File: ", 20, this.getHeight() - 10 - g2d.getFontMetrics().getHeight());
		
		y = 30;
		
		Hashtable<String, Vertex> table = DijkstraGUI.getDijkstraGraph().getVertexTable();
		
		g2d.drawString("Please select cities from following:", x, y += g2d.getFontMetrics().getHeight());
		
		for (Enumeration<String> keyEnumeration = table.keys(); keyEnumeration.hasMoreElements(); )
		{
			String key = keyEnumeration.nextElement();
			g2d.drawString(key, x, y += g2d.getFontMetrics().getHeight());
		}
	}
	
	private void drawGraphWithPath(final Graphics2D g2d) {
		int leftX = 40;
		int topY = 40;
		int rightX = this.getWidth() - 40;
		int bottomY = this.getHeight() - 80;
		
		Hashtable<String, Vertex> table = DijkstraGUI.getDijkstraGraph().getVertexTable();
		ArrayList<DijkstraHeapNode> pathList = DijkstraGUI.getDijkstraGraph().getCalculatedPath();
		
		// Draw Graph
		g2d.setColor(DijkstraPanel.HUSKY_PURPLE);
		for (Enumeration<String> keyEnumeration = table.keys(); keyEnumeration.hasMoreElements(); )
		{
			String key = keyEnumeration.nextElement();
			DijkstraHeapNode dhn = (DijkstraHeapNode) table.get(key).getData();
		
			if (!pathList.contains(dhn))
			{
				drawVertex(g2d, leftX, topY, rightX, bottomY, dhn);
			}
		}
		
		for (@SuppressWarnings("unchecked")
		Iterator<Edge> edgeIter = DijkstraGUI.getDijkstraGraph().getGraph().edges(); edgeIter.hasNext(); )
		{
			Edge edge = edgeIter.next();
			
			DijkstraHeapNode dhn1 = (DijkstraHeapNode) edge.getFirstEndpoint().getData();
			DijkstraHeapNode dhn2 = (DijkstraHeapNode) edge.getSecondEndpoint().getData();
			
			g2d.setColor(DijkstraPanel.HUSKY_PURPLE);
			drawEdge(g2d, leftX, topY, rightX, bottomY, dhn1, dhn2);
		}
		
		// Draw path
		g2d.setColor(Color.RED);
		Stroke stroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(2));
		for (Iterator<DijkstraHeapNode> dhnIter = pathList.iterator(); dhnIter.hasNext(); )
		{
			DijkstraHeapNode dhn = dhnIter.next();
			DijkstraHeapNode dhnPrevious = (dhn.getPreviousNode() != null) ? (DijkstraHeapNode) dhn.getPreviousNode().getData() : null;
			
			drawVertex(g2d, leftX, topY, rightX, bottomY, dhn);
		
			if (dhnPrevious != null)
			{
				drawEdge(g2d, leftX, topY, rightX, bottomY, dhn, dhnPrevious);
			}
		}
		g2d.setStroke(stroke);
	}

	private void drawEdge(final Graphics2D g2d, int leftX, int topY,
			int rightX, int bottomY, DijkstraHeapNode dhn,
			DijkstraHeapNode dhnPrevious) {
		
		g2d.drawLine(
				(int) ((dhn.getGuiX() * (rightX - leftX)) + leftX + (CIRCLE_SIZE / 2)), 
				(int) ((dhn.getGuiY() * (bottomY - topY)) + topY + (CIRCLE_SIZE / 2)),
				(int) ((dhnPrevious.getGuiX() * (rightX - leftX)) + leftX + (CIRCLE_SIZE / 2)), 
				(int) ((dhnPrevious.getGuiY() * (bottomY - topY)) + topY) + (CIRCLE_SIZE / 2));
	
//
//		Was trying to print edge weights. Location comes out correct but 
//      calculating weight is a problem for now.
//
//		int stringX = (int) (((dhnPrevious.getGuiX() * (rightX - leftX)) + leftX + (CIRCLE_SIZE / 2)) 
//				+ (dhn.getGuiX() * (rightX - leftX)) + leftX + (CIRCLE_SIZE / 2)) / 2;
//		
//		int stringY = (int) (((dhnPrevious.getGuiY() * (bottomY - topY)) + topY + (CIRCLE_SIZE / 2)) 
//		+ (dhn.getGuiY() * (bottomY - topY)) + topY + (CIRCLE_SIZE / 2)) / 2;
//		
//		g2d.drawString((String) "" + (dhn.getDistance() - dhnPrevious.getDistance()), 
//				(int) (stringX), 
//				(int) (stringY));
	}

	private void drawVertex(final Graphics2D g2d, int leftX, int topY,
			int rightX, int bottomY, DijkstraHeapNode dhn) {
		g2d.fillOval(
				(int) ((dhn.getGuiX() * (rightX - leftX)) + leftX), 
				(int) ((dhn.getGuiY() * (bottomY - topY)) + topY),
				CIRCLE_SIZE,
				CIRCLE_SIZE);
		g2d.drawString((String) dhn.getMyNode().getName() + "(" + dhn.getDistance() + ")", 
				(int) ((dhn.getGuiX() * (rightX - leftX)) + leftX) - STRING_OFFSET_X, 
				(int) ((dhn.getGuiY() * (bottomY - topY)) + topY) - STRING_OFFSET_Y);
		TOTAL_DISTANCE = "" + dhn.getDistance();
	}
}
