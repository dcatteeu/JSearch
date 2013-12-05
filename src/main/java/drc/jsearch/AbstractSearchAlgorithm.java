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

public abstract class AbstractSearchAlgorithm implements SearchAlgorithmInterface
{    
    protected ClosedListInterface closedlist;
    protected OpenListInterface openlist;
    protected DepthLimitInterface depthlimit;
    protected long nofNodesVisited = 0;
    protected long nofNodesPruned = 0;
    protected long nofClosedListHits = 0;
    protected long currentDepth = 0;

    public long nofNodesVisited () {
	return nofNodesVisited;
    }
    
    public long currentDepth () {
	return currentDepth;
    }

    protected Node initialNode (SearchProblemInterface problem) {
	StateInterface initialState = problem.getInitialState();
	double h = problem.heuristic(initialState);
	Node node = new Node(null, initialState, null, 0, h);
	return node;
    }
}
