package com.uw.hw6b.gui;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import com.uw.hw6b.Dijkstra;

public class BrowseAction extends AbstractAction {
	
	/**
	 * Eclipse generated default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	
	private DijkstraGUI myFrame;
	private JTextField start;
	private JTextField end;

	/**
	 * The constructor of the action.
	 */
	public BrowseAction(final DijkstraGUI dg, final JTextField start, final JTextField end) {
		super("Browse For File");
		myFrame = dg;
		this.start = start;
		this.end = end;
	}

	@Override
	public void actionPerformed(final ActionEvent the_e) {
		
		String filePath = "";
		
		if (DijkstraGUI.getDijkstraGraph() != null)
		{
			filePath = DijkstraGUI.getDijkstraGraph().getCurrentFilePath();
		}
		
		JFileChooser fc = new JFileChooser(filePath);
		File f;
		fc.setDialogTitle("Open graph file...");
		int i = fc.showOpenDialog(fc);
		if (i == JFileChooser.OPEN_DIALOG) {
			f = fc.getSelectedFile();
			try
			{
				Dijkstra dijkstra = Dijkstra.loadGraphForDijkstra(f.getAbsolutePath());
				DijkstraGUI.setDijkstraGraph(dijkstra);
				myFrame.enableControls(true);
			}
			catch (NullPointerException ex) {
				// "Not a valid file input";
			} catch (NumberFormatException ex) {
				// "Not a valid file input";
			}
		}
		myFrame.repaintPanel();
	}
}
