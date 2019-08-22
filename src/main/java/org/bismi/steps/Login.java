package org.bismi.steps;
/**
 * @author Sulfikar Ali Nazar -
 * sulfikar@outlook.com
 *  BismiAutomationFramework - https://bismi.solutions
 */

import org.bismi.util.CommonUtil;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.*;



public class Login {

	private Scenario loginScenario;
	
	@Before
	public void beforeScenario(Scenario scenario) {
		this.loginScenario=scenario;
	}
	
	@Given("I Launch critical application")
	public void i_Launch_critical_application() {
		System.out.println(" -------------------------------> ");
		System.out.println(" -------------------------------> ");
		System.out.println(" -------------------------------> ");
		System.out.println(" -------------------------------> ");
		System.out.println("I login to critical application >"+0/8);
		System.out.println(" -------------------------------> ");
		System.out.println(" -------------------------------> ");
		System.out.println(" -------------------------------> ");
		System.out.println(" -------------------------------> ");
		loginScenario.write("This is a write statement");
		loginScenario.embed(CommonUtil.getScreenshotAsByte(), "image/png");
		loginScenario.write("This is a second  statement");
		loginScenario.embed(CommonUtil.getScreenshotAsByte(), "image/png");
	}

	@When("I see login page")
	public void i_see_login_page() {
		System.out.println(" -------------------------------> ");
	}

	@When("I enter valid credentials")
	public void i_enter_valid_credentials() {
		
		loginScenario.embed(CommonUtil.getScreenshotAsByte(), "image/png");
	}

	@Then("should be navigated to home page")
	public void should_be_navigated_to_home_page() {

	}

}
