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
    
    protected int[] nofNodesVisitedPerDepth = new int[0];
    protected int nofNodesVisited = 0;
    protected int nofNodesPruned = 0;
    protected int nofClosedListHits = 0;
    protected int currentDepth = 0;

    /**
     * Cancels (or uncancels) the search. It will stop at the next
     * iteration. When the search is cancelled, you need to uncancel
     * before you can run the same algorithm again.
     *
     * @param cancel  true cancels, false uncancels
     */
    public void cancel (boolean cancel) {
	isCancelled = cancel;
    }

    /**
     * Let the algorithm track the number of visited nodes per depth
     * level. 
     *
     * @param depth  track number of visited nodes per depth level
     * below given depth. Use zero if you do not want track visited
     * nodes per depth level.
     */
    public void trackNofNodesVisitedPerDepth (int depth) {
	nofNodesVisitedPerDepth = new int[depth];
    }

    /**
     * Return the number of visited nodes at given depth. Assumes
     * depth is a valid index.
     *
     * @param depth  root node has depth 0
     * @return number of visited nodes at given depth
     */
    public int nofNodesVisited (int depth) {
	return nofNodesVisitedPerDepth[depth];
    }

    /**
     * Return the total number of visited nodes.
     *
     * @param total number of visited nodes
     */
    public int nofNodesVisited () {
	return nofNodesVisited;
    }

    public void updateNofNodesVisited (Node node) {
	nofNodesVisited++;
	if (node.depth < nofNodesVisitedPerDepth.length) {
	    nofNodesVisitedPerDepth[node.depth]++;
	}
    }
    
    public int nofClosedListHits () {
	return nofClosedListHits;
    }
    
    public int currentDepth () {
	return currentDepth;
    }

    public double currentDepthLimit() {
	return depthlimit.currentLimit();
    }
    
    public int memoryUse () {
	return openlist.size() + closedlist.size();
    }
    
    public int cacheSize () {
	return closedlist.size();
    }
    
    /**
     * Resets all statistics. Call this function (manually?) before
     * running the search algorithm.
     */
    public void resetStatistics () {
	for (int i = 0; i < nofNodesVisitedPerDepth.length; i++) {
	    nofNodesVisitedPerDepth[i] = 0;
	}
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
