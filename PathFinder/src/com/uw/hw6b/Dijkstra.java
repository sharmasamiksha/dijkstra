package com.uw.hw6b;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

public class Dijkstra {

	private String filePath; 
	private SimpleGraph simpleGraph;
	private Hashtable<String, Vertex> hashTable;
	private ArrayList<DijkstraHeapNode> calculatedPath;

	public static Dijkstra loadGraphForDijkstra(String fileName)
	{
		return new Dijkstra(fileName);
	}
	
	@SuppressWarnings("unchecked")
	private Dijkstra(String filePath) {

		this.filePath = filePath;
		this.simpleGraph = new SimpleGraph();

		if (!filePath.equals("")) {
			hashTable = GraphInput.LoadSimpleGraph(simpleGraph,
								filePath);
			
			Iterator<Vertex> iter = simpleGraph.vertices(); // iterating each vertex of the graph
			while (iter.hasNext()) {
				Vertex v = iter.next();
				DijkstraHeapNode heapNode = new DijkstraHeapNode(v);
				v.setData(heapNode); // associating the DijkstraHeapNode
									 // object with Vertex object
			}
				
			System.out.println(hashTable.toString());
		}
	}
	
	public String getCurrentFilePath()
	{
		return this.filePath;
	}
	
	public SimpleGraph getGraph()
	{
		return this.simpleGraph;
	}
	
	public Hashtable<String, Vertex> getVertexTable()
	{
		return this.hashTable;
	}

	public ArrayList<DijkstraHeapNode> getCalculatedPath() {
		return this.calculatedPath;
	}
	
	public void calculatePath(String start, String end)
	{
		this.calculatedPath = calculateAndReturnPath(start, end);
	}
	
	private ArrayList<DijkstraHeapNode> calculateAndReturnPath(String start, String end)
	{
		start = start.trim();
		end = end.trim();
		
		if (hashTable.containsKey(start) && hashTable.containsKey(end)) {
			Vertex startV = hashTable.get(start);
			Vertex endV = hashTable.get(end);

			// Dijsktra algorithm implementation begins
			DijkstraMinHeap heap = new DijkstraMinHeap();
			
			@SuppressWarnings("unchecked")
			Iterator<Vertex> iter = simpleGraph.vertices(); // iterating each vertex of the graph
			while (iter.hasNext()) {
				Vertex v = iter.next();
				DijkstraHeapNode dhn = (DijkstraHeapNode) v.getData();
				dhn.init();
				heap.insert(dhn); // inserting every
										// DijkstraHeapNode object into
										// heap
			}

			DijkstraHeapNode startDhn = (DijkstraHeapNode) startV
					.getData();
			startDhn.updateDistanceIfLess(0, null); // updating the
													// distance of the
													// starting
													// node/vertex

			for (int i = 0; i < simpleGraph.vertexList.size(); i++) // running
																	// this
																	// loop
																	// |V|
																	// times
			{
				try {
					DijkstraHeapNode uDhn = heap.deleteMin(); // Capturing the node deleted
																// from heap
					Vertex u = uDhn.getMyNode(); // finding the associated vertex
													// of the deleted DijkstraHeapNode
					@SuppressWarnings("unchecked")
					Iterator<Edge> iterEdge = u.incidentEdgeList
							.iterator();
					while (iterEdge.hasNext()) // iterating each neighboring vertex of
												// the vertex in the known set
					{ // and updating the distance accordingly
						Edge edge = iterEdge.next();
						Vertex v = simpleGraph.opposite(u, edge);
						DijkstraHeapNode vDhn = (DijkstraHeapNode) v
								.getData();

						if (vDhn.getHeapIndex() > 0) {
							double distance = uDhn.getDistance()
									+ (Double) edge.getData();
							vDhn.updateDistanceIfLess(distance, u);
						}
					}
				} catch (EmptyHeapException exception) {
					exception.printStackTrace();
				}
			}

			DijkstraHeapNode dhn = (DijkstraHeapNode) endV.getData();
			ArrayList<DijkstraHeapNode> nodeList = new ArrayList<DijkstraHeapNode>();
			nodeList.add(0, dhn);

			while (dhn.getPreviousNode() != null) {
				dhn = (DijkstraHeapNode) dhn.getPreviousNode()
						.getData();
				nodeList.add(0, dhn);
			}
			
			return nodeList;
//
//			StringBuilder sb = new StringBuilder();
//			sb.append("PATH: \n");
//			for (DijkstraHeapNode dh : nodeList) {
//				sb.append("City: ");
//				sb.append(dh.getMyNode().getName());
//				sb.append(" -- Total Distance: ");
//				sb.append(dh.getDistance());
//				sb.append("\n");
//			}
//
			//PATH = sb.toString();
			//System.out.println(PATH);
		}
		else
		{
			return null;
		}
	}
}
