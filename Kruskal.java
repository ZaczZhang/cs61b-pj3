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
  public static WUGraph minSpanTree(WUGraph g){
   WUGraph t = new WUGraph();
   Object[] vert = g.getVertices();
   Edge[] edgeArr = new Edge[g.edgeCount()*5];
   int edgeIter = 0;
   for(int i = 0; i < vert.length; i++){
    t.addVertex(vert[i]);
    Neighbors neighbor = g.getNeighbors(vert[i]);
    Object[] vertNeighbors = neighbor.neighborList;
    int[] weightList = neighbor.weightList;
    for(int j = 0; j < vertNeighbors.length; j++){
     Edge edge = new Edge(vert[i], vertNeighbors[j], weightList[j]);
     edgeArr[edgeIter] = edge;
     edgeIter++;
   }
 }
 quicksort(edgeArr, edgeIter-1);
 DisjointSets edgeDJS = new DisjointSets(g.vertexCount());
 HashTableChained vertHTC = new HashTableChained(g.vertexCount());
 for (int k = 0; k < g.vertexCount(); k++) {
  vertHTC.insert(vert[k], k);
}
for (int l = 0, m = t.edgeCount(); m < g.vertexCount() -1; l++) {
  Edge tempEdge = edgeArr[l];
  if (tempEdge == null) break;
  int firstWeight = Integer.MIN_VALUE;
  int secondWeight = Integer.MIN_VALUE;
  firstWeight = edgeDJS.find((Integer) vertHTC.find(tempEdge.getVertex1()).value());
  secondWeight = edgeDJS.find((Integer) vertHTC.find(tempEdge.getVertex2()).value());
  if (edgeDJS.find(firstWeight) != edgeDJS.find(secondWeight)) {
   t.addEdge(tempEdge.getVertex1(), tempEdge.getVertex2(), tempEdge.getWeight());
   edgeDJS.union(firstWeight, secondWeight);
 }
}
return t;
}

  /**
   *  Quicksort algorithm.
   *  @param a an array of int items.
   **/
  public static void quicksort(Edge[] a, int hi) {
    quicksort(a, 0, hi);
  }

   /**
   *  Method to swap two ints in an array.
   *  @param a an array of ints.
   *  @param index1 the index of the first int to be swapped.
   *  @param index2 the index of the second int to be swapped.
   **/
   public static void swapReferences(Edge[] a, int index1, int index2) {
    Edge tmp = a[index1];
    a[index1] = a[index2];
    a[index2] = tmp;
  }

  /**
   *  This is a generic version of C.A.R Hoare's Quick Sort algorithm.  This
   *  will handle arrays that are already sorted, and arrays with duplicate
   *  keys.
   *
   *  If you think of an array as going from the lowest index on the left to
   *  the highest index on the right then the parameters to this function are
   *  lowest index or left and highest index or right.  The first time you call
   *  this function it will be with the parameters 0, a.length - 1.
   *
   *  @param a       an integer array
   *  @param lo0     left boundary of array partition
   *  @param hi0     right boundary of array partition
   **/
  private static void quicksort(Edge a[], int lo0, int hi0) {
   int lo = lo0;
   int hi = hi0;
   int mid;

   if (hi0 > lo0) {

       // Arbitrarily establishing partition element as the midpoint of
       // the array.
     swapReferences(a, lo0, (lo0 + hi0)/2);
     mid = a[(lo0 + hi0) / 2].getWeight();

       // loop through the array until indices cross.
     while (lo <= hi) {
         // find the first element that is greater than or equal to 
         // the partition element starting from the left Index.
       while((lo < hi0) && (a[lo].getWeight() < mid)) {
         lo++;
       }

         // find an element that is smaller than or equal to 
         // the partition element starting from the right Index.
       while((hi > lo0) && (a[hi].getWeight() > mid)) {
         hi--;
       }
         // if the indices have not crossed, swap them.
       if (lo <= hi) {
         swapReferences(a, lo, hi);
         lo++;
         hi--;
       }
     }

       // If the right index has not reached the left side of array
       // we must now sort the left partition.
     if (lo0 < hi) {
       quicksort(a, lo0, hi);
     }

       // If the left index has not reached the right side of array
       // must now sort the right partition.
     if (lo < hi0) {
       quicksort(a, lo, hi0);
     }
   }
 }
