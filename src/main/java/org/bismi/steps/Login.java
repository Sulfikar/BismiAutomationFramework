package org.bismi.steps;

import io.cucumber.java.en.*;

public class Login {

	@Given("I Launch critical application")
	public void i_Launch_critical_application() {
		System.out.println(" -------------------------------> ");
		System.out.println(" -------------------------------> ");
		System.out.println(" -------------------------------> ");
		System.out.println(" -------------------------------> ");
		System.out.println("I login to critical application >");
		System.out.println(" -------------------------------> ");
		System.out.println(" -------------------------------> ");
		System.out.println(" -------------------------------> ");
		System.out.println(" -------------------------------> ");
	}

	@When("I see login page")
	public void i_see_login_page() {
		System.out.println(" -------------------------------> ");
	}

	@When("I enter valid credentials")
	public void i_enter_valid_credentials() {

	}

	@Then("should be navigated to home page")
	public void should_be_navigated_to_home_page() {

	}

}
