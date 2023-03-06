package com.stephenfg.sre.events;

import com.stephenfg.sre.events.statechange.StatechangeEvent;

public interface IPublisher {
    void notifySubscribers(StatechangeEvent evt);
}
