package de.pa2.projects.audio.cube.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.pa2.commons.console.Command;
import de.pa2.projects.audio.controller.AudioController;
import de.pa2.projects.audio.controller.AudioControllerFactory;

/**
 * command to play a playlist which is stored remotely using a custom .m3u.lnk
 * file that only contains the remote link of the playlist like:
 * http://www.wdr.de/wdrlive/media/kiraka.m3u
 */
public class PlayRemotePlaylistCommand implements Command {
	private File playlist = null;
	private static final Logger LOG = LoggerFactory.getLogger(PlayRemotePlaylistCommand.class);

	public PlayRemotePlaylistCommand(File playlist) {
		super();
		this.playlist = playlist;
	}

	public static List<String> read(String targetURL) throws IOException {
		List<String> result = new LinkedList<>();
		URL url = new URL(targetURL);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));

		String inputLine;
		while ((inputLine = bufferedReader.readLine()) != null) {
			result.add(inputLine);
		}

		bufferedReader.close();
		return result;
	}

	@Override
	public void execute(String... args) {
		AudioController audio = AudioControllerFactory.getAudioController();
		audio.stop();
		audio.clear();

		List<String> lines;
		String url = null;
		try {
			lines = Files.readAllLines(Paths.get(playlist.getPath()), Charset.forName("UTF8"));
			for (String line : lines) {
				if (line != null && line.trim().length() > 0 && !line.startsWith("#")) {
					url = line;
				}
			}
		} catch (IOException e) {
			LOG.error("could not read remote playlist: {}", e.getMessage(), e);
		}

		if (url != null) {
			try {
				List<String> content = read(url);
				boolean played = false;
				for (String entry : content) {
					if (!entry.startsWith("#")) {
						if (!played) {
							audio.play(entry);
							played = true;
						} else {
							audio.enqueue(entry);
						}
					}
				}
			} catch (IOException e) {
				LOG.error("could not read playlist: {}", e.getMessage(), e);
			}

		} else {
			LOG.error("could not find a valid url in playlist: {}", playlist.getAbsolutePath());
		}
	}

}
