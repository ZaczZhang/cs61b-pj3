/* Kruskal.java */

import graph.*;
import set.*;
import list.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   */
  public static WUGraph minSpanTree(WUGraph g){
	WUGraph t = new WUGraph();
	Object[] vert = g.getVertices();
	DList wEdge = new DList();

	for(int i = 0; i < vert.length; i++){
		t.addVertex(vert[i]);
		
		Neighbors neighbor = g.getNeighbors(vert[i]);
		Object[] vertNeighbors = neighbor.neighborList;
		int[] weightList = neighbor.weightList;

		for(int j = 0; j < vertNeighbors.length; j++){
			Edge edge = new Edge(vert[i], vertNeighbors[j], weightList[j]);
			wEdge.insertBack(edge);
		}
	}
	return t;
  }

}
