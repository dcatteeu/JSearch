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

public abstract class AbstractOpenList implements OpenListInterface {

    protected Queue<Node> nodes;

    public Node poll () {
	return nodes.poll();
    }
    
    public boolean add (Node node) {
	return nodes.add(node);
    }

    public boolean addAll (LinkedList<Node> nodes) {
	boolean result = this.nodes.addAll(nodes);
	return result;
    }
    
    public void clear () {
	nodes.clear();
    }
}
