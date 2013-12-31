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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class NodeTest extends TestCase {
    
    public NodeTest (String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(NodeTest.class);
    }

    public void testNode() {
	Node a = new Node(null, new StringState("a"), null, 0, 4);
	Node b = new Node(a, new StringState("b"), null, 1, 3);
	Node c = new Node(b, new StringState("c"), null, 2, 4);

	assertNull(a.parent);
	assertSame(a, b.parent);
	assertNull(c.action);
	assertEquals(0, a.depth);
	assertEquals(2, c.depth);
	assertEquals(0.0, a.pathcost);
	assertEquals(3.0, c.pathcost);
	assertEquals(3.0, b.heuristic);
	assertEquals(4.0, a.totalcost);
	assertEquals(7.0, c.totalcost);
	
	List<Node> expected = new LinkedList<Node>();
	expected.add(a);
	expected.add(b);
	expected.add(c);
	List<Node> path = c.pathTo();
	assertEquals(expected, path);
    }
}
