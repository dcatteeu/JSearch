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

import java.util.LinkedList;

public class Example1
{
    final static String labels[]
	= {"S", "A", "B", "C", "D", "E", "G1", "G2", "G3"};
    final static double heuristics[]
	= {100, 10, 25, 1, 3, 6, 0, 0, 0};
    final static String start = "S";
    final static String goals[] = {"G1", "G2", "G3"};
    
    Graph graph;
    
    Example1 () {
	graph = new Graph(labels, heuristics, start, goals);

	/* Each edge is a possible action. The order in which edges
	 * are added to a vertex, determine the order in which edges
	 * are explored. */
	graph.addEdge("S",  "A",  1);
	graph.addEdge("S",  "B",  7);
	graph.addEdge("A",  "B",  2);
	graph.addEdge("A",  "C",  1);
	graph.addEdge("A",  "D", 15);
	graph.addEdge("B",  "D", 10);
	graph.addEdge("B",  "E",  4);
	graph.addEdge("B",  "G3",11);
	graph.addEdge("C",  "S",  2);
	graph.addEdge("D",  "G1", 5);
	graph.addEdge("D",  "G2", 3);
	graph.addEdge("E",  "D",  3);
	graph.addEdge("G2", "E",  6);
    }

    LinkedList<String> path (Node solution) {
	LinkedList<Node> path = solution.pathTo();
	LinkedList<String> labels = new LinkedList<String>();
	for (Node node : path) {
	    Graph.Vertex vertex = (Graph.Vertex) node.state;
	    labels.add(vertex.label);
	}
	return labels;
    }

    void report (String algorithm, Node solution) {
	System.out.println(algorithm + ": " + path(solution)
			   + ", cost: " + solution.totalcost);
    }

    void test () {
	report("depth first", graph.depthFirstSearch());
	report("breadth first", graph.breadthFirstSearch());
	report("uniform cost", graph.uniformCostSearch());
	report("A* treesearch", graph.aStarSearch(false));
	report("A* graphsearch", graph.aStarSearch(true));
	report("ID DFS", graph.iterativeDeepeningDepthFirstSearch());
	report("ID A*", graph.iterativeDeepeningAStarSearch(false));
    }

    public static void main (String[] argv) {
	Example1 example = new Example1();
	example.test();
    }
}
