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

public class IterativeDeepeningSearch extends GenericSearchAlgorithm
{
    public IterativeDeepeningSearch (DepthLimitInterface depthlimit) {
	super(new LifoOpenList(), new DummyClosedList(), depthlimit);
    }

    /* Implements Iterative deepening Depth first search */
    public IterativeDeepeningSearch () {
	this(new DummyDepthLimit() {
		public boolean belowLimit (Node node) {
		    return node.depth < limit;
		}
	    });
    }
    
    public Node search (SearchProblemInterface problem) {
	Node initialNode = initialNode(problem);
	depthlimit.initializeLimit(initialNode);
	
	Node solution = null;
	for (int i = 0; i < Long.MAX_VALUE; i++) {
	    /* Probably not necessary, as the openlist is empty if the
	     * search failed. */
	    openlist.clear();
	    
	    solution = super.search(problem);
	    if (solution == null) return null;
	    
	    if (problem.isSolution(solution.state)) return solution;
	    
	    depthlimit.incLimit(solution);
	}
	return null; // No solution found.
    }
}
