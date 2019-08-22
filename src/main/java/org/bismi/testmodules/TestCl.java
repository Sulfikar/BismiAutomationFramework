package org.bismi.testmodules;
/**
 * @author Sulfikar Ali Nazar -
 * sulfikar@outlook.com
 *  BismiAutomationFramework - https://bismi.solutions
 */
import org.bismi.testng.DynamicTestRunner;
import org.bismi.util.CommonUtil;

public class TestCl {

	public static void main(String[] args) {
		String fi="D:\\cucumberreport\\report1.png";
		DynamicTestRunner dr = new DynamicTestRunner();
		
		dr.dynamicRun();

	}

}
