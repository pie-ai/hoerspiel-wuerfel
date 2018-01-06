package de.pa2.projects.audio.cube;

import java.io.File;
import java.io.IOException;

import de.pa2.commons.console.AbstractReader;
import de.pa2.commons.console.ConsoleReader;
import de.pa2.commons.console.commands.ExitCommand;
import de.pa2.projects.audio.controller.AudioControllerFactory;
import de.pa2.projects.audio.cube.commands.PlayCollectionCommandResolver;
import de.pa2.projects.audio.cube.commands.PlayPlaylistCommandResolver;

/**
 * cli main class
 */
public class CommandLineInterface {
	public static void main(String... args) throws IOException, InterruptedException {
		AbstractReader reader = new ConsoleReader();
				// new FifoReader(new File("fifo"));
		// new ConsoleReader();

		// register commands and command resolvers
		for (String command : ExitCommand.COMMAND_ALTERNATIVES) {
			reader.getCommandHandler().put(command, new ExitCommand());
		}
		reader.getCommandResolvers().add(new PlayCollectionCommandResolver());
		reader.getCommandResolvers().add(new PlayPlaylistCommandResolver());

		/* setup audio controller */
		
		// fix PATH for my raspberry pi zero w
		if (new File("/usr/lib/arm-linux-gnueabihf/").exists())
		{
			String path = System.getProperty("PATH") + ":" + "/usr/lib/arm-linux-gnueabihf/";
			System.setProperty("PATH", path);
		}
		AudioControllerFactory.getAudioController();

		reader.run();
	}

	private CommandLineInterface() {
		super();

	}
}
