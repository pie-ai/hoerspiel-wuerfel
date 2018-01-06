package de.pa2.projects.audio.controller;

import de.pa2.commons.configuration.Configuration;
import de.pa2.commons.configuration.ConfigurationPrefix;
import de.pa2.commons.configuration.DefaultStringValue;

@ConfigurationPrefix("audio.controller")
public interface AudioControllerFactoryConfiguration extends Configuration{
	@DefaultStringValue("de.pa2.projects.audio.controller.VLCHeadlessController")
	String getImplementation();
}
