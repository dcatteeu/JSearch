package drc.search;

import java.util.Comparator;

public class UniformCostComparator implements Comparator<Node> {
    
    @Override
    public int compare (Node x, Node y) {
	if (x.pathcost < y.pathcost) return -1;
	if (x.pathcost > y.pathcost) return +1;
	return 0;
    }
}
