package org.bismi.testng;
/**
 * @author Sulfikar Ali Nazar -
 * sulfikar@outlook.com
 *  BismiAutomationFramework - https://bismi.solutions
 */
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cucumber.api.event.Event;
import cucumber.api.event.EventHandler;
import cucumber.runner.EventBus;
import cucumber.runner.Runner;
import cucumber.runner.RunnerSupplier;
import cucumber.runner.SingletonRunnerSupplier;
import cucumber.runner.ThreadLocalRunnerSupplier;
import cucumber.runner.TimeService;
import cucumber.runner.TimeServiceEventBus;
import cucumber.runtime.BackendModuleBackendSupplier;
import cucumber.runtime.BackendSupplier;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.FeaturePathFeatureSupplier;
import cucumber.runtime.Runtime;
import cucumber.runtime.FeatureSupplier;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;

import cucumber.runtime.filter.Filters;
import cucumber.runtime.formatter.PluginFactory;
import cucumber.runtime.formatter.Plugins;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.model.FeatureLoader;
import cucumber.runtime.order.PickleOrder;
import io.cucumber.core.options.FilterOptions;
import io.cucumber.core.options.PluginOptions;

public class DynamicTestRunner {

	private Logger log = LogManager.getLogger(DynamicTestRunner.class);
	ClassLoader classLoader = getClass().getClassLoader();
	ResourceLoader resourceLoader = new MultiLoader(classLoader);
	RuntimeOptionsFactory runtimeOptionsFactory = new RuntimeOptionsFactory(getClass());
	RuntimeOptions runtimeOptions = runtimeOptionsFactory.create();
	ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
	EventBus bus = new TimeServiceEventBus(TimeService.SYSTEM);

	public void dynamicRun() {
		try {

			setFeatureFileLocation();
			setStepDefenision();// Need to set packages in this location
			List<String> filTags = new ArrayList<String>();
			filTags.add("@DataCreation");
			setTagFilters(filTags);
			Plugins plugins = getPlugins();
			Filters filters = new Filters(runtimeOptions);
			FeatureSupplier featureSupplier = getFeatureSupplier();
			PickleOrder pickleOrder = runtimeOptions.getPickleOrder();
			RunnerSupplier runnerSupplier = getRunnerSupplier();
			ExecutorService executor = getExecutorService();
			Runtime runtime = new Runtime(plugins, runtimeOptions, bus, filters, runnerSupplier, featureSupplier,
					executor, pickleOrder);
			runtime.run();

		} catch (Exception e) {
			log.info("Error in module " + e.toString());

		}

	}

	private void setTagFilters(List<String> filTags) {
		for (String string : filTags) {
			// runtimeOptions.getTagFilters().add("@DataCreation");
			runtimeOptions.getTagFilters().add(string);
		}

	}

	private void setFeatureFileLocation() {

		try {
			runtimeOptions.getFeaturePaths1().clear();
			runtimeOptions.getFeaturePaths1().add(new URI("classpath:org/bismi/scenarios"));
			// add more paths here
		} catch (URISyntaxException e) {
			log.info("Error in setting feature file path");
		}
	}

	private void setStepDefenision() {
		List<URI> stepdefinitionList = new ArrayList<URI>();
		try {
			stepdefinitionList.add(new URI("classpath:org/bismi/steps"));
			runtimeOptions.getGlue().clear();
			runtimeOptions.getGlue().addAll(stepdefinitionList);
		} catch (URISyntaxException e) {

			log.info("Error in adding step defenision " + e.toString());
		}

	}

	private List<String> getReportFormats() {
		String[] formatArrays = new String[] { "html:target/cucumber/html-report",
				"json:target/cucumber/json-report/cucumber.json",
				"testng:target/cucumber/testng-report/cucumber.xml",
				"rerun:target/cucumber/failed-report/failed,rerun.txt" };
		List<String> reportformatList = Arrays.asList(formatArrays);
		return reportformatList;
	}

	private Plugins getPlugins() {
		PluginFactory pluginFactory = new PluginFactory();
		PluginOptions pluginOptions = new PluginOptions() {

			@Override
			public boolean isStrict() {

				return false;
			}

			@Override
			public boolean isMonochrome() {

				return false;
			}

			// [progress, default_summary]
			@Override
			public List<String> getPluginNames() {

				return getReportFormats();
			}
		};
		Plugins plugins1 = new Plugins(classLoader, pluginFactory, pluginOptions);
		return plugins1;
	}

	private FeatureSupplier getFeatureSupplier() {
		FeatureLoader featureLoader = new FeatureLoader(resourceLoader);
		FeatureSupplier featureSupplier = new FeaturePathFeatureSupplier(featureLoader, runtimeOptions);
		return featureSupplier;
	}

	private RunnerSupplier getRunnerSupplier() {
		BackendSupplier backendSupplier = new BackendModuleBackendSupplier(resourceLoader, classFinder, runtimeOptions);
		RunnerSupplier runnerSupplier = runtimeOptions.isMultiThreaded()
				? new ThreadLocalRunnerSupplier(runtimeOptions, bus, backendSupplier)
				: new SingletonRunnerSupplier(runtimeOptions, bus, backendSupplier);
		return runnerSupplier;
	}

	private ExecutorService getExecutorService() {
		ExecutorService executor = runtimeOptions.isMultiThreaded()
				? Executors.newFixedThreadPool(runtimeOptions.getThreads(), new CucumberThreadFactory())
				: new SameThreadExecutorService();
		return executor;
	}

	private static final class CucumberThreadFactory implements ThreadFactory {

		private static final AtomicInteger poolNumber = new AtomicInteger(1);
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final String namePrefix;

		CucumberThreadFactory() {
			this.namePrefix = "cucumber-runner-" + poolNumber.getAndIncrement() + "-thread-";
		}

		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, namePrefix + this.threadNumber.getAndIncrement());
		}
	}

	private static final class SameThreadExecutorService extends AbstractExecutorService {

		@Override
		public void execute(Runnable command) {
			command.run();
		}

		@Override
		public void shutdown() {
			// no-op
		}

		@Override
		public List<Runnable> shutdownNow() {
			return Collections.emptyList();
		}

		@Override
		public boolean isShutdown() {
			return true;
		}

		@Override
		public boolean isTerminated() {
			return true;
		}

		@Override
		public boolean awaitTermination(long timeout, TimeUnit unit) {
			return true;
		}
	}

}
