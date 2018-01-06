package de.pa2.project.audio.cube.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import de.pa2.commons.configuration.ConfigurationFactory;
import de.pa2.commons.console.Command;
import de.pa2.iot.MessageMapper;

public class RemoteCommand implements Command {
	private String command;

	public RemoteCommand(String command) {
		super();
		this.command = command;
	}

	public void execute(String... args) {
		ClientConfiguration config = ConfigurationFactory.getInstance(ClientConfiguration.class);
		try {
			InetAddress address = InetAddress.getByName(config.getAddress());
			int port = config.getPort();
			StringBuilder commandLine = new StringBuilder(command);
			for (int i = 0; i < args.length; i++) {
				commandLine.append(" ");
				commandLine.append(args[i]);
			}
			CommandInputMessage command = new CommandInputMessage(commandLine.toString());
			StringBuilder message = new StringBuilder();
			message.append(MessageMapper.getInstance().serialize(command));
			DatagramPacket packet = new DatagramPacket(message.toString().getBytes(), message.length(), address, port);
			DatagramSocket datagramSocket = new DatagramSocket();
			datagramSocket.send(packet);

			// TODO feedback is missing

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
