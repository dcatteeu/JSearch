/* Copyright 2013 David Catteeuw

This file is part of JSearch.

JSearch is free software: you can redistribute it and/or modify it
under the terms of the GNU General Public License as published by the
Free Software Foundation, either version 3 of the License, or (at your
option) any later version.

JSearch is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
for more details.

You should have received a copy of the GNU General Public License
along with JSearch.  If not, see <http://www.gnu.org/licenses/>. */

package drc.jsearch;

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
