package drc.search;

import java.util.*;

public abstract class SearchProblem implements SearchProblemInterface {

    public double stepcost (StateInterface from, ActionInterface action,
			    StateInterface to) {
	return 1;
    }

    public double heuristic (StateInterface state) {
	return 0;
    }

    public Node treeSearch (Queue<Node> openlist) {
	StateInterface initialState = this.getInitialState();
	double h = heuristic(initialState);
	Node initialNode = new Node(null, initialState, null, 0, h);
	System.out.println(initialNode.toString());
	openlist.add(initialNode);
	Node node = openlist.poll();
	while (node != null) {
	    if (isSolution(node.state)) return node;
	    List<Node> children = node.expand(this);
	    for (Node child : children) openlist.add(child);
	    node = openlist.poll();
	}
	return null;
    }

    public Node breadthFirstTreeSearch () {
	Queue<Node> openlist = new LinkedList<Node>();
	return treeSearch(openlist);
    }

    public Node depthLimitedGraphSearch (Queue<Node> openlist, int limit) {
	/* Create closedlist */
	Set<StateInterface> closedlist = new HashSet<StateInterface>();

	/* Create initial node and add to openlist */
	StateInterface initialState = this.getInitialState();
	double h = heuristic(initialState);
	Node initialNode = new Node(null, initialState, null, 0, h);
	openlist.add(initialNode);

	/* Loop until openlist empty or solution is found */
	Node node = openlist.poll();
	while (node != null && node.depth < limit) {
	    if (!closedlist.contains(node.state)) {
		closedlist.add(node.state);
		if (isSolution(node.state)) return node;
		List<Node> children = node.expand(this);
		for (Node child : children) openlist.add(child);
		node = openlist.poll();
	    }
	}
	return null; // No solution found.
    }

    public Node iterativeDeepeningGraphSearch (Queue<Node> openlist,
					       int maxLimit) {
	Node solution = null;
	int limit = 1;
	while (limit < maxLimit) {
	    openlist.clear(); // Probably not necessary, as the
			      // openlist is empty if the search
			      // failed.
	    solution = depthLimitedGraphSearch(openlist, limit);
	    if (solution != null) return solution;
	    limit++;
	}
	return solution;
    }

    public Node iterativeDeepeningAStarSearch (int maxLimit) {
	Queue<Node> openlist
	    = new PriorityQueue<Node>(11, new AStarComparator());
	return iterativeDeepeningGraphSearch(openlist, maxLimit);
    }
}
