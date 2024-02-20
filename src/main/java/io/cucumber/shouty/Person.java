package io.cucumber.shouty;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private final Network network;
    private final List<String> messagesHeard = new ArrayList<>();
    private final int location;

    public Person(Network network, Integer location) {
        this.network = network;
        this.location = location;
        network.subscribe(this);
    }

    public void shout(String message) {
        network.broadcast(message, location);
    }

    public void hear(String message) {
        messagesHeard.add(message);
    }
    public List<String> getMessagesHeard() {
        return messagesHeard;
    }

    public int getLocation() {
        return this.location;
    }
}
