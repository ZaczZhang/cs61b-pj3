/* Vertex.java */

package graph;
import list.*;

/**
  Vertex is an object that represents a vertex in the graph
	package. It keeps track of the adjacency list and its location
	in terms of the list of vertices.
	**/
	public class Vertex {

		protected DList adjList;
		protected DListNode vertListNode;
		public Vertex() {
			adjList = new DList();
		}
	/**
		changeVertListNode() Changes the reference to the node
		that carries the vertex Object pointer in the list of vertices.
		@params: DListNode theNode (the node that carries the
		vertex Object pointer in the list of vertices.)
		@return: none.
		**/
		public void changeVertListNode(DListNode theNode) {
			vertListNode = theNode;
		}
	/**
		getAdjList() returns the adjacency list for this vertex.
		@params: None
		@return: adjList (the adjacency list for this vertex.)
		**/
		public DList getAdjList() {
			return adjList;
		}

	/**
		getVertListNode() returns the node that carries the vertex
		Object pointer in the list of vertices.
		@params: None
		@return: vertListNode (the node that carries the vertex
		Object Pointer in the list of vertices)
**/
public DListNode getVertListNode() {
	return vertListNode;
}

}
