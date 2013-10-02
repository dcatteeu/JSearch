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

public abstract class AbstractSearchProblem implements SearchProblemInterface {

    public double stepcost (StateInterface from, ActionInterface action,
			    StateInterface to) {
	return 1;
    }

    public double heuristic (StateInterface state) {
	return 0;
    }

    /* The generic search algorithm. */
    public Node genericSearch (OpenListInterface openlist,
			       boolean graphSearch,
			       long maxDepth) {
	/* Closed list. */
	ClosedListInterface closedlist
	    = graphSearch ? new ClosedList() : new DummyClosedList();
	
	/* Create initial node and add to openlist. */
	StateInterface initialState = this.getInitialState();
	double h = heuristic(initialState);
	Node initialNode = new Node(null, initialState, null, 0, h);
	openlist.add(initialNode);

	/* Loop until openlist is empty, maximum depth reached, or
	 * solution is found. */
	Node node = openlist.poll();
	while (node != null && node.depth < maxDepth) {
	    if (!closedlist.contains(node.state)) {
		closedlist.add(node.state);
		if (isSolution(node.state)) return node;
		LinkedList<Node> children = node.expand(this);
		openlist.addAll(children);
		node = openlist.poll();
	    }
	}
	return null; // No solution found.
    }

    public Node iterativeDeepening (OpenListInterface openlist,
				    boolean graphSearch,
				    long maxDepth) {
	Node solution = null;
	int limit = 1;
	while (limit < maxDepth) {
	    /* Probably not necessary, as the openlist is empty if the
	     * search failed. */
	    openlist.clear();
	    
	    solution = genericSearch(openlist, graphSearch, limit);
	    if (solution != null) return solution;
	    limit++;
	}
	return null; // No solution found.
    }

    public Node depthLimitedSearch (long maxDepth) {
    	OpenListInterface openlist = new LifoOpenList();
	return genericSearch(openlist, false, maxDepth);
    }

    public Node treeSearch (OpenListInterface openlist) {
	/* TODO: In Lisp i'd pass a function, instead of a number, and
	 * here i'd provide a function that always returns false. In
	 * C++, i'd use the strategy pattern and multiple
	 * inheritance. What's the best way to do this in Java? */
	return genericSearch(openlist, false, Long.MAX_VALUE);
    }

    public Node depthFirstSearch () {
    	OpenListInterface openlist = new LifoOpenList();
    	return treeSearch(openlist);
    }

    public Node breadthFirstSearch () {
	OpenListInterface openlist = new FifoOpenList();
	return treeSearch(openlist);
    }

    public Node uniformCostSearch () {
    	OpenListInterface openlist
	    = new PriorityQueueOpenList(11, new UniformCostComparator());
    	return treeSearch(openlist);	
    }

    public Node depthLimitedGraphSearch (long maxDepth) {
    	OpenListInterface openlist = new LifoOpenList();
	return genericSearch(openlist, false, maxDepth);
    }

    public Node greedyBestFirstSearch (boolean graphSearch) {
	OpenListInterface openlist
	    = new PriorityQueueOpenList(11, new GreedyComparator());
	return genericSearch(openlist, graphSearch, Long.MAX_VALUE);
    }

    public Node aStarSearch (boolean graphSearch) {
	OpenListInterface openlist
	    = new PriorityQueueOpenList(11, new AStarComparator());
	return genericSearch(openlist, graphSearch, Long.MAX_VALUE);
    }

    public Node iterativeDeepeningAStarSearch (boolean graphSearch) {
	OpenListInterface openlist
	    = new PriorityQueueOpenList(11, new AStarComparator());
	return iterativeDeepening(openlist, graphSearch, Long.MAX_VALUE);
    }
}
