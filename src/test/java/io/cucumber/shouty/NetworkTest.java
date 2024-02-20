package io.cucumber.shouty;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.mockito.Mockito.*;

class NetworkTest {
    private final int range = 100;
    private final Network network = new Network(range);
    private final String message = "Free bagels!";

    @Test
    public void broadcasts_a_message_to_a_listener_within_range() {
        int seanLocation = 0;
        Person lucy = mock(Person.class);
        network.subscribe(lucy);
        network.broadcast(message, seanLocation);

        verify(lucy).hear(message);
    }

    @Test
    public void does_not_broadcast_a_message_to_a_listener_out_of_range() {
        int seanLocation = 0;
        Person laura = mock(Person.class);
        when(laura.getLocation()).thenReturn(101);
        network.subscribe(laura);
        network.broadcast(message, seanLocation);

        verify(laura, never()).hear(message);
    }

    @Test
    public void does_not_broadcast_a_message_to_a_listener_out_of_range_negative_distance() {
        int sallyLocation = 101;
        Person lionel = mock(Person.class);
        when(lionel.getLocation()).thenReturn(0);
        network.subscribe(lionel);
        network.broadcast(message, sallyLocation);

        verify(lionel, never()).hear(message);
    }

    @Test
    public void does_not_broadcast_a_message_over_180_characters_even_if_listener_is_in_range() {
        int seanLocation = 0;

        char[] chars = new char[181];
        Arrays.fill(chars, 'x');
        String longMessage = String.valueOf(chars);

        Person laura = mock(Person.class);
        network.subscribe(laura);
        network.broadcast(longMessage, seanLocation);

        verify(laura, never()).hear(longMessage);
    }
}