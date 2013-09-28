package drc.search;

import java.util.Comparator;

public class GreedyComparator implements Comparator<Node> {
    
    @Override
    public int compare (Node x, Node y) {
	if (x.heuristic < y.heuristic) return -1;
	if (x.heuristic > y.heuristic) return +1;
	return 0;
    }
}
