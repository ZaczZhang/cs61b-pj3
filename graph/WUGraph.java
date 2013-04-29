/*
7/7 ON AUTOGRADER! 

Whats next? Clean the code.
  -- Indentations
  -- classes instead of arrays
  -- proper caps
*/

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
        DList vertAdjList = new DList();
  vertList.insertFront(vertex);
  Object[] vertValue = new Object[2];
  vertValue[0] = vertAdjList;
  vertValue[1] = vertList.front();
  vertHashTable.insert(vertex, vertValue);
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
    DList removeVertAL = (DList) ((Object[]) vertHashTable.find(vertex).value())[0];
    int iter = removeVertAL.length();
    for (int i = 0; i<iter; i++) {
      try {
      removeEdge(vertex, ((Object[]) removeVertAL.front().item())[0]);

    } catch (InvalidNodeException INE) {
      System.err.println(INE);
    }
    }
      try {
        ((DListNode) ((Object[]) vertHashTable.find(vertex).value())[1]).remove();
      } catch (InvalidNodeException INE) {
        System.err.println(INE);
      }
      vertHashTable.remove(vertex);
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
      return ((DList) ((Object[]) vertHashTable.find(vertex).value())[0]).length();
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
      DList nlist = (DList) ((Object[]) vertHashTable.find(vertex).value())[0];
      if (nlist.length() <= 0) {
        return null;
      }
      Object earray[] = new Object[nlist.length()];
      int warray[] = new int[nlist.length()];
      DListNode curr = (DListNode) nlist.front();
      for(int i = 0; i < nlist.length(); i++){
        try {
          earray[i] = ((Object[]) curr.item())[0];
          VertexPair temp = new VertexPair(vertex, ((Object[]) curr.item())[0]);
          if (isEdge(vertex, ((Object[]) curr.item())[0])) {
            warray[i] = (Integer) ((Object[]) edgeHashTable.find(temp).value())[0];
          }
          if (curr != nlist.back()) {
              curr = (DListNode) curr.next();
            }
        } catch (InvalidNodeException INE) {
          System.err.println(INE);
        }
      }
      now.neighborList = earray;
      now.weightList = warray;
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
        ((Object[]) edgeHashTable.find(tempVertex).value())[0] = weight;
      } else {
        Object[] tempInsertionU = new Object[2];
        Object[] tempInsertionV = new Object[2];
        try{
        if (u.equals(v)) {
          tempInsertionU[0] = v;
        ((DList) ((Object[]) vertHashTable.find(u).value())[0]).insertFront(tempInsertionU);
        ((Object[]) ((DList) ((Object[]) vertHashTable.find(u).value())[0]).front().item())[1] = ((DList) ((Object[]) vertHashTable.find(v).value())[0]).front();
      }
       else if (!u.equals(v)) {
          tempInsertionV[0] = u;
          ((DList) ((Object[]) vertHashTable.find(v).value())[0]).insertFront(tempInsertionV);
          tempInsertionU[0] = v;
        ((DList) ((Object[]) vertHashTable.find(u).value())[0]).insertFront(tempInsertionU);
            ((Object[]) ((DList) ((Object[]) vertHashTable.find(u).value())[0]).front().item())[1] = ((DList) ((Object[]) vertHashTable.find(v).value())[0]).front();
            ((Object[]) ((DList) ((Object[]) vertHashTable.find(v).value())[0]).front().item())[1] = ((DList) ((Object[]) vertHashTable.find(u).value())[0]).front();
        }
        } catch (InvalidNodeException INE) {
            System.err.println(INE);
          }
      Object[] weightAndNodes = new Object[3];
      weightAndNodes[0] = weight;
      weightAndNodes[1] = ((DList) ((Object[]) vertHashTable.find(u).value())[0]).front();
      weightAndNodes[2] = ((DList) ((Object[]) vertHashTable.find(v).value())[0]).front();
      edgeHashTable.insert(tempVertex, weightAndNodes);
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
          ((DListNode) ((Object[]) edgeHashTable.find(edgeRemoved).value())[1]).remove();
          if (!u.equals(v)) {
            ((DListNode) ((Object[]) edgeHashTable.find(edgeRemoved).value())[2]).remove();
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
      int weight = (Integer) ((Object[]) edgeHashTable.find(temp).value())[0];
      return weight;
    }
    return 0;
  }

}
