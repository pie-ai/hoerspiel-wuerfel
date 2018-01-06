package de.pa2.projects.audio.cube.commands;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.pa2.commons.console.Command;
import de.pa2.projects.audio.controller.AudioController;
import de.pa2.projects.audio.controller.AudioControllerFactory;

/**
 * command to play a playlist from a .m3u.lnk file
 */
public class PlayPlaylistCommand implements Command {
	private File playlist = null;
	private static final Logger LOG = LoggerFactory.getLogger(PlayPlaylistCommand.class);

	public PlayPlaylistCommand(File playlist) {
		super();
		this.playlist = playlist;
	}

	@Override
	public void execute(String... args) {
		AudioController audio = AudioControllerFactory.getAudioController();
		audio.stop();
		audio.clear();

		audio.play(playlist.getAbsolutePath());
	}

}
