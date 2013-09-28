package drc.search;

public class StateActionPair {
    public final StateInterface state;
    public final ActionInterface action;

    public StateActionPair (StateInterface state, ActionInterface action) {
	this.state = state;
	this.action = action;
    }
}
