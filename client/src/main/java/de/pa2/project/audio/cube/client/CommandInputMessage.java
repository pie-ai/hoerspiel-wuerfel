package de.pa2.project.audio.cube.client;

import de.pa2.iot.AbstractMessage;

public class CommandInputMessage extends AbstractMessage {
	private String cmd;

	public CommandInputMessage() {
		super("CommandInputMessage");
	}

	public CommandInputMessage(String cmd) {
		super("CommandInputMessage");
		this.cmd = cmd;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

}
