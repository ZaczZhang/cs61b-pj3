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
	//HashTableChained edgeHTC= new HashTableChained();
	Edge[] edgeArr = new Edge[g.edgeCount()*5];
	System.out.println("edgeArr.length(): " + edgeArr.length);
	int edgeIter = 0;
	for(int i = 0; i < vert.length; i++){
		t.addVertex(vert[i]);

		Neighbors neighbor = g.getNeighbors(vert[i]);
		Object[] vertNeighbors = neighbor.neighborList;
		for (int z = 0; z<vertNeighbors.length; z++) {
			System.out.println("neighbor[z]: " + vertNeighbors[z]);
		}
		int[] weightList = neighbor.weightList;

		for(int j = 0; j < vertNeighbors.length; j++){
			Edge edge = new Edge(vert[i], vertNeighbors[j], weightList[j]);
			System.out.println("weight: " + weightList[j]);
			edgeArr[edgeIter] = edge;
			edgeIter++;
			/*if (edgeHTC.find(edge) == null) {
				edgeHTC.insert(edge, edge.getWeight());
				edgeArr[edgeIter] = edge;
				edgeIter++;
			}*/
		}
	}
	quicksort(edgeArr, edgeIter-1);
	return t;
  }

  /**
   *  Quicksort algorithm.
   *  @param a an array of int items.
   **/
  public static void quicksort(Edge[] a, int hi) {
  	for(int i = 0; i<hi; i++) {
   		//System.out.println("Weight " + i + " is: " + a[i].getWeight());
   }
    quicksort(a, 0, hi);
    for(int k = 0; k<hi; k++) {
   		//System.out.println("Weight " + k + " is: " + a[k].getWeight());
   }
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

}
