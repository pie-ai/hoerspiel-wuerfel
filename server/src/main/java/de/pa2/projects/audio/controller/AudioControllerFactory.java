package de.pa2.projects.audio.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.pa2.commons.configuration.ConfigurationFactory;

public class AudioControllerFactory {
	private static AudioController instance = null;
	private static final Logger LOG = LoggerFactory.getLogger(AudioControllerFactory.class);

	private static synchronized void init() {
		if (instance != null) {
			return;
		}
		AudioControllerFactoryConfiguration configuration = ConfigurationFactory
				.getInstance(AudioControllerFactoryConfiguration.class);
		try {
			AudioController tmp = (AudioController) Class.forName(configuration.getImplementation()).newInstance();
			tmp.init();
			instance = tmp;
		} catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			LOG.error("could not instantiate audio controller: {}", e.getMessage(), e);
		}
	}

	public static AudioController getAudioController() {

		if (instance == null) {
			init();
		}

		return instance;
	}
}
