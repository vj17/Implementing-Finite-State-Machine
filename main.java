import java.util.*;

class FiniteStatesMachine {
    Map<StateEventKey, String> transitionMap = new HashMap<>();
    String currentState;

    public void add(String currentState, String event, String nextState) {
        var stateEventKey = new StateEventKey(currentState, event);
        transitionMap.put(stateEventKey, nextState);
    }

    public void setInitialState(String initialState) {
        this.currentState = initialState;
    }

    public String run(String event) {
        StateEventKey key = findKey(this.currentState, event);

        if(key == null)
            return "Key not found";

        var nextState = transitionMap.get(key);
        this.currentState = nextState;

        return nextState;
 }

    private StateEventKey findKey(String currentState, String event) {
        var stateEventKeySet = transitionMap.keySet();
        for(var key: stateEventKeySet) {
            if(key.state == currentState && key.event == event)
                return key;
        }
        return null;
    }

    private class StateEventKey {
        String state;
        String event;

        public StateEventKey(String state, String event) {
            this.state = state;
            this.event = event;
        }
    }
}

class Main {
  public static void main(String[] args) {

    // For Customer Support scenario
    FiniteStatesMachine fsm = new FiniteStatesMachine();

    // User defines his own states, events, transitions
    fsm.add("idle", "incoming", "ringing");
    fsm.add("ringing", "pickup", "talking");
    fsm.add("ringing", "hangup", "voicemail");
    fsm.add("talking", "hangup", "idle");

    // User needs to set the initialState (he can change the state as well)
    fsm.setInitialState("idle");

    System.out.println(fsm.run("incoming")); //ringing
    System.out.println( fsm.run("hangup")); //voicemail
    
    fsm.setInitialState("ringing");

    System.out.println(fsm.run("pickup")); //talking
    System.out.println(fsm.run("hangup")); //idle

    // For DevOps scenario

    FiniteStatesMachine fsm2 = new FiniteStatesMachine();
    fsm2.add("stable", "db_down", "critical");
    fsm2.add("stable", "nw_down", "warning");
    fsm2.add("critical", "db_up", "stable");
    fsm2.add("warning", "nw_up", "stable");

    fsm2.setInitialState("stable");

    System.out.println(fsm2.run("db_down")); // critical
    System.out.println(fsm2.run("db_up")); //stable
    System.out.println(fsm2.run("nw_down")); //warning
    }
   
  }
