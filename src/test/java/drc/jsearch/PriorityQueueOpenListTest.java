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

public class PriorityQueueOpenListTest extends TestCase {
    
    public PriorityQueueOpenListTest (String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(PriorityQueueOpenListTest.class);
    }

    public void testPriorityQueueOpenList() {
	Node a = new Node(null, null, null, 0, 4);
	Node b = new Node(a, null, null, 1, 3);
	Node c = new Node(b, null, null, 2, 6);
	Node d = new Node(c, null, null, 3, 1);
	PriorityQueueOpenList openlist
	    = new PriorityQueueOpenList(new GreedyComparator());
	assertNull(openlist.poll());

	openlist.add(a);
	openlist.add(b);
	openlist.add(c);
	assertSame(b, openlist.poll());
	assertSame(a, openlist.poll());
	
	List<Node> list = new LinkedList<Node>();
	list.add(a);
	list.add(b);
	list.add(d);
	openlist.addAll(list);
	assertSame(d, openlist.poll());
	assertSame(b, openlist.poll());
	assertSame(a, openlist.poll());
	assertSame(c, openlist.poll());
    }
}
