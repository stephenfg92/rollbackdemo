package com.stephenfg.sre.events.statechange;

public interface StatechangeSubscriber {
    void onStateChange(StatechangeEvent evt);
}
