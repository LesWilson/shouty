package io.cucumber.shouty;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class StepDefinitions {

    private static final Integer DEFAULT_RANGE = 100;
    private Network network = new Network(DEFAULT_RANGE);
    private Map<String, Person> people;

    static class Whereabouts {
        public String name;
        public Integer location;

        public Whereabouts(String name, Integer location) {
            this.name = name;
            this.location = location;
        }
    }

    @DataTableType
    public Whereabouts createWhereabouts(Map<String, String> entry) {
        return new Whereabouts(entry.get("name"), Integer.parseInt(entry.get("location")));
    }

    @Before
    public void setup() {
        people = new HashMap<>();
    }

    @Given("the range is {int}")
    public void the_range_is(Integer range) {
        network = new Network(range);
    }

//    @Given("people are located at")
//    public void people_are_located_at(io.cucumber.datatable.DataTable dataTable) {
//        dataTable.asMaps()
//                .forEach(p -> people.put(p.get("name"), new Person(network, Integer.parseInt(p.get("location")))));
//    }

//    @Given("people are located at")
//    public void people_are_located_at(List<Whereabouts> whereabouts) {
//        whereabouts.forEach(p -> people.put(p.name, new Person(network, p.location)));
//    }

    @Given("people are located at")
    public void people_are_located_at(@Transpose List<Whereabouts> whereabouts) {
        whereabouts.forEach(p -> people.put(p.name, new Person(network, p.location)));
    }

    @Given("A/a person named {word}")
    public void a_person_named(String name) {
        people.put(name, new Person(network, 0));
    }

    @When("{word} shouts {string}")
    public void shout(String name, String message) {
        people.get(name).shout(message);
    }

    @When("{word} shouts the following message")
    public void shoutsTextString(String name, String message) {
        people.get(name).shout(message);
    }
    @When("{word} shouts")
    public void shout(String name) {
        people.get(name).shout("hello anybody");
    }

    @Then("{word} hears {word}'s message(s)")
    public void hears(String listenerName, String shoutersName) {
        assertThat(network.getMessages(), is(equalTo(people.get(listenerName).getMessagesHeard())));
    }

    @Then("{word} should hear a shout")
    public void hears(String listenerName) {
        assertThat(people.get(listenerName).getMessagesHeard(), hasSize(greaterThan(0)));
    }

    @Then("{word} should not hear a shout")
    public void should_not_hear_anything(String listenerName) {
        assertThat(people.get(listenerName).getMessagesHeard(), hasSize(equalTo(0)));
    }

    @Then("{word} does not hear {word}'s message")
    public void listener_does_not_hear_any_message(String name, String shouter) {
        assertThat(people.get(name).getMessagesHeard(), is(empty()));
    }

    @Then("{word} hears the following messages")
    public void lucy_hears_the_following_messages(String listenerName, io.cucumber.datatable.DataTable expectedMessages) {
        List<List<String>> messagesHeard = new ArrayList<>();
        people.get(listenerName)
                .getMessagesHeard()
                .forEach(it -> messagesHeard.add(Collections.singletonList(it)));
        expectedMessages.diff(DataTable.create(messagesHeard));
    }
}
