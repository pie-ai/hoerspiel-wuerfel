package de.pa2.projects.audio.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.pa2.commons.configuration.ConfigurationFactory;

public class VLCAudioController implements AudioController {
	private static final Logger LOG = LoggerFactory.getLogger(VLCAudioController.class);
	private Executor executor;
	private DefaultExecuteResultHandler resultHandler = null;
	LinkedList<ExecuteWatchdog> executorWatchDogs = new LinkedList<ExecuteWatchdog>();

	@Override
	public void init() throws IOException {
		VLCAudioControllerConfiguration configuration = ConfigurationFactory
				.getInstance(VLCAudioControllerConfiguration.class);

		CommandLine cmd = new CommandLine(configuration.getBinary());
		for (String parameter : configuration.getParameters().split(" ")) {
			cmd.addArgument(parameter.trim());
		}

		executor = new DefaultExecutor();
		executor.setExitValue(1);
		OutputStream out = new LogOutputStream() {

			@Override
			protected void processLine(String line, int logLevel) {
				LOG.debug("{}: {}", logLevel, line);
			}
		};

		resultHandler = new DefaultExecuteResultHandler();

		OutputStream err = out;
		InputStream in = null;
		
		String text = "help";
		ByteArrayInputStream stdin = new ByteArrayInputStream((text + "\n").getBytes("UTF-8"));
		in = stdin;
		
		executor.setStreamHandler(new PumpStreamHandler(out, err, in));

		ExecuteWatchdog watchdog = new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT);
		Executor executor = new DefaultExecutor();
		executor.setExitValue(1);
		executor.setWatchdog(watchdog);

		executor.execute(cmd, resultHandler);
		executorWatchDogs.add(watchdog);

	}

	public void test() throws IOException {
		String text = "help";
		ByteArrayInputStream stdin = new ByteArrayInputStream((text + "\n").getBytes("UTF-8"));
		executor.getStreamHandler().setProcessOutputStream(stdin);

	}

	public void quit() throws IOException, InterruptedException {
		String text = "help";
		ByteArrayInputStream stdin = new ByteArrayInputStream((text + "\n").getBytes("UTF-8"));
		executor.getStreamHandler().setProcessOutputStream(stdin);
		resultHandler.waitFor();
	}

}
