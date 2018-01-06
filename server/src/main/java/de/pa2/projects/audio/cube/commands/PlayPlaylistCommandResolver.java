package de.pa2.projects.audio.cube.commands;

import java.io.File;
import java.io.FilenameFilter;

import de.pa2.commons.configuration.ConfigurationFactory;
import de.pa2.commons.console.Command;
import de.pa2.commons.console.CommandResolver;
import de.pa2.projects.audio.cube.ServerConfiguration;

/**
 * command resolver that looks up .m3u and .m3u.lnk files and returns play
 * commands if found for the command identifier
 */
public class PlayPlaylistCommandResolver implements CommandResolver {
	private File getPlaylist(final String identifier) {
		String baseDirectory = ConfigurationFactory.getInstance(ServerConfiguration.class).getAudioArchive();
		File dir = new File(baseDirectory);
		if (dir.exists() && dir.isDirectory()) {
			String[] candidates = dir.list(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.contains(identifier) && new File(dir, name).isFile();
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
		File audioFile = this.getPlaylist(parts[0]);
		if (audioFile != null) {
			if (audioFile.getAbsolutePath().endsWith(".lnk")) {
				return new PlayRemotePlaylistCommand(audioFile);
			} else {
				return new PlayPlaylistCommand(audioFile);
			}

		} else {

			return null;
		}
	}

}
