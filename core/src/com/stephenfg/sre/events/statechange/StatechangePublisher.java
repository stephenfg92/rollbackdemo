package com.stephenfg.sre.events.statechange;

import com.stephenfg.sre.events.IPublisher;

import java.util.ArrayList;
import java.util.List;

public class StatechangePublisher implements IPublisher {
    private List<StatechangeSubscriber> subscribers = new ArrayList<>();

    public void addSubscriber(StatechangeSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void notifySubscribers(StatechangeEvent evt) {
        for (StatechangeSubscriber subscriber : subscribers){
            subscriber.onStateChange(evt);
        }
    }
}
