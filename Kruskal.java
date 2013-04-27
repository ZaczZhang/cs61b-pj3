/* Kruskal.java */

import graph.*;
import set.*;
import list.*;
import dict.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   */
    public static WUGraph minSpanTree(WUGraph g) {
        WUGraph T = new WUGraph();
        Object[] vertices = g.getVertices();
        for (int i = 0; i < vertices.length; i++) {
            T.addVertex(vertices[i]);
        }
        
        DList edgeList = new DList(); 
        //Neighbors neighbor = 
        for (int i = 0; i < vertices.length; i++) {
            Neighbors neighbor = g.getNeighbors(vertices[i]);
            Object[] neighborList = neighbor.neighborList;
            int[] weightList = neighbor.weightList;
            for (int c = 0; c < neighborList.length; c++) {
                Edge e = new Edge(vertices[i], neighborList[c], weightList[c]);  
                edgeList.insertFront(e);
            }
        }
        
        Edge[] edgeArray = new Edge[edgeList.length()];
        mergeSort(edgeArray);
        
        HashTableChained vertexTable = new HashTableChained();
        for (int i = 0; i < edgeArray.length; i++) {
            vertexTable.insert(edgeArray[i], i);
        }
        
        //VertexPair pair = new VertexPair();
        return T;
    }

    
  /**
   *  Mergesort algorithm.
   *  @param a an array of int items.
   **/
  public static void mergeSort(Edge[] a) {
    Edge[] tmpArray = new Edge[a.length];

    mergeSort(a, tmpArray, 0, a.length - 1);
  }

  /**
   *  Internal method that makes recursive calls.
   *  @param a an array of int items.
   *  @param tmpArray an array to place the merged result.
   *  @param left the left-most index of the subarray.
   *  @param right the right-most index of the subarray.
   **/
  private static void mergeSort(Edge[] a, Edge[] tmpArray, int left, int right) {
    if (left < right) {
      int center = (left + right) / 2;
      mergeSort(a, tmpArray, left, center);
      mergeSort(a, tmpArray, center + 1, right);
      merge(a, tmpArray, left, center + 1, right);
    }
  }

  /**
   *  Internal method that merges two sorted halves of a subarray.
   *  @param a an array of int items.
   *  @param tmpArray an array to place the merged result.
   *  @param leftPos the left-most index of the subarray.
   *  @param rightPos the index of the start of the second half.
   *  @param rightEnd the right-most index of the subarray.
   **/
  private static void merge(Edge[] a, Edge[] tmpArray, int leftPos, int rightPos,
                            int rightEnd) {
    int leftEnd = rightPos - 1;
    int tmpPos = leftPos;
    int numElements = rightEnd - leftPos + 1;

    // Main loop
    while (leftPos <= leftEnd && rightPos <= rightEnd) {
      if (a[leftPos].getWeight() < a[rightPos].getWeight()) {
        tmpArray[tmpPos++] = a[leftPos++];
      } else {
        tmpArray[tmpPos++] = a[rightPos++];
      }
    }
    while (leftPos <= leftEnd) {
      tmpArray[tmpPos++] = a[leftPos++];
    }
    while(rightPos <= rightEnd) {
      tmpArray[tmpPos++] = a[rightPos++];
    }
    // Copy TmpArray back
    for (int i = 0; i < numElements; i++, rightEnd--) {
      a[rightEnd] = tmpArray[rightEnd];
    }
  }
    
}
