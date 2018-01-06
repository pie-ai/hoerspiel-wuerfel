package de.pa2.projects.audio.cube;

import de.pa2.commons.configuration.Configuration;
import de.pa2.commons.configuration.ConfigurationPrefix;
import de.pa2.commons.configuration.DefaultStringValue;

/**
 * Configuration Singleton
 */
@ConfigurationPrefix("server")
public interface ServerConfiguration extends Configuration{

//	private static final String PROPERTY_SERVER = "server";
//	/**
//	 * server to connect to
//	 *
//	 * @return
//	 */
//	public String getServer() {
//		return this.getString(PROPERTY_SERVER, DEFAULT_SERVER);
//	}

//	private static final int DEFAULT_PORT = 6600;
//
//	/**
//	 * port of server to connect to
//	 *
//	 * @return
//	 */
//	public int getPort() {
//		return this.getInt(PROPERTY_PORT, DEFAULT_PORT);
//	}

	/**
	 * archive directory to find audio
	 * 
	 * @return
	 */
	@DefaultStringValue("archive")
	public String getAudioArchive();

}
