package de.pa2.projects.audio.cube.commands;

import java.io.File;
import java.io.FileFilter;

import de.pa2.commons.console.Command;
import de.pa2.projects.audio.controller.AudioController;
import de.pa2.projects.audio.controller.AudioControllerFactory;

/**
 * command to play content of a directory
 */
public class PlayCollectionCommand implements Command {
	private File directory = null;
	private FileFilter filter = new FileFilter() {

		@Override
		public boolean accept(File pathname) {
			return pathname.getAbsolutePath().endsWith(".mp3");
		}
	};

	public PlayCollectionCommand(File directory) {
		super();
		this.directory = directory;
	}

	@Override
	public void execute(String... args) {
		AudioController audio = AudioControllerFactory.getAudioController();
		audio.stop();
		audio.clear();
		File[] files = directory.listFiles(filter);
		for (int i = 0; i < files.length; i++) {
			if (i == 0) {
				audio.play(files[i].getAbsolutePath());
			} else {
				audio.enqueue(files[i].getAbsolutePath());
			}
		}
	}

}
