package de.pa2.projects.audio.controller;

import de.pa2.commons.configuration.Configuration;
import de.pa2.commons.configuration.ConfigurationPrefix;
import de.pa2.commons.configuration.DefaultStringValue;

@ConfigurationPrefix("audio.controller.vlc")
public interface VLCAudioControllerConfiguration extends Configuration {
	@DefaultStringValue("vlc")
	String getBinary();
	
	@DefaultStringValue("-I rc")
	String getParameters();
}
