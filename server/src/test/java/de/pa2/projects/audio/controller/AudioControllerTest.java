package de.pa2.projects.audio.controller;

import java.io.IOException;
import java.net.URISyntaxException;

public class AudioControllerTest {
	public static void main(String... args) throws IOException, InterruptedException, URISyntaxException {
		VLCHeadlessController controller = new VLCHeadlessController();
		controller.init();
		controller.test();
	}
}
