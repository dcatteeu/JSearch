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

    // Need openlistinterface to implement this, since a stack is not
    // a queue but a vector.
    // public Node depthFirstTreeSearch () {
    // 	Queue<Node> openlist = new Stack<Node>();
    // 	return treeSearch(openlist);
    // }

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
