package org.bismi.testng;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IExecutionListener;

public class ExecutionListener implements IExecutionListener {
	private Logger log = LogManager.getLogger(ExecutionListener.class);
	@Override
	public void onExecutionStart() {
		log.info("Starting execution listener ");
		
	}

	@Override
	public void onExecutionFinish() {
		log.info("Generating report ....... ");
		ReportUtility.GenerateCucumberReport();
		System.out.println("Generated report");
		
	}
}
