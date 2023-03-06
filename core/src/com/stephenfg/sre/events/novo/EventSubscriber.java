package com.stephenfg.sre.events.novo;

public interface EventSubscriber {
    void subscribeToEvent(Class<? extends Event> event, String methodName);
}
