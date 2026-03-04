Feature: Any user can use sauce demo website to test 

Scenario: User wants to test login and logout scenario
 
Given user is on the login page

When user enter username
And user eneter password
And user click on login button
Then user should be able to login and navigate to product page
When user click on logout
Then user should be able to logout and come back to login page