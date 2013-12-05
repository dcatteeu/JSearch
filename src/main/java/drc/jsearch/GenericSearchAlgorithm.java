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
    
    public Node search (SearchProblemInterface problem) {
	openlist.add(initialNode(problem));
	Node node = openlist.poll();
	Node shallowestNode = null;
	
	/* Loop until openlist is empty or solution is found. */
	while (node != null) {
	    nofNodesVisited++;
	    if (!depthlimit.belowLimit(node)) {
		/* Depth limit reached. */
		nofNodesPruned++;
		if (shallowestNode == null ||
		    node.totalcost < shallowestNode.totalcost) {
		    shallowestNode = node;
		}
	    } else if (closedlist.contains(node.state)) {
		/* State already seen. */
		nofClosedListHits++;
	    } else {
		/* State not yet seen. */
		closedlist.add(node.state);
		if (problem.isSolution(node.state)) return node; // Solution found.
		LinkedList<Node> children = node.expand(problem);
		openlist.addAll(children);
	    }
	    node = openlist.poll();
	}
	return shallowestNode; // No solution found.
    }
}
