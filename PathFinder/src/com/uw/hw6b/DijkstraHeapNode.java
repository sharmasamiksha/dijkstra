package com.uw.hw6b;

public class DijkstraHeapNode implements Comparable<DijkstraHeapNode> {

	private double distance;
	private int heapIndex;
	private Vertex previousNode;
	private Vertex myNode;
	private DistanceUpdateListener listener;
	
	private double guiX;
	private double guiY;
	
	public DijkstraHeapNode (Vertex myNode) {	
		this.myNode = myNode;
		
		this.setGuiX(Math.random());
		this.setGuiY(Math.random());
		
		init();
	}
	
	public void init() {
		this.distance = Integer.MAX_VALUE;
		this.previousNode = null;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void updateDistanceIfLess(double distance, Vertex previousNode) {
		if(distance < this.distance)
		{	
			this.distance = distance;
			this.previousNode = previousNode;
			
			// Notify any listener listening to my distance update
			if (this.listener != null)
			{
				this.listener.onDistanceUpdate(heapIndex);
		    }
		}
	}

	public int getHeapIndex() {
		return heapIndex;
	}
	
	public void setHeapIndex(int heapIndex) {
		this.heapIndex = heapIndex;
	}

	public Vertex getPreviousNode() {
		return previousNode;
	}
	
	public Vertex getMyNode() {
		return myNode;
	}
	
	public void registerDistanceUpdateListener(DistanceUpdateListener listener) {
		this.listener = listener;
	}
	
	public void unregisterDistanceUpdateListener() {
		this.listener = null;
	}

	@Override
	public int compareTo(DijkstraHeapNode node) {
		if(this.distance < node.distance) {
			return -1;
		}
		else if(this.distance > node.distance) {
			return 1;
		}
				
		return 0;
	}
	
	public double getGuiX() {
		return guiX;
	}

	public void setGuiX(double guiX) {
		this.guiX = guiX;
	}

	public double getGuiY() {
		return guiY;
	}

	public void setGuiY(double guiY) {
		this.guiY = guiY;
	}

	public interface DistanceUpdateListener
	{
		public void onDistanceUpdate(int index);
	}
}
