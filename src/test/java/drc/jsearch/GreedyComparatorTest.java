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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class GreedyComparatorTest extends TestCase {
    
    public GreedyComparatorTest (String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(GreedyComparatorTest.class);
    }

    public void testGreedyComparator() {
	Node a = new Node(null, null, null, 0, 2);
	Node b = new Node(a, null, null, 1, 3);
	Node c = new Node(a, null, null, 2, 2);
	Node d = new Node(a, null, null, 1, 1);
	GreedyComparator comparator = new GreedyComparator();

	assertEquals(-1, comparator.compare(a, b));
	assertEquals(0, comparator.compare(a, c));
	assertEquals(+1, comparator.compare(a, d));
    }
}
