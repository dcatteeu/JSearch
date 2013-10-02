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

public class ClosedListTest extends TestCase {

    private static class State implements StateInterface {
	String label;

	State (String label) {
	    this.label = label;
	}

	public boolean equals (StateInterface other) {
	    State otherState = (State) other;
	    return label.equals(otherState.label);
	}

	public int hashCode () {
	    return label.hashCode();
	}
    }
    
    public ClosedListTest (String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ClosedListTest.class);
    }

    public void testClosedList() {
	State a = new State("a");
	State b = new State("b");
	ClosedList closedlist = new ClosedList();
	assertFalse(closedlist.contains(a));
	closedlist.add(a);
        assertTrue(closedlist.contains(a));
	assertFalse(closedlist.contains(b));
	closedlist.add(b);
        assertTrue(closedlist.contains(a));
	assertTrue(closedlist.contains(b));
    }
}
