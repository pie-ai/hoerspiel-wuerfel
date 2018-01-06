package de.pa2.projects.audio.controller;

import java.io.IOException;

public interface AudioController {
	void init() throws IOException;

	void play(String item);

	void enqueue(String item);

	void stop();

	void pause();

	void shutdown();

	void clear();
}
