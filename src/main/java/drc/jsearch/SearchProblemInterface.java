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
