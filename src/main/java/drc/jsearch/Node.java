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

public class Node
{
    public final Node parent;
    public final StateInterface state;
    public final ActionInterface action;
    public final int depth;
    public final double pathcost, heuristic, totalcost;

    Node (Node parent, StateInterface state, ActionInterface action,
	  double stepcost, double heuristic) {
	this.parent = parent;
	this.state = state;
	this.action = action;
	this.depth = (parent==null ? 0 : parent.depth + 1);
	this.pathcost = (parent==null ? 0 : parent.pathcost + stepcost);
	this.heuristic = heuristic;
	this.totalcost = pathcost + heuristic;
    }
    
    List<Node> expand (SearchProblemInterface problem) {
	List<Node> children = new LinkedList<Node>();
	List<StateActionPair> stateActionPairs = problem.successors(state);
	for (StateActionPair stateActionPair : stateActionPairs) {
	    double stepcost = problem.stepcost(this.state,
					       stateActionPair.action,
					       stateActionPair.state);
	    double heuristic = problem.heuristic(stateActionPair.state);
	    Node child = new Node(this, stateActionPair.state,
				  stateActionPair.action, stepcost,
				  heuristic);
	    children.add(child);
	}
	return children;
    }

    // Return the path (as a list of nodes) from the initial node to
    // this node.
    public List<Node> pathTo () {
	LinkedList<Node> path = new LinkedList<Node>();
	Node node = this;
	while (node != null) {
	    path.addFirst(node);
	    node = node.parent;
	}
	return path;
    }

    // Return the path (as a list of actions) from the initial node to
    // this node.
    public List<ActionInterface> actionsTo () {
	LinkedList<ActionInterface> actions = new LinkedList<ActionInterface>();
	Node node = this;
	// As long as there is a parent, there is an action to get to
	// node.
	while (node.parent != null) {
	    actions.addFirst(node.action);
	    node = node.parent;
	}
	return actions;
    }

    @Override
    public String toString () {
	return new String("<Node: state:" + state
			  + ", action:" + (action==null ? "null" : action)
			  + ", depth: " + depth
			  + ", f: " + totalcost 
			  + ", g: " + pathcost
			  + ", h: " + heuristic+ ">");
    }
}
