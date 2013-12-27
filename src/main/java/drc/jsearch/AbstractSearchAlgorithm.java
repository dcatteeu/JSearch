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

public abstract class AbstractSearchAlgorithm
    implements SearchAlgorithmInterface
{
    protected boolean isCancelled = false;
    public ClosedListInterface closedlist;
    protected OpenListInterface openlist;
    protected DepthLimitInterface depthlimit;
    protected int nofNodesVisited;
    protected int nofNodesPruned;
    protected int nofClosedListHits;
    protected int currentDepth;

    public void cancel (boolean cancel) {
	isCancelled = cancel;
    }

    public int nofNodesVisited () {
	return nofNodesVisited;
    }
    
    public int nofClosedListHits () {
	return nofClosedListHits;
    }
    
    public int currentDepth () {
	return currentDepth;
    }

    public int memoryUse () {
	return openlist.size() + closedlist.size();
    }

    public int cacheSize () {
	return closedlist.size();
    }

    public void resetStatistics () {
	nofNodesVisited = 0;
	nofNodesPruned = 0;
	nofClosedListHits = 0;
	currentDepth = 0;
    }

    protected Node initialNode (SearchProblemInterface problem) {
	StateInterface initialState = problem.getInitialState();
	double h = problem.heuristic(initialState);
	Node node = new Node(null, initialState, null, 0, h);
	return node;
    }
}
