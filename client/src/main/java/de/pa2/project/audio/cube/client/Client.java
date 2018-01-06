package de.pa2.project.audio.cube.client;

import java.io.IOException;

import de.pa2.commons.console.Command;
import de.pa2.commons.console.CommandResolver;
import de.pa2.commons.console.ConsoleReader;
import de.pa2.iot.MessageMapper;

public class Client {
	public static void main(String[] args) throws IOException, InterruptedException {
		
		// clear screen
		// https://stackoverflow.com/questions/2979383/java-clear-the-console
	    System.out.print("\033[H\033[2J");
	    
	    // print welcome
	    System.out.println("audio cube client shell");
		
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
