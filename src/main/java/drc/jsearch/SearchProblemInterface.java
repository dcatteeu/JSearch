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

public interface SearchProblemInterface {
    StateInterface getInitialState();
    boolean isSolution (StateInterface state);
    List<StateActionPair> successors (StateInterface state);
    double stepcost (StateInterface from, ActionInterface action,
		     StateInterface to);
    double heuristic (StateInterface state);
}
