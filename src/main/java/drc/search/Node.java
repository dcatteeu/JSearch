package drc.search;

import java.util.*;

public class Node {
    public final Node parent;
    public final StateInterface state;
    public final ActionInterface action;
    public final int depth;
    public final double pathcost, heuristic, totalcost;

    Node (Node parent, StateInterface state, ActionInterface action,
	  double stepcost, double heuristic) {
	this.parent = parent;
	this.state = state;
	this.action = action;
	this.depth = (parent==null ? 0 : parent.depth);
	this.pathcost = (parent==null ? 0 : parent.pathcost) + stepcost;
	this.heuristic = heuristic;
	this.totalcost = pathcost + heuristic;
    }
    
    List<Node> expand (SearchProblemInterface problem) {
	List<Node> children = new ArrayList<Node>();
	List<StateActionPair> stateActionPairs = problem.successors(state);
	for (StateActionPair stateActionPair : stateActionPairs) {
	    double stepcost = problem.stepcost(this.state,
					       stateActionPair.action,
					       stateActionPair.state);
	    double heuristic = problem.heuristic(stateActionPair.state);
	    Node child = new Node(this, stateActionPair.state,
				  stateActionPair.action, stepcost,
				  heuristic);
	    children.add(child);
	}
	return children;
    }

    public Node[] pathTo () {
	Deque<Node> path = new ArrayDeque<Node>();
	Node node = this;
	while (node != null) {
	    System.out.println(node.toString());
	    path.addFirst(node);
	    node = node.parent;
	}
	Node[] a = {};
	return path.toArray(a);
    }

    public String toString () {
	return new String("<Node: " +
			  //(parent==null ? "null" : parent.toString()) + ", " +
			  state.toString() + ", " +
			  (action==null ? "null" : action.toString())
			  + ", " +
			  depth + ", " +
			  pathcost + ", " +
			  heuristic + ", " +
			  totalcost + ">");
    }
}
