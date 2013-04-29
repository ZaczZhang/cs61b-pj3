//EDGE

public class Edge {
    private Object vertex1;
    private Object vertex2;
    private int weight;

    public Edge (Object start, Object end, int theWeight) {
        vertex1 = start;
        vertex2 = end;
        weight = theWeight;
    }

    public int getWeight() {
        return weight;
    }

    public Object getVertex1() {
      return vertex1;
    }

    public Object getVertex2() {
      return vertex2;
    }

    //public int hashCode() {
    //return 
    //}

    //public boolean equals() {
        
    //}

    /**
   * hashCode() returns a hashCode equal to the sum of the hashCodes of each
   * of the two objects of the pair, so that the order of the objects will
   * not affect the hashCode.  Self-edges are treated differently:  we don't
   * add an object's hashCode to itself, since the result would always be even.
   * We add one to the hashCode so that a self-edge will not collide with the
   * object itself if vertices and edges are stored in the same hash table.
   */
  public int hashCode() {
    if (vertex1.equals(vertex2)) {
      return vertex1.hashCode() + 1;
    } else {
      return vertex1.hashCode() + vertex2.hashCode();
    }
  }

  /**
   * equals() returns true if this VertexPair represents the same unordered
   * pair of objects as the parameter "o".  The order of the pair does not
   * affect the equality test, so (u, v) is found to be equal to (v, u).
   */
  public boolean equals(Object o) {
    if (o instanceof Edge) {
      return ((vertex1.equals(((Edge) o).vertex1)) &&
              (vertex2.equals(((Edge) o).vertex2))) ||
             ((vertex1.equals(((Edge) o).vertex2)) &&
              (vertex2.equals(((Edge) o).vertex1)));
    } else {
      return false;
    }
  }
}
