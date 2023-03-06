package com.stephenfg.sre.events;

public interface Subscriber {
    Subscriber subscribeToEvent(Class<? extends Event> event);
}
