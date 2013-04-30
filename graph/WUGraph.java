/* WUGraph.java */

  package graph;
  import dict.*;
  import list.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {
  protected HashTableChained vertHashTable;
  protected HashTableChained edgeHashTable;
  protected DList vertList = new DList();
  
  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph() {
    vertHashTable = new HashTableChained(10); 
    edgeHashTable = new HashTableChained(10);
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount() {
    return vertHashTable.size();
  }

  /**
   * edgeCount() returns the number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount(){
    return edgeHashTable.size();
  }
  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices() {
    Object[] vertArray = new Object[vertList.length()];
    DListNode current = (DListNode) vertList.front();
    for (int i = 0; i<vertList.length(); i++) {
      /*
        Takes every node of the list of vertices, 
        and puts them in an array to be outputted.
      */
        try {
          vertArray[i] = current.item();
          current = (DListNode) current.next();
        } catch (InvalidNodeException INE) {
          System.err.println(INE);
        }
      }
      return vertArray;
    }

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.  The
   * vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex){
    if (!isVertex(vertex)) {
      vertList.insertFront(vertex);
      Vertex vertexAdded = new Vertex();
      vertexAdded.changeVertListNode((DListNode) vertList.front());
      vertHashTable.insert(vertex, vertexAdded);
    }
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex) {
    if (isVertex(vertex)) {
      try { 
        Vertex theVertex = (Vertex) vertHashTable.find(vertex).value();
        DList adjList = theVertex.getAdjList();
        HalfEdge theHalfEdge = null;
        int iter = adjList.length();
        for (int i = 0; i<iter; i++) {
        /*
          Goes through every node of the
          vertices' adjacency lists and removes
          the edge that connects the two.
        */
          theHalfEdge = ((HalfEdge) adjList.front().item()).getHalfVertex();
          removeEdge(vertex, theHalfEdge); // Remove the edge.
        }
        ((DListNode) theVertex.getVertListNode()).remove(); // Remove the vertex from List
      } catch (InvalidNodeException INE) {
        System.err.println(INE);
      }
      vertHashTable.remove(vertex); // Remove vertex from hashTable
    }
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex){
    return vertHashTable.find(vertex) != null;
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex){
    if (isVertex(vertex)) {
      return ((Vertex) vertHashTable.find(vertex).value()).getAdjList().length();
    } else {
      return 0;
    }
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex){
    Neighbors now = new Neighbors();
    if(isVertex(vertex)) {
      Vertex theVertex = (Vertex) vertHashTable.find(vertex).value();
      DList theAdjList = (DList) theVertex.getAdjList();
      if (theAdjList.length() <= 0) {
        return null;
      }
      Object edgeArr[] = new Object[theAdjList.length()];
      int weightArr[] = new int[theAdjList.length()];
      DListNode currVertNode = (DListNode) theAdjList.front();
      HalfEdge currentVertex = null;
      int edgeWeight = 0;
      for(int i = 0; i < theAdjList.length(); i++) {
        /*
          Goes through every edge that connects to the
          vertex and collects the edge's weight and the
          vertex that it connects to.
        */
          try {
            currentVertex = (HalfEdge) currVertNode.item();
            VertexPair temp = new VertexPair(vertex, currentVertex.getHalfVertex());
            edgeWeight = ((GraphEdge) edgeHashTable.find(temp).value()).getWeight();
            edgeArr[i] = currentVertex.getHalfVertex();
            if (isEdge(vertex, currentVertex.getHalfVertex())) {
              weightArr[i] = edgeWeight;
            }
            if (currVertNode != theAdjList.back()) {
              currVertNode = (DListNode) currVertNode.next();
            }
          } catch (InvalidNodeException INE) {
            System.err.println(INE);
          }
        }
        now.neighborList = edgeArr;
        now.weightList = weightArr;
      }
      return now;

    }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the edge is already
   * contained in the graph, the weight is updated to reflect the new value.
   * Self-edges (where u == v) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight) {
   if (isVertex(u) && isVertex(v)) {
    VertexPair tempVertex = new VertexPair(u, v);
    if (isEdge(u, v)) {
      ((GraphEdge) edgeHashTable.find(tempVertex).value()).changeWeight(weight);
    } else {
      HalfEdge halfU = new HalfEdge();
      HalfEdge halfV = new HalfEdge();
      try {
        DList uAdjList = ((Vertex) vertHashTable.find(u).value()).getAdjList();
        DList vAdjList = ((Vertex) vertHashTable.find(v).value()).getAdjList();
        HalfEdge uHalfNode = null;
        HalfEdge vHalfNode = null;
        if (u.equals(v)) {
          halfU.changeHalfVertex(v); // Change the vertex that the edge connects to.
          uAdjList.insertFront(halfU); // Inserts the half-edge under V into the U AdjList.
          uHalfNode = (HalfEdge) uAdjList.front().item();
          uHalfNode.changeHalfNode((DListNode) vAdjList.front()); // Remember the ref to the node.
        }
        else if (!u.equals(v)) {
          halfV.changeHalfVertex(u);
          vAdjList.insertFront(halfV);
          halfU.changeHalfVertex(v);
          uAdjList.insertFront(halfU);
          uHalfNode = (HalfEdge) uAdjList.front().item();
          vHalfNode = (HalfEdge) vAdjList.front().item();
          uHalfNode.changeHalfNode((DListNode) vAdjList.front());
          vHalfNode.changeHalfNode((DListNode) uAdjList.front());
        }
        GraphEdge edgeAdded = new GraphEdge(weight, (DListNode) uAdjList.front(), (DListNode) vAdjList.front());
        edgeHashTable.insert(tempVertex, edgeAdded);
      } catch (InvalidNodeException INE) {
        System.err.println(INE);
      }
    }
  }
}

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v){
    if (isVertex(u) && isVertex(v) && isEdge(u, v)) {
      VertexPair edgeRemoved = new VertexPair(u, v);
      try {
        GraphEdge theEdge = (GraphEdge) edgeHashTable.find(edgeRemoved).value();
        theEdge.getVertex1Node().remove();
        if (!u.equals(v)) {
          theEdge.getVertex2Node().remove();
        }
      } catch (InvalidNodeException INE) {
        System.err.println(INE);
      }
      edgeHashTable.remove(edgeRemoved);
    }
  } 

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v) {
    VertexPair edgeFound = new VertexPair(u, v);
    return edgeHashTable.find(edgeFound) != null;
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but
   * also more annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v) {
    if (isEdge(u, v)) {
      VertexPair temp = new VertexPair(u, v);
      int weight = ((GraphEdge) edgeHashTable.find(temp).value()).getWeight();
      return weight;
    }
    return 0;
  }

}
