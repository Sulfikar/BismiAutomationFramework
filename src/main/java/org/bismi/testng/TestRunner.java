package org.bismi.testng;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IExecutionListener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.cucumber.testng.CucumberFeatureWrapper;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.PickleEventWrapper;
import io.cucumber.testng.TestNGCucumberRunner;

@CucumberOptions(features = "src/main/scenarios", tags = {"@DataCreation"}, plugin = "json:bin/target/LoginFunctionality.json",glue = { "org.bismi.steps" })
public class TestRunner{
	private Logger log = LogManager.getLogger(TestRunner.class);
	private TestNGCucumberRunner testNGCucumberRunner;

	@BeforeClass(alwaysRun = true)
	public void setUpClass() {
		try {
			log.info("Setting class " + TestRunner.class.getName());
			testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
		} catch (Exception e) {
			log.error("Error in class set up " + TestRunner.class.getName());
		}
	}

	@DataProvider
	public Object[][] scenarios() {
		return testNGCucumberRunner.provideScenarios();
	}

	@Test(groups = "cucumber scenarios", description = "Run Cucumber Scenarios", dataProvider = "scenarios")
	public void scenario(PickleEventWrapper pickleEvent, CucumberFeatureWrapper cucumberFeature) throws Throwable {
		testNGCucumberRunner.runScenario(pickleEvent.getPickleEvent());
		
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() throws Exception {
		testNGCucumberRunner.finish();
		 
	}

	
}
