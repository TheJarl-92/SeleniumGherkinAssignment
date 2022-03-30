Feature: Create a user

  Scenario Outline: Try to create a user
    Given I use "<browser>" as browser
    Given I write in "<email>" in email
    Given I write in "<userName>" in username
    Given I write in "<passWord>" in password
    When I click the sign up button
    Then A message saying "<message>" appears

    #Message table
    #Success - This is a proper new user created
    #noEmail - Tries to create a user without an email
    #longUser - Tries to create a user with a userName above 100 characters
    #blockUser - Tries to create a user with a userName that's already in use

    #email - Be sure to use "gherkinTester" when creating several proper new users to not get an error message about email already in use
    #Also be sure to use something that looks like a proper email if you're not using "gherkinTester" or else the test will fail

    #userName - Be sure to use "gherkinTest" when creating several proper new users to not get an error message about user already in use
    #If you want an original username be sure to not use any symbols or odd characters because the site won't accept those for a username

    #Password - Be sure to use capital letters, small letters, numbers, symbols and at least 8 characters long. Otherwise the test will also fail
    #It will fail because you can't even click the sign up button if the password is too weak

    Examples:
      | browser | email                          | userName          | passWord | message   |
      | chrome  | gherkinTester                  | gherkinTest       | T3sting! | Success   |
      | edge    |                                | LoadOfHoopla      | T3sting! | noEmail   |
      | chrome  | TestingThisInGherkin@gmail.com | tooLongUserName   | T3sting! | longUser  |
      | chrome  | 2testingInGherkin@gmail.com    | TestingssonTester | T3sting! | blockUser |
      | edge    | gherkinTester                  | gherkinTest       | t00W34k? | success   |
      | edge    | hehehehe@hotmail.com           | admin             | i8Klal!0 | blockuser |


  #Saved the old ones to use as backup or check some values

  #Background:
    #Given I use "chrome" as browser

  #Scenario: Create a normal user
    #Given I write in "2testingInGherkin@gmail.com" in email
    #Given I write in "gherkinTest" in username
    #Given I write in "T3sting!" in password
    #When I click the sign up button
    #Then A message saying "Success | Mailchimp" appears

  #Scenario: Create user without email
    #Given I write in "" in email
    #* I write in "gherkinTest" in username
    #* I write in "T3sting!" in password
    #When I click the sign up button
    #Then An error message saying "Please enter a value" appears

  #Scenario: Create a user with long username
    #Given I write in "2testingInGherkin@gmail.com" in email
    #* I write in "tooLongUserName" in username
    #* I write in "T3sting!" in password
    #When I click the sign up button
    #Then An error message saying "Enter a value less than 100 characters long" appears

  #Scenario: Create a user but the username already exists
    #Given I write in "2testingInGherkin@gmail.com" in email
    #* I write in "TestingssonTester" in username
    #* I write in "T3sting!" in password
    #When I click the sign up button
    #Then An error message saying "Another user with this username already exists. Maybe it's your evil twin. Spooky." appears
