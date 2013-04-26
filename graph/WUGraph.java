/*
Whats wrong?

Adding an edge calls for InvalidNodeException

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
  protected DList vertList = new DList(); // Keeps track of vertices for getNeighbors & getVertices
  
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
  Object[] vertArray = new Object[vertList.length()]; // Array of vertexes (output)
  DListNode current = (DListNode) vertList.front();
  for (int i = 0; i<vertList.length(); i++) { 
    /* Traverse list of vertices and put every item into the output array. */
    try {
      vertArray[i] = current.item();
      current = (DListNode) current.next();
    } catch (InvalidNodeException INE) {
      System.out.println("75");
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
      vertList.insertBack(vertex);
      Object[] vertValue = new Object[2];
      /*  Keeps track of [0] the adjacency list & [1] Its reference in the DList of vertices */
      vertValue[0] = vertAdjList;
      vertValue[1] = vertList.back();
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
    try {
      ((DListNode) ((Object[]) vertHashTable.find(vertex).value())[1]).remove(); // Remove From DList of vertices.
    } catch (InvalidNodeException INE) {
      System.err.println(INE);
    }
    DListNode cursor = (DListNode) removeVertAL.front();
    for (int i = 0; i<removeVertAL.length(); i++) {
        try {
            /*  Remove edges that involve this vertex from the edgeHashTable
                Remove half-edges from other vertices' adjLists*/
            VertexPair edgeRemoving = new VertexPair(vertex, ((Object[]) ((DListNode) ((Object[]) cursor.item())[1]).item())[0]);
            edgeHashTable.remove(edgeRemoving);
            ((DListNode) ((Object[]) cursor.item())[1]).remove();
          if (cursor != removeVertAL.back()) {
            cursor = (DListNode) cursor.next();
          }
        } catch (InvalidNodeException INE) {
          System.out.println("129");
          System.err.println(INE);
        }
        break;
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
    return ((DList) ((Object[]) vertHashTable.find(vertex).value())[0]).length();
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
      DList nlist = (DList) ((Object[]) vertHashTable.find(vertex).value())[0]; // the vertex's adjList
      System.out.println("nlist's length is: " + nlist.length());
      if (nlist.length() <= 0) {
        return null;
      }
      Object earray[] = new Object[nlist.length()];
      int warray[] = new int[nlist.length()];
      DListNode curr = (DListNode) nlist.front();
      for(int i = 0; i < nlist.length(); i++){
        try {
          /*
            Sets the cells of the array of neighbors to the neighbors of the vertex's adjList
            Sets the cells of the weight array to the weight of the edge between the vertex &
            it's neighbor. 
          */
        earray[i] = ((Object[]) curr.item())[0];
        VertexPair temp = new VertexPair(vertex, ((Object[]) curr.item())[0]);
        warray[i] = (Integer) edgeHashTable.find(temp).value();
          if (curr != nlist.back()) {
            curr = (DListNode) curr.next();
        }
      } catch (InvalidNodeException INE) {
          System.out.println("193");
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
        /* If it's already an edge, remove the edge from the hashTable then insert it 
        again with new weight.  */
        edgeHashTable.remove(tempVertex);
        edgeHashTable.insert(tempVertex, weight);
      } else {
        /*
          if it's not already an edge, insert it into the hashtable, then insert it 
          declare it's [0]  
        */
        edgeHashTable.insert(tempVertex, weight);
          if (u.equals(v)) {
            Object[] tempInsertion = new Object[2];
            try {
            ((DList) ((Object[]) vertHashTable.find(u).value())[0]).insertFront(tempInsertion);
            ((Object[]) ((DList) ((Object[]) vertHashTable.find(u).value())[0]).front().item())[0] = v;
            ((Object[]) ((DList) ((Object[]) vertHashTable.find(u).value())[0]).front().item())[1] = ((DList) ((Object[]) vertHashTable.find(v).value())[0]).front();
            } catch (InvalidNodeException INEIN) {
              System.err.println(INEIN);
              System.out.println("inner 1");
            }
          } else {
            try {
            Object[] tempInsertion = new Object[2];
            ((DList) ((Object[]) vertHashTable.find(u).value())[0]).insertFront(tempInsertion);
            ((Object[]) ((DList) ((Object[]) vertHashTable.find(u).value())[0]).front().item())[0] = v;
            ((Object[]) ((DList) ((Object[]) vertHashTable.find(v).value())[0]).front().item())[0] = u;
            ((Object[]) ((DList) ((Object[]) vertHashTable.find(u).value())[0]).front().item())[1] = ((DList) ((Object[]) vertHashTable.find(v).value())[0]).front();
            ((Object[]) ((DList) ((Object[]) vertHashTable.find(v).value())[0]).front().item())[1] = ((DList) ((Object[]) vertHashTable.find(u).value())[0]).front();
          } catch (InvalidNodeException INEIN) {
              System.err.println(INEIN);
              System.out.println("inner 2");
            }
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
    if (isVertex(u) && isVertex(v)) {
    VertexPair edgeRemoved = new VertexPair(u, v);
    if (isEdge(u, v)) {
      DList vertEdges = (DList) ((Object[]) vertHashTable.find(u).value())[0];
      DListNode cursor = (DListNode) vertEdges.front();
      for (int i = 0; i<vertEdges.length(); i++) {
        try {
          if (((Object[]) cursor.item())[0].equals(u) || ((Object[]) cursor.item())[0].equals(v)) {
          if ((DListNode) ((Object[]) cursor.item())[1] != null) {
            ((DListNode) ((Object[]) cursor.item())[1]).remove();
          }
          cursor.remove();
          break;
        }
          if (cursor != vertEdges.back()) {
            cursor = (DListNode) cursor.next();
          }
        } catch (InvalidNodeException INE) {
          System.out.println("265");
          System.err.println(INE);
        }
      }
      edgeHashTable.remove(edgeRemoved);
    } 
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
      int weight = (Integer) edgeHashTable.find(temp).value();
      return weight;
    }
    return 0;
  }

}
