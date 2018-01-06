package de.pa2.project.audio.cube.client;

import java.io.IOException;

import de.pa2.commons.console.Command;
import de.pa2.commons.console.CommandResolver;
import de.pa2.commons.console.ConsoleReader;
import de.pa2.iot.MessageMapper;

public class Client {
	public static void main(String[] args) throws IOException, InterruptedException {
		// init mapper
		MessageMapper.getInstance().init(CommandInputMessage.class);

		// create and start console reader
		ConsoleReader reader = new ConsoleReader();
		reader.getCommandResolvers().add(new CommandResolver() {

			public Command resolve(String commandLine) {
				return new RemoteCommand(commandLine);
			}
		});
		reader.run();
	}
}
