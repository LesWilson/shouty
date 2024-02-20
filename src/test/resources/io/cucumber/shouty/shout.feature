Feature: Hear shout

  This application allows users to hear other users shouts,
  as long as they are close enough to each other

  To Do:
    - only shout to people within a certain distance

#  Background:
#    Given A person named Lucy
#    And a person named Sean

  Rule: Shouts can be heard by other users
    Scenario: Listener hears a different message
      Given A person named Sean
      And a person named Lucy
      When Sean shouts "free coffee!"
      Then Lucy hears Sean's message

  Rule: Shouts should only be heard if listener is within range
    Scenario: Listener is within range
      Given the range is 100
      And people are located at
        | name     | Sean | Lucy |
        | location | 0    | 50   |
#        | name | location |
#        | Sean | 0        |
#        | Lucy | 50       |
      When Sean shouts
      Then Lucy should hear a shout

    Scenario: Listener is out of range
      Given the range is 100
      And people are located at
        | name     | Sean | Larry |
        | location | 0    | 150   |
#        | name  | location |
#        | Sean  | 0        |
#        | Larry | 150      |
      When Sean shouts
      Then Larry should not hear a shout


  Rule: Listener should be able to hear multiple shouts
    Scenario: Listener hears a different message
      Given A person named Sean
      And a person named Lucy
      When Sean shouts "free coffee!"
      And Sean shouts "And donuts!"
      Then Lucy hears the following messages
        | free coffee! |
        | And donuts!  |

  Rule: Maximum length of message is 100 characters
    Scenario: Message is too long
      Given A person named Sean
      And a person named Lucy
#      When Sean shouts "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890"
      When Sean shouts the following message
           """
           1234567890123456789
           012345678901234567890123456789
           01234567890123456789
           012345678901234567890123456789
           012345678901234567890"
           """
      Then Lucy should not hear a shout
