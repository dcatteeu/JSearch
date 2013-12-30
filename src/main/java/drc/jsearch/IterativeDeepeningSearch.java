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
    protected double maxDepth = 0;
    protected boolean bounded = false;
    
    public IterativeDeepeningSearch (ClosedListInterface closedlist,
				     DepthLimitInterface depthlimit) {
	super(new LifoOpenList(), closedlist, depthlimit);
    }
    
    /**
     * Returns the ID depth-first search, where the depth-limited
     * search uses a closedlist.
     *
     * @param closedlist
     */
    public IterativeDeepeningSearch (ClosedListInterface closedlist) {
	this(closedlist, new SimpleDepthLimit());
    }
    
    public IterativeDeepeningSearch (DepthLimitInterface depthlimit) {
	this(new DummyClosedList(), depthlimit);
    }

    /**
     * Returns the standard ID depth-first search.
     */
    public IterativeDeepeningSearch () {
	this(new SimpleDepthLimit());
    }

    /**
     * Returns the current iteration. During each iteration, depth
     * limited search is performed. Iterations are 0-based, so the
     * current iteration is also the number of times the depth limit
     * was increased.
     *
     * @return the current iteration
     */
    public int currentIteration () {
	return iteration;
    }

    /**
     * Makes the iterative deepening search bounded or unbounded.
     *
     * @param bounded  true/false
     */
    public void setMaxDepth (boolean bounded) {
	this.bounded = bounded;
    }

    /**
     * Makes the iterative deepening search bounded.
     *
     * @param maxDepth  the maximum depth at which depth limited search
     * is performed
     */
    public void setMaxDepth (double maxDepth) {
	this.maxDepth = maxDepth;
	this.bounded = true;
    }

    /**
     * Performs iterative deepening search and returns the solution or
     * null if no solution was found.
     *
     * @param problem  the problem to solve
     * @return the solution if one is found, otherwise null
     */
    public Node execute (SearchProblemInterface problem) {
	Node initialNode = initialNode(problem);
	depthlimit.initializeLimit(initialNode);
	
	Node solution = null;
	iteration = 0;
	while (!bounded || depthlimit.currentLimit() <= maxDepth) {
	    solution = super.execute(problem);

	    /* No solution exists, or search cancelled. */
	    if (solution == null) return null;
	    
	    /* Solution found. */
	    if (problem.isSolution(solution.state)) return solution; 

	    /* No solution found, search deeper. */
	    depthlimit.updateLimit(solution);
	    iteration++;
	}
	// Maximum depth limit reached.
	return null;
    }
}
