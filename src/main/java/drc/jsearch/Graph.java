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

public class Graph extends AbstractSearchProblem {

    public class Vertex implements StateInterface {
    
	static final double DEFAULT_HEURISTIC = 0.0;
	
	final String label; // Should be unique for each vertex.
	final double heuristic;
	final boolean isGoal;
	List<Edge> edges; // FiFo sorted.
	
	Vertex (String label, double heuristic, boolean isGoal) {
	    this.label = label;
	    this.heuristic = heuristic;
	    this.isGoal = isGoal;
	    this.edges = new LinkedList<Edge>();
	}
	
	void addEdge (Edge edge) {
	    edges.add(edge);
	}
	
	/* StateInterface implementation. */

	@Override
	public boolean equals (Object other) {
	    Vertex otherVertex = (Vertex) other;
	    return label.equals(otherVertex.label);
	}

	@Override
	public int hashCode () {
	    return label.hashCode();
	}

	@Override
	public String toString () {
	    StringBuilder neighbors = new StringBuilder();
	    Iterator<Edge> it = edges.iterator();
	    while (it.hasNext()) {
		Edge e = it.next();
		neighbors.append(e.to.label);
		if (it.hasNext()) neighbors.append(",");
	    }
	    return new String("<Vertex: " + label
			      + ", neighbors=" + neighbors + ">");
	}
    }

    public class Edge implements ActionInterface {
	
	final static double DEFAULT_COST = 1.0;
	
	final Vertex from, to;
	final double cost;
	
	Edge (Vertex from, Vertex to, double cost) {
	    this.from = from;
	    this.to = to;
	    this.cost = cost;
	}
	
	Edge (Vertex from, Vertex to) {
	    this(from, to, DEFAULT_COST);
	}

	@Override
	public String toString () {
	    return new String("<Edge: from=" + from + ", to=" + to
			      + ", cost=" + cost + ">");
	}
    }
    
    HashMap<String, Vertex> vertices;
    Vertex start;

    public Graph (String[] labels, double[] heuristics, String start,
		  String[] goals) {
	vertices = new HashMap<String, Vertex>();
	for (int i = 0; i < labels.length; i++) {
	    boolean isGoal = false;
	    for (int j = 0; j < goals.length; j++) {
		if (labels[i].equals(goals[j])) {
		    isGoal = true;
		    break;
		}
	    }
	    Vertex vertex = new Vertex(labels[i], heuristics[i], isGoal);
	    vertices.put(labels[i], vertex);
	    if (labels[i].equals(start)) this.start = vertex;
	}
    }

    public void addEdge (String from, String to, double cost) {
	Vertex fromVertex = vertices.get(from);
	Vertex toVertex = vertices.get(to);
	Edge edge = new Edge(fromVertex, toVertex, cost);
	fromVertex.addEdge(edge);
    }

    @Override
    public String toString () {
	return new String("<Graph: " + vertices.keySet() + ">");
    }
    
    /* SearchProblem implementation. */

    public StateInterface getInitialState () {
	return start;
    }

    public boolean isSolution (StateInterface state) {
	Vertex vertex = (Vertex) state;
	return vertex.isGoal;
    }

    public List<StateActionPair> successors (StateInterface state) {
	Vertex vertex = (Vertex) state;
	List<StateActionPair> stateActionPairs 
	    = new LinkedList<StateActionPair>();
	for (Edge edge : vertex.edges) {
	    stateActionPairs.add(new StateActionPair(edge.to, edge));
	}
	return stateActionPairs;
    }

    public double stepcost (StateInterface from, ActionInterface action,
			    StateInterface to) {
	Edge edge = (Edge) action;
	assert edge.from.equals((Vertex) from);
	assert edge.to.equals((Vertex) to);
	return edge.cost;
    }

    public double heuristic (StateInterface state) {
	Vertex vertex = (Vertex) state;
	return vertex.heuristic;
    }
}
