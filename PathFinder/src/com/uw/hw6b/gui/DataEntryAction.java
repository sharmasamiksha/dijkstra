package com.uw.hw6b.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JTextField;

public class DataEntryAction extends AbstractAction {
	
	private static final long serialVersionUID = 4342506722359198678L;
	private DijkstraGUI myFrame;
	private JTextField start;
	private JTextField end;
	
	public DataEntryAction(final DijkstraGUI dg, final JTextField start, final JTextField end) {
		super("Calculate Route!");
		myFrame = dg;
		this.start = start;
		this.end = end;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DijkstraGUI.getDijkstraGraph().calculatePath(start.getText(), end.getText());
		myFrame.repaintPanel();
	}
}
