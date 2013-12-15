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

public class DummyClosedList implements ClosedListInterface {

    public DummyClosedList () {
	; // Do nothing.
    }

    public boolean contains (StateInterface state) {
	return false;
    }

    public boolean add (StateInterface state) {
	return true; // True indicates the element was not yet in the
		     // set.
    }

    public void clear () {
	; // Do nothing.
    }

    public int size () {
	return 0;
    }
}
