package de.pa2.commons.console;

import de.pa2.iot.Message;
import de.pa2.iot.MessageMapper;
import de.pa2.project.audio.cube.client.CommandInputMessage;

public class UdpReaderTest {
	public static void main(String... args) {
		MessageMapper.getInstance().init(CommandInputMessage.class);

		UdpReader reader = new UdpReader(33333) {
			@Override
			protected String extractCommand(String data) {
				Message message = MessageMapper.getInstance().deserialize(data);
				if (message instanceof CommandInputMessage) {
					return ((CommandInputMessage) message).getCmd();
				}
				return "";
			}
		};
		reader.run();
	}
}
