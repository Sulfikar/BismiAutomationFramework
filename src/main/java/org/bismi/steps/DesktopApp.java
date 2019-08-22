package org.bismi.steps;

import org.bismi.util.CommonUtil;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;

public class DesktopApp {
	
private Scenario desktopScenario;
	
	@Before
	public void beforeScenario(Scenario scenario) {
		this.desktopScenario=scenario;
	}
	@Given("I launch notepad")
	public void i_launch_notepad() {
	 System.out.println(" ----> Launching Notepad -----> ");
	 desktopScenario.embed(CommonUtil.getScreenshotAsByte(), "image/png","Sulfikarsdata");
	}

	@When("I enter some data")
	public void i_enter_some_data() {
	 
	    
	}

	@Then("I validate entered text")
	public void i_validate_entered_text() {
	 
	    
	}
}
