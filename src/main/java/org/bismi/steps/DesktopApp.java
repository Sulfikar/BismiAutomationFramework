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



public class DesktopApp {
	
private Scenario desktopScenario;
	
	@Before
	public void beforeScenario(Scenario scenario) {
		this.desktopScenario=scenario;
	}
	@Given("I launch notepad")
	public void i_launch_notepad() {
	 System.out.println(" ----> Launching Notepad -----> ");
	 desktopScenario.embed(CommonUtil.getScreenshotAsByte(), "image/png");
	}

	@When("I enter some data")
	public void i_enter_some_data() {
	 
	    
	}

	@Then("I validate entered text")
	public void i_validate_entered_text() {
	 
	    
	}
}
