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

public class IterativeDeepeningSearch
    extends GenericSearchAlgorithm
{
    protected int iteration = 0;
    
    public IterativeDeepeningSearch (ClosedListInterface closedlist,
				     DepthLimitInterface depthlimit) {
	super(new LifoOpenList(), closedlist, depthlimit);
    }
    
    public IterativeDeepeningSearch (ClosedListInterface closedlist) {
	this(closedlist, new SimpleDepthLimit());
    }
    
    public IterativeDeepeningSearch (DepthLimitInterface depthlimit) {
	this(new DummyClosedList(), depthlimit);
    }

    /* Implements Iterative deepening Depth first search */
    public IterativeDeepeningSearch () {
	this(new SimpleDepthLimit());
    }

    public int currentIteration () {
	return iteration;
    }
    
    public Node execute (SearchProblemInterface problem) {
	Node initialNode = initialNode(problem);
	depthlimit.initializeLimit(initialNode);
	
	Node solution = null;
	for (iteration = 0; true; iteration++) {
	    // System.out.println("start iteration: " + iteration
	    // 		       + ", " + depthlimit.currentLimit());
	    solution = super.execute(problem);

	    /* No solution exists, or search cancelled. */
	    if (solution == null) return null;
	    
	    /* Solution found. */
	    if (problem.isSolution(solution.state)) return solution; 

	    /* No solution found, search deeper. */
	    depthlimit.updateLimit(solution);
	    
	}
	// We should not get here.
    }
}
