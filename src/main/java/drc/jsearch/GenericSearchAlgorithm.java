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

public class GenericSearchAlgorithm extends AbstractSearchAlgorithm
{
    GenericSearchAlgorithm (OpenListInterface openlist,
			    ClosedListInterface closedlist,
			    DepthLimitInterface function) {
	this.openlist = openlist;
	this.closedlist = closedlist;
	this.depthlimit = function;
    }
    
    GenericSearchAlgorithm (OpenListInterface openlist,
			    ClosedListInterface closedlist) {
	this(openlist, closedlist, new DummyDepthLimit ());
    }
    
    GenericSearchAlgorithm (OpenListInterface openlist) {
	this(openlist, new DummyClosedList());
    }
    
    GenericSearchAlgorithm () {
	this(new FifoOpenList());
    }
    
    public Node execute (SearchProblemInterface problem) {
	Node result = null, node, shallowestNode = null;
	resetStatistics();
	openlist.add(initialNode(problem));

	/* Loop until openlist is empty, search is cancelled, or
	 * solution is found. */
	while (true) {
	    node = openlist.poll();
	    if (node == null) {
		result = shallowestNode;
		break; // Open list empty, no solution found.
	    }
	    if (isCancelled) {
		result = null;
		break; // Search cancelled.
	    }
	    updateNofNodesVisited(node);
	    currentDepth = Math.max(currentDepth, node.depth);
	    if (!depthlimit.belowLimit(node)) {
		/* Depth limit reached. */
		nofNodesPruned++;
		if (shallowestNode == null ||
		    node.totalcost < shallowestNode.totalcost) {
		    shallowestNode = node;
		}
	    } else if (problem.isSolution(node.state)) {
		/* Solution found. */
		result = node; 
		break; // Solution found.
	    } else if (closedlist.contains(node.state)) {
		/* State already seen. */
		nofClosedListHits++;
	    } else {
		/* State not yet seen, not a solution, below limit. */
		closedlist.add(node.state);
		List<Node> children = node.expand(problem);
		openlist.addAll(children);
	    }
	}
	openlist.clear();
	closedlist.clear();
	return result;
    }
}
