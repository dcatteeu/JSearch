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

interface DepthLimitInterface {
    boolean belowLimit (Node node);
    void incLimit (Node node);
}

abstract class AbstractDepthLimit implements DepthLimitInterface {
    double limit = 1;
    AbstractDepthLimit (double limit) {
	this.limit = limit;
    }
    AbstractDepthLimit () {
	this(1);
    }
    public void incLimit (Node node) {
	limit++;
    }
}

public abstract class AbstractSearchProblem
    implements SearchProblemInterface {

    private long nofNodesVisited = 0;
    private long nofNodesPruned = 0;
    private long nofClosedListHits = 0;

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
			       DepthLimitInterface function) {
	/* Closed list. */
	ClosedListInterface closedlist
	    = graphSearch ? new ClosedList() : new DummyClosedList();
	
	/* Create initial node and add to openlist. */
	StateInterface initialState = this.getInitialState();
	double h = heuristic(initialState);
	Node initialNode = new Node(null, initialState, null, 0, h);
	openlist.add(initialNode);
	
	/* Loop until openlist is empty or solution is found. */
	Node node = openlist.poll();
	Node shallowestNode = null;
	while (node != null) {
	    nofNodesVisited++;
	    if (!function.belowLimit(node)) {
		/* Depth limit reached. */
		nofNodesPruned++;
		if (shallowestNode == null ||
		    node.totalcost < shallowestNode.totalcost)
		    shallowestNode = node;
	    } else if (closedlist.contains(node.state)) {
		/* State already visited. */
		nofClosedListHits++;
	    } else {
		/* State not yet visited. */
		closedlist.add(node.state);
		if (isSolution(node.state)) return node;
		LinkedList<Node> children = node.expand(this);
		openlist.addAll(children);
	    }
	    node = openlist.poll();
	}
	return shallowestNode; // No solution found.
    }

    /* The generic search algorithm without depth limit. */
    public Node genericSearch (OpenListInterface openlist,
			       boolean graphSearch) {
	DepthLimitInterface function = new AbstractDepthLimit () {
		public boolean belowLimit (Node n) {
		    return true;
		}
	    };
	return genericSearch (openlist, graphSearch, function);
    }

    public Node iterativeDeepening (OpenListInterface openlist,
				    boolean graphSearch,
				    DepthLimitInterface function) {
	Node solution = null;
	for (int i = 0; i < Long.MAX_VALUE; i++) {
	    /* Probably not necessary, as the openlist is empty if the
	     * search failed. */
	    openlist.clear();
	    
	    solution = genericSearch(openlist, graphSearch, function);
	    if (solution == null)
		return null;
	    
	    if (isSolution(solution.state))
		return solution;
	    
	    function.incLimit(solution);
	}
	return null; // No solution found.
    }

    /* Performs DFS with increasing depth limits. The limit is
     * initially 1 and is incremented by 1. */
    public Node iterativeDeepeningDepthFirstSearch () {
    	OpenListInterface openlist = new LifoOpenList();
	double h = heuristic(getInitialState());
	DepthLimitInterface function = new AbstractDepthLimit(1) {
		public boolean belowLimit (Node node) {
		    return node.depth < limit;
		}
	    };
	return iterativeDeepening(openlist, false, function);
    }

    /* Performs DFS with increasing depth limits. The limit is
     * initially the heuristic of the root node and is incremented
     * with the minimum pathcost of any pruned node. */
    public Node iterativeDeepeningAStarSearch (boolean graphSearch) {
    	OpenListInterface openlist = new LifoOpenList();
	double h = heuristic(getInitialState());
	DepthLimitInterface function = new AbstractDepthLimit(h) {
		public boolean belowLimit (Node node) {
		    return node.totalcost < limit;
		}
	    };
	return iterativeDeepening(openlist, graphSearch, function);
    }

    public Node depthLimitedSearch (long maxDepth) {
	DepthLimitInterface function = new AbstractDepthLimit(maxDepth) {
		public boolean belowLimit (Node n) {
		    return n.depth < limit;
		}
	    };
    	OpenListInterface openlist = new LifoOpenList();
	return genericSearch(openlist, false, function);
    }

    public Node treeSearch (OpenListInterface openlist) {
	return genericSearch(openlist, false);
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
	DepthLimitInterface function = new AbstractDepthLimit(maxDepth) {
		public boolean belowLimit (Node n) {
		    return n.depth < limit;
		}
	    };
    	OpenListInterface openlist = new LifoOpenList();
	return genericSearch(openlist, true, function);
    }

    public Node greedyBestFirstSearch (boolean graphSearch) {
	OpenListInterface openlist
	    = new PriorityQueueOpenList(11, new GreedyComparator());
	return genericSearch(openlist, graphSearch);
    }

    public Node aStarSearch (boolean graphSearch) {
	OpenListInterface openlist
	    = new PriorityQueueOpenList(11, new AStarComparator());
	return genericSearch(openlist, graphSearch);
    }
}
