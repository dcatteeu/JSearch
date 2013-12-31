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

public class LifoOpenListTest extends TestCase {
    
    public LifoOpenListTest (String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(LifoOpenListTest.class);
    }

    public void testLifoOpenList() {
	Node a = new Node(null, null, null, 0, 4);
	Node b = new Node(a, null, null, 1, 3);
	Node c = new Node(b, null, null, 2, 2);
	Node d = new Node(c, null, null, 3, 1);
	LifoOpenList openlist = new LifoOpenList();
	assertNull(openlist.poll());

	openlist.add(a);
	openlist.add(b);
	assertSame(b, openlist.poll());
	assertSame(a, openlist.poll());
	
	LinkedList<Node> list = new LinkedList<Node>();
	list.add(c);
	list.add(d);
	openlist.addAll(list);
	openlist.add(a);
	assertSame(a, openlist.poll());
	openlist.add(b);
	assertSame(b, openlist.poll());
	assertSame(d, openlist.poll());
	assertSame(c, openlist.poll());
    }
}
