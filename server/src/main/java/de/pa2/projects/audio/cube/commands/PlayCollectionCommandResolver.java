package de.pa2.projects.audio.cube.commands;

import java.io.File;
import java.io.FilenameFilter;

import de.pa2.commons.configuration.ConfigurationFactory;
import de.pa2.commons.console.Command;
import de.pa2.commons.console.CommandResolver;
import de.pa2.projects.audio.cube.ServerConfiguration;

/**
 * command resolver that checks if there is a directory that allows playback as
 * a collection of songs
 */
public class PlayCollectionCommandResolver implements CommandResolver {

	private File getAudioDirectory(final String identifier) {
		String baseDirectory = ConfigurationFactory.getInstance(ServerConfiguration.class).getAudioArchive();
		File dir = new File(baseDirectory);
		if (dir.exists() && dir.isDirectory()) {
			String[] candidates = dir.list(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.contains(identifier) && new File(dir, name).isDirectory();
				}
			});

			if ((candidates != null) && (candidates.length > 0)) {
				return new File(baseDirectory, candidates[0]);
			}
		}
		return null;
	}

	@Override
	public Command resolve(String commandLine) {
		String[] parts = commandLine.trim().split(" ");
		File audioDirectory = this.getAudioDirectory(parts[0]);
		if (audioDirectory != null) {
			return new PlayCollectionCommand(audioDirectory);
		} else {

			return null;
		}
	}

}
