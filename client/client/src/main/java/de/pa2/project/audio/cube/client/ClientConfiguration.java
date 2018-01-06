package de.pa2.project.audio.cube.client;

import de.pa2.commons.configuration.Configuration;
import de.pa2.commons.configuration.ConfigurationPrefix;
import de.pa2.commons.configuration.DefaultIntValue;
import de.pa2.commons.configuration.DefaultStringValue;

@ConfigurationPrefix("client")
public interface ClientConfiguration extends Configuration {

	/**
	 * ip address of the server
	 * 
	 * @return
	 */
	@DefaultStringValue("127.0.0.1")
	String getAddress();

	/**
	 * port the server listens to
	 * 
	 * @return
	 */
	@DefaultIntValue(33333)
	int getPort();
}
