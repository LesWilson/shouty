package io.cucumber.shouty;

import java.util.ArrayList;
import java.util.List;

public class Network {
    private final List<String> messages = new ArrayList<>();
    private final List<Person> peopleInNetwork = new ArrayList<>();
    private final Integer range;

    public Network(Integer range) {
        this.range = range;
    }

    public void subscribe(Person person) {
        peopleInNetwork.add(person);
    }

    public void broadcast(String message, int shouterLocation) {
        if(messageLengthIsValid(message)) {
            messages.add(message);
            peopleInNetwork.stream()
                    .filter(p -> personIsInRange(shouterLocation, p))
                    .forEach(p -> p.hear(message));
        }
    }

    private boolean personIsInRange(int shouterLocation, Person p) {
        return Math.abs(p.getLocation() - shouterLocation) <= range;
    }

    private boolean messageLengthIsValid(String message) {
        return message.length() <= 100;
    }

    public List<String> getMessages() {
        return messages;
    }
}
