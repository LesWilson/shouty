package io.cucumber.shouty;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PersonTest {
    private final Network network = mock(Network.class);

    @Test
    public void it_subscribes_to_the_network() {
        Person person = new Person(network, 100);
        verify(network).subscribe(person);
    }

    @Test
    public void it_has_a_location() {
        Person person = new Person(network, 100);
        assertEquals(100, person.getLocation());
    }

    @Test
    public void broadcasts_shouts_to_the_network() {
        String message = "Free bagels!";
        Person sean = new Person(network, 0);
        sean.shout(message);
        verify(network).broadcast(message, 0);
    }

    @Test
    public void remembers_messages_heard() {
        String message = "Free bagels!";
        Person lucy = new Person(network, 100);
        lucy.hear(message);
        assertEquals(Collections.singletonList(message), lucy.getMessagesHeard());
    }
}