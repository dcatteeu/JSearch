package drc.jsearch;

import java.util.Comparator;

public class AStarComparator implements Comparator<Node> {
    
    @Override
    public int compare (Node x, Node y) {
	if (x.totalcost < y.totalcost) return -1;
	if (x.totalcost > y.totalcost) return +1;
	return 0;
    }
}
